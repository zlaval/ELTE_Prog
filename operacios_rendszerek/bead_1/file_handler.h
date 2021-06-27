#include "patient.h"
#include "utils.h"

#ifndef BEAD_1_FILE_HANDLER_H
#define BEAD_1_FILE_HANDLER_H

void add(struct Patient patient);

void save(struct Patient *patients, int size, char *type);

void list();

void loadAll(struct Patient *patients, int *size);

void patientCount(int *size, int *nextId);


#endif //BEAD_1_FILE_HANDLER_H
