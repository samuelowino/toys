#include <stdio.h>
void double_values(int *arr, int size);
void double_values(int *arr, int size){
	for(int i = 0; i < size; i++){
		arr[i] *= 2;
	}
}
int main(){
	int arr[5] = {4,2,3,8,9};
	double_values(arr,5);
	int i = 0;
	do {
		printf("[%d]\t",arr[i]);
		i++;
	}while(i < 5);
}

