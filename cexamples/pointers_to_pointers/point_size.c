#include <stdio.h>
int main(){
	double _double = 10.4534354334;
	printf("char*\t%zu bytes \t char\t%zu bytes\n", sizeof(char*),sizeof('c'));
	printf("int*\t%zu bytes \t int\t%zu bytes \n", sizeof(int*),sizeof(5));
	printf("float*\t%zu bytes \t float\t%zu bytes \n", sizeof(float*),sizeof(10.54f));
	printf("double*\t%zu bytes \t double\t%zu bytes \n", sizeof(double*), sizeof(_double));
	printf("void *\t%zu bytes \n", sizeof(void*));
	return 0;
}
