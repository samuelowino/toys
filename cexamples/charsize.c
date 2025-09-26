#include <stdio.h>
int string_length(char arr[]);
int main(){
	char word[11] = "Judge Jude";
	int length = string_length(word);
	printf("\n%s - length - %d",word,length);
}
int string_length(char chars[]) {
	int index = 0;
	int total_chars = 0;
	while (chars[index] != '\0'){
		++total_chars;
		++index;
	}
	return total_chars;
}

