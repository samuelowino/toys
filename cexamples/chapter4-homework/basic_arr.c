#include <stdio.h>
int main(){
	int numbers[] = {9,8,7,6,5,4,3,2,1,0};
	size_t ln = sizeof(numbers) / sizeof(int);
	for (int i = 0; i < ln; i++){
		printf("%d",numbers[i]);
	}
	printf("\n");
	int *numbs = numbers;
	int sum = 0;
	int largest = 0;
	int smallest = 0;
	int even_count = 0;
	int odd_count = 0;
	while(*numbs){
		printf("%d",*numbs);
		sum += *numbs;
		largest = (largest > *numbs) ? largest : *numbs;
		smallest = (smallest < *numbs) ? smallest : *numbs;
		if (*numbs % 2 == 0) even_count++; else odd_count++; 
		numbs++;
	}
	printf("\nsum\t%d\tsmallest\t%d\tlargest\t%d\todds\t%d\tevens\t%d",sum,smallest,largest,odd_count,even_count);
}
