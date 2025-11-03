#include <stdio.h>
#include <time.h>
#include <stdlib.h>
typedef struct{
	void (*start)(void);
	void (*pause)(int);
	void (*stop)(void);
} MediaPlayer;
void player_start(void){
	printf("Starting playback...\n");
}
void player_pause(int current_track){
	printf("Pausing playback at track[%d]\n",current_track);
}
void player_stop(){
	printf("Playback stopped\n");
}
int main(){
	srand(time(NULL));
	MediaPlayer walk_man = {player_start, player_pause,player_stop};
	walk_man.start();
	walk_man.pause(rand() * 100);
	walk_man.stop();
	return 0;
}

