/****************************************
 * 					*
 *  System Programming - Project 1	*
 *  Group : G15				*
 *  Duran Eren				*
 *  Cagatay Erdiz			*
 *  Ahmet Gozuberk			*
 *  13.10.2015				*
 * 					*
 ****************************************/

#include<stdio.h>
#include<string.h>
#include<stdlib.h>

int kmp(char *text, char *search_string);

int main(int argc, char **argv)
{
	if (argc != 2){
		printf("Command line format is wrong!\n");
		exit(1);
	}

	int c, i = 0;
	int size = 0;
	int result;
	char *fileName = argv[1];
	char *text;
	char search_string[200]; 

	FILE *input = fopen(fileName, "r");
	
	// finding size of file
	if (input) {
		while (getc(input) != EOF)
			size++;
	}
	else {
		printf("Unable to open %s.\n", fileName);
		exit(1);
	}
	
	// seek to beginning of the file
	rewind(input); 
	// allocating for char array
	text = (char*)(malloc(size + 1)); 

	// reading file
	while ((c = getc(input)) != EOF) {
		text[i] = c;
		i++;
	}
	text[size] = 0;
	
	fclose(input);
	
	// scannig pattern
	printf("\nEnter string to search: ");
	scanf("%[^\n]",search_string);
	
	// printing text and pattern
	printf("\nText: \n%s\n\n", text);
	printf("Searched string: \n%s\n\n", search_string);
	
	// calling kmp function from assembly 
	result = kmp(text, search_string);
	
	if(result != -1){ printf("\n\nFound first pattern at index %d \n\n", result); }
	else{printf("\n\nPattern not found\n\n");}
	
	return 0;
}
