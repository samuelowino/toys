#include <stdio.h>
int toInt(char []);
int main(){
	char num[] = {'4','8','9','3'};
	int number = toInt(num);
	printf("Number %d",number);
}
int toInt(char s[]){
	int n = 0;
	for (int i = 0; s[i] >= '0' && s[i] <= '9';i++){
		n = 10 * n + (s[i] - '0');	
	}
	return n;
}
