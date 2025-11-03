#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef struct{
	char *name;
	float grade;
} Student;
Student* new_student(const char *name,float grade){
	Student *student = malloc(sizeof(Student));
	if(!student){
		fprintf(stderr,"OOM: Failed to alloc new Student");
		exit(EXIT_FAILURE);
	}
	student->name = strdup(name);
	student->grade = grade;
	return student;
}
float average_grade(Student *students, size_t n){
	float sum = 0;
	for (size_t i = 0; i < n; i++){
		sum += students[i].grade;
	}
	return n > 0 ? sum / n : 0.0f; 
}
void free_student(Student *p){
	free(p->name);
	free(p);
}
int main(){
	Student *gracy = new_student("Gracy",78.23);
	Student *mallic = new_student("Mallic",86.12);
	Student students[] = {*gracy,*mallic};
	float avg = average_grade(students,2);
	printf("AVG: %.2f",avg);
	free_student(gracy);
	free_student(mallic);
}

