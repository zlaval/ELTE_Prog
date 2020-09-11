
#ifndef ALG1CPP_COUNTINGSORT_H
#define ALG1CPP_COUNTINGSORT_H


class CountingSort {

    int getDigit(std::string n, int i) {
        std::string d = n.substr(i, 1);
        return std::stoi(d);
    }

    void countingSort(std::string *a, std::string *res, int size, int d, int index) {
        int c[d];
        for (int i = 0; i < d; i++) {
            c[i] = 0;
        }
        for (int i = 0; i < size; i++) {
            c[getDigit(a[i], index)]++;
        }
        for (int i = 1; i < d; i++) {
            c[i] += c[i - 1];
        }
        for (int i = size - 1; i >= 0; i--) {
            int l = getDigit(a[i], index);
            c[l]--;
            int j = c[l];
            res[j] = a[i];
        }
    }

public:

    void sort(std::string *a, int size, int numLength, int d) {
        std::string *t = a;
        std::string r[size];
        std::string *res = r;
        int round=0;
        for (int i = numLength - 1; i >= 0; i--) {
            countingSort(t, res, size, d, i);
            std::cout<<"After "<<round++<<" round"<<std::endl;
            printResult(res,size);
            std::cout<<std::endl;
            std::string *tmp=t;
            t = res;
            res=tmp;

        }
        std::cout<<"Result:"<<std::endl;
        printResult(t, size);
    }

    void printResult(std::string *a, int size) {
        for (int i = 0; i < size; i++) {
            std::cout << a[i] << " ,";
        }
    }

};

#endif //ALG1CPP_COUNTINGSORT_H
