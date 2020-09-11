
#ifndef ALG1CPP_BRFORWARD_H
#define ALG1CPP_BRFORWARD_H

#endif //ALG1CPP_BRFORWARD_H

class BRForward {
private:
    std::string *a;
    int m;
    int numLength;
public:
    BRForward(std::string *a, int m, int numLength) {
        this->a = a;
        this->m = m;
        this->numLength = numLength;
    }
    ~BRForward() {
        //TODO a deleted for easier handling the sandbox
        delete a;
    }
    void sort(int d, int from, int to) {
        if (d >= numLength) {
            return;
        }
        int e = from;
        int v = to;

        while (e < v) {
            std::string sa = a[e];
            while (e <= v && getDigit(a[e], d) == 0) {
                e++;
                sa = a[e];
            }
            std::string sb = a[v];
            while (e <= v && getDigit(a[v], d) == 1) {
                v--;
                sb = a[v];
            }


            if (e < v) {
                std::cout << "in round " << d << ". swap elements: a[" << e << "]=" << sa << " with a[" << v << "]="
                          << sb
                          << std::endl;
                std::string tmp = a[e];
                a[e] = a[v];
                a[v] = tmp;
                std::cout << "array after change: ";
                printArray(a, m);
                std::cout << std::endl;
                std::cout << ".........." << std::endl;
            }

        }
        std::cout << "array lower from-to : "<<from<<" - "<<v<<std::endl;
        std::cout << "array upper from-to : "<<v+1<<" - "<<to<<std::endl;
        std::cout << "**" << std::endl;
        sort(d + 1, from, v);
        sort(d + 1, v + 1, to);
    }

    void binRadFw() {
        sort(0, 0, m - 1);
        printArray(a, m);
    }

    void printArray(std::string *arr, int l) {
        for (int i = 0; i < l; i++) {
//            if (i == k+1) {
//                std::cout << " | ";
//            }

            std::cout << arr[i] << " ";
        }
    }

    int getDigit(std::string n, int i) {
        std::string d = n.substr(i, 1);
        return std::stoi(d);
    }

};
