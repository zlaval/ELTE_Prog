#include <iostream>

using namespace std;

const int MAX_LOTTERY_VALUE = 90;

bool isAllDrawed(bool draws[]);
void resetDraws(bool draws[]);


int main()
{

    int numberOfDraws;
    cin >> numberOfDraws;

    bool draws[MAX_LOTTERY_VALUE] = {false};

    int result = 0;

    for (int i = 0; i < numberOfDraws * 5; i++)
    {
        int value;
        cin >> value;
        value--;
        draws[value] = true;
        if (isAllDrawed(draws))
        {
            result++;
            resetDraws(draws);
        }
    }
    cout << result;

    return 0;
}

bool isAllDrawed(bool draws[])
{
    int i = 0;
    while (i < MAX_LOTTERY_VALUE)
    {
        if (draws[i] == false)
        {
            return false;
        }
        i++;
    }
    return true;
}

void resetDraws(bool draws[])
{
    for (int i = 0; i < MAX_LOTTERY_VALUE; i++)
    {
        draws[i] = false;
    }
}