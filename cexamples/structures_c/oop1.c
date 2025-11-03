#include <stdio.h>
struct Shape;
typedef struct Shape{
	float (*area)(struct Shape *self);
} Shape;
typedef struct{
	Shape base;
	float radius;
} Circle;
typedef struct{
	Shape base;
	float length;
} Square;
float circle_area(Shape *s){
	Circle *c = (Circle*)s;
	return 3.14159f * c->radius * c->radius;
}
float square_area(Shape *s){
	Square *sq = (Square*)s;
	return sq->length * sq->length;
}
int main(){
	// instance of Circle {shape{area_func},radius}
	Circle c = {{.area = circle_area}, 9.0f};
	// instance of Square {shape{area_func},length}
	Square sq = {{.area = square_area},10.0f};
	float c_area = c.base.area((Shape*)&c);
	float sq_area = sq.base.area((Shape*)&sq);
	printf("Circle Area => %.2f\n",c_area);
	printf("Square Area => %.2f\n",sq_area);
	return 0;
}

