#include <stdio.h>
#include <stdlib.h>
#include <math.h>
typedef struct {
	float x;
	float y;
} Point;
Point* make_point(float x,float y){
	Point *p = malloc(sizeof(Point));
	if (!p) {
		printf("OOM! Failed to alloc new point\n");
		exit(EXIT_FAILURE);
	}
	p->x = x;
	p->y = y;
	return p;
}
float distance(Point *a,Point *b){
	float dx = b->x - a->x;
	float dy = b->y - a->y;
	return sqrtf(dx*dx + dy*dy);
}
int main(){
	Point *p1 = make_point(4.5f,2.3f);
        Point *p2 = make_point(10.2f,21.7f);
	float dist = distance(p1,p2);
	printf("Distance from [%.2f,%.2f] to [%.2f,%.2f] is %.2f",
			p1->x,p1->y,p2->x,p2->y,dist);
	free(p1);
	free(p2);
	return 0;
}
