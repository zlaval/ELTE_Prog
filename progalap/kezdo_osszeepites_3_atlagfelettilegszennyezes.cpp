#include <iostream>

using namespace std;

int main()
{

    int numberOfDays;
    cin >> numberOfDays;

    int nightlyContaminations[1000];
    int aboveAverage[1000];

    int sumOfNightlyContaminations = 0;

    for (int i = 0; i < numberOfDays; i++)
    {
        int daily;
        int nightly;

        cin >> daily >> nightly;
        nightlyContaminations[i] = nightly;
        sumOfNightlyContaminations += nightly;
    }

    double averageNightlyContamination = ((double)sumOfNightlyContaminations) / numberOfDays;

    int overAverageCount = 0;

    for (int i = 0; i < numberOfDays; i++)
    {
        if (nightlyContaminations[i] > averageNightlyContamination)
        {
            aboveAverage[overAverageCount++] = i + 1;
        }
    }

    cout << overAverageCount << " ";
    for (int i = 0; i < overAverageCount; i++)
    {
        cout << aboveAverage[i] << " ";
    }

    return 0;
}
