#include <stdio.h>
int powers(int,int);
int main(){
	for (int i = 0; i < 10; i++){
		int base1 = 3;
		int base2 = -base1;
		int pow = powers(base1,i);
		int pow2 = powers(base2,i);
		printf("\n%d\t^pow\t%d\t%d",base1,i,pow);
		printf("\n%d\t^pow\t%d\t%d",base2,i,pow2);
	}
	return 0;
}
int powers(int base, int exp){
	if (exp == 0) return 1;
	int pow = base;
	for (int index = 1;index < exp; index++){
		pow = pow * base; 
	}
	return pow;
}

