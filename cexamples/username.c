#include <stdio.h>
int main(){
	char password[8] = {'\0'};
	char username[16] = {'\0'};
	int index = 0;
	char c;
	printf("\nSet Password:");
	while((c = getchar()) != '\n' && index < 8){
		password[index] = c;
		++index;
	}	
	printf("\nSet Username:");
	index = 0;
	while((c = getchar()) != '\n' && index < 16){
		username[index] = c;
		++index;
	}
	printf("\n%s|%s",username,password);
	return 0;
}

