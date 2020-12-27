#include <iostream>

using namespace std;

int main()
{

    //N
    int dayCount;

    //K
    int daysToHeatAlert;

    //H
    int tempBarrier;

    int dailyAverageTemperatures[100000];

    cin >> dayCount >> daysToHeatAlert >> tempBarrier;

    //beolvassuk az atlaghomersekleteket
    for (int i = 0; i < dayCount; i++)
    {
        int averageTemperature;
        cin >> averageTemperature;
        dailyAverageTemperatures[i] = averageTemperature;
    }

    int countOfHeatAlerts = 0;

    int firstStartIndex = -1;
    int secondStartIndex = -1;
    int firstEndIndex = -1;
    int secondEndIndex = -1;
    int dayCountAboveBarrer = 0;
    int minDistance = 100001;

    for (int i = 0; i < dayCount; i++)
    {

        if (dailyAverageTemperatures[i] > tempBarrier)
        {
            if (firstStartIndex == -1)
            {
                firstStartIndex = i;
            }
            else if (firstEndIndex != -1 && secondStartIndex == -1)
            {
                secondStartIndex = i;
            }
            dayCountAboveBarrer++;
          //  cout << i << ". szam barrier counter" << dayCountAboveBarrer << endl;
        }
        else
        {
            if (dayCountAboveBarrer >= daysToHeatAlert)
            {
                if (firstEndIndex == -1 &&dailyAverageTemperatures[i]<=dailyAverageTemperatures[firstStartIndex+daysToHeatAlert])
                {
                    firstEndIndex = i;
                    countOfHeatAlerts++;
                }
                else if (secondEndIndex == -1 &&dailyAverageTemperatures[i]<=dailyAverageTemperatures[secondEndIndex+daysToHeatAlert])
                {
                    secondEndIndex = i;
                    countOfHeatAlerts++;
                }
            }
            else
            {
                if (firstEndIndex == -1)
                {
                    firstStartIndex = -1;
                }
                if (secondEndIndex == -1)
                {
                    secondStartIndex = -1;
                }
            }
            dayCountAboveBarrer = 0;
        }

        if (secondEndIndex != -1)
        {
          //  cout << "FS: " << firstStartIndex << " FE:" << firstEndIndex << " SS: " << secondStartIndex << " SE: " << secondEndIndex << endl;
            int distance = secondStartIndex + daysToHeatAlert - firstEndIndex - 1;
            if (distance < minDistance)
            {
                minDistance = distance;
            }
            firstStartIndex = -1;
            secondStartIndex = -1;
            firstEndIndex = -1;
            secondEndIndex = -1;
            dayCountAboveBarrer = 0;
        }
    }

    if (countOfHeatAlerts == 0)
    {
        cout << -1;
    }
    else if (countOfHeatAlerts == 1)
    {
        cout << -2;
    }
    else
    {
        cout << minDistance;
    }

    return 0;
}