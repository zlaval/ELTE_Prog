#include <iostream>

using namespace std;

int main()
{

    int numberOfDays;
    int temperatureToTest;

    cin >> numberOfDays;
    cin >> temperatureToTest;

    int numberOfHotDays = 0;

    for (int i = 0; i < numberOfDays; i++)
    {
        int dailyTemperature;
        cin >> dailyTemperature;
        if (dailyTemperature > temperatureToTest)
        {
            numberOfHotDays++;
        }
    }

    cout << numberOfHotDays;

    return 0;
}