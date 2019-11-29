#include <stdio.h>

int mostCommonElements(int *array, int size);

int main()
{
    int array[10] = {12, 3, 54, 3, 12, 5, 23, 3, 7, 2};
    int result = mostCommonElements(array, sizeof(array) / sizeof(array[0]));
    printf("%d", result);
    return 0;
}

int mostCommonElements(int *array, int size)
{

    for (int i = 0; i < size; i++)
    {

        printf("%d ", array[i]);
    }

    return 1;
}