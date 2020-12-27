
#ifndef ALG1CPP_BRBACK_H
#define ALG1CPP_BRBACK_H

#include <string>
#include <iostream>

class BRBack {
    std::string *a;
    std::string *b;
    int m;
    int numLength;
public:
    BRBack(std::string *a, int m, int numLength) {
        this->a = a;
        this->b = new std::string[m];
        this->m = m;
        this->numLength = numLength;
    }
    ~BRBack() {
        delete b;
        //TODO a deleted for easier handling the sandbox
        delete a;
    }

    void binRadix() {
        std::string *actualArray = a;
        std::string *backArray = b;
        int e = 0;
        int v = m - 1;
        int k = m - 1;
        int round = 0;
        for (int i = numLength - 1; i >= 0; i--) {
            int j = 0;
            while (j <= k) {
                std::string num = actualArray[j];
                int digit = getDigit(num, i);
                if (digit == 0) {
                    backArray[e++] = num;
                } else {
                    backArray[v--] = num;
                }
                j++;
            }
            j = m - 1;
            while (j > k) {
                std::string num = actualArray[j];
                int digit = getDigit(num, i);
                if (digit == 0) {
                    backArray[e++] = num;
                } else {
                    backArray[v--] = num;
                }
                j--;
            }
            k = e - 1;
            e = 0;
            v = m - 1;

            std::cout << "k=" << k << std::endl;
            std::cout << "After " << round << " round the processed array: ";
            printArray(actualArray, m, k);
            std::cout << std::endl;
            std::cout << "After " << round << " round the new array: ";
            printArray(backArray, m, k);
            round++;
            std::cout << std::endl;
            std::cout << "******************************************************************" << std::endl;
            std::string *tmp = actualArray;
            actualArray = backArray;
            backArray = tmp;
        }

        for (int i = 0; i <= k; i++) {
            backArray[i] = actualArray[i];
        }
        int j = k;
        for (int l = m - 1; l > k; l--) {
            backArray[++j] = actualArray[l];
        }

        actualArray = backArray;
        std::cout << "The final result: ";
        printArray(actualArray, m, -1);


    }

    void printArray(std::string *arr, int l, int k) {
        for (int i = 0; i < l; i++) {
            if (i == k+1) {
                std::cout << " | ";
            }

            std::cout << arr[i] << " ";
        }
    }

    int getDigit(std::string n, int i) {
        std::string d = n.substr(i, 1);
        return std::stoi(d);
    }

};


#endif //ALG1CPP_BRBACK_H
