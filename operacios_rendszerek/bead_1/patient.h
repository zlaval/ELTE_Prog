#ifndef PATIENT_H
#define PATIENT_H

struct Patient {
    int id;
    char name[50];
    char paid[10];
    char yearOfBirth[10];
    char phoneNumber[20];
    int vaccinated;
};

#endif //BEAD_1_PATIENT_H
