/****************************************
 * 										*
 *  System Programming - Project 2		*
 *  Group : G15							*
 *  Duran Eren							*
 *  Cagatay Erdiz						*
 *  Ahmet Gozuberk						*
 *  04.11.2015							*
 * 										*
 ****************************************/

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <errno.h>
#define sysNum 355

int main (int argc, char **argv){
	
	if(argc != 3){
		printf("Error: Command line format must be like './program pid casper'\n");
		return EXIT_FAILURE;
	}
	
	syscall(sysNum, atoi(argv[1]), atoi(argv[2]));
	
	switch(errno){
		case EPERM:
				printf("Error: Root privileges must be taken. To get root privileges, run as 'sudo'");
				return EXIT_FAILURE;
		case ESRCH:
				printf("Error: No such process. Run 'ps aux' then choose valid pid\n");
				return EXIT_FAILURE;
		case EINVAL:
				printf("Error: Casper value must be 0, 1, 2 or 3\n");
				return EXIT_FAILURE;
		case 0:
				printf("Casper value has been set successfully\n");
				return EXIT_SUCCESS;
		}
}
