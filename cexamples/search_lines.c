#include <stdio.h>
#define MAXLINE 1000
int get_line(char line[], int max);
int str_index(char source[], char search_for[]);
char pattern[] = "an";
int main(){
	char line[MAXLINE];
	int found = 0;
	while(get_line(line,MAXLINE) >= 0){
		if(str_index(line,pattern) >= 0){
			printf("\n%s",line);
			found++;
		}
	}
	return found;
}
int get_line(char s[],int lim){
	int c = 0;
	int i = 0;
	while(--lim > 0 && (c = getchar()) != EOF && c != '\n'){
		s[i++] = c;
		if(c == '\n'){
			s[i++] = c;
		}
		s[i] = '\0';
	}
	return i;
}
int str_index(char s[],char t[]){
	int i;
	int j;
	int k;
	for(i = 0;s[i] != '\0';i++){
		for(j = i, k = 0;t[k] != '\0' && s[j] == t[k]; j++, k++){
			;
		}
		if (k > 0 && t[k] == '\0'){
			return i;
		}
	}
	return -1;
}

