
#ifndef ALG1CPP_HEAP_H
#define ALG1CPP_HEAP_H

#include <iostream>
#include <math.h>

//1től indexelt
//balra tömörített bináris fa amire
//max heap: minden belső csúcs nagyobb mint a gyerekeié
//részfák kupacok, bal jobb oldal között nincs reláció
class Heap {
private:
    int *array;
    int size;
    int maxSize;
public:
    Heap(int m) {
        this->size = 0;
        this->maxSize = m + 1;
        array = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            array[i] = 0;
        }
    }
    Heap(int *array, int size, int maxSize) {
        this->array = array;
        this->size = size;
        this->maxSize = maxSize;
    }

    ~Heap() {
        delete array;
    }

    //kicseréli az első elemmel és megpróbálja lesűllyeszteni, az aktuálisnál 1-el kisebbig max
    void heapSort() {
        std::cout << "********************Start heapSort***************************" << std::endl;
        buildMaxHeap();
        int m = size;
        while (m > 1) {
            std::cout << "Start Hs round swap with m=" << m << std::endl;
            swap(1, m);
            m--;
            std::cout << "Hs sink with m=" << m << std::endl;
            sink(1, m);
            std::cout << "End Hs round" << std::endl;
        }
        std::cout << "********************End heapSort***************************" << std::endl;
    }

    void add(int x) {
        if (size < maxSize) {
            std::cout << "***********************Add start element:" << x << "**********************" << std::endl;
            std::cout << "array before add:" << std::endl;
            printHeap();
            size++;
            int j = size;
            array[j] = x;
            std::cout << "Put " << x << " to " << j << ". place" << std::endl;
            std::cout << "array after put the element" << std::endl;
            printHeap();
            int i = parent(j);
            while (j > 1 && array[i] < x) {
                swap(i, j);
                j = i;
                i = parent(i);
            }
            std::cout << "array after add:" << std::endl;
            printHeap();
            std::cout << "*****************************Add end***************************" << std::endl;
        }
    }

    void swap(int i, int j) {
        std::cout << "start swap array[" << i << "]=" << array[i] << " with array[" << j << "]=" << array[j]
                  << std::endl;
        std::cout << "array before swap:" << std::endl;
        printHeap();
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        std::cout << "array after swap:" << std::endl;
        printHeap();
        std::cout << "swap end" << std::endl;
    }

    int parent(int index) {
        return index / 2;
    }

    int left(int index) {
        return 2 * index;
    }
    int right(int index) {
        return left(index) + 1;
    }

    //a legkisebb szűlőtől a tömb elejéig minden szűlőt próbál lesűllyeszteni, balra ha jobb gyerek kisebb mint balgyerek
    void buildMaxHeap() {
        int k = parent(size);
        std::cout << "----Start build max heap with k=" << k << "----" << std::endl;
        while (k >= 1) {
            std::cout << "----Build max heap with k=" << k << "----" << std::endl;
            sink(k, size);
            k--;
        }
        std::cout << "----End build max----" << std::endl;
    }

    int remMax() {
        std::cout << "***********************RemMax start**********************" << std::endl;
        std::cout << "array before remMax:" << std::endl;
        printHeap();
        if (size > 0) {
            int max = array[1];
            std::cout << "remove array[1] as max and change array[1]=" << array[1] << " to array[" << size << "]="
                      << array[size] << std::endl;
            array[1] = array[size];
            array[size] = 0;
            size--;
            std::cout << "array after remove 1. element" << std::endl;
            printHeap();
            sink(1, size);

            std::cout << "***********************RemMax end**********************" << std::endl;
            return max;
        }
        std::cout << "***********************RemMax end, empty array**********************" << std::endl;
        return -1;
    }

    void sink(int k, int n) {
        std::cout << "     SINK an element, n=" << n << " k=" << k << std::endl;
        int i = k;
        int j = left(k);
        bool b = true;
        std::cout << "array before sink:" << std::endl;
        printHeap();
        while (j <= n && b) {
            if (j < n && array[j + 1] > array[j]) {
                j++;
            }
            if (array[i] < array[j]) {
                swap(i, j);
                i = j;
                j = left(j);
            } else {
                b = false;
            }
        }
        std::cout << "      SINK END" << std::endl;
    }

    int max() {
        if (size > 0) {
            return array[1];
        }
        return -1;
    }

    bool isFull() { return size == maxSize; }

    bool isEmpty() { return size == 0; }

    void setEmpty() { size = 0; }

    void printHeap() {
        for (int i = 1; i < maxSize; i++) {
            std::cout << array[i] << ", ";
        }
        std::cout << std::endl;
        printAsTree();
    }

    void printAsTree() {
        int level = 0;
        int height = ceil(log2(size));
        int lastRowElementCount = pow(2, height - 1);
        int slot = 4 * lastRowElementCount;
        std::cout << "    tree   " << std::endl;
        for (int i = 1; i <= size; i++) {

            int actualLevel = log2(i);

            if (actualLevel > level) {
                level = actualLevel;
                std::cout << std::endl;
            }
            int elementOnLevel = pow(2, level);
            std::string e = std::to_string(array[i]);
            if (e.length() < 2) {
                e += " ";
            }
            e = " " + e + " ";
            std::string st = "";
            int spaceCount = (slot / elementOnLevel) / 2 - 2;
            for (int j = 0; j < spaceCount; j++) {
                st += " ";
            }
            st += e;

            for (int j = 0; j < spaceCount; j++) {
                st += " ";
            }
            std::cout << st;
        }
        std::cout << std::endl;
    }

};


#endif //ALG1CPP_HEAP_H
