#include <stdio.h>
int main(){
	int i = 0;
again:{
      	printf("Already %d\nAgain!",i);
	i++;
      } if(i < 10) goto again;		
}

