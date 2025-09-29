#include <stdio.h>
int* shell_sort(int v[],int n);
int* shell_sort(int v[], int n){
	int gap;
	int i;
	int temp;
	int j;
	for(gap = n/2;gap > 0;gap /= 2){
		for (i = gap;i < n; i++){
			for (j=i-gap; j >= 0 && v[j] > v[j + gap];j-=gap){
				temp = v[j];
				v[j] = v[j + gap];
				v[j + gap] = temp;
			}
		}
	}
	return v;
}
int main(){
	int v[] = {7,1,4,5,6,9,2,3,8};
	int n = 9;
	int *sorted = shell_sort(v,n);
	printf("Sorted array\n");
	for (int i = 0; i < n; i++){
		printf("%d", sorted[i]);
	}
	return 0;
}

