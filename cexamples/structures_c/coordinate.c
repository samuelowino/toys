#include <stdio.h>
struct Point{
	float x;
	float y;
};
struct Rect {
	struct Point lat;
	struct Point lng;
};
int main(){
	struct Point lat;
	struct Point lng;
	struct Rect rect; 
	lat.x = 34.12f;
	lat.y = 12.45f;
	lng.x = 90.12f;
	lng.y = 33.44f;
	rect.lat = lat;
	rect.lng = lng;
	printf("Pokro coordinates Lng(%.2f,%.2f) Lat(%.2f,%.2f)",
			rect.lng.x,rect.lng.y,
			rect.lat.x,rect.lat.y);
}
