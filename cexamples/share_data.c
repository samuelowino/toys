#include <stdio.h>
void double_val(int *value){
	(*value) *= 2;
}
void tripple(int *value){
	(*value) *= 3;
}
int main(){
	int value;
	scanf("%d",&value);
	tripple(&value);
	printf("\ntrippled:%d",value);
	double_val(&value);
	printf("\ndoubled and trippled: %d",value);
}

