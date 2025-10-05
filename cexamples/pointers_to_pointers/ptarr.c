#include <stdio.h>
int main(){
	char *planets[] = {"Venus","Mars","Earth","Jupiter","Saturn","Neptune","Pluto",NULL};
	char **pp = planets;
	while(*pp != NULL){
		printf("[%s]",*pp);
		pp++;
	}
	return 0;
}

