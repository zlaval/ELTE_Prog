#include <iostream>
#include <string>

using namespace std;

//#define BIRO

const string SETTLEMENTS_USER_MESSAGE = "Please enter the number of the settlements";
const string DAYS_USER_MESSAGE = "Please enter the number of the days";

const int MAX_COUNT = 1000;
const int MIN_COUNT = 1;

const int MIN_TEMPERATURE = -50;
const int MAX_TEMPERATURE = +50;

int measures[MAX_COUNT][MAX_COUNT];

void readNumber(int &num, const string &userMessage, bool (*validate)(const int &)) {
#ifdef BIRO
    cin >> num;
#else
    bool error = false;
    string str;
    do {
        cout << userMessage << ": ";
        cin >> num;
        bool nan = cin.fail();
        error = nan ? nan : validate(num);
        if (error) {
            cout << "Unexpected data: ";
            if (nan) {
                cout << "NAN!";
            } else {
                cout << num;
            }
            cout << ", please enter a valid number within the given range!";
            cin.clear();
            getline(cin, str);
        }
        cout << endl;
    } while (error);
#endif
}

string createTemperatureReadMessage(const int &row, const int &col) {
#ifdef BIRO
    return "";
#else
    string message = "Please enter the ";
    message.append(to_string(row));
    message.append(". settlement's ");
    message.append(to_string(col));
    message.append(". temperature");
    return message;
#endif
}

void printResult(const int &resultCount, const int &settlementCount, const bool *const containsMinimums) {
#ifdef BIRO
    cout << resultCount << " ";
#else
    cout << "Number of settlements contains a daily minimum temperature: " << resultCount << endl;
#endif
    for (int i = 0; i < settlementCount; i++) {
        if (containsMinimums[i]) {
            cout << (i + 1) << " ";
        }
    }
}

bool isInvalidCount(const int &count) {
    if (count < MIN_COUNT || count > MAX_COUNT) {
        cout << count << " is not in the range " << MIN_COUNT << " and " << MAX_COUNT << endl;
        return true;
    }
    return false;
}

bool isInvalidMeasure(const int &temperature) {
    if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
        cout << temperature << " Celsius is not in the range " << MIN_TEMPERATURE << " and " << MAX_TEMPERATURE << endl;
        return true;
    }
    return false;
}

void readSettlementsCount(int &settlementCount) {
    readNumber(settlementCount, SETTLEMENTS_USER_MESSAGE, isInvalidCount);
}

void readNumberOfDays(int &numberOfDays) {
    readNumber(numberOfDays, DAYS_USER_MESSAGE, isInvalidCount);
}

void readMeasures(const int &settlementCount, const int &numberOfDays) {
    for (int i = 0; i < settlementCount; i++) {
        for (int j = 0; j < numberOfDays; j++) {
            string message = createTemperatureReadMessage(i + 1, j + 1);
            int measure;
            readNumber(measure, message, isInvalidMeasure);
            measures[i][j] = measure;
        }
    }
}

int calculateMinimumTemperatureOfGivenDay(const int &dayIndex, const int &settlementCount) {
    int minTemperatureOfDay = MAX_TEMPERATURE;
    for (int i = 0; i < settlementCount; i++) {
        int actualTemperature = measures[i][dayIndex];
        if (actualTemperature < minTemperatureOfDay) {
            minTemperatureOfDay = actualTemperature;
        }
    }
    return minTemperatureOfDay;
}

void calculateMinimumTemperatureOnEachDays(const int &settlementCount,
                                           const int &numberOfDays,
                                           int *const minimumTemperatures) {
    for (int i = 0; i < numberOfDays; i++) {
        minimumTemperatures[i] = calculateMinimumTemperatureOfGivenDay(i, settlementCount);
    }
}

void initializeResultContainerArray(bool *const array) {
    for (int i = 0; i < MAX_COUNT; i++) {
        array[i] = false;
    }
}

void signMinimums(const int &settlementCount,
                  const int &numberOfDays,
                  const int *const minimumTemperatures,
                  int &resultCount,
                  bool *const containsMinimums) {

    resultCount = 0;
    for (int i = 0; i < numberOfDays; i++) {
        int minTempOfDay = minimumTemperatures[i];
        for (int j = 0; j < settlementCount; j++) {
            int actualTemperature = measures[j][i];
            if (actualTemperature == minTempOfDay && !containsMinimums[j]) {
                containsMinimums[j] = true;
                resultCount++;
            }
        }
    }
}

int main() {
    int settlementCount, numberOfDays, resultCount;
    bool containsMinimum[MAX_COUNT];
    int minimumTemperatures[MAX_COUNT];

    readSettlementsCount(settlementCount);
    readNumberOfDays(numberOfDays);
    readMeasures(settlementCount, numberOfDays);
    initializeResultContainerArray(containsMinimum);
    calculateMinimumTemperatureOnEachDays(settlementCount, numberOfDays, minimumTemperatures);
    signMinimums(settlementCount, numberOfDays, minimumTemperatures, resultCount, containsMinimum);
    printResult(resultCount, settlementCount, containsMinimum);

    return 0;
}