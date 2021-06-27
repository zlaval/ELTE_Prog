#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>


const char *PARENT_TO_CHILD_1 = "/tmp/parent_to_child_1_cz72ym_18";
const char *CHILD_1_TO_CHILD_2 = "/tmp/child_1_to_child_2_cz72ym_18";
const char *CHILD_2_TO_PARENT = "/tmp/child_2_to_parent_cz72ym_18";

struct voterMsg {
    int id;
    int canVote;
};

struct voteStatMsg {
    int success;
    int failed;
};

void handler(int sig) {
    printf("Child is ready %i\n", sig);
}

int isNumber(const char *str) {
    int i = 0;
    while (str[i] != '\0') {
        if (isdigit(str[i]) == 0) {
            return 1;
        }
        i++;
    }
    return 0;
}

int readNumber(char *label) {
    char *input = 0;
    do {
        size_t length = 0;
        printf("%s: ", label);
        getline(&input, &length, stdin);
        input[strlen(input) - 1] = 0;
    } while (isNumber(input) == 1);
    return atoi(input);
}

void createPipe(const char *pipeName) {
    int pipeId = mkfifo(pipeName, S_IRUSR | S_IWUSR);
    if (pipeId == -1) {
        printf("Error creating pipe!");
    } else {
        printf("Pipe created %s\n", pipeName);
    }
}

void closePipe(int pipe, const char *name) {
    close(pipe);
    unlink(name);
}

int main() {
    srand(time(NULL));
    signal(SIGTERM, handler);
    createPipe(PARENT_TO_CHILD_1);
    createPipe(CHILD_1_TO_CHILD_2);
    createPipe(CHILD_2_TO_PARENT);
    int pid1 = fork();
    if (pid1 > 0) {
        int pid2 = fork();
        if (pid2 > 0) {//parent
            printf("Parent started\n");
            int voterIds[1000];
            printf("Parent waits for children's signals\n");
            pause();
            pause();
            printf("Children's signals has been arrived\n");
            int numberOfVoters = readNumber("Number of voters");

            for (int i = 0; i < numberOfVoters; ++i) {
                int id = rand() % 10000 + 1;
                voterIds[i] = id;
            }
            int parentToChild1Pipe = open(PARENT_TO_CHILD_1, O_WRONLY);
            write(parentToChild1Pipe, &numberOfVoters, sizeof(int));
            for (int i = 0; i < numberOfVoters; ++i) {
                printf("Parent write to %i voter to child 1: %i\n", i, voterIds[i]);
                write(parentToChild1Pipe, &voterIds[i], sizeof(int));
            }

            //todo get stat from child and print stat into file
            int child2ToParentPipe = open(CHILD_2_TO_PARENT, O_RDONLY);
            struct voteStatMsg voteStatMsg;
            read(child2ToParentPipe, &voteStatMsg, sizeof(struct voteStatMsg));
            printf("success/failed = %i/%i\n", voteStatMsg.success, voteStatMsg.failed);
            double successPercentage = ((double) voteStatMsg.success / (voteStatMsg.failed + voteStatMsg.success) *
                                        100);
            double failPercentage = ((double) voteStatMsg.failed / (voteStatMsg.failed + voteStatMsg.success) * 100);
            printf("Percentage of success = %f\n", successPercentage);
            printf("Percentage of failed = %f\n", failPercentage);


//            int status;
//            wait(&status);
//            wait(&status);
            close(child2ToParentPipe);
            closePipe(parentToChild1Pipe, PARENT_TO_CHILD_1);
            printf("Parent finished\n");
        } else {//child 2 sealer
            printf("Child 2 started\n");

            sleep(2);
            kill(getppid(), SIGTERM);
            int child1ToChild2Pipe = open(CHILD_1_TO_CHILD_2, O_RDONLY);

            int numberOfVoters;
            printf("Child 2 waits to read from pipe\n");
            read(child1ToChild2Pipe, &numberOfVoters, sizeof(int));
            printf("Child 2 waits for %i message\n", numberOfVoters);
            struct voterMsg msg[1000];
            int voted = 0;
            int failedToVote = 0;
            for (int i = 0; i < numberOfVoters; ++i) {
                read(child1ToChild2Pipe, &msg[i], sizeof(struct voterMsg));
                printf("Child 2 receive message, %i vote %i\n", msg[i].id, msg[i].canVote);
                if (msg[i].canVote == 0) {
                    failedToVote++;
                } else {
                    voted++;
                }
            }
            printf("Child 2 is received all messages from Child 1\n");

            int child2ToParentPipe = open(CHILD_2_TO_PARENT, O_WRONLY);
            struct voteStatMsg voteStatMsg = {voted, failedToVote};
            write(child2ToParentPipe, &voteStatMsg, sizeof(struct voteStatMsg));

            sleep(2);
            closePipe(child2ToParentPipe, CHILD_2_TO_PARENT);
            close(child1ToChild2Pipe);
            printf("Child 2 finished\n");
        }

    } else {//child 1 checker
        printf("Child 1 started\n");


        sleep(1);
        printf("Child 1 send sigterm\n");
        kill(getppid(), SIGTERM);
        int child1ToChild2Pipe = open(CHILD_1_TO_CHILD_2, O_WRONLY);
        int parentToChild1Pipe = open(PARENT_TO_CHILD_1, O_RDONLY);
        int numberOfVoters;
        read(parentToChild1Pipe, &numberOfVoters, sizeof(int));
        printf("Children wait %i voters\n", numberOfVoters);


        int voterIds[1000];
        for (int i = 0; i < numberOfVoters; i++) {
            read(parentToChild1Pipe, &voterIds[i], sizeof(int));
            printf("Child 1 received %i voter. Id: %i\n", i, voterIds[i]);

        }
        write(child1ToChild2Pipe, &numberOfVoters, sizeof(int));
        for (int i = 0; i < numberOfVoters; ++i) {
            int chance = rand() % 100 + 1;
            int canVote = 0;
            if (chance > 20) {
                canVote = 1;
            }
            struct voterMsg msg = {voterIds[i], canVote};
            printf("Child 1 send voter to child 2.\n");
            write(child1ToChild2Pipe, &msg, sizeof(struct voterMsg));
        }
        sleep(5);
        close(parentToChild1Pipe);
        closePipe(child1ToChild2Pipe, CHILD_1_TO_CHILD_2);
        printf("Child 1 finished\n");
    }

    return 0;
}
