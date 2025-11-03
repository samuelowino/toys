#include <stdio.h>
#include <math.h>
typedef struct{
	float x;
	float y;
	float (*distance)(float,float);
} Point;
float euclidean(float x, float y){
	return sqrtf((x * x) + (y * y));
}
float non_euclidean(float x, float y){
	return sqrtf(x + y);
}
int main(){
	Point points[] = {
		{3, 4, euclidean},
		{6, 8, non_euclidean},
		{9, 12, euclidean}
	};
	for(int i = 0; i < 3; i++){
		printf("Point (%.1f, %.1f): Distance = %.2f\n",
			points[i].x, points[i].y,
			points[i].distance(points[i].x,points[i].y));
	}
	return 0;
}

