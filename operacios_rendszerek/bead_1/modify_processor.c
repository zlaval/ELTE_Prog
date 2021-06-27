#include "modify_processor.h"

void modifyPatient() {
    char *id = readIn("Patient ID to modify");
    int identifier = atoi(id);

    struct Patient patients[1000];
    int size;

    loadAll(patients, &size);

    int found = 0;
    int index = 0;
    struct Patient patient;
    for (int i = 0; i < size; i++) {
        struct Patient p = patients[i];
        if (p.id == identifier) {
            patient = p;
            index = i;
            found = 1;
        }
    }

    if (found == 1) {
        printf("Type the new value or press enter to not modify!\n");

        char *name = readBack("Name", patient.name);

        char *birth;
        do {
            birth = readBack("Year of birth (4 digits)", patient.yearOfBirth);
        } while (strlen(birth) > 0 && (isNumber(birth) == 1 || strlen(birth) != 4));

        char *phoneNumber = readBack("Phone number", patient.phoneNumber);

        char *paid;
        do {
            paid = readBack("Paid (yes/no)", patient.paid);
        } while (strlen(paid) > 0 && strcmp(paid, "no") != 0 && strcmp(paid, "yes") != 0);

        if (strlen(name) > 1 == 1) {
            strcpy(patient.name, name);
        }
        if (strlen(paid) > 1 == 1) {
            strcpy(patient.paid, paid);
        }

        if (strlen(phoneNumber) > 1 == 1) {
            strcpy(patient.phoneNumber, phoneNumber);
        }

        if (strlen(birth) > 1 == 1) {
            strcpy(patient.yearOfBirth, birth);
        }
        patients[index] = patient;
        save(patients, size, "w");
    } else {
        printf("Patient not found! \n");
    }


}
