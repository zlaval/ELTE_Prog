#include <iostream>

using namespace std;

int main()
{

    int numberOfDays;
    cin >> numberOfDays;

    int result = 0;
    int previousDayTemperature;

    for (int i = 0; i < numberOfDays; i++)
    {
        int temperature;
        cin >> temperature;
        if (temperature < 0)
        {
            if (i == 0 || previousDayTemperature >= 0)
            {
                result++;
            }
        }
        previousDayTemperature = temperature;
    }

    cout << result;
    return 0;
}
