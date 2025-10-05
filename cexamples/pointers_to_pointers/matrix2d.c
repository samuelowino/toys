#include <stdio.h>
#include <stdlib.h>
int main(){
	int rows = 8;
	int cols = 8;
	//	matrix
 	//	│
 	//	├─-─► [ int* ][ int* ][ int* ]  ← allocated by malloc(rows * sizeof(int*))
       	//		 │        │      │
       	//		 │        │      └──► [ int int int int ]   ← row 2 (4 ints)
       	//		 │        └─────---─► [ int int int int ]   ← row 1
       	//		 └────-───-------───► [ int int int int ]   ← row 0
	int **matrix = malloc(rows * sizeof(int*));
	
	for (int i = 0; i < rows; i++){
		matrix[i] = malloc(cols * sizeof(int));
	}

	for(int i = 0;i < rows; i++){
		for(int j = 0; j < cols; j++){
			matrix[i][j] = i + j;
		}
	}

	for(int i = 0; i < rows; i++){
		for(int j = 0; j < cols; j++){
			printf("[%d]\t", matrix[i][j]);
		}
		printf("\n");
	}

	for (int i =0; i < rows; i++){
		free(matrix[i]);
	}
	free(matrix);
	return 0;
}

