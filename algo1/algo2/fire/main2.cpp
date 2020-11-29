
#include <iostream>
#include <set>

using namespace std;

const int MAX_CITIES = 1001;
const int MAX_ROUTE = 2000;

int adjMatrix[MAX_CITIES][MAX_CITIES];
int fireDepartments[MAX_CITIES];
int fireDepartmentIndecies[MAX_CITIES];
int citiesOnFire[MAX_CITIES];

int main() {

    //read data
    int countOfCities;
    int countOfRoutes;
    int countOfFireDepartments;
    int countOfAlerts;

    cin >> countOfCities >> countOfRoutes >> countOfFireDepartments >> countOfAlerts;

    for (int i = 0; i <= countOfCities; ++i) {
        //fireDepartments[i] = 0;
        //citiesOnFire[i] = 0;
        for (int j = 0; j <= countOfCities; ++j) {
            // if (i == j) {
            // adjMatrix[i][j] = 0;
            //} else {
            adjMatrix[i][j] = MAX_ROUTE;
            // }
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
        fireDepartments[fireDepartment] = 1;
        fireDepartmentIndecies[i] = fireDepartment;
    }

    for (int i = 0; i < countOfAlerts; ++i) {
        int cityOnFire;
        cin >> cityOnFire;
        citiesOnFire[i] = cityOnFire;
    }

    auto cmp = [](pair<int, int> a, pair<int, int> b) { return a.second < b.second; };

    for (int i = 0; i < countOfAlerts; ++i) {
        int burningCity = citiesOnFire[i];
        if (fireDepartments[burningCity] == 1) {
            cout << burningCity << endl;
        } else {

            int d[MAX_CITIES];
            int pi[MAX_CITIES];
            for (int j = 0; j <= countOfCities; ++j) {
                d[j] = MAX_ROUTE;
                pi[j] = MAX_ROUTE;
            }

            std::set<pair<int, int>, decltype(cmp)> q(cmp);

            d[burningCity] = 0;
            pi[burningCity] = 0;
            q.insert(make_pair(burningCity, 0));

            while (!q.empty()) {
                pair<int, int> actual = *(q.begin());
                q.erase(q.begin());
                int u = actual.first;
                int *row = adjMatrix[u];
                for (int j = 0; j <= countOfCities; ++j) {
                    int edge = row[j];
                    int distance = d[u] + edge;
                    if (distance < d[j]) {
                        // pair<int, int> a = make_pair(j, d[j]);
                        // q.erase(a);
                        d[j] = distance;
                        pi[j] = u;
                        pair<int, int> p = make_pair(j, distance);
                        //q.erase()//todo erease old pair d[j] before update
                        q.insert(p);
                    }

                }

            }

            int minDistanceFireDepartment = fireDepartmentIndecies[0];
            int minDistance = d[minDistanceFireDepartment];
            for (int j = 1; j < countOfFireDepartments; ++j) {
                int fi = fireDepartmentIndecies[j];
                if (d[fi] < minDistance) {
                    minDistance = d[fi];
                    minDistanceFireDepartment = fi;
                }
            }

            int x = minDistanceFireDepartment;
            while (x != 0) {
                cout << x << " ";
                x = pi[x];
            }
            cout << endl;
        }

    }
}


