#include <stdio.h>
#include <stdlib.h>
#define EXIT_ON_ERROR -1
typedef struct Student{
	char *name;
	int age;
	float grade;
} Student;
float average_grade(Student students[], int count){
	float sum = 0;
	for (int i = 0; i < count; i++){
		sum += students[i].grade;
	}
	return sum / ((float)count);
}
float _average_grade(Student *student_pt,int count){
	float sum = 0;
	for (int i = 0; i < count; i++){
		sum += student_pt->grade;
		student_pt++;
	}
	return sum / ((float) count);
}

int main(int argc, char *argv[]){
	if(argc < 4){
		printf("Error! Invalid arguments: student name|age|grade required");
		return EXIT_ON_ERROR;
	}
	printf("sizeof Student Struct size => \t%zu bytes\n",sizeof(Student));
	int student_args = argc - 1;
	int complete_args = (student_args % 3 == 0) ? 1 : 0;
	if (!complete_args){
		printf("Incomplete args: required name|age|grade for each student\n");
		return EXIT_ON_ERROR;
	}
	int no_of_students = student_args / 3;
	Student students[no_of_students];
	int next_arg = 1;
	for (int i = 0; i < no_of_students;i++){
		students[i].name = argv[next_arg];
		next_arg++;
		students[i].age = atoi(argv[next_arg]);
		next_arg++;
		students[i].grade = atoi(argv[next_arg]);
		next_arg++;
	}
	printf("\nStudents:\tName\tAge\tGrade");
	for (int i = 0; i < no_of_students; i++){
		printf("\n\t\t%s\t%d\t\t%.2f\n",students[i].name,students[i].age,students[i].grade);
	}
	float avg = average_grade(students,no_of_students);
	float _avg = _average_grade(students,no_of_students); 
	printf("AVG: %.2f\n",avg);
	printf("AVG2: %.2f\n",_avg);
	return 0;
}
