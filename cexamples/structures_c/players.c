#include <stdio.h>
#define MAX_LEVELS 5
typedef struct Player{
	char *name;
	int scores[MAX_LEVELS];
} Player;
int main(){
	Player players[] = {
		{"Jammy", {5,0,1,7,10}},
		{"Majum", {1,4,11,13,21}}
	};
	for (int i = 0; i < 2; i++){
		printf("Player:%s\t[",players[i].name);
		for (int j = 0; j < MAX_LEVELS; j++){
			printf("%d\t",players[i].scores[j]);
		}
		printf("]\n");
	}
}

