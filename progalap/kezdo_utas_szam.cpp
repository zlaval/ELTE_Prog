#include <iostream>

using namespace std;

int main()
{

    int numberOfBuses;
    int maxPlaces;
    cin >> numberOfBuses >> maxPlaces;

    int numberOfCrowded = 0;
    int numberOfEmpty = 0;

    int emptyBuses[100];
    int fullBuses[100];
    int eIndex = 0;
    int fIndex = 0;

    for (int i = 0; i < numberOfBuses; i++)
    {

        int numberOfActualPassangers;
        cin >> numberOfActualPassangers;

        double fullness = (double)numberOfActualPassangers / maxPlaces * 100.0;
        if (fullness >= 80.0)
        {
            numberOfCrowded++;
            fullBuses[fIndex++] = i + 1;
        }
        else if (fullness < 20.0)
        {
            numberOfEmpty++;
            emptyBuses[eIndex++] = i + 1;
        }
    }

    cout << numberOfCrowded << " ";
    for (int i = 0; i < fIndex; i++)
    {
        cout << fullBuses[i] << " ";
    }
    cout << endl;
    cout << numberOfEmpty << " ";
    for (int i = 0; i < eIndex; i++)
    {
        cout << emptyBuses[i] << " ";
    }

    return 0;
}