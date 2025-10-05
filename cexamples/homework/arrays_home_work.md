## Arrays Homework
---

# 🧩 **C Arrays: Practice and Examples**

---

## **4.1 One-Dimensional Arrays**

Arrays store multiple values of the same type in contiguous memory locations.

### 🧠 Example 1: Initialize and print array elements

```c
#include <stdio.h>

int main() {
    int nums[5] = {10, 20, 30, 40, 50};
    for (int i = 0; i < 5; i++) {
        printf("nums[%d] = %d\n", i, nums[i]);
    }
    return 0;
}
```

### 🧠 Example 2: Sum of array elements

```c
#include <stdio.h>

int main() {
    int arr[5] = {1, 2, 3, 4, 5};
    int sum = 0;
    for (int i = 0; i < 5; i++) {
        sum += arr[i];
    }
    printf("Sum = %d\n", sum);
    return 0;
}
```

### 💪 Exercises

1. Find the **largest** and **smallest** elements in an array.
2. Count how many elements are **even** and **odd**.

---

## **4.2 Array Initialization and Bounds**

Learn how arrays are initialized and what happens when you exceed their bounds.

### 🧠 Example 1: Partial initialization

```c
#include <stdio.h>

int main() {
    int a[5] = {1, 2};  // remaining initialized to 0
    for (int i = 0; i < 5; i++)
        printf("%d ", a[i]);
    return 0;
}
```

### 🧠 Example 2: Demonstrating out-of-bounds access (unsafe!)

```c
#include <stdio.h>

int main() {
    int a[3] = {10, 20, 30};
    printf("a[5] = %d (undefined behavior!)\n", a[5]);
    return 0;
}
```

### 💪 Exercises

1. Try initializing an array without specifying its size (e.g., `int a[] = {1,2,3};`).
2. Experiment with accessing elements beyond array bounds and observe results (for learning only).

---

## **4.3 Arrays and Loops**

Loops are the most common way to process arrays.

### 🧠 Example 1: Reversing an array

```c
#include <stdio.h>

int main() {
    int a[5] = {10, 20, 30, 40, 50};
    for (int i = 4; i >= 0; i--)
        printf("%d ", a[i]);
    return 0;
}
```

### 🧠 Example 2: Counting positive and negative numbers

```c
#include <stdio.h>

int main() {
    int arr[6] = {-1, 3, -4, 7, 9, -2};
    int pos = 0, neg = 0;
    for (int i = 0; i < 6; i++) {
        if (arr[i] > 0) pos++;
        else neg++;
    }
    printf("Positive: %d, Negative: %d\n", pos, neg);
    return 0;
}
```

### 💪 Exercises

1. Write a program to **find the average** of array elements.
2. Write a program to **count zeros** in an array.

---

## **4.4 Multi-dimensional Arrays**

Arrays can have two or more dimensions (e.g., matrices).

### 🧠 Example 1: 2D array initialization

```c
#include <stdio.h>

int main() {
    int mat[2][3] = {
        {1, 2, 3},
        {4, 5, 6}
    };
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 3; j++)
            printf("%d ", mat[i][j]);
        printf("\n");
    }
    return 0;
}
```

### 🧠 Example 2: Sum of a 2D matrix

```c
#include <stdio.h>

int main() {
    int m[2][2] = {{1, 2}, {3, 4}};
    int sum = 0;
    for (int i = 0; i < 2; i++)
        for (int j = 0; j < 2; j++)
            sum += m[i][j];
    printf("Matrix sum = %d\n", sum);
    return 0;
}
```

### 💪 Exercises

1. Write a program to **transpose** a 3×3 matrix.
2. Write a program to **add two 2×2 matrices**.

---

## **4.5 Arrays and Functions**

Arrays are passed to functions by reference (as pointers).

### 🧠 Example 1: Function to print array elements

```c
#include <stdio.h>

void printArray(int a[], int n) {
    for (int i = 0; i < n; i++)
        printf("%d ", a[i]);
}

int main() {
    int data[4] = {5, 10, 15, 20};
    printArray(data, 4);
    return 0;
}
```

### 🧠 Example 2: Function to compute array average

```c
#include <stdio.h>

double average(int a[], int n) {
    int sum = 0;
    for (int i = 0; i < n; i++)
        sum += a[i];
    return (double)sum / n;
}

int main() {
    int marks[5] = {80, 90, 70, 85, 95};
    printf("Average = %.2f\n", average(marks, 5));
    return 0;
}
```

### 💪 Exercises

1. Write a function that **returns the max element** in an array.
2. Write a function that **reverses** an array in place.

---

## **4.6 Character Arrays (Strings)**

Strings are arrays of characters ending with `'\0'`.

### 🧠 Example 1: Manual string creation

```c
#include <stdio.h>

int main() {
    char name[] = {'H', 'i', '\0'};
    printf("%s\n", name);
    return 0;
}
```

### 🧠 Example 2: Counting string length

```c
#include <stdio.h>

int main() {
    char s[] = "Hello";
    int len = 0;
    while (s[len] != '\0') len++;
    printf("Length = %d\n", len);
    return 0;
}
```

### 💪 Exercises

1. Write a program to **reverse a string** manually.
2. Write a program to **count vowels and consonants**.

---

## **4.7 Arrays and Memory Layout**

Learn how arrays occupy contiguous memory.

### 🧠 Example 1: Printing addresses of array elements

```c
#include <stdio.h>

int main() {
    int a[4] = {1, 2, 3, 4};
    for (int i = 0; i < 4; i++)
        printf("&a[%d] = %p\n", i, (void*)&a[i]);
    return 0;
}
```

### 🧠 Example 2: Pointer-based traversal

```c
#include <stdio.h>

int main() {
    int a[4] = {10, 20, 30, 40};
    int *p = a;
    for (int i = 0; i < 4; i++)
        printf("%d ", *(p + i));
    return 0;
}
```

### 💪 Exercises

1. Demonstrate that array names represent the **address of the first element**.
2. Print memory addresses for each element in a 2D array.


