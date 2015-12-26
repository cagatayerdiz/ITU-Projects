#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include "range_ioctl.h"

int main(int argc, char **argv){
	if(argc < 2){
		printf("Error: Too few command line arguments.\n");
		return -1;
	}
	
	int value;
	char *path_name = argv[1];
	
	int fd = open(path_name, O_RDWR);
	
	if(fd < 0){
		printf("Error: Can't open device file.\n");
		return -1;
	}
	
	if(argc == 2){
		int read_byte = read(fd, &value, 4);
		if(read_byte == 0){
			printf("End of File!!!\n");
		}	
		else{
			printf("\nCurrent Value: %d\n", value);
		}
	}
	
	else if(argc == 3 || argc == 4){
		int arg = (argc == 4) ? atoi(argv[3]) : 0;
		
		/* 
			ioctl(file_descriptor, request, arg)
			Command line argv[2]:
			1 	RANGE_GET_STEP_SIZE
			2	RANGE_SET_STEP_SIZE
			3	RANGE_GET_COUNTER
			4	RANGE_SET_COUNTER
			5	RANGE_SET_ROTATE_MODE
			6	RANGE_SET_EOF_MODE
			7	RANGE_SET_START_VALUE
			8	RANGE_SET_END_VALUE
			Command line argv[3]: set_value
		*/
		
		switch(atoi(argv[2])){
			case 1:
				ioctl(fd, RANGE_GET_STEP_SIZE, &arg);
				printf("Range step size: %d\n", arg);
				break;
			case 2:
				ioctl(fd, RANGE_SET_STEP_SIZE, &arg);
				printf("Step size hase been set as %d\n", arg);
				break;
			case 3:
				ioctl(fd, RANGE_GET_COUNTER, &arg);
				printf("Range counter: %d\n", arg);
				break;
			case 4:
				ioctl(fd, RANGE_SET_COUNTER, &arg);
				printf("Counter has been set as %d\n", arg);
				break;
			case 5:
				ioctl(fd, RANGE_SET_ROTATE_MODE, &arg);
				printf("Rotate mode has been set as %d\n", arg);
				break;
			case 6:
				ioctl(fd, RANGE_SET_EOF_MODE, &arg);
				printf("End mode has been set as %d\n", arg);
				break;
			case 7:
				ioctl(fd, RANGE_SET_START_VALUE, &arg);
				printf("Start value has been set as %d\n", arg);
				break;
			case 8:
				ioctl(fd, RANGE_SET_END_VALUE, &arg);
				printf("End value has been set as %d\n", arg);
				break;
			default:
				printf("Error\n");
				break;
		}
	}
	
	else if(argc > 4){
		printf("Error: Too many command line arguments.\n");
		return -1;
	}
	
	return 0;
}
