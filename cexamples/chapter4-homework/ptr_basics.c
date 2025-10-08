#include <stdio.h>
void swapper(int *a, int *b){
	int temp = *a;
	*a = *b;
	*b = temp;
}
void dblv(int *a){
	*a = *a * 2;
}
void sumr(int *a, int *b, int *sum){
	*sum = *a + *b;
}
int main(){
	int x = 10;
	int *ptr = &x;
	printf("Address of x = %p\n", (void*)&x);
	printf("Values stored in p = %p\n", (void*)ptr);
	printf("Address of pointer ptr %p\n", (void*)&ptr);
	printf("Value pointed to by p = %d\n", *ptr);
	int a = 5;
	int b = 12;
	printf("\nstart\ta\t%d\tb\t%d",a,b);
	int *a_ptr = &a;
	int *b_ptr = &b;
	swapper(a_ptr,b_ptr);
	printf("\nswap\ta\t%d\tb\t%d",a,b);
	dblv(&a);
	dblv(&b);
	printf("\ndouble\ta\t%d\tb\t%d",a,b);
	int sum = 0;
	sumr(&a,&b,&sum);
	printf("\nsum\ta\t%d\tb\t%d\tsum => %d",a,b,sum);
	return 0;
}
