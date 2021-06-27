#include "vaccination_processor.h"
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>

const char *BUS_ONE_PARENT_TO_CHILD = "/tmp/bus_one_sender_cz72ym";
const char *BUS_ONE_CHILD_TO_PARENT = "/tmp/bus_one_receiver_cz72ym";
const char *BUS_TWO_PARENT_TO_CHILD = "/tmp/bus_two_sender_cz72ym";
const char *BUS_TWO_CHILD_TO_PARENT = "/tmp/bus_two_receiver_cz72ym";


void updatePatientToVaccinated(int id) {
    struct Patient patients[1000];
    int size;
    loadAll(patients, &size);
    int index = 0;
    for (int i = 0; i < size; i++) {
        struct Patient p = patients[i];
        if (p.id == id) {
            index = i;
        }
    }
    patients[index].vaccinated = 1;
    save(patients, size, "w");
}

void startVaccination() {
    srand(time(NULL));
    struct Patient patients[1000];
    int size;
    loadAll(patients, &size);

    struct Patient filteredPatients[10];
    int filteredPatientsSize = 0;

    for (int i = 0; i < size && filteredPatientsSize < 10; ++i) {
        struct Patient patient = patients[i];
        if (patient.vaccinated == 0) {
            filteredPatients[filteredPatientsSize++] = patient;
        }
    }

    if (filteredPatientsSize < 5) {
        printf("Only %d people are waiting for vaccination, bus does not start today.\n", filteredPatientsSize);
        return;
    }
    startProcess(filteredPatients, filteredPatientsSize);
}

void createPipe(const char *pipeName) {
    int pipeId = mkfifo(pipeName, S_IRUSR | S_IWUSR);
    if (pipeId == -1) {
        printf("Error creating pipe!");
    }
}

void waitForStartSignal(int pipe, const char *name) {
    char start[11];
    read(pipe, start, sizeof(start));
    printf("%s bus signed its ready to accept patients! Start sending...\n", name);
}

void closePipe(int pipe, const char *name) {
    close(pipe);
    unlink(name);
}

void sendPatient(int pipe, const char *name, const struct Patient *patient) {
    printf("Send %s to the %s bus.\n", patient->name, name);
    write(pipe, patient, sizeof(struct Patient));
}

void updatePatient(int pipe) {
    struct Patient patient;
    read(pipe, &patient, sizeof(struct Patient));
    updatePatientToVaccinated(patient.id);
    printf("%s vaccinated. Multipass was sent.\n", patient.name);
}

void startProcess(const struct Patient *patients, int size) {
    createPipe(BUS_ONE_PARENT_TO_CHILD);
    createPipe(BUS_ONE_CHILD_TO_PARENT);

    int pid = fork();
    if (pid > 0) {
        if (size > 9) {
            createPipe(BUS_TWO_CHILD_TO_PARENT);
            createPipe(BUS_TWO_PARENT_TO_CHILD);

            int pid2 = fork();
            if (pid2 > 0) {//parent with two children
                printf("Two buses were sent.\n");
                int childToParentFirst = open(BUS_ONE_CHILD_TO_PARENT, O_RDONLY);
                int parentToChildFirst = open(BUS_ONE_PARENT_TO_CHILD, O_WRONLY);
                int childToParentSecond = open(BUS_TWO_CHILD_TO_PARENT, O_RDONLY);
                int parentToChildSecond = open(BUS_TWO_PARENT_TO_CHILD, O_WRONLY);

                waitForStartSignal(childToParentFirst, "First");
                waitForStartSignal(childToParentSecond, "Second");

                for (int i = 0; i < 5; ++i) {
                    sendPatient(parentToChildFirst, "first", &patients[i]);
                    sendPatient(parentToChildSecond, "second", &patients[i + 5]);
                }

                int vaccinatedCount;
                read(childToParentFirst, &vaccinatedCount, sizeof(int));
                for (int i = 0; i < vaccinatedCount; i++) {
                    updatePatient(childToParentFirst);
                }

                read(childToParentSecond, &vaccinatedCount, sizeof(int));
                for (int i = 0; i < vaccinatedCount; i++) {
                    updatePatient(childToParentSecond);
                }

                closePipe(childToParentFirst, BUS_ONE_CHILD_TO_PARENT);
                closePipe(parentToChildFirst, BUS_ONE_PARENT_TO_CHILD);
                closePipe(childToParentSecond, BUS_TWO_CHILD_TO_PARENT);
                closePipe(parentToChildSecond, BUS_TWO_PARENT_TO_CHILD);
            } else { //child - second bus [5,9]
                printf("Second bus was sent.\n");
                startBus("Second", BUS_TWO_CHILD_TO_PARENT, BUS_TWO_PARENT_TO_CHILD);
            }

        } else { //parent with one child
            printf("Only one bus was sent.\n");
            int childToParent = open(BUS_ONE_CHILD_TO_PARENT, O_RDONLY);
            int parentToChild = open(BUS_ONE_PARENT_TO_CHILD, O_WRONLY);
            waitForStartSignal(childToParent, "First");
            for (int i = 0; i < 5; ++i) {
                sendPatient(parentToChild, "first", &patients[i]);
            }

            int vaccinatedCount;
            read(childToParent, &vaccinatedCount, sizeof(int));

            for (int i = 0; i < vaccinatedCount; i++) {
                updatePatient(childToParent);
            }

            closePipe(childToParent, BUS_ONE_CHILD_TO_PARENT);
            closePipe(parentToChild, BUS_ONE_PARENT_TO_CHILD);
        }

    } else { //child - first bus [0,4]
        printf("First bus was sent.\n");
        startBus("First", BUS_ONE_CHILD_TO_PARENT, BUS_ONE_PARENT_TO_CHILD);
    }

}

void startBus(const char *name, const char *childToParentName, const char *parentToChildName) {
    int childToParent = open(childToParentName, O_WRONLY);
    int parentToChild = open(parentToChildName, O_RDONLY);

    write(childToParent, "HARCRA_FEL\n", 11);
    printf("%s bus are waiting for the patients.\n", name);

    struct Patient vaccinatedPatients[5];
    int numberOfVaccinated = 0;

    for (int i = 0; i < 5; ++i) {
        struct Patient patient;
        read(parentToChild, &patient, sizeof(struct Patient));

        int chance = rand() % 100 + 1;
        if (chance < 90) {
            vaccinatedPatients[numberOfVaccinated] = patient;
            printf("%s arrived at the %s bus. Vaccine was given.\n", vaccinatedPatients[numberOfVaccinated].name, name);
            numberOfVaccinated++;
        } else {
            printf("%s did not arrive at the %s bus.\n", patient.name, name);
        }
    }

    write(childToParent, &numberOfVaccinated, sizeof(int));
    for (int j = 0; j < numberOfVaccinated; j++) {
        write(childToParent, &vaccinatedPatients[j], sizeof(struct Patient));
    }

    close(childToParent);
    close(parentToChild);
    printf("%s bus has finished the vaccination!\n", name);
    exit(0);
}
