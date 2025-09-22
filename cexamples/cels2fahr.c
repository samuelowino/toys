#include <stdio.h>

#define UPPER 1000
#define STEP 20
#define START 0

int main(){
	float fahr = START;
	printf("fahr\tcels\n");
	while(fahr <= UPPER){
		float cels = (fahr - 32.0) * (5.0/9.0);
		printf("%5.0f\t%'6.2f\n",fahr,cels);
		fahr = fahr + STEP;
	}
}
