#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>

struct msg {
    long mtype;
    char text[200];
};

//1 message 1 receiver by msg type
int send(int mq, char *text, int type) {
    struct msg msg;
    msg.mtype = type;
    strcpy(msg.text, text);
    printf("Sending message %s\n", msg.text);
    msgsnd(mq, &msg, strlen(msg.text) + 1, 0);
    return 0;
}

int receive(int mq, int type) {
    struct msg msg;
    int status = msgrcv(mq, &msg, 200, type, 0); //only 3 msg type dispatched
    printf("Received msg=%s\n", msg.text);
    if (status < 0) {
        printf("error receive message\n");
    }
    return 0;
}

int main(int argc, char *argv[]) {
    key_t key;
    int mq;
    key = ftok(argv[0], 1);
    printf("Key: %d\n", key);
    mq = msgget(key, 0600 | IPC_CREAT);
    if (mq < 0) {
        perror("msgget\n");
        return 1;
    }

    int pid = fork();
    if (pid > 0) { //parent
        send(mq, "Hello sent from parent\n", 3);
        receive(mq, 4);
        wait(NULL);
        msgctl(mq, IPC_RMID, NULL);

    } else { //child
        sleep(2);
        receive(mq, 3);
        send(mq, "Hello sent from child\n ", 4);
    }
    return 0;
}