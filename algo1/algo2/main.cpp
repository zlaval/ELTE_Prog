
#include <iostream>
#include <limits>
//used to print uft-8 infinite sign
#include <Windows.h>

class WarshallFloyd {
public:
    WarshallFloyd(int **matrix, int N) {
        this->matrix = matrix;
        this->N = N;
        createHelperMatrices();
        preFillHelperMatrices();
    }

    ~WarshallFloyd() {
        for (int i = 0; i < N; i++) {
            delete[] D[i];
            delete[] P[i];
        }
        delete[] D;
        delete[] P;
    }

    void calculatePath() {
        printMatrix(matrix, "Adjacency matrix");
        printLineSeparator();
        printDPMatrices("initial");
        runFW();
    }


private:
    int **matrix;
    int N;
    int **D;
    int **P;

    void runFW() {
        int round = 0;
        for (int k = 0; k < N; ++k) {
            bool hasChange = false;
            for (int i = 0; i < N; ++i) {

                for (int j = 0; j < N; ++j) {
                    int ijPathWeight = D[i][k] + D[k][j];
                    if (D[i][k] == INT_MAX || D[k][j] == INT_MAX) {
                        ijPathWeight = INT_MAX;
                    }
                    if (D[i][j] > ijPathWeight) {
                        hasChange = true;
                        D[i][j] = ijPathWeight;
                        P[i][j] = P[k][j];
                        if (i == j && D[i][i] < 0) {
                            std::cout << "Negative cycle detected, end edge " << i + 1 << std::endl;
                            return;
                        }
                    }
                }

            }
            round++;
            if (hasChange) {
                printDPMatrices("in step " + std::to_string(round));
            }
        }
        printAllPathes();
    }

    void printAllPathes() {
        for (int i = 0; i < N; ++i) {
            printNodePathes(i);
        }
    }

    void printNodePathes(int node) {
        int *row = P[node];

        std::cout << "Routes from node " << node + 1 << std::endl;
        std::cout << std::endl;
        for (int i = 0; i < N; i++) {
            if (i != node) {
                std::cout << "Route from " << node + 1 << " to " << i + 1 << ". Weight: " << D[node][i] << std::endl;
                std::string path =  std::to_string(i + 1);
                int through = row[i] - 1;
                while (through != node && through != 0) {
                    path = std::to_string((through + 1)) + " -> " + path;
                    through = row[through] - 1;
                }

                path = std::to_string((node + 1)) + " -> " + path;
                std::cout << "Path: " << path << std::endl;
            }
        }
        std::cout << "--------------------------------" << std::endl;
    }

    void createHelperMatrices() {
        this->D = new int *[N];
        this->P = new int *[N];
        for (int i = 0; i < N; ++i) {
            D[i] = new int[N];
            P[i] = new int[N];
        }
    }

    void preFillHelperMatrices() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                int actualElement = matrix[i][j];
                D[i][j] = actualElement;
                if (i != j && actualElement != INT_MAX) {
                    P[i][j] = i + 1;
                } else {
                    P[i][j] = 0;
                }
            }
        }

    }

    void printDPMatrices(std::string text) {
        printMatrix(D, "D matrix " + text);
        printLineSeparator();
        printMatrix(P, "P matrix " + text);
        printLineSeparator();
        printLineSeparator();
    }

    void printLineSeparator() {
        std::cout << "***********************************" << std::endl;
    }

    void printMatrix(int **matrix, std::string name) {
        std::cout << name << ":" << std::endl;
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

    }


};

int main() {
    //set windows consol to utf-8
    SetConsoleOutputCP(CP_UTF8);
    const int N = 4;

    int **matrixPointer = new int *[N]{
        new int[N]{0, 2, INT_MAX, 5},
        new int[N]{INT_MAX, 0, 1, INT_MAX},
        new int[N]{3, INT_MAX, 0, 1},
        new int[N]{INT_MAX, 2, INT_MAX, 0}
    };

    auto *warshallFloyd = new WarshallFloyd(matrixPointer, N);
    warshallFloyd->calculatePath();
    delete warshallFloyd;

    for (int i = 0; i < N; ++i) {
        delete[] matrixPointer[i];
    }
    delete[] matrixPointer;

    return 0;
}