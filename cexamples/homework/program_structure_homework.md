
## Functions and Program Structure Homework

---

# 🧩 **C Chapter 4 — Functions and Program Structure**

---

## **4.1 Basics of Functions**

Functions allow modular programming — they take arguments, perform operations, and return values.

### 🧠 Example 1: Simple function with return

```c
#include <stdio.h>

int square(int n) {
    return n * n;
}

int main() {
    printf("Square of 5 = %d\n", square(5));
    return 0;
}
```

### 🧠 Example 2: Function with no return value

```c
#include <stdio.h>

void greet() {
    printf("Hello from a function!\n");
}

int main() {
    greet();
    return 0;
}
```

### 💪 Exercises

1. Write a function that returns the **cube** of an integer.
2. Write a function that prints whether a number is **even or odd**.

---

## **4.2 Functions Returning Non-integers**

Functions can return other data types such as `double`, `char`, or pointers.

### 🧠 Example 1: Function returning `double`

```c
#include <stdio.h>

double area_of_circle(double r) {
    const double PI = 3.14159;
    return PI * r * r;
}

int main() {
    printf("Area = %.2f\n", area_of_circle(5.0));
    return 0;
}
```

### 🧠 Example 2: Function returning a pointer

```c
#include <stdio.h>

int* getMax(int *a, int *b) {
    return (*a > *b) ? a : b;
}

int main() {
    int x = 10, y = 20;
    printf("Max = %d\n", *getMax(&x, &y));
    return 0;
}
```

### 💪 Exercises

1. Write a function that returns the **average** of two floating-point numbers.
2. Write a function that returns a **pointer to the smaller integer**.

---

## **4.3 External Variables**

External (global) variables are defined outside any function and shared across files.

### 🧠 Example 1: Using global variables

```c
#include <stdio.h>

int count = 0;  // external variable

void increment() {
    count++;
}

int main() {
    increment();
    increment();
    printf("Count = %d\n", count);
    return 0;
}
```

### 🧠 Example 2: Accessing extern variable across files

*(file1.c)*

```c
int counter = 100;
```

*(file2.c)*

```c
#include <stdio.h>

extern int counter;

int main() {
    printf("Counter = %d\n", counter);
    return 0;
}
```

### 💪 Exercises

1. Use an external variable to **track total function calls**.
2. Create two files — one defines a global variable, the other **reads and modifies it**.

---

## **4.4 Scope Rules**

Scope determines where variables are visible — local, global, or block.

### 🧠 Example 1: Local vs. global scope

```c
#include <stdio.h>

int x = 5;

int main() {
    int x = 10;
    printf("Local x = %d, Global x = %d\n", x, ::x); // C doesn’t support :: — for concept only
    return 0;
}
```

### 🧠 Example 2: Shadowing example

```c
#include <stdio.h>

int x = 1;

void show() {
    int x = 2;
    printf("Inside show(): x = %d\n", x);
}

int main() {
    printf("Global x = %d\n", x);
    show();
    return 0;
}
```

### 💪 Exercises

1. Write a program where a **local variable hides a global variable**.
2. Show how changing a local variable **does not affect** a global one.

---

## **4.5 Header Files**

Header files store declarations shared between multiple `.c` files.

### 🧠 Example 1: Simple header usage

*(mathutils.h)*

```c
int square(int);
```

*(main.c)*

```c
#include <stdio.h>
#include "mathutils.h"

int square(int x) { return x * x; }

int main() {
    printf("%d\n", square(4));
    return 0;
}
```

### 🧠 Example 2: Function prototype in header

```c
#include <stdio.h>
#include "helper.h"  // contains: void greet(void);

int main() {
    greet();
    return 0;
}
```

### 💪 Exercises

1. Create a header file for **mathematical functions** (`add`, `subtract`).
2. Split a program into **two files + one header** and compile with `gcc main.c other.c`.

---

## **4.6 Static Variables**

Static variables preserve value between function calls.

### 🧠 Example 1: Static inside a function

```c
#include <stdio.h>

void counter() {
    static int count = 0;
    count++;
    printf("Count = %d\n", count);
}

int main() {
    counter();
    counter();
    counter();
    return 0;
}
```

### 🧠 Example 2: File-level static variable

```c
#include <stdio.h>

static int secret = 42;

void showSecret() {
    printf("Secret = %d\n", secret);
}

int main() {
    showSecret();
    return 0;
}
```

### 💪 Exercises

1. Write a function that counts **how many times it was called**.
2. Create a **file-level static variable** that is inaccessible from other files.

---

## **4.7 Register Variables**

`register` suggests storing variables in CPU registers (faster access).

### 🧠 Example 1: Register variable in a loop

```c
#include <stdio.h>

int main() {
    register int i;
    for (i = 0; i < 5; i++)
        printf("%d ", i);
    return 0;
}
```

### 🧠 Example 2: Register with arithmetic

```c
#include <stdio.h>

int main() {
    register int counter = 10;
    printf("Counter = %d\n", counter);
    return 0;
}
```

### 💪 Exercises

1. Use a register variable in a **loop sum** program.
2. Demonstrate that you **can’t get the address** of a register variable.

---

## **4.8 Block Structure**

Blocks `{}` define nested scopes.

### 🧠 Example 1: Variable shadowing in nested blocks

```c
#include <stdio.h>

int main() {
    int x = 5;
    {
        int x = 10;
        printf("Inner x = %d\n", x);
    }
    printf("Outer x = %d\n", x);
    return 0;
}
```

### 🧠 Example 2: Lifetime demonstration

```c
#include <stdio.h>

int main() {
    {
        int temp = 20;
        printf("Inside block: %d\n", temp);
    }
    // temp no longer exists here
    return 0;
}
```

### 💪 Exercises

1. Write a program with **nested blocks** using the same variable name.
2. Test lifetime of variables inside nested scopes.

---

## **4.9 Initialization**

Initialize variables when declared to avoid garbage values.

### 🧠 Example 1: Scalar initialization

```c
#include <stdio.h>

int main() {
    int x = 10;
    double y = 3.14;
    printf("%d %.2f\n", x, y);
    return 0;
}
```

### 🧠 Example 2: Array initialization

```c
#include <stdio.h>

int main() {
    int arr[3] = {1, 2, 3};
    for (int i = 0; i < 3; i++)
        printf("%d ", arr[i]);
    return 0;
}
```

### 💪 Exercises

1. Try **partial initialization** (`int a[5] = {1,2};`).
2. Initialize an array using a **for loop** instead of braces.

---

## **4.10 Recursion**

A function calling itself is recursion.

### 🧠 Example 1: Factorial using recursion

```c
#include <stdio.h>

int factorial(int n) {
    return (n == 0) ? 1 : n * factorial(n - 1);
}

int main() {
    printf("5! = %d\n", factorial(5));
    return 0;
}
```

### 🧠 Example 2: Recursive Fibonacci

```c
#include <stdio.h>

int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}

int main() {
    for (int i = 0; i < 6; i++)
        printf("%d ", fib(i));
    return 0;
}
```

### 💪 Exercises

1. Write a recursive function for **power(base, exp)**.
2. Write a recursive function to **reverse a string**.

---

## **4.11 The C Preprocessor**

The preprocessor runs before compilation — handles macros, includes, and conditionals.

---

### **4.11.1 File Inclusion**

```c
#include <stdio.h>   // standard header
#include "myheader.h" // user header
```

**Example:**

```c
#include <stdio.h>
#include "greet.h"

int main() {
    greet();
    return 0;
}
```

---

### **4.11.2 Macro Substitution**

### 🧠 Example 1: Simple macro

```c
#include <stdio.h>
#define PI 3.14159
#define AREA(r) (PI * (r) * (r))

int main() {
    printf("Area = %.2f\n", AREA(5));
    return 0;
}
```

### 🧠 Example 2: Multi-line macro

```c
#include <stdio.h>
#define PRINT_SUM(a,b) { int sum = a + b; printf("Sum = %d\n", sum); }

int main() {
    PRINT_SUM(3, 4);
    return 0;
}
```

### 💪 Exercises

1. Write a macro that returns the **maximum** of two numbers.
2. Write a macro to **swap** two variables.

---

### **4.11.3 Conditional Inclusion**

### 🧠 Example 1: Using `#ifdef`

```c
#include <stdio.h>
#define DEBUG

int main() {
#ifdef DEBUG
    printf("Debug mode ON\n");
#endif
    return 0;
}
```

### 🧠 Example 2: Using `#if` and `#else`

```c
#include <stdio.h>
#define VERSION 2

int main() {
#if VERSION == 1
    printf("Old version\n");
#else
    printf("New version\n");
#endif
    return 0;
}
```

### 💪 Exercises

1. Use `#ifdef` to enable/disable **debug messages**.
2. Use conditional compilation to include **different functions** based on a macro value.


