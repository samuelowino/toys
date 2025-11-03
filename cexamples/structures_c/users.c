#include <stdio.h>
#include <stdlib.h>
#include <time.h>
struct Date{
	int year;
	int month;
	int day_of_month;
	int hour;
	int min;
};
struct User{
	long int id;
	char *name;
	char *password_encoded;
	struct Date created_at;
};
struct User composeUser(char *name,char *pass){
	struct Date today;
	struct User user;
	today.year = 2025;
	today.month = 10;
	today.day_of_month = 8;
	today.hour = 12;
	today.min = 35;
	
	srand(time(NULL));
	user.id = rand() * 100 + 1;
	user.name = name;
	user.password_encoded = pass;
	user.created_at = today;
	return user;
}
int main(int argc,char *argv[]){
	printf("Compose\n");
	if (argc < 3){
		printf("Error: Invalid arguments: username and password required!");
		return -1;
	}
	char *username = argv[1];
	char *pass = argv[2];
	struct User user = composeUser(username,pass);
	printf("User registration successful!\n");
	printf("User.name ==> %s\n",user.name);
	printf("User.pass ==> %s\n",user.password_encoded);
	printf("Created at ==> %d/%d/%d hh%d mm%d",
			user.created_at.year,
			user.created_at.month,
			user.created_at.day_of_month,
			user.created_at.hour,
			user.created_at.min);
	return 0;
}

