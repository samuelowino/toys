#include <stdio.h>
typedef struct{
	char *name;
	int credits;
}Course;
typedef struct{
	char *name;
	Course *courses;
}Student;
int main(){
	Course credits[] = {
		{"Math", 	5},
		{"Physica", 	3},
		{"Chemistry", 	5}
	};
	Student s = {"Alice",credits};
	printf("Student: %s\n",s.name);
	for(int i = 0; i < 3;i++){
		printf("%s\t(%d credits)\n",s.courses[i].name,s.courses[i].credits);
	}
	return 0;
}

