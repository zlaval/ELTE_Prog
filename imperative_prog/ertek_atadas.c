#include <stdio.h>

int addByValue(int a, int b)
{

    return a + b;
}

void addByRef(int *a, int *b, int *res)
{
    *res = *a + *b;
}

int main()
{
    int a = 10, b = 20, c;
    addByRef(&a, &b, &c);
    addByValue(a, b);

    int *x = &a;
    int *y = &b;

    addByValue(*x, *y);
    printf("%d - %d",010,020);
}