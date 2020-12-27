#include <iostream>
#include<queue>

using namespace std;

enum State {
    UNVISITED, VISITING, VISITED
};

const int INT_MAXVALUE = 2147483647;

struct Flight;


struct Airport {
    Airport(int name) : name(name) {}
    int name;
    int flightTimeFromStart = INT_MAXVALUE;
    bool inQueue = false;
    State state = UNVISITED;
    Airport *predecessor = nullptr;
    vector<Flight *> flightsTo;
};

struct Flight {
    Flight(int departureTimeOnDayInMins, int flightDuration, Airport *departure, Airport *arrival)
        : departureTimeOnDayInMins(departureTimeOnDayInMins), flightDuration(flightDuration), departure(departure),
          arrival(arrival) {}
    int departureTimeOnDayInMins;
    int flightDuration;
    Airport *departure = nullptr;
    Airport *arrival = nullptr;
};

struct ComparePath {
    bool operator()(Airport *a1, Airport *a2) {
        return a1->flightTimeFromStart > a2->flightTimeFromStart;
    }
};

int calculateDurationToNextStation(Airport *airport, Flight *flight, Airport *departure);

Airport *airports[10001];

const int DAY_MINS = 1440;

int main() {
    int countOfCities;
    int directFlights;
    int departureIndex;
    int arrivalIndex;

    cin >> countOfCities >> directFlights >> departureIndex >> arrivalIndex;

    Airport *departure = new Airport(departureIndex);
    Airport *arrival = new Airport(arrivalIndex);
    airports[departureIndex] = departure;
    airports[arrivalIndex] = arrival;

    for (int i = 0; i < directFlights; i++) {
        int departureFrom;
        int arriveAt;
        int departureTimeInMins;
        int flightDuration;
        cin >> departureFrom >> arriveAt >> departureTimeInMins >> flightDuration;

        Airport *from = airports[departureFrom];
        Airport *to = airports[arriveAt];

        if (from == nullptr) {
            from = new Airport(departureFrom);
            airports[departureFrom] = from;
        }
        if (to == nullptr) {
            to = new Airport(arriveAt);
            airports[arriveAt] = to;
        }

        Flight *flight = new Flight(departureTimeInMins, flightDuration, from, to);
        from->flightsTo.push_back(flight);
    }

    priority_queue<Airport *, vector<Airport *>, ComparePath> pq;

    departure->flightTimeFromStart = 0;
    departure->inQueue = true;
    pq.push(departure);
    while (!pq.empty()) {
        Airport *start = pq.top();
        pq.pop();
        start->inQueue = false;
        for (Flight *flight: start->flightsTo) {
            Airport *target = flight->arrival;
            target->state = VISITING;
            int flightDuration = calculateDurationToNextStation(start, flight,departure);
            if (flightDuration < target->flightTimeFromStart) {
                target->flightTimeFromStart = flightDuration;
                target->predecessor = start;
                if (!target->inQueue) {
                    target->inQueue = true;
                    pq.push(target);
                }
            }
        }
        make_heap(const_cast<Airport **>(&pq.top()),
                  const_cast<Airport **>(&pq.top()) + pq.size(),
                  ComparePath()
        );

        start->state = VISITED;
    }


    if (arrival->flightTimeFromStart != INT_MAXVALUE && arrival->predecessor != nullptr) {
        cout << arrival->flightTimeFromStart << endl;
        int sumOf = 0;
        string result = "";
        Airport *actualAirport = arrival->predecessor;
        while (actualAirport != nullptr) {
            sumOf++;
            result = to_string(actualAirport->name) + " " + result;
            actualAirport = actualAirport->predecessor;
        }
        cout << sumOf << " " << result;
    } else {
        cout << "0";
    }


    return 0;
}

int calculateDurationToNextStation(Airport *airport, Flight *flight, Airport *departure) {
    int minTimeToDeparture = 0;
    if (airport != departure) {
        minTimeToDeparture = (airport->flightTimeFromStart + 60) % DAY_MINS;
    }
    int waitTime;
    if (flight->departureTimeOnDayInMins < minTimeToDeparture) {
        waitTime = DAY_MINS - ((airport->flightTimeFromStart % DAY_MINS) - flight->departureTimeOnDayInMins);
    } else {
        waitTime = flight->departureTimeOnDayInMins - (airport->flightTimeFromStart % DAY_MINS);
    }
    return airport->flightTimeFromStart + flight->flightDuration + waitTime;
}

