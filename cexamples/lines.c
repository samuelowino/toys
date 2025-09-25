#include <stdio.h>
int main(){
	printf("Press '-' to exit\n");
	int c;
	int lines = 0;
	while((c = getchar()) != '-') {
		if (c == '\n'){
			++lines;
		}
	}
	printf("Lines %d", lines);
	return 0;
}
