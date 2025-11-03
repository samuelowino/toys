#include <stdio.h>
#include <stdlib.h>
typedef struct{
	int id;
	float salary;
} Employee;
int main(){
	int n = 3;
	Employee *employees = malloc(n * sizeof(Employee));
	for(int i = 0;i < n;i++){
		employees[i].id = i + 1;
		employees[i].salary = 3000 + i * 500;
	}
	for(int i = 0;i < n;i++){
		printf("Employee Rank\t#%d\tSalary\t%.2f\n",employees[i].id,employees[i].salary);
	}
	free(employees);
	return 0;
}

