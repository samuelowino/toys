#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef struct{
	char *title;
	int year;
}Book;
int compare_year(const void *a,const void *b){
	const Book *bookA = a;
	const Book *bookB = b;
	return bookB->year - bookA->year;
}
int main(){
	Book library[] = {
		{"C Primer Plus", 2012},
		{"The Art of C", 1998},
		{"Modern C", 2020}
	};
	size_t count = sizeof(library) / sizeof(library[0]);
	qsort(library,count,sizeof(Book),compare_year);
	for(size_t i = 0; i< count; i++){
		printf("%d\t%s\n", library[i].year, library[i].title);
	}
	return 0;
}

