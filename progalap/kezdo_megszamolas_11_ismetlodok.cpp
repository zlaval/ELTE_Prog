#include <iostream>

using namespace std;

int main()
{

    int numberOfElements;
    int limit;

    cin >> numberOfElements >> limit;

    int repeatedElement = 0;

    int elements[100];

    for (int i = 0; i < numberOfElements; i++)
    {
        int element;
        cin >> element;
        elements[i] = element;
    }

    for (int i = 0; i < numberOfElements - limit; i++)
    {
        int actualElement = elements[i];

        int j = i + 1;
        int actualLimit = j + limit;

        bool find = false;

        while (j < actualLimit && !find)
        {
            int checkedElement = elements[j];
            if (actualElement == checkedElement)
            {
                repeatedElement++;
                find = true;
            }
            j++;
        }
    }

    cout << repeatedElement;

    return 0;
}
