#include "mock_generator.h"

static char *randomString(char *str, int size) {
    const char charset[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    --size;
    for (int n = 0; n < size; n++) {
        if (n == size / 2) {
            str[n] = ' ';
        } else {
            int key = rand() % (int) (sizeof charset - 1);
            str[n] = charset[key];
        }
    }
    str[size] = '\0';
    return str;
}

void generateMock() {
    char *patientsCountStr = readIn("Mock patients count");
    int patientCount = atoi(patientsCountStr);
    for (int i = 0; i < patientCount; ++i) {
        struct Patient patient;
        char name[10];
        randomString(name, 10);
        strcpy(patient.name, name);
        strcpy(patient.paid, "no");
        strcpy(patient.phoneNumber, "0660555666");
        strcpy(patient.yearOfBirth, "1987");
        patient.vaccinated = 0;
        add(patient);
    }

}
