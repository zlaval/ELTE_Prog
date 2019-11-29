#include <iostream>

using namespace std;

int main()
{

    int numberOfDay;
    cin >> numberOfDay;

    int previousIncome = 0;
    int head = 0;

    for (int i = 0; i < numberOfDay; i++)
    {

        int dailyIncome;
        cin >> dailyIncome;

        if (dailyIncome != previousIncome)
        {
            head++;
        }

        previousIncome = dailyIncome;
    }

    cout << head;

    return 0;
}