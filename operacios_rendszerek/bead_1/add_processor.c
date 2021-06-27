#include "add_processor.h"

void addPatient() {
    char *name = readIn("Name");

    char *birth;
    do {
        birth = readIn("Year of birth (4 digits)");
    } while (isNumber(birth) == 1 || strlen(birth) != 4);

    char *phoneNumber = readIn("Phone number");

    char *paid;
    do {
        paid = readIn("Paid (yes/no)");
    } while (strcmp(paid, "no") != 0 && strcmp(paid, "yes") != 0);

    struct Patient patient;

    strcpy(patient.name, name);
    strcpy(patient.paid, paid);
    strcpy(patient.phoneNumber, phoneNumber);
    strcpy(patient.yearOfBirth, birth);
    patient.vaccinated = 0;

    add(patient);
}

