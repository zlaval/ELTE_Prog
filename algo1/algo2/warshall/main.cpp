#include <iostream>
#include <limits>
#include <Windows.h>

const int N = 4;


void printMatrix(int matrix[N][N]) {

    for (int n = 0; n < N; ++n) {
        for (int m = 0; m < N; ++m) {
            int value = matrix[n][m];
            std::string strval = std::to_string(value);
            if (value == INT_MAX) {
                strval = "∞";
            }
            std::cout << strval << " ";
        }


        std::cout << std::endl;
    }
    std::cout << std::endl;
    std::cout << "*****************" << std::endl;
}


int main() {
    SetConsoleOutputCP(CP_UTF8);

    int matrix[N][N] = {
        {0,       INT_MAX, 1, INT_MAX},
        {1,       0, INT_MAX, INT_MAX},
        {INT_MAX, INT_MAX, 0, 1},
        {INT_MAX, 1, INT_MAX, 0},
    };

    int T[N][N];
    int round = 0;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            T[i][j] = matrix[i][j];
        }
        T[i][i] = 1;
    }

    std::cout << " Prefilled:" << std::endl;
    printMatrix(T);

    for (int k = 0; k < N; k++) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (T[i][j] == 1 || (T[i][k] == 1 && T[k][j] == 1)) {
                    T[i][j] = 1;
                }
            }
        }
        round++;
        std::cout << round << " round:" << std::endl;
        printMatrix(T);
    }
    std::cout << " Result:" << std::endl;
    printMatrix(T);

    return 0;
}

