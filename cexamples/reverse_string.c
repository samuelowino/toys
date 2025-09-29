#include <stdio.h>
#include <string.h>
char* reverse(char s[]);
char* reverse(char s[]){
	int c;
	int i = 0;
	int j = 0;
	for (j = strlen(s) - 1;i < j;i++,j--){
		c = s[i];
		s[i] = s[j];
		s[j] = c;
	}
	return s;
}
int main(){
	char s[] = "Release M-764";
	char *reversed = reverse(s);
	for (int i = 0; i < strlen(reversed); i++){
		printf("%c",reversed[i]);
	}
	return 0;
}

