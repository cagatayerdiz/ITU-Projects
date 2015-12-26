#include <linux/syscalls.h>
#include <linux/kernel.h>
#include <linux/sched.h>
#include <linux/errno.h>
#include <linux/pid_namespace.h>
//#include <stdlib.h>
//#include <unistd.h>
#include <asm-generic/unistd.h>
//#include <sys/types.h>
#include <linux/linkage.h>
#include <linux/cred.h>
#include <asm-generic/current.h>
#include <linux/uidgid.h>

asmlinkage long set_casper(pid_t pid, int value){
	
	if(current_uid().val != 0)
		return -EPERM;
	/*
     if(getuid() != 0) // bu kodu işleyen prosesin uid'si root değilse
		return -EPERM;
     */
     struct task_struct *task = find_task_by_vpid(pid);
	
	 if(task == NULL)
		return -ESRCH;
	
	 if(value < 0 || value > 3)
		return -EINVAL;
		
     task->casper = value;
     return 0;
}
