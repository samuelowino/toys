#include <stdio.h>
typedef struct Point{
	float x;
	float y;
} Point;
int main(){
	Point points[] = {
		{1.0f,2.1f},
		{3.0f,4.5f},
		{7.0f,6.5f}
	};
        for(int i = 0; i < 3; i++){
		printf("x[%.2f]\ty[%.2f]\n",points[i].x,points[i].y);
	}
}
