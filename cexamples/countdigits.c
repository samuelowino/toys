#include <stdio.h>
int main(){
	char digits[10] = {' '};
	int c;
	int index = 0;
	while((c = getchar()) != EOF){
		if (c >= '0' && c <= '9'){
			digits[index] = c;
		}
		++index;
		printf("\n===================================================\n");
		for(int i = 0;i < 10;i++){
			printf("\ndigits at %d => %c",i, digits[i]);
		}
		printf("\n==================================================\n");
        }
	return 0;
}

