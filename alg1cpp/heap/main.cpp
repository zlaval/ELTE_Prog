#include "Heap.h"

int main() {

    Heap heap(15);
    heap.add(40);
    heap.add(26);
    heap.add(27);
    heap.add(21);
    heap.add(14);
    heap.add(15);
    heap.add(2);
    heap.add(9);
    heap.add(6);
    heap.add(3);
    heap.add(8);
    heap.add(10);

    std::cout << "***************ORIG TREE***********************************" << std::endl;
    heap.remMax();
    std::cout << "***************RESULT TREE***********************************" << std::endl;
    heap.printHeap();


//    int array[15]={0,4,1,3,2,18,9,10,14,12,7,0,0,0,0};
//    Heap heap(array,10,15);
//    heap.heapSort();

    return 0;
}
