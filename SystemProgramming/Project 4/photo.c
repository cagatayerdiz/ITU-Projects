/*
 *
 * Compile: gcc photo.c -o photo -Wall -ansi -W -std=c99 -g -ggdb -D_GNU_SOURCE -D_FILE_OFFSET_BITS=64 -lfuse 
 * Mount: photo readwrite_filesystem mount_point
 *
 */

#define FUSE_USE_VERSION 26

static const char* photoVersion = "2008.09.24";

#include <sys/types.h>
#include <sys/stat.h>
#include <sys/statvfs.h>
#include <stdio.h>
#include <strings.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <errno.h>
#include <fcntl.h>
#include <sys/xattr.h>
#include <dirent.h>
#include <unistd.h>
#include <fuse.h>


// Global to store our read-write path
char *rw_path;

// Translate an photo path into it's underlying filesystem path
static char* translate_path(const char* path)
{

    char *rPath= malloc(sizeof(char)*(strlen(path)+strlen(rw_path)+1));

    strcpy(rPath,rw_path);
    if (rPath[strlen(rPath)-1]=='/') {
        rPath[strlen(rPath)-1]='\0';
    }
    strcat(rPath,path);

    return rPath;
}


/******************************
*
* Callbacks for FUSE
*
*
*
******************************/

static int photo_getattr(const char *path, struct stat *st_data)
{
    int res;
    char *upath=translate_path(path);

    res = lstat(upath, st_data);
    free(upath);
    if(res == -1) {
        return -errno;
    }
    return 0;
}

static int photo_readlink(const char *path, char *buf, size_t size)
{
    int res;
    char *upath=translate_path(path);

    res = readlink(upath, buf, size - 1);
    free(upath);
    if(res == -1) {
        return -errno;
    }
    buf[res] = '\0';
    return 0;
}

static int photo_readdir(const char *path, void *buf, fuse_fill_dir_t filler,off_t offset, struct fuse_file_info *fi)
{
    DIR *dp;
    struct dirent *de;
    int res;

    (void) offset;
    (void) fi;

    char *upath=translate_path(path);

    dp = opendir(upath);
    free(upath);
    if(dp == NULL) {
        res = -errno;
        return res;
    }
	
	//make directory, mode read write execute
	mkdir("/SUBJECTS", 0777);
	mkdir("/KEYWORDS", 0777);
	
    while((de = readdir(dp)) != NULL) {
        struct stat st;
        memset(&st, 0, sizeof(st));
        st.st_ino = de->d_ino;
        st.st_mode = de->d_type << 12;
        if (filler(buf, de->d_name, &st, 0))
            break;
    }

    closedir(dp);
    return 0;
}

static int photo_mknod(const char *path, mode_t mode, dev_t rdev)
{
    (void)path;
    (void)mode;
    (void)rdev;
    return -EROFS;
}

static int photo_mkdir(const char *path, mode_t mode)
{
    int res;

    res = mkdir(path, mode);
    if(res == -1)
        return -errno;

    return 0;
}

static int photo_open(const char *path, struct fuse_file_info *finfo)
{
    int res;

    /* We allow opens, unless they're tring to write, sneaky
     * people.
     */
    int flags = finfo->flags;

    if ((flags & O_WRONLY) || (flags & O_RDWR) || (flags & O_CREAT) || (flags & O_EXCL) || (flags & O_TRUNC) || (flags & O_APPEND)) {
        return -EROFS;
    }

    char *upath=translate_path(path);

    res = open(upath, flags);

    free(upath);
    if(res == -1) {
        return -errno;
    }
    close(res);
    return 0;
}

static int photo_read(const char *path, char *buf, size_t size, off_t offset, struct fuse_file_info *finfo)
{
    int fd;
    int res;
    (void)finfo;

    char *upath=translate_path(path);
    fd = open(upath, O_RDONLY);
    free(upath);
    if(fd == -1) {
        res = -errno;
        return res;
    }
    res = pread(fd, buf, size, offset);

    if(res == -1) {
        res = -errno;
    }
    close(fd);
    return res;
}

static int photo_statfs(const char *path, struct statvfs *st_buf)
{
    int res;
    char *upath=translate_path(path);

    res = statvfs(upath, st_buf);
    free(upath);
    if (res == -1) {
        return -errno;
    }
    return 0;
}

static int photo_access(const char *path, int mode)
{
    int res;
    char *upath=translate_path(path);

    /* Don't pretend that we allow writing
     * Chris AtLee <chris@atlee.ca>
     */
    if (mode & W_OK)
        return -EROFS;

    res = access(upath, mode);
    free(upath);
    if (res == -1) {
        return -errno;
    }
    return res;
}

static int photo_getxattr(const char *path, const char *name, char *value, size_t size)
{
    int res;

    char *upath=translate_path(path);
    res = lgetxattr(upath, name, value, size);
    free(upath);
    if(res == -1) {
        return -errno;
    }
    return res;
}

static int photo_listxattr(const char *path, char *list, size_t size)
{
    int res;

    char *upath=translate_path(path);
    res = llistxattr(upath, list, size);
    free(upath);
    if(res == -1) {
        return -errno;
    }
    return res;

}

struct fuse_operations photo_oper = {
    .getattr     = photo_getattr,
    .readlink    = photo_readlink,
    .readdir     = photo_readdir,
    .mknod       = photo_mknod,
    .mkdir       = photo_mkdir,
    .open        = photo_open,
    .read        = photo_read,
    .statfs      = photo_statfs,
    .access      = photo_access,

    /* Extended attributes support for userland interaction */
    .getxattr    = photo_getxattr,
    .listxattr   = photo_listxattr
};
enum {
    KEY_HELP,
    KEY_VERSION,
};

static void usage(const char* progname)
{
    fprintf(stdout,
            "usage: %s readwritepath mountpoint [options]\n"
            "\n"
            "   Mounts readwritepath as a read-only mount at mountpoint\n"
            "\n"
            "general options:\n"
            "   -o opt,[opt...]     mount options\n"
            "   -h  --help          print help\n"
            "   -V  --version       print version\n"
            "\n", progname);
}

static int photo_parse_opt(void *data, const char *arg, int key,
                          struct fuse_args *outargs)
{
    (void) data;

    switch (key)
    {
    case FUSE_OPT_KEY_NONOPT:
        if (rw_path == 0)
        {
            rw_path = strdup(arg);
            return 0;
        }
        else
        {
            return 1;
        }
    case FUSE_OPT_KEY_OPT:
        return 1;
    case KEY_HELP:
        usage(outargs->argv[0]);
        exit(0);
    case KEY_VERSION:
        fprintf(stdout, "ROFS version %s\n", photoVersion);
        exit(0);
    default:
        fprintf(stderr, "see `%s -h' for usage\n", outargs->argv[0]);
        exit(1);
    }
    return 1;
}

static struct fuse_opt photo_opts[] = {
    FUSE_OPT_KEY("-h",          KEY_HELP),
    FUSE_OPT_KEY("--help",      KEY_HELP),
    FUSE_OPT_KEY("-V",          KEY_VERSION),
    FUSE_OPT_KEY("--version",   KEY_VERSION),
    FUSE_OPT_END
};

int main(int argc, char *argv[])
{
    struct fuse_args args = FUSE_ARGS_INIT(argc, argv);
    int res;

    res = fuse_opt_parse(&args, &rw_path, photo_opts, photo_parse_opt);
    if (res != 0)
    {
        fprintf(stderr, "Invalid arguments\n");
        fprintf(stderr, "see `%s -h' for usage\n", argv[0]);
        exit(1);
    }
    if (rw_path == 0)
    {
        fprintf(stderr, "Missing readwritepath\n");
        fprintf(stderr, "see `%s -h' for usage\n", argv[0]);
        exit(1);
    }

    fuse_main(args.argc, args.argv, &photo_oper, NULL);

    return 0;
}
