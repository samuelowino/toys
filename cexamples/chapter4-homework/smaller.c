#include <stdio.h>
int* smaller(int *a, int *b){
	return (*a < *b) ? a : b; 
}
int main(){
	int a = 10;
	int b = 15;
	int *small = smaller(&a,&b);
	printf("%d is the smaller of %d and %d",*small,a, b);
	return 0;
}
