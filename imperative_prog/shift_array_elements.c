#include <stdio.h>

void shiftElements(int *array, int size)
{
    array = array + size;
    int last = *(array - 1);
    for (int i = size; i > 0; i--)
    {
        *(--array) = *(array - 1);
    }
    *array = last;
}

void printArray(int *array, int size)
{
    for (int i = 0; i < size; i++)
    {
        printf("%d ", *(array++));
    }
    printf("\n");
}

int main()
{
    int array[] = {6, 8, 2, 7, 89, 4, 33, 3, 45, 65};
    int size = sizeof(array) / sizeof(array[0]);
    printArray(array, size);
    shiftElements(array, size);
    printArray(array, size);
    return 0;
}
