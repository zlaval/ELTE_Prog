#include <iostream>

using namespace std;

const int MAX_CITIES = 1010;
const int MAX_ROUTE = 2147483647;

int adjMatrix[MAX_CITIES][MAX_CITIES];
int parentMatrix[MAX_CITIES][MAX_CITIES];
int fireDepartments[MAX_CITIES];
int citiesOnFire[MAX_CITIES];

int main() {

    for (int i = 0; i < MAX_CITIES; ++i) {
        fireDepartments[i] = 0;
        citiesOnFire[i] = 0;
        for (int j = 0; j < MAX_CITIES; ++j) {
            if (i == j) {
                adjMatrix[i][j] = 0;
            } else {
                adjMatrix[i][j] = MAX_ROUTE;
            }
            parentMatrix[i][j] = 0;
        }
    }

    int countOfCities;
    int countOfRoutes;
    int countOfFireDepartments;
    int countOfAlerts;

    cin >> countOfCities >> countOfRoutes >> countOfFireDepartments >> countOfAlerts;

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

    //TODO can use less array portion
    for (int i = 0; i < countOfFireDepartments; ++i) {
        int fireDepartment;
        cin >> fireDepartment;
        fireDepartments[i] = fireDepartment;
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
                if (adjMatrix[i][k] == MAX_ROUTE || adjMatrix[k][j] == MAX_ROUTE) {
                    ijPathWeight = MAX_ROUTE;
                }
                if (adjMatrix[i][j] > ijPathWeight) {
                    adjMatrix[i][j] = ijPathWeight;
                    parentMatrix[i][j] = parentMatrix[k][j];
                }
            }
        }
    }


    for (int i = 0; i < countOfAlerts; ++i) {

        int alarmIndex = citiesOnFire[i];

        bool isFireDep = false;
        int k = 0;
        while (!isFireDep && k <= countOfFireDepartments) {
            isFireDep = fireDepartments[k] == alarmIndex;
            k++;
        }

        if (isFireDep) {
            cout << alarmIndex << endl;
        } else {
            auto routes = adjMatrix[alarmIndex];
            int min = 1010;
            int minIndex = 0;
            for (int j = 0; j <= countOfCities; ++j) {
                int routeDistance = routes[j];
                if (routeDistance != 0 && routeDistance < min) {
                    isFireDep = false;
                    int z = 0;
                    while (!isFireDep && z < countOfFireDepartments) {
                        isFireDep = fireDepartments[z] == j;
                        z++;
                    }
                    if (isFireDep) {
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
