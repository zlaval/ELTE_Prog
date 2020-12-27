#include <stdio.h>

int main()
{

    int numberOfFlats;
    int expensiveLimit;
    scanf("%d", &numberOfFlats);
    scanf("%d", &expensiveLimit);

    int numberOfExpensiveFlats = 0;
    int flatIndexes[numberOfFlats];

    for (int i = 0; i < numberOfFlats; i++)
    {
        int area, price;
        scanf("%d", &area);
        scanf("%d", &price);
        if (price > expensiveLimit)
        {
            flatIndexes[numberOfExpensiveFlats] = i + 1;
            numberOfExpensiveFlats++;
        }
    }

    printf("%d ", numberOfExpensiveFlats);

    for (int i = 0; i < numberOfExpensiveFlats; i++)
    {
        printf("%d ", flatIndexes[i]);
    }

    return 0;
}