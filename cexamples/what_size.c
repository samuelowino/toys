#include <stdio.h>
int main(){
	printf("\nsizeof integer\t%zu", sizeof(int));
	printf("\nsizeof short int\t%zu", sizeof(short));
	printf("\nsizeof long_int\t%zu", sizeof(long));
	printf("\nsizeof unsigned_int\t %zu", sizeof(unsigned int));
	printf("\nsizeof signed_int\t %zu", sizeof(signed int));
	printf("\nsizeof double\t%zu", sizeof(double));
	printf("\nsizeof float\t%zu", sizeof(float));
	printf("\nsizeof char\t%zu", sizeof(char));
	printf("\nsizeof unsigned char\t%zu", sizeof(unsigned char));
}
