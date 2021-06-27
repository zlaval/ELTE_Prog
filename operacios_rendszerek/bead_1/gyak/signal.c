#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>


void handler(int sig) {
    printf("signal arrived %i\n", sig);
}

int main() {

    signal(SIGTERM, handler);

    int pid1 = fork();

    if (pid1 > 0) { //parent
        int pid2 = fork();
        if (pid2 > 0) {//parent 2
            pause();
            int status;
            wait(&status);
            printf("Parent signed with status %i\n", status);
            wait(&status);
            printf("Parent end with status %i\n", status);
        } else {//child 2
            sleep(2);
            printf("Child 2 sends a signal\n");
            kill(getppid(), SIGTERM);
            printf("Child 2 ends\n");
        }

    } else { //child1
        sleep(3);
        printf("Child 1 sends a signal\n");
        kill(getppid(), SIGTERM);
        printf("Child 1 ends\n");
    }


    return 0;
}