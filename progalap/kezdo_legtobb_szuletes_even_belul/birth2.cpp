#include <iostream>

using namespace std;

int main()
{
    const int MAX_PEOPLE_NUM = 100000;
    const int YEAR_RANGE = 117;

    int data[YEAR_RANGE][10000];
    int dataIndex[YEAR_RANGE] = {0};

    int numberOfPeople;
    cin >> numberOfPeople;

    for (int i = 0; i < numberOfPeople; i++)
    {
        int birthDay;
        int birthMonth;
        int birthYear;
        cin >> birthYear >> birthMonth >> birthDay;
        int birthDate = birthYear * 10000 + birthMonth * 100 + birthDay;
        int index = birthYear - 1900;
        data[index][dataIndex[index]] = birthDate;
        dataIndex[index]++;
    }

    int maxCount = 0;
    int maxDate = 0;

    for (int i = 0; i < YEAR_RANGE; i++)
    {

        for (int j = 0; j < dataIndex[i]; j++)
        {

            int actualDate = data[i][j];
            int actualEndDate = actualDate + 10000;
            int count = 0;
            int fi = 0;

            while (fi < dataIndex[i])
            {
                int testedDate = data[i][fi];
                if (testedDate >= actualDate && testedDate < actualEndDate)
                {
                    count++;
                }

                fi++;
            }

            if (i < YEAR_RANGE - 1)
            {
                int si = 0;
                while (si < dataIndex[i + 1])
                {
                    int testedDate = data[i + 1][si];
                    if (testedDate >= actualDate && testedDate < actualEndDate)
                    {
                        count++;
                    }

                    si++;
                }
            }
            if (count > maxCount)
            {
                maxCount = count;
                maxDate = actualDate;
            }
        }
    }

    if (maxDate > 0)
    {
        int year = maxDate / 10000;
        int month = maxDate / 100 - year * 100;
        int day = maxDate - year * 10000 - month * 100;

        cout << year << " " << month << " " << day<<endl;
    }
    return 0;
}