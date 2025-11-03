#include <stdio.h>
#include <stdlib.h>
#include <locale.h>
void println();
typedef struct Planet{
	char *name;
  	int position;
  	float surface_area;
  	float distance_from_earth;
} Planet;
Planet* compose_planets(){
	Planet template[] = {
                 {"Mercury", 1, 600452.43f, 350544.77f},
		 {"Venus",   2, 450311.54f, 150733.44f},
                 {"Earth",   3, 510072.00f,      0.0f},
                 {"Mars",    4, 144798.00f,  78340.00f},
                 {"Jupiter", 5, 6141900.00f, 628730.00f},
                 {"Saturn",  6, 4270000.00f, 1275000.00f},
                 {"Uranus",  7, 808300.00f, 2723950.00f},
                 {"Neptune", 8, 761800.00f, 4351400.00f},
                 {"Pluto",   9, 16600.00f,  5869690.00f}
         };
	 printf("templates addr\t%p\n",(void*)template);
	 println();
         Planet *planets_list = malloc(9 * sizeof(Planet));
	 printf("allocated planets_list addr\t[heap mem]\t%p\n",(void*)planets_list);
	 println();
	 printf("addr of pointer to planets_list\t[stack mem]\t%p\n",&planets_list);
	 println();
	 printf("addr stored in pointer to \t[heap mem]\t%p\n",(void*)planets_list);
	 println();
	 for (int i = 0; i < 9;i++){
		 printf("Update value at \t[heap mem]\t[%p]\n",&planets_list[i]);
                  planets_list[i] = template[i];
         }
	 return planets_list;
}
void println(){
	int c = 0;
	printf("\n");
	do {
		printf("=");
		c++;
	} while (c < 100);
	printf("\n");
}
int compare_planets(const void *planet1,const void *planet2){
	const Planet *p1 = planet1;
	const Planet *p2 = planet2;
	return (p1->surface_area > p2->surface_area) - (p1->surface_area < p2->surface_area);
}
int main(){
	setlocale(LC_NUMERIC,"");
	Planet *planets_list = compose_planets();
	println();
	printf("main:returned pointer\t%p\n",(void*)planets_list);
	println();
	for (int i = 0; i < 9; i++){
		printf("[%1s\t\t%5d\t\t%5.2f\t\t%5.2f\t\t[%p]]\n",
			planets_list[i].name,
			planets_list[i].position,
			planets_list[i].surface_area,
			planets_list[i].distance_from_earth, (void*)&planets_list[i]);
	}
	qsort(planets_list,9,sizeof(Planet),compare_planets);
	println();
	printf("Sorted by surface area\n");
	for (int i = 0; i < 9; i++){
                 printf("[%1s[%'.2f]\t\t%'.2f\t\t[%p]]\n",
                         planets_list[i].name,
                         planets_list[i].surface_area,
                         planets_list[i].distance_from_earth, (void*)&planets_list[i]);
	}
	free(planets_list);
	return 0;
}

