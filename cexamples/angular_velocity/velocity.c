#include <stdio.h>
extern const double PI; 
extern const double DEFAULT_DURATION_SEC;
double angular_velocity(double angle, double duration_sec){
	if (angle < 1){
		angle = PI;
	}
	if (duration_sec < 1){
		duration_sec = DEFAULT_DURATION_SEC;
	}
	return angle /duration_sec;
}
void demo_velocity(){
	double omega = PI / DEFAULT_DURATION_SEC;
	printf("Demo Angular Velocity Calculation"
			"\nAngle is PI => %.3lf"
			"\nDuration in sec => %.3lf",
			PI,DEFAULT_DURATION_SEC);
}

