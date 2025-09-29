#include <stdio.h>
int toLower(int);
int main(){
	int c;
	while((c = getchar()) != EOF){
		printf("%c", toLower(c));
	}
	return 0;
}
int toLower(int c){
	if (c <= 'Z' && c >= 'A'){
		int step = 'a' - 'A';
		return c + step;
	} else {
		return c;
	}
}
