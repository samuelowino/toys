#include <stdio.h>
#include <stdlib.h>
#include <time.h>
struct Data {
	int numbers[1000];
};
void process(struct Data *d);
void process(struct Data *d){
	srand(time(NULL));
	for(int i = 0; i < 1000;i++){
		d->numbers[i] = (rand() % 100) + 1;
	}
}
int main(){
	struct Data emptyData = {0};
	process(&emptyData);
	int i = 0;
	while(i < 1000){
		printf("\nContains[%d]",emptyData.numbers[i]);
		i++;
	}
	return 0;
}

