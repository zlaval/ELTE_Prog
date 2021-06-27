#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/sem.h>


const int CHILD_1 = 1;
const int CHILD_2 = 2;
const char *PARENT_TO_CHILD_1 = "/tmp/parent_to_child_1_cz72ym_vahur";
const char *PARENT_TO_CHILD_2 = "/tmp/parent_to_child_2_cz72ym_ficko";

void handler(int sig) {
    printf("FELDERITES %i\n", sig);
}

struct msg {
    long mtype;
    int number;
};


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

int send(int mq, int num, int msgtype) {
    struct msg msg;
    msg.mtype = msgtype;
    msg.number = num;
    msgsnd(mq, &msg, sizeof(int), 0);
    return 0;
}

int receive(int mq, int msgtype) {
    struct msg msg;
    int status = msgrcv(mq, &msg, sizeof(int), msgtype, 0); //only 3 msg type dispatched
    printf("Child %i found %d foxes\n", msgtype, msg.number);
    if (status < 0) {
        printf("error receive message\n");
    }
    return msg.number;
}

void childProcess(int mq, int child, const char *pipelineName, int semid);

void writeToFile(int number) {
    FILE *file;
    file = fopen("data.txt", "a");
    fwrite(&number, sizeof(int), 1, file);
    fclose(file);
}

int createSemaphore(const char *pathname, int semaphoreValue) {
    int semid;
    key_t key = ftok(pathname, 1);
    if ((semid = semget(key, 1, IPC_CREAT | S_IRUSR | S_IWUSR)) < 0) {
        perror("semget");
    }
    if (semctl(semid, 0, SETVAL, semaphoreValue) < 0) {
        perror("semctl");
    }
    return semid;
}

void setSemaphore(int semid, int op) {
    struct sembuf opt;
    opt.sem_num = 0;
    opt.sem_op = op;
    opt.sem_flg = 0;
    if (semop(semid, &opt, 1) < 0) {
        perror("semop");
    }
}

void deleteSemaphore(int semid) {
    semctl(semid, 0, IPC_RMID);
}

int main(int argc, char *argv[]) {
    signal(SIGTERM, handler);
    key_t key = ftok(argv[0], 1);
    int mq = msgget(key, 0600 | IPC_CREAT);
    if (mq < 0) {
        perror("msgget\n");
        return 1;
    }

    createPipe(PARENT_TO_CHILD_1);
    createPipe(PARENT_TO_CHILD_2);

    int semid = createSemaphore(argv[0], 1);

    int pid1 = fork();
    if (pid1 > 0) {
        int pid2 = fork();
        if (pid2 > 0) {//parent vuk
            sleep(1);
            printf("Send FELDERITES to children\n");
            kill(pid1, SIGTERM);
            kill(pid2, SIGTERM);
            int child1Num = receive(mq, CHILD_1);
            int child2Num = receive(mq, CHILD_2);

            int parentToChild1Pipe = open(PARENT_TO_CHILD_1, O_WRONLY);
            int parentToChild2Pipe = open(PARENT_TO_CHILD_2, O_WRONLY);

            int child1Vaccine = (int) ((double) child1Num * 0.8);
            int child2Vaccine = (int) ((double) child2Num * 0.8);

            write(parentToChild1Pipe, &child1Vaccine, sizeof(int));
            write(parentToChild2Pipe, &child2Vaccine, sizeof(int));

            int status;
            wait(&status);
            wait(&status);
            closePipe(parentToChild1Pipe, PARENT_TO_CHILD_1);
            closePipe(parentToChild2Pipe, PARENT_TO_CHILD_2);
            deleteSemaphore(semid);
        } else {//child 2 ficko
            childProcess(mq, CHILD_2, PARENT_TO_CHILD_2, semid);
        }

    } else { //child 1 vahur
        childProcess(mq, CHILD_1, PARENT_TO_CHILD_1, semid);
    }

    return 0;
}

void childProcess(int mq, int child, const char *pipelineName, int semid) {
    srand(time(NULL) + child);
    printf("Child %d waits for signal from parent\n", child);
    pause();
    printf("Child %d starts scouting\n", child);
    int rnd = rand() % 11 + 20;
    send(mq, rnd, child);
    int result;
    int pipeline = open(pipelineName, O_RDONLY);
    read(pipeline, &result, sizeof(int));
    printf("Child %i can accept %i foxes\n", child, result);

    setSemaphore(semid, -1);
    writeToFile(5);
    setSemaphore(semid, 1);

    close(pipeline);
}