#include <stdio.h>
int str_len(char *s){
	int length;
	for(length = 0; *s != '\0';s++){
		length++;	
	}
	return length;
}
int main(){
	char *s = "Cry me a river!";
	int length = str_len(s);
	printf("\nLength: %d",length);
	return 0;
}
