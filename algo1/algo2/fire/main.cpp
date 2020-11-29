#include <iostream>

using namespace std;

const int MAX_CITIES = 1001;
const int MAX_ROUTE = 2000;

int adjMatrix[MAX_CITIES][MAX_CITIES];
int parentMatrix[MAX_CITIES][MAX_CITIES];
int fireDepartments[MAX_CITIES];
int citiesOnFire[MAX_CITIES];

int main() {

    int countOfCities;
    int countOfRoutes;
    int countOfFireDepartments;
    int countOfAlerts;

    cin >> countOfCities >> countOfRoutes >> countOfFireDepartments >> countOfAlerts;

    for (int i = 0; i <= countOfCities; ++i) {
        fireDepartments[i] = 0;
        citiesOnFire[i] = 0;
        for (int j = 0; j <= countOfCities; ++j) {
            if (i == j) {
                adjMatrix[i][j] = 0;
            } else {
                adjMatrix[i][j] = MAX_ROUTE;
            }
            parentMatrix[i][j] = 0;
        }
    }

    for (int i = 0; i < countOfRoutes; ++i) {
        int firstCity;
        int secondCity;
        int distance;

        cin >> firstCity >> secondCity >> distance;
        adjMatrix[firstCity][secondCity] = distance;
        adjMatrix[secondCity][firstCity] = distance;

        parentMatrix[firstCity][secondCity] = firstCity;
        parentMatrix[secondCity][firstCity] = secondCity;

    }

    for (int i = 0; i < countOfFireDepartments; ++i) {
        int fireDepartment;
        cin >> fireDepartment;
        fireDepartments[fireDepartment] = 1;
    }

    for (int i = 0; i < countOfAlerts; ++i) {
        int cityOnFire;
        cin >> cityOnFire;
        citiesOnFire[i] = cityOnFire;
    }


    for (int k = 1; k <= countOfCities; ++k) {
        for (int i = 1; i <= countOfCities; ++i) {
            for (int j = 1; j <= countOfCities; ++j) {
                int ijPathWeight = adjMatrix[i][k] + adjMatrix[k][j];
                if (adjMatrix[i][j] > ijPathWeight) {
                    adjMatrix[i][j] = ijPathWeight;
                    parentMatrix[i][j] = parentMatrix[k][j];
                }
            }
        }
    }


    for (int i = 0; i < countOfAlerts; ++i) {
        int alarmIndex = citiesOnFire[i];
        if (fireDepartments[alarmIndex] == 1) {
            cout << alarmIndex << endl;
        } else {
            auto routes = adjMatrix[alarmIndex];
            int min = 1010;
            int minIndex = 0;
            for (int j = 0; j <= countOfCities; ++j) {
                int routeDistance = routes[j];
                if (routeDistance != 0 && routeDistance < min) {
                    if (fireDepartments[j] == 1) {
                        min = routeDistance;
                        minIndex = j;
                    }
                }
            }

            cout << minIndex << " ";
            int through = parentMatrix[alarmIndex][minIndex];
            while (through != 0 && through != alarmIndex) {
                cout << through << " ";
                through = parentMatrix[alarmIndex][through];
            }

            cout << alarmIndex << " ";

            cout << endl;
        }


    }

}
