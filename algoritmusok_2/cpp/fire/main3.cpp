#include <iostream>
#include <vector>

using namespace std;

const int MAX_CITIES = 1001;
const int MAX_ROUTE = 2000;

int adjMatrix[MAX_CITIES][MAX_CITIES];
int fireDepartments[MAX_CITIES];
int citiesOnFireIndices[MAX_CITIES];
int citiesOnFire[MAX_CITIES];

int shortestPathToCityWeights[MAX_CITIES];
string shortestPathToCity[MAX_CITIES];

vector<pair<int, int>> queue;

inline pair<int, int> remMiFromQueue() {
    pair<int, int> min = queue[0];
    int index = 0;
    for (int i = 1; i < queue.size(); ++i) {
        pair<int, int> actual = queue[i];
        if (actual.second < min.second) {
            min = actual;
            index = i;
        }
    }
    queue.erase(queue.begin() + index);
    return min;
}

inline void addToQueue(pair<int, int> pair) {
    for (int i = 0; i < queue.size(); ++i) {
        if (queue[i].first == pair.first) {
            queue.erase(queue.begin() + i);
            break;
        }
    }
    queue.push_back(pair);
}


int main() {

    //read data
    int countOfCities;
    int countOfRoutes;
    int countOfFireDepartments;
    int countOfAlerts;

    cin >> countOfCities >> countOfRoutes >> countOfFireDepartments >> countOfAlerts;

    for (int i = 0; i <= countOfCities; ++i) {
        shortestPathToCityWeights[i] = MAX_ROUTE;
        shortestPathToCity[i] = "";
        for (int j = 0; j <= countOfCities; ++j) {
            adjMatrix[i][j] = MAX_ROUTE;
        }
    }

    for (int i = 0; i < countOfRoutes; ++i) {
        int firstCity;
        int secondCity;
        int distance;

        cin >> firstCity >> secondCity >> distance;
        adjMatrix[firstCity][secondCity] = distance;
        adjMatrix[secondCity][firstCity] = distance;
    }

    for (int i = 0; i < countOfFireDepartments; ++i) {
        int fireDepartment;
        cin >> fireDepartment;
        fireDepartments[i] = fireDepartment;
    }

    for (int i = 0; i < countOfAlerts; ++i) {
        int cityOnFire;
        cin >> cityOnFire;
        citiesOnFireIndices[cityOnFire] = 1;
        citiesOnFire[i] = cityOnFire;
    }

    //calculate path
    for (int i = 0; i < countOfFireDepartments; ++i) {
        int fireDepartment = fireDepartments[i];

        int burningCityVisited = 0;
        int d[MAX_CITIES];
        int pi[MAX_CITIES];
        for (int j = 0; j <= countOfCities; ++j) {
            d[j] = MAX_ROUTE;
            pi[j] = MAX_ROUTE;
        }

        queue.clear();
        d[fireDepartment] = 0;
        pi[fireDepartment] = 0;
        addToQueue(make_pair(fireDepartment, 0));
        if (citiesOnFireIndices[fireDepartment] == 1) {
            shortestPathToCityWeights[fireDepartment] = 0;
            shortestPathToCity[fireDepartment] = to_string(fireDepartment);
        }

        //maybe it can run just til visited burning cities count neq alerts count
        while (!queue.empty()) {
            pair<int, int> actual = remMiFromQueue();
            int u = actual.first;
            int *row = adjMatrix[u];

            for (int j = 0; j <= countOfCities; ++j) {
                int edge = row[j];
                int distance = d[u] + edge;
                if (distance < d[j]) {
                    d[j] = distance;
                    pi[j] = u;
                    pair<int, int> p = make_pair(j, distance);
                    addToQueue(p);
                }
            }

            if (citiesOnFireIndices[u] == 1) {
                if (d[u] < shortestPathToCityWeights[u]) {
                    shortestPathToCityWeights[u] = d[u];
                    int s = u;
                    string path = "";
                    while (s != 0) {
                        path = to_string(s) + " " + path;
                        s = pi[s];
                    }
                    shortestPathToCity[u] = path;
                }
                burningCityVisited++;
            }
        }
    }

    for (int i = 0; i < countOfAlerts; ++i) {
        cout << shortestPathToCity[citiesOnFire[i]] << endl;
    }

}


