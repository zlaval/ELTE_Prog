#ifndef BEAD_1_FILE_HANDLER_H

#include "file_handler.h"

#endif

#ifndef BEAD_1_VACCINATION_PROCESSOR_H
#define BEAD_1_VACCINATION_PROCESSOR_H

void startVaccination();

void startProcess(const struct Patient *patients, int size);

void startBus(const char *name, const char *childToParentName, const char *parentToChildName);
#endif
