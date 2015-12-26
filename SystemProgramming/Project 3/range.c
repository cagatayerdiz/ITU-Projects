#include <linux/module.h>
#include <linux/moduleparam.h>
#include <linux/init.h>
#include <linux/kernel.h>	/* printk() */
#include <linux/slab.h>		/* kmalloc() */
#include <linux/fs.h>		/* everything... */
#include <linux/errno.h>	/* error codes */
#include <linux/types.h>	/* size_t */
#include <linux/proc_fs.h>
#include <linux/fcntl.h>	/* O_ACCMODE */
#include <linux/cdev.h>
#include <asm/switch_to.h>	/* cli(), *_flags */
#include <asm/uaccess.h>	/* copy_*_user */

#include "range_ioctl.h"
         
#define RANGE_NR_DEVS 4
#define ROTATE_MODE 0
#define EOF_MODE 1

int range_major;
int range_minor = 0;
int range_nr_devs = RANGE_NR_DEVS;
int range_stepSize = 1;
int range_startValue = 0;
int range_endValue = 10;
int range_mode = ROTATE_MODE;

module_param(range_major, int, S_IRUGO);
module_param(range_minor, int, S_IRUGO);
module_param(range_nr_devs, int, S_IRUGO);
module_param(range_startValue, int, S_IRUGO);
module_param(range_endValue, int, S_IRUGO);
module_param(range_stepSize, int, S_IRUGO);
module_param(range_mode, int, S_IRUGO);

MODULE_AUTHOR("Ahmet Gozuberk,  Cagatay Erdiz, Duran Eren");    
MODULE_LICENSE("Dual BSD/GPL");

struct range_dev {
	int currentValue;   
    int startValue;
    int stepSize;   
    int endValue;  
    int mode;
    struct cdev cdev;
};

struct range_dev *range_devices;

int range_open(struct inode *inode, struct file *filp)
{
    struct range_dev *dev;
    dev = container_of(inode->i_cdev, struct range_dev, cdev);
    filp->private_data = dev;
    return 0;
}

int range_release(struct inode *inode, struct file *filp)
{
    return 0;
}

ssize_t range_read(struct file *filp, char __user *buf, size_t count,
                   loff_t *f_pos)
{
	int value;
    struct range_dev *dev = filp->private_data;
	
	if(dev->mode == ROTATE_MODE){
		if(dev->stepSize > 0 && dev->endValue < dev->currentValue)
			dev->currentValue = dev->startValue;
		else if(dev->stepSize < 0 && dev->endValue > dev->currentValue)
			dev->currentValue = dev->startValue;
	}
	else{
		if(dev->stepSize > 0 && dev->endValue < dev->currentValue){
			printk(KERN_WARNING "End of file!!!\n");
			return 0;
		}
		else if(dev->stepSize < 0 && dev->endValue > dev->currentValue){
			printk(KERN_WARNING "End of file!!!\n");
			return 0;
		}
	}
	value = dev->currentValue;
	if(copy_to_user(buf, &value, 4)){
		return -1;
	}
	dev->currentValue = dev->currentValue + dev->stepSize;
	return 4;
}

ssize_t range_write(struct file *filp, const char __user *buf, size_t count, loff_t *f_pos)
{
	printk(KERN_WARNING "Write operation is not supported.\n");
	return -EOPNOTSUPP;
}

long range_ioctl(struct file *filp, unsigned int cmd, unsigned long arg)
{	
	int err = 0;
	int retval = 0;
	struct range_dev *dev = filp->private_data;

	if (_IOC_TYPE(cmd) != RANGE_IOC_MAGIC) return -ENOTTY;
	if (_IOC_NR(cmd) > RANGE_IOC_MAXNR) return -ENOTTY;

	if (_IOC_DIR(cmd) & _IOC_READ)
		err = !access_ok(VERIFY_WRITE, (void __user *)arg, _IOC_SIZE(cmd));
	else if (_IOC_DIR(cmd) & _IOC_WRITE)
		err =  !access_ok(VERIFY_READ, (void __user *)arg, _IOC_SIZE(cmd));
	if (err) return -EFAULT;
	
	switch(cmd){
		case RANGE_GET_STEP_SIZE:
			retval = __put_user(dev->stepSize, (int __user *)arg);
			break;
		case RANGE_SET_STEP_SIZE:
			if (! capable (CAP_SYS_ADMIN))
				return -EPERM;
			retval = __get_user(dev->stepSize, (int __user *)arg);
			break;
		case RANGE_GET_COUNTER:
			retval = __put_user(dev->currentValue, (int __user *)arg);
			break;
		case RANGE_SET_COUNTER:
			if (! capable (CAP_SYS_ADMIN))
				return -EPERM;
			retval = __get_user(dev->currentValue, (int __user *)arg);
			break;
		case RANGE_SET_ROTATE_MODE:
			if (! capable (CAP_SYS_ADMIN))
				return -EPERM;
			dev->mode = ROTATE_MODE;
			break;	
		case RANGE_SET_EOF_MODE:
			if (! capable (CAP_SYS_ADMIN))
				return -EPERM;
			dev->mode = EOF_MODE;
			break;
		case RANGE_SET_START_VALUE:
			if (! capable (CAP_SYS_ADMIN))
				return -EPERM;
			retval = __get_user(dev->startValue, (int __user *)arg);
			break;
		case RANGE_SET_END_VALUE:
			if (! capable (CAP_SYS_ADMIN))
				return -EPERM;
			retval = __get_user(dev->endValue, (int __user *)arg);
			break;
		default:
			return -ENOTTY;
	}
	return retval;
}

loff_t range_llseek(struct file *filp, loff_t off, int whence)
{
	printk(KERN_WARNING "llseek operation is not supported.\n");
	return -EOPNOTSUPP;
}

struct file_operations range_fops = {
    .owner =    THIS_MODULE,
    .llseek = range_llseek,
    .read =     range_read,
    .write = range_write,
    .unlocked_ioctl = range_ioctl,
    .open =     range_open,
    .release =  range_release,
};

void range_cleanup_module(void)
{
    int i;
    dev_t devno = MKDEV(range_major, range_minor);

    if (range_devices) {
        for (i = 0; i < range_nr_devs; i++) {
            cdev_del(&range_devices[i].cdev);
        }
    kfree(range_devices);
    }
    unregister_chrdev_region(devno, range_nr_devs);
}

int range_init_module(void)
{
    int result, i;
    int err;
    dev_t devno = 0;
    struct range_dev *dev;

    result = alloc_chrdev_region(&devno, range_minor, range_nr_devs,"range");
    range_major = MAJOR(devno);
    if (result < 0) {
        printk(KERN_WARNING "range: can't get major id.\n");
        return result;
    }

    range_devices = kmalloc(range_nr_devs * sizeof(struct range_dev),
                            GFP_KERNEL);
    if (!range_devices) {
        result = -ENOMEM;
        goto fail;
    }
    memset(range_devices, 0, range_nr_devs * sizeof(struct range_dev));

    if(range_stepSize == 0 || range_endValue == range_startValue){
		range_stepSize = 1;
		range_startValue = 0;
		range_endValue =10;
		range_mode = ROTATE_MODE;
		printk(KERN_NOTICE "Warning: Default Values are Taken.\n");
	}
    else if(abs(range_endValue - range_startValue) < abs(range_stepSize)){
		range_stepSize = 1;
		range_startValue = 0;
		range_endValue =10;
		range_mode = ROTATE_MODE;
		printk(KERN_NOTICE "Warning: Default Values are Taken.\n");
	}
    else if(range_endValue > range_startValue && range_stepSize < 0){
		range_stepSize = 1;
		range_startValue = 0;
		range_endValue =10;
		range_mode = ROTATE_MODE;
		printk(KERN_NOTICE "Warning: Default Values are Taken.\n");
	}
    else if(range_endValue < range_startValue && range_stepSize > 0){
		range_stepSize = 1;
		range_startValue = 0;
		range_endValue =10;
		range_mode = ROTATE_MODE;
		printk(KERN_NOTICE "Warning: Default Values are Taken.\n");
	}	
    /* Initialize each device. */
    for (i = 0; i < range_nr_devs; i++) {
        dev = &range_devices[i];
        devno = MKDEV(range_major, range_minor + i);
        cdev_init(&dev->cdev, &range_fops);
        dev->cdev.owner = THIS_MODULE;
        dev->cdev.ops = &range_fops;
        dev->startValue = range_startValue;
		dev->endValue = range_endValue;
		dev->stepSize = range_stepSize;
		dev->mode = range_mode;
        dev->currentValue = range_startValue; 
        err = cdev_add(&dev->cdev, devno, 1);
        if (err)
            printk(KERN_NOTICE "Error %d adding range%d", err, i);
    }

    return 0; /* succeed */

  fail:
    range_cleanup_module();
    return result;
}

module_init(range_init_module);
module_exit(range_cleanup_module);
