#include <stdio.h>
#include "sort.h"

//gcc -o main main.c sort.h sort.c
int main()
{

    int array[] = {67, 5, 3, 76, 32, 1, 45, 8, 3, 79, 34};
    int size = sizeof(array) / sizeof(array[0]);
    sort(array, size);

    for (int i = 0; i < size; i++)
    {
        printf("%d ", array[i]);
    }

    return 0;
}