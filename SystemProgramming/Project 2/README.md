*extracting linux source
-----------------------
cd Desktop

sudo mv /usr/src/linux-source-3.13.0/linux-source-3.13.0.tar.bz2 linux-source-3.13.0.tar.bz2

tar -xjvf linux-source-3.13.0.tar.bz2


*writing system call
-------------------

cd linux-source-3.13.0

mkdir mycall

geany mycall/mycall.c

geany mycall/Makefile

geany Makefile

geany arch/x86/syscalls/syscall_32.tbl

geany include/linux/syscalls.h


*compiling linux kernel
----------------------

make localmodconfig

make-kpkg clean

fakeroot make-kpkg --initrd --append-to-version=-custom kernel_image kernel_headers
