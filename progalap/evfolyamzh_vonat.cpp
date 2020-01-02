//missing edge case, only one/two station

#include <iostream>

using namespace std;

void readData();
void calculateTravelTime();
void countStationsGoThrough();
void findStationStayMoreThenPrevious();
void findStationPassagesWithoutStop();
void findLongestPassageStopInEveryStation();

int departures[1000];
int arrivals[1000];
int stationCount;

int main()
{
    cin >> stationCount;
    readData();
    calculateTravelTime();
    countStationsGoThrough();
    findStationStayMoreThenPrevious();
    findStationPassagesWithoutStop();
    findLongestPassageStopInEveryStation();
}

void readData()
{
    for (int i = 0; i < stationCount; i++)
    {
        cin >> arrivals[i] >> departures[i];
    }
}

void calculateTravelTime()
{
    int departTime;
    int arriveTime;
    for (int i = 0; i < stationCount; i++)
    {
        if (departures[i] == 0 && arrivals[i] != 0)
        {
            arriveTime = arrivals[i];
        }
        else if (departures[i] != 0 && arrivals[i] == 0)
        {
            departTime = departures[i];
        }
    }
    int travelTime = arriveTime - departTime;
    cout << "#" << endl;
    cout << travelTime << endl;
}

void countStationsGoThrough()
{
    int count = 0;
    for (int i = 0; i < stationCount; i++)
    {
        if (departures[i] != 0 && departures[i] == arrivals[i])
        {
            count++;
        }
    }
    cout << "#" << endl;
    cout << count << endl;
}

void findStationStayMoreThenPrevious()
{
    int previousStopTime = -1;
    int results[1000];
    int resultCount = 0;
    bool isNeverStopped = true;

    for (int i = 0; i < stationCount; i++)
    {
        int departure = departures[i];
        int arrival = arrivals[i];
        if (arrival != 0 && departure != 0 && departure != arrival)
        {

            isNeverStopped = false;
            int stopTime = departure - arrival;
            if (stopTime > previousStopTime && previousStopTime != -1)
            {
                results[resultCount++] = i + 1;
            }
            previousStopTime = stopTime;
        }
    }

    cout << "#" << endl;
    for (int i = 0; i < resultCount; i++)
    {
        cout << results[i] << " ";
    }
    if (isNeverStopped)
    {
        cout << -2;
    }
    else if (resultCount == 0)
    {
        cout << -1;
    }

    cout << endl;
}

void findStationPassagesWithoutStop()
{
    cout << "#" << endl;
    int startIndex = -1;
    int endIndex = -1;

    for (int i = 0; i < stationCount; i++)
    {
        int departure = departures[i];
        int arrival = arrivals[i];
        if (arrival != 0 && departure != 0 && departure == arrival)
        {
            if (startIndex == -1)
            {
                startIndex = i + 1;
                endIndex = startIndex;
            }
            else
            {
                endIndex = i + 1;
            }
        }

        if (endIndex != -1 && departure != arrival)
        {
            cout << startIndex << " " << endIndex << endl;
            startIndex = -1;
            endIndex = -1;
        }
    }
}

void findLongestPassageStopInEveryStation()
{
    int bestStart = -1;
    int bestEnd = -1;

    int actualStart = -1;
    int actualEnd = -1;

    bool stopped = false;

    for (int i = 0; i < stationCount; i++)
    {
        int departure = departures[i];
        int arrival = arrivals[i];
        if (arrival != 0 && departure != 0 && arrival != departure)
        {
            stopped = true;
            if (actualStart == -1)
            {
                actualStart = i + 1;
                actualEnd = actualStart;
            }
            else
            {
                actualEnd = i + 1;
            }
        }
        if (arrival != 0 && departure != 0 && arrival == departure)
        {
            if (actualEnd != -1)
            {
                int bestLength = bestEnd - bestStart;
                int actualLenth = actualEnd - actualStart;
                if (actualLenth > bestLength)
                {
                    bestStart = actualStart;
                    bestEnd = actualEnd;
                }
                actualStart = -1;
                actualEnd = -1;
            }
        }
    }

    cout << "#" << endl;
    if (!stopped)
    {
        cout << -1;
    }
    else if (bestEnd != -1)
    {
        cout << bestStart << " " << bestEnd;
    }
}