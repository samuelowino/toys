#include <stdio.h>
#ifndef VELOCITY_H
	#define VELOCITY_H
	void demo_velocity();
	double angular_velocity(double,double);
#endif
void collect_variables();
void divider(int);
int main(){
	divider(12);
	printf("\nANGULAR VELOCITY\n(D) To run demo (I) Enter custom variables");
	int c;
	while((c = getchar()) != '\n'){
		if (c == 'D'){
			demo_velocity();
			break;
		} else if (c == 'I'){
			divider(12);
			collect_variables();
			break;
		}

	}
	return 0;
}
void collect_variables(){
	divider(12);
	printf("\nAngle:\n");
	double angle = 0.0;
	scanf("%lf",&angle);
	printf("Duration of moment in seconds:\n");
	divider(12);
	double duration_sec = 0.0;
	scanf("%lf",&duration_sec);
	double av = angular_velocity(angle,duration_sec);
	divider(12);
	printf("\n: w = %lf",av);
}
void divider(int size){
	int i = 0;
	printf("\n");
	do {
		printf("=");
		i++;
	} while(i < size);
}

