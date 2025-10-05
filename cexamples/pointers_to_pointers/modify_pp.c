#include <stdio.h>
int main(){
	int x = 10;
	int *p = &x;
	printf("\nbefore [*p = 100]\tx => %d",x);
	*p = 100;
	printf("\nafter [*p = 100]\tx => %d",x);
	int **pp = &p;
	**pp = 300;
	printf("\nafter [**pp = 300]\tx ==> %d",x);
	return 0;
}

