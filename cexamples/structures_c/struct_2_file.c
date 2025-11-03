#include <stdio.h>
#include <stdlib.h>
typedef struct{
	int id;
	double balance;
}Account;
int main(){
	Account accounts[3] = {
		{101, 2500.45},
		{102, 4300.00},
		{103, 1800.25}
	};
	FILE *file = fopen("accounts.dat","wb");
	fwrite(accounts, sizeof(Account), 3, file);
	fclose(file);
	Account *loaded = malloc(3 * sizeof(Account));
	file = fopen("accounts.dat","rb");
	fread(loaded, sizeof(Account), 3, file);
	fclose(file);
	for(int i = 0; i < 3; i++){
		printf("ID:\t%d\tBalance\t%.2f\n", loaded[i].id, loaded[i].balance);
	}
	free(loaded);
	return 0;
}

