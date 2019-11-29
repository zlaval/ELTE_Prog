#include <iostream>

using namespace std;

int main()
{

    int years[100];

    int numberOfCategories;
    int numberOfEvents;
    cin >> numberOfEvents;
    cin >> numberOfCategories;

    for (int i = 0; i < numberOfEvents; i++)
    {
        int category;
        string event;
        cin >> years[i] >> category;
        cin.ignore();
        getline(cin,event);
    }

    int from;
    int to;
    cin >> from >> to;
    for (int i = 0; i < numberOfCategories; i++)
    {
        string categoryName;
        cin >> categoryName;
    }

    int sum = 0;

    for (int i = 0; i < numberOfEvents; i++)
    {
        int year = years[i];

        if (year >= from && year <= to)
        {
            sum++;
        }
    }

    cout << sum;

    return 0;
}