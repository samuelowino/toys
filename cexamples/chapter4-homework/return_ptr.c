#include <stdio.h>
int* getMax(int *a, int *b){
	return (*a > *b) ? a : b;
}
int main(){
	int x = 10;
	int y = 20;
	int *res = getMax(&x,&y);
	printf("Grt %d", *res);
}

