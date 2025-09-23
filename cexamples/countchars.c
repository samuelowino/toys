#include <stdio.h>

int main(){
	int c = 0;
	long count = 0;
	while((c = getchar()) != EOF){
		++count;
		printf("char ASCII code %d | %c\n", c, c);
		printf("Char count %ld\n", count);
	}
	return 0;
}
