#include <stdio.h>
int main(int argc, char *argv[]){
	printf("Args size\%d\n",argc);
	printf("Args\n");
	for(int i = 0; i < argc; i++){
		printf("Argument:%s\n", argv[i]);
	}
	return 0;
}

