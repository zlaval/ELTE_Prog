#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>

int main(int argc, char *argv[]) {
    key_t key = ftok(argv[0], 1);
    int sm = shmget(key, 500/*size of memory*/, IPC_CREAT | S_IRUSR | S_IWUSR);
    //attach shared memory
    char *s = shmat(sm, NULL, 0);

    int pid = fork();
    if (pid > 0) {//parent
        char buffer[] = "TO MEMORY";
        strcpy(s, buffer);
        //release shared memory
        shmdt(s);
        wait(NULL);
        //delete shared memory
        shmctl(sm, IPC_RMID, NULL);
    } else {//child
        sleep(2);
        printf("Read from shared memory: %s", s);
        shmdt(s);
    }

    return 0;
}