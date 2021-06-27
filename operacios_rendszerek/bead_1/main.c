#include "add_processor.h"
#include "delete_processor.h"
#include "modify_processor.h"
#include "vaccination_processor.h"
#include "mock_generator.h"

void run();

void listPatient();

void processCommand(char *command);

int main() {
    run();
    printf("\nbye\n");
    return 0;
}

void printCommands() {
    printf("\nAvalilable commands:\n\n");
    printf("list      List the patients\n");
    printf("add       Add new patient\n");
    printf("delete    Delete a patient\n");
    printf("modify    Modify a patient\n");
    printf("vaccinate Start the vaccination\n");
    printf("mock      Put mock patients into database\n");
    printf("exit      Exit from the application\n\n");
}

void run() {
    printCommands();
    int exitResult = 1;
    while (exitResult != 0) {
        char *command = 0;
        size_t length = 0;
        printf("Type a command: ");
        getline(&command, &length, stdin);
        exitResult = strcmp(command, "exit\n");
        processCommand(command);
        if (exitResult != 0) {
            printCommands(command);
        }
    }
}

void processCommand(char *command) {
    if (strcmp(command, "list\n") == 0) {
        listPatient();
    } else if (strcmp(command, "add\n") == 0) {
        addPatient();
    } else if (strcmp(command, "delete\n") == 0) {
        removePatient();
    } else if (strcmp(command, "modify\n") == 0) {
        modifyPatient();
    } else if (strcmp(command, "vaccinate\n") == 0) {
        startVaccination();
    } else if (strcmp(command, "mock\n") == 0) {
        generateMock();
    } else if (strcmp(command, "exit\n") == 0) {
    } else {
        printf("\nInvalid command, please try again\n\n");
    }
}

void listPatient() {
    printf("Patient list: \n\n");
    list();
}
