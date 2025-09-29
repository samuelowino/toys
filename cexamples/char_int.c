#include <stdio.h>
int main(){
	for (int i = 0;i <= 'z'; i++){
		if (i >= '0' && i <= '9'){
			printf("Number [%c]\t%d", i, i);
		}
		printf("\n");
		if (i >= 'A' && i <= 'z'){
			printf("Alphabet [%c]\t%d", i, i);
		}
		printf("\n");
		if (i <= '\'' && i >= '!'){
			printf("Special Char [%c]\t%d", i,i);
		}
	}
}
