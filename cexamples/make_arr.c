#include <stdio.h>
#include <stdlib.h>
int* make_arr(int);
int* make_arr(int length){
	int *arr = malloc(length * sizeof(int));
	for (int i = 0; i < length; i++){
		arr[i] = i * 2;
	}
	return arr;
}
int main() {
	int *arr = make_arr(8);
	int i = 0;
	do {
		printf("%d\t", arr[i]);
		i++;
	} while(i < 8);
}

