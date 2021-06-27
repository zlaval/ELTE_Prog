#include "delete_processor.h"


void removePatient() {
    char *id = readIn("Patient ID to delete");
    int identifier = atoi(id);

    struct Patient patients[1000];
    int size;

    loadAll(patients, &size);

    struct Patient newPatients[size];
    int k = 0;
    for (int i = 0; i < size; i++) {
        struct Patient p = patients[i];
        if (p.id != identifier) {
            newPatients[k++] = p;
        }
    }

    if (size == 0 || k == size) {
        printf("Patient not found!\n");
    }

    save(newPatients, k, "w");
}