#include <stdio.h>
#include <stdlib.h>
void populate_null_ptr(int **null_ptr){
	*null_ptr = (int *)malloc(sizeof(int));
	**null_ptr = 100; 
}
int main(){
	int *null_ptr = NULL;
	populate_null_ptr(&null_ptr);
	printf("Ptr val %d",*null_ptr);
	free(null_ptr);
	return 0;
}

