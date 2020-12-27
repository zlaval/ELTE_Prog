/* Developer: Zalan Toth
 * Neptun: cz72ym
 * Email: zlaval@gmail.com
 * Task: Minimal temperature on a settlement in any time
 */

#include <iostream>
#include <string>

using namespace std;

//If active, the program use simplified output message and input read, without validation.
//#define BIRO

//Messages to the user's input read
const string SETTLEMENTS_USER_MESSAGE = "Please enter the number of the settlements";
const string DAYS_USER_MESSAGE = "Please enter the number of the days";

//Max number of settlements (N) and days (M)
const int MAX_COUNT = 1000;
//Min number of settlements (N) and days (M)
const int MIN_COUNT = 1;

//Accepted range of the given temperatures
const int MIN_TEMPERATURE = -50;
const int MAX_TEMPERATURE = +50;

// The (H) two dimensional array to hold the temperature measurements.
// Real data size: first dimension is settlementCount (N), second is numberOfDays (M)
int measures[MAX_COUNT][MAX_COUNT];


/**
 * Reads a number from the input.
 * If BIRO directive is not defined, asks for valid input while it is invalid.
 * @param num: reference to value which will hold the given number from input
 * @param userMessage: instruction to the user
 * @param validate: function pointer to the validate function
 */
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

/**
 * Create a message to the user to enter the @row-th settlement @cols-th day of temperature
 * @param row
 * @param col
 * @return the UI message
 */
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

/**
 * Print the settlements count and the indices of settlements, where any minimum value was found
 * @param resultCount
 * @param settlementCount
 * @param containsMinimums
 */
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

/**
 * Checks if @count is in the accepted range.
 * @param count
 * @return @true if invalid, else @false
 */
bool isInvalidCount(const int &count) {
    if (count < MIN_COUNT || count > MAX_COUNT) {
        cout << count << " is not in the range " << MIN_COUNT << " and " << MAX_COUNT << endl;
        return true;
    }
    return false;
}

/**
 * Checks if @temperature data is in the accepted range.
 * @param temperature
 * @return @true if invalid, else @false
 */
bool isInvalidMeasure(const int &temperature) {
    if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
        cout << temperature << " Celsius is not in the range " << MIN_TEMPERATURE << " and " << MAX_TEMPERATURE << endl;
        return true;
    }
    return false;
}

/**
 * Reads the count of the settlements from the standard input
 * @param settlementCount: reference to the value, read from input
 */
void readSettlementsCount(int &settlementCount) {
    readNumber(settlementCount, SETTLEMENTS_USER_MESSAGE, isInvalidCount);
}


/**
 * Reads the number of days when temperature is measured
 * @param numberOfDays: reference to the value, read from input
 */
void readNumberOfDays(int &numberOfDays) {
    readNumber(numberOfDays, DAYS_USER_MESSAGE, isInvalidCount);
}

/**
 * Reads @settlementCount number of temperature measurements into the @H matrix
 * @param settlementCount
 * @param numberOfDays
 */
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

/**
 * Find the minimum temperature of the given day.
 * @param dayIndex
 * @param settlementCount
 * @return the minimum temperature of the given day
 */
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

/**
 * Find the minimum temperature to each days and collect its to an array.
 * @param settlementCount
 * @param numberOfDays
 * @param minimumTemperatures: reference to the array, which hold the daily minimums
 */
void calculateMinimumTemperatureOnEachDays(const int &settlementCount,
                                           const int &numberOfDays,
                                           int *const minimumTemperatures) {
    for (int i = 0; i < numberOfDays; i++) {
        minimumTemperatures[i] = calculateMinimumTemperatureOfGivenDay(i, settlementCount);
    }
}

/**
 * Initialize the given bool array with @false values
 * @param array
 */
void initializeResultContainerArray(bool *const array) {
    for (int i = 0; i < MAX_COUNT; i++) {
        array[i] = false;
    }
}

/**
 * Mark the settlements where daily minimum can be found
 * @param settlementCount
 * @param numberOfDays
 * @param resultCount
 * @param containsMinimums
 * @param minimumTemperatures
 */
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

/**
 * The entry point of the application
 */
int main() {
    //Number of settlements (settlementCount=>N), days (numberOfDays=>M) and result count (resultCount=>T)
    int settlementCount, numberOfDays, resultCount;
    //Hold true if the settlements on the given index contains minimum, else false
    bool containsMinimum[MAX_COUNT];
    //Hold the minimum temperatures of each day
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
