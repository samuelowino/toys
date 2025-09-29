#include <stdio.h>
#include <string.h>
int main(){
	int c;
	int i = 0;
	char choice[11] = {'\0'};
	printf("Choose destination: Heaven(H): Hell(F)\n");
	while((c = getchar()) != '\n'){
		choice[i] = c;
		i++;
		if (strcmp(choice,"H") == 0)
			goto heaven;
		else if (strcmp(choice, "F") == 0)
			goto hell;
	}
hell:
	printf("You are in hell👹");
	return 0;
heaven:
	printf("You are now in heaven 👼🏻");
        return 0;
}

