#include "utils.h"
#include <ctype.h>

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

char *readIn(char *label) {
    return readBack(label, 0);
}

char *readBack(char *label, char *value) {
    char *input = 0;
    size_t length = 0;
    if (value == 0) {
        printf("%s: ", label);
    } else {
        printf("%s (%s): ", label, value);
    }
    getline(&input, &length, stdin);
    input[strlen(input) - 1] = 0;
    printf("\n");
    return input;
}