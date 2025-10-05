## C Pointers Practice
> Add checkmark ✅ to indicate completion

---

## 🧭 **5.1 Pointers and Addresses**

### Example 1 — Basic pointer usage

```c
#include <stdio.h>

int main() {
    int x = 10;
    int *p = &x;

    printf("x = %d\n", x);
    printf("Address of x = %p\n", (void*)&x);
    printf("Value stored in p = %p\n", (void*)p);
    printf("Value pointed to by p = %d\n", *p);
    return 0;
}
```

### Example 2 — Changing value through pointer

```c
#include <stdio.h>

int main() {
    int a = 5;
    int *ptr = &a;
    *ptr = 20; // modifies a directly
    printf("a = %d\n", a);
    return 0;
}
```

### Exercises

1. Write a program to print the addresses of three different variables (`int`, `float`, and `char`).
2. Write a program that swaps two integers using only pointers (no temporary variable).

---

## 🧩 **5.2 Pointers and Function Arguments**

### Example 1 — Passing by value vs passing by reference

```c
#include <stdio.h>

void increment(int n) { n++; }
void incrementPtr(int *n) { (*n)++; }

int main() {
    int x = 5;
    increment(x);
    printf("After increment(): %d\n", x);
    incrementPtr(&x);
    printf("After incrementPtr(): %d\n", x);
}
```

### Example 2 — Swap function

```c
#include <stdio.h>

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int main() {
    int x = 10, y = 20;
    swap(&x, &y);
    printf("x = %d, y = %d\n", x, y);
}
```

### Exercises

1. Write a function that doubles the value of an integer using a pointer.
2. Write a function that receives two integers and stores their sum through a pointer argument.

---

## 📚 **5.3 Pointers and Arrays**

### Example 1 — Array and pointer equivalence

```c
#include <stdio.h>

int main() {
    int arr[] = {10, 20, 30};
    int *p = arr; // same as &arr[0]
    printf("%d %d %d\n", *p, *(p+1), *(p+2));
}
```

### Example 2 — Iterating with pointer

```c
#include <stdio.h>

int main() {
    int arr[] = {1, 2, 3, 4};
    for (int *p = arr; p < arr + 4; p++)
        printf("%d ", *p);
}
```

### Exercises

1. Write a program to find the sum of an array using pointer arithmetic.
2. Write a program to reverse an array using pointers.

---

## ➕ **5.4 Address Arithmetic**

### Example 1 — Pointer addition

```c
#include <stdio.h>

int main() {
    int a[] = {5, 10, 15, 20};
    int *p = a;
    printf("%d\n", *(p + 2)); // access 3rd element
}
```

### Example 2 — Pointer difference

```c
#include <stdio.h>

int main() {
    int arr[] = {10, 20, 30, 40, 50};
    int *p1 = &arr[1];
    int *p2 = &arr[4];
    printf("Difference = %ld elements\n", p2 - p1);
}
```

### Exercises

1. Write a program to print every alternate element in an array using pointer arithmetic.
2. Compute the distance between two pointers in an array.

---

## 🔠 **5.5 Character Pointers and Functions**

### Example 1 — Using a character pointer

```c
#include <stdio.h>

int main() {
    char *msg = "Hello, world!";
    printf("%s\n", msg);
}
```

### Example 2 — String length function

```c
#include <stdio.h>

int strlength(char *s) {
    int n = 0;
    while (*s++) n++;
    return n;
}

int main() {
    printf("%d\n", strlength("Pointer"));
}
```

### Exercises

1. Write your own version of `strcpy()` using pointers.
2. Write a function that counts vowels in a string using a character pointer.

---

## 🪣 **5.6 Pointer Arrays; Pointers to Pointers**

### Example 1 — Array of pointers

```c
#include <stdio.h>

int main() {
    char *names[] = {"Alice", "Bob", "Charlie"};
    for (int i = 0; i < 3; i++)
        printf("%s\n", names[i]);
}
```

### Example 2 — Pointer to pointer

```c
#include <stdio.h>

int main() {
    int x = 100;
    int *p = &x;
    int **pp = &p;
    printf("%d\n", **pp);
}
```

### Exercises

1. Write a program to print strings stored in an array of pointers.
2. Create a pointer-to-pointer and modify the value of the base variable through it.

---

## 🧮 **5.7 Multi-dimensional Arrays**

### Example 1 — Static 2D array

```c
#include <stdio.h>

int main() {
    int matrix[2][3] = {{1, 2, 3}, {4, 5, 6}};
    printf("%d\n", matrix[1][2]);
}
```

### Example 2 — Access with pointer notation

```c
#include <stdio.h>

int main() {
    int a[2][2] = {{10, 20}, {30, 40}};
    printf("%d\n", *(*(a + 1) + 0)); // a[1][0]
}
```

### Exercises

1. Write a program to print a 3×3 matrix using nested loops.
2. Find the sum of all elements in a 2D array.

---

## 🔗 **5.8 Initialization of Pointer Arrays**

### Example 1 — Initialize string array

```c
#include <stdio.h>

int main() {
    char *days[] = {"Sun", "Mon", "Tue", "Wed"};
    for (int i = 0; i < 4; i++)
        printf("%s\n", days[i]);
}
```

### Example 2 — Integer pointer array

```c
#include <stdio.h>

int main() {
    static int a = 1, b = 2, c = 3;
    int *arr[] = {&a, &b, &c};
    for (int i = 0; i < 3; i++)
        printf("%d ", *arr[i]);
}
```

### Exercises

1. Write a program to initialize an array of pointers to integers and print their values.
2. Create an array of pointers to strings (e.g., fruit names) and print them.

---

## 🧭 **5.9 Pointers vs. Multi-dimensional Arrays**

### Example 1 — 2D array access

```c
#include <stdio.h>

int main() {
    int a[2][3] = {{1, 2, 3}, {4, 5, 6}};
    printf("%d\n", a[1][2]);
}
```

### Example 2 — Array of pointers (same data shape)

```c
#include <stdio.h>

int main() {
    int r1[] = {1, 2, 3};
    int r2[] = {4, 5, 6};
    int *rows[] = {r1, r2};
    printf("%d\n", rows[1][2]);
}
```

### Exercises

1. Write equivalent pointer and array versions for accessing a matrix element.
2. Compare memory layouts of a 2D array and an array of pointers (print addresses).

---

## 🖥 **5.10 Command-line Arguments**

### Example 1 — Count arguments

```c
#include <stdio.h>

int main(int argc, char *argv[]) {
    printf("Argument count: %d\n", argc);
}
```

### Example 2 — Print all arguments

```c
#include <stdio.h>

int main(int argc, char *argv[]) {
    for (int i = 0; i < argc; i++)
        printf("argv[%d] = %s\n", i, argv[i]);
}
```

### Exercises

1. Write a program that prints all command-line arguments in reverse order.
2. Write a program that counts the total number of characters across all arguments.

---

## 🧩 **5.11 Pointers to Functions**

### Example 1 — Function pointer

```c
#include <stdio.h>

int add(int a, int b) { return a + b; }

int main() {
    int (*fp)(int, int) = add;
    printf("%d\n", fp(2, 3));
}
```

### Example 2 — Passing a function as argument

```c
#include <stdio.h>

int add(int a, int b) { return a + b; }
int operate(int x, int y, int (*func)(int, int)) { return func(x, y); }

int main() {
    printf("%d\n", operate(5, 10, add));
}
```

### Exercises

1. Write a function pointer for subtraction and call it through another function.
2. Create a program that chooses a function (add/mul) based on a numeric input.

---

## 🧵 **5.12 Complicated Declarations**

### Example 1 — Pointer to function returning int

```c
int (*fp)(int, int);
```

### Example 2 — Pointer to array of pointers

```c
int *(*pArr[5]);
```

To clarify, you can visualize them using `cdecl` or the **right-left rule**.

### Exercises

1. Declare a pointer to a function returning a pointer to `char`.
2. Declare and initialize a pointer to an array of integers.


