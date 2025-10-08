#include <stdio.h>
float atoi_float(char*);
float avrg_val(float *a, float *b){
	return ((*a + *b) / 2.0f);
}
int main(int argc, char *argv[]){
	if (argc < 3){
		printf("Error:Invalid arguments: missing float 1 and float 2");
		return -1;
	}
	float a = atoi_float(argv[1]);
	float b = atoi_float(argv[2]);
	float avg = avrg_val(&a,&b);
	printf("AVG of %f and %f is %f", a, b, avg);
	return 0;
}
float atoi_float(char *text){
	float flt = 0.0;
	int in_dec = 0;
	int ln = 0;
	for(int i = 0; text[i] != '\0';i++){
		ln++;
	}
	for(int i = 0; i < ln; i++){
		if(text[i] <= '9' && text[i] >= '0'){
			if (in_dec){
				flt += (text[i] / 100.0);
			} else {
				flt = flt * 10.0 + text[i] - '0';
			}
		}
		if (text[i] == '.'){
			in_dec = 1;
		}
	}
	return flt;
}


