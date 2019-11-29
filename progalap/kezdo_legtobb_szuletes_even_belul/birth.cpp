#include <iostream>

using namespace std;

int main()
{
    const int MAX_PEOPLE_NUM = 100000;

    int numberOfPeople;
    cin >> numberOfPeople;

    int births[MAX_PEOPLE_NUM];

    int yearCount[117];
    int countInYear[117];
    for (int i = 0; i < 117; i++)
    {
        yearCount[i] = 0;
    }

    for (int i = 0; i < numberOfPeople; i++)
    {
        int birthDay;
        int birthMonth;
        int birthYear;
        cin >> birthYear >> birthMonth >> birthDay;

        int birth = birthYear * 100;
        birth += (birthMonth + 10);
        birth -= 10;
        birth *= 100;
        birth += (birthDay + 10);
        birth -= 10;
        births[i] = birth;

        yearCount[birthYear]++;
    }

    int maxValue = 0;
    int maxIndex = 0;
    for (int i = 0; i < 116; i++)
    {
        int count = yearCount[i] + yearCount[i + 1];
        if (maxValue < count)
        {
            maxValue = count;
            maxIndex = i;
        }
    }
    int testedYear = (maxIndex + 1900) * 10000;
    for (int i = 0; i < numberOfPeople; i++)
    {
        if (births[i] > testedYear && births[i] < testedYear + 20000)
        {

            int birthPlusOne = births[i] + 10000;

            countInYear[i] = 1;
            for (int j = 0; j < i; j++)
            {
                int previousBirth = births[j];
                if (births[i] >= previousBirth && births[i] < previousBirth + 10000)
                {
                    yearCount[j]++;
                }
                if (previousBirth < birthPlusOne && previousBirth >= births[i])
                {
                    yearCount[i]++;
                }
            }
        }
    }

     maxIndex = 0;
     maxValue = 0;
    for (int i = 0; i < numberOfPeople; i++)
    {
        if (maxValue < yearCount[i])
        {
            maxIndex = i;
            maxValue = yearCount[i];
        }
    }
    int year = births[maxIndex] / 10000;
    int month = births[maxIndex] / 100 - year * 100;
    int day = births[maxIndex] - year * 10000 - month * 100;

    cout << year << " " << month << " " << day;

    return 0;
}