#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int guess_letter(int);
int random_char();
int active_guess;
int main(){
	int active_guess = 0;
	printf("Guess Next Letter {A,B,C...Z}\n");
	for(;;){
		active_guess = random_char();
		char letter;
		int c;
		while((c = getchar()) != '\n'){
			letter = c;
		}
		if (guess_letter(letter)) {
			printf("\nCorrect ✅\n");
		} else {
			printf("\nIncorrect 🔴: Actual is %c\n",active_guess);
		}
	}
	return 0;
}
int guess_letter(int number){
	int result = (number == active_guess) ? 1 : 0;
	static int guess_count = 0;
	++guess_count;
	printf("\nAttempts %d",guess_count);
	return result;
}
int random_char(){
	srand(time(NULL));
	return 1 + rand() % (99 - 1 + 1);	
}

