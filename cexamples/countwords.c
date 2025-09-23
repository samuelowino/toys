#include <stdio.h>
#define IN_WORD 0
#define OUTSIDE_WORD 1
int main(){
	int state = 0;
	int c;
	long wc = 0;
	while((c = getchar()) != EOF){
		if (c == ' ' || c == '\n' || c == '\t'){
			state = OUTSIDE_WORD;
			++wc;	
			printf("\nWord count %ld\n", wc);
		} else {
			state = IN_WORD;
		}
	}
	return 0;
}
