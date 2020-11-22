#include <iostream>
#include<queue>
#include <limits>

using namespace std;

const int N = 10001;

enum State {
    UNVISITED, VISITING, VISITED
};

struct Flight {
    int departureTimeMin;
    int flightDuration; //weight


};

struct Airport {
    int index;
    int flightTimeFromStart = INT_MAX;
    State state = UNVISITED;
    Airport *parent = nullptr;
};

Flight *matrix[N][N];


Airport airport[N];

int main() {
    int countOfCities;
    int directFlights;
    int departure;
    int arrival;

    cin >> countOfCities >> directFlights >> departure >> arrival;

    for (int i = 0; i < directFlights; i++) {
        int departureFrom;
        int arriveAt;
        int departureTimeMin;
        int flightDuration;
        cin >> departureFrom >> arriveAt >> departureTimeMin >> flightDuration;
        Flight flight = {departureTimeMin, flightDuration};
        Flight *flightPtr = &flight;
        matrix[departureFrom][arriveAt] = flightPtr;
    }

    priority_queue<Airport, vector<Airport>, greater<>> queue;
    //TODO greater on

    airport[departure].flightTimeFromStart = 0;
    airport[departure].index = departure;
    queue.push(airport[departure]);

    while (!queue.empty() && airport[arrival].state != VISITED) {
        Airport station = queue.top();
        int stationIndex = station.index;
        queue.pop();
        for (int adjIndex = 0; adjIndex < N; ++adjIndex) {
            airport[adjIndex].index = adjIndex;
            if (matrix[stationIndex][adjIndex] != nullptr) {
                int actualFlightTimeFrom = station.flightTimeFromStart + matrix[stationIndex][adjIndex]->flightDuration;
                if (actualFlightTimeFrom < airport[adjIndex].flightTimeFromStart) {
                    airport[adjIndex].flightTimeFromStart = actualFlightTimeFrom;
                    airport[adjIndex].parent = &station;

                    //TODO add to queue if not in and adjust

                }
                //TODO calc
            }


        }

    }


    return 0;
}
