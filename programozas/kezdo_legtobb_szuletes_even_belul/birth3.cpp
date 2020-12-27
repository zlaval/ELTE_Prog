#include <iostream>

using namespace std;

int main()
{
    int DAY_IN_RANGE = 42823;

    int dayOfMonth[] = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
    int birthOnDay[DAY_IN_RANGE];
    for (int i = 0; i < DAY_IN_RANGE; i++)
    {
        birthOnDay[i] = 0;
    }

    int numberOfPeople;
    cin >> numberOfPeople;

    for (int i = 0; i < numberOfPeople; i++)
    {
        int birthDay;
        int birthMonth;
        int birthYear;
        cin >> birthYear >> birthMonth >> birthDay;

        int index = (birthYear - 1900) * 366 + dayOfMonth[birthMonth - 1] + (birthDay - 1);
        birthOnDay[index]++;
    }

    int maxCount = 0;
    int maxCountIndex = -1;

    for (int i = 0; i < DAY_IN_RANGE; i++)
    {

        int count = 0;
        if (birthOnDay[i] != 0)
        {
            int endDayIndex = i + 366;
            int j = i;
            while (j < endDayIndex && j < DAY_IN_RANGE)
            {
                count += birthOnDay[j];
                j++;
            }
        }

        if (count > maxCount)
        {
            maxCount = count;
            maxCountIndex = i;
        }
    }

    if (maxCountIndex > -1)
    {
        int year = maxCountIndex / 366;
        int remainder = maxCountIndex - year * 366;
        year += 1900;
        int month = 1;
        while ((remainder / dayOfMonth[month]) > 0)
        {
            month++;
        }
        int day = remainder - dayOfMonth[month - 1] + 1;
        cout << year << " " << month << " " << day << endl;
    }

    return 0;
}