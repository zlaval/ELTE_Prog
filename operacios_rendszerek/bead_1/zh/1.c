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
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>

void handler(int sig) {
    printf("FELDERITES %i\n", sig);
}

struct msg {
    long mtype;
    int number;
};

int send(int mq, int num) {
    struct msg msg;
    msg.mtype = 7;
    msg.number = num;
    msgsnd(mq, &msg, sizeof(int), 0);
    return 0;
}

int receive(int mq) {
    struct msg msg;
    int status = msgrcv(mq, &msg, sizeof(int), 7, 0); //only 3 msg type dispatched
    printf("Found %d foxes\n", msg.number);
    if (status < 0) {
        printf("error receive message\n");
    }
    return 0;
}

void childProcess(int mq, int child);

int main(int argc, char *argv[]) {
    signal(SIGTERM, handler);
    key_t key = ftok(argv[0], 1);
    int mq = msgget(key, 0600 | IPC_CREAT);
    if (mq < 0) {
        perror("msgget\n");
        return 1;
    }

    int pid1 = fork();
    if (pid1 > 0) {
        int pid2 = fork();
        if (pid2 > 0) {//parent vuk
            sleep(2);
            printf("Send FELDERITES to children\n");
            kill(pid1, SIGTERM);
            kill(pid2, SIGTERM);
            receive(mq);
            receive(mq);
            //int status;
            //wait(&status);
            //wait(&status);
        } else {//child 2 ficko
            childProcess(mq, 2);
        }

    } else { //child 1 vahur
        childProcess(mq, 1);
    }

    return 0;
}

void childProcess(int mq, int child) {
    srand(time(NULL) + child);
    printf("Child %d waits for signal from parent\n", child);
    pause();
    printf("Child %d starts scouting\n", child);
    int rnd = rand() % 11 + 20;
    send(mq, rnd);
}