#include "file_handler.h"

const char *FILE_NAME = "database.txt";

void add(struct Patient patient) {
    int size;
    int nextId;
    patientCount(&size, &nextId);
    patient.id = nextId;
    save(&patient, 1, "a");
}

void patientCount(int *size, int *nextId) {
    *size = 0;
    *nextId = 0;
    struct Patient patients[1000];
    loadAll(patients, size);
    if (size > 0) {
        int i = 0;
        int maxId = 0;
        while (i < *size) {
            if (patients[i].id > maxId) {
                maxId = patients[i].id;
            }
            i++;
        }
        *nextId = maxId + 1;
    }
}

void list() {
    struct Patient patients[1000];
    int size;
    loadAll(patients, &size);
    if (size <= 0) {
        printf("No record found!\n");
    }
    for (int i = 0; i < size; ++i) {
        struct Patient patient = patients[i];
        char *vaccinated;
        if (patient.vaccinated == 0) {
            vaccinated = "no";
        } else {
            vaccinated = "yes";
        }
        printf("id: %d\nname: %s\nbirth: %s\nphone: %s\npaid: %s\nvaccinated: %s\n\n", patient.id, patient.name,
               patient.yearOfBirth,
               patient.phoneNumber, patient.paid, vaccinated);
    }
}

void save(struct Patient *patients, int size, char *type) {
    FILE *file;
    file = fopen(FILE_NAME, type);
    if (file == NULL) {
        printf("Can't open the file and save the patient, please try again!\n");
        return;
    }
    for (int i = 0; i < size; ++i) {
        fwrite(&patients[i], sizeof(struct Patient), 1, file);
    }
    fclose(file);
}

void loadAll(struct Patient *patients, int *size) {
    FILE *file;
    file = fopen(FILE_NAME, "r");
    if (file == NULL) {
        *size = 0;
        return;
    }
    int i = 0;
    while (fread(&patients[i++], sizeof(struct Patient), 1, file));
    *size = i - 1;
}
