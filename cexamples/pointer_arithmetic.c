#include <stdio.h>
#include <string.h>
#define ALLOCSIZE 1000
static char allocbuf[ALLOCSIZE];
static char *allocp = allocbuf;
char *alloc(int n){
	if (allocbuf + ALLOCSIZE - allocp >= n){ // Bounds checking!
		allocp += n; 		// Increment the pointer
		return allocp - n; 	// return addr to next free mem
	} else {
		return 0;
	}
}
void afree(char *p){
	if (p >= allocbuf && p < allocbuf + ALLOCSIZE){	//more bounds checking
		printf("\nmem before free [%p]", (void*)allocp);
		allocp = p; // move to the start addr
		printf("\nmem after free [%p]", (void*)allocp);
	}
}
int main(){
	char *message = alloc(8);
	if (message) {
		strcpy(message,"Bread!");
		printf("\n*message => [%p]\tallocp position => [%p]\t%s", (void*)message,(void*)allocp,message);
		afree(message);
		printf("\n*messagge => [%p]\tallocp position => [%p]", (void*)message,(void*)allocp);
	}
	return 0;	
}
