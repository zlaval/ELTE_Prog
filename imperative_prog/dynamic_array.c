#include <stdio.h>
#include <stdlib.h>

int main()
{

    int numberOfVariables;
    scanf("%d", &numberOfVariables);

    int *numbers = (int *)calloc(numberOfVariables, sizeof(int));

    if (numbers == 0)
    {
        return 1;
    }

    for (int i = 0; i < numberOfVariables; i++)
    {
        int tmp;
        scanf("%d", &tmp);
        numbers[i] = tmp;
    }

    for (int i = 0; i < numberOfVariables; i++)
    {
        printf("%d \n", numbers[i]);
    }

    free(numbers);
    numbers = 0;

    return 0;
}