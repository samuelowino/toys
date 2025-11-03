#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef struct {
	char *name;
	int position;
	float surface_area;
	float distance_from_earth;
} Planet;
typedef struct {
	char *name;
	int age;
} Person;
Person* new_person(const char *name, int age){
	Person *p = malloc(sizeof(Person));
	if(!p){
		fprintf(stderr,"Error: OOM cannot alloc new person");
		exit(EXIT_FAILURE);
	}
	p->name = strdup(name);
	p->age = age;
	return p;
}
void print_person(const Person *p){
	printf("[%s]\t[%d]\n",p->name,p->age);
}
void free_person(Person *p){
	free(p->name);
	free(p);
}
int main(){
	Planet *planet = malloc(sizeof(Planet));
	if(planet == NULL){
		printf("OOM -> Planet alloc failed\n");
		return -1;
	}
	planet->name = "Jupiter";
	planet->position = 3;
	planet->surface_area = 67433222.123;
	planet->distance_from_earth = 234895123.455;
	printf("Planet\t[%s\t%d\t%.2f\t%.2f]\n",
			planet->name,
			planet->position,
			planet->surface_area,
			planet->distance_from_earth);
	free(planet);
	Person *george = new_person("Geroge",21);
	Person *mark = new_person("Phill",45);
	print_person(george);
	print_person(mark);
	free_person(george);
	free_person(mark);
	return 0;
}

