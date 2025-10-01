#include <stdio.h>
#include <ctype.h>
double to_double(char [],size_t);
double to_double(char exp[], size_t exp_length){
	double val;
	double power = 1.0;
	int i = 0;
	int sign;
	for (i = 0;exp[i] != '\0';i++){
		if(isspace(exp[i])){
			continue;
		}
		sign = (exp[i] == '-') ? -1 : 1;
		if(exp[i] == '+' || exp[i] == '-'){
			i++;
		}
		for(val = 0.0; isdigit(exp[i]);i++){
			val = 10.0 * val + (exp[i] - '0');
		}
	 	if(exp[i] == '.'){
			i++;
		}
		for(power = 1.0;isdigit(exp[i]);i++){
			val = 10.0 * val + (exp[i] - '0');
			power *= 10;
		}
	}
	return sign * val / power;
}
int main(){
	printf("\nEnter expr:\n");
	char exp[100] = {'\0'};
	char c;
	int i = 0;
	while((c = getchar()) != '\n'){
		exp[i] = c;
		i++;
	}
	size_t exp_length = sizeof(exp) / sizeof(exp[0]); 
	double val = to_double(exp,exp_length);
	printf("\n%.3lf",val);
	return 0;
}

