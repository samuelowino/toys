#include <stdio.h>

int main(){
	int c;
	long count = 0;
	while((c = getchar()) != EOF){
		if (c == '\n'){
			++count;
			printf("\nlines count %ld\n",count);
		}
	}
	return 0;
}
