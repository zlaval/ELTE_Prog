#include "sort.h"

int partition(int *array, int start, int end)
{
    int pivot = array[start];
    int head = start;
    int tail = end;

    while (head < tail)
    {
        while (head < tail && array[--tail] >= pivot)
        {
        }

        if (head < tail)
        {
            array[head] = array[tail];
        }
        while (head < tail && array[++head] <= pivot)
        {
        }

        if (head < tail)
        {
            array[tail] = array[head];
        }
    }
    array[tail] = pivot;
    return tail;
}

void quickSort(int *array, int start, int end)
{
    if ((end - start) < 2)
    {
        return;
    }

    int pivotIndex = partition(array, start, end);
    quickSort(array, start, pivotIndex);
    quickSort(array, pivotIndex + 1, end);
}

void sort(int *array, int size)
{
    quickSort(array, 0, size);
}