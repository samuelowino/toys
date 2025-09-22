#include <stdio.h>
#include <stdbool.h>

#define SIZE 12

int main(){
	char stream[SIZE] = {'S','I','M','P','L','E',' ','S','I','M','O','N'};
	for (int i = 0;i < SIZE;i++){
		putchar(stream[i]);
	}
	printf("Enter message\n"); 
	char input;
	while((input = getchar()) != EOF){
		printf("%c",input);
	}	
	return 0;
}
