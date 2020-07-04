#include "Heap.h"

int main() {
   // 30;22;27;2;7;20;15;1;0;3;4
    Heap heap(9);
    heap.add(50);
    heap.add(45);
    heap.add(42);
    heap.add(37);
    heap.add(35);
    heap.add(31);
    heap.add(15);
    heap.add(8);
    heap.add(9);

    std::cout << "***************INNEN***********************************" << std::endl;
   heap.remMax();
    std::cout << "***************utana***********************************" << std::endl;
    heap.printHeap();
//
//
//    heap.add(61);
//    std::cout << "**************************************************" << std::endl;
//
//    heap.add(43);
    // heap.remMax();

    // heap.printAsTree();
    // heap.remMax();
    //heap.printAsTree();

//    int array[15]={0,4,1,3,2,18,9,10,14,12,7,0,0,0,0};
//    Heap heap(array,10,15);
//    heap.heapSort();

    return 0;
}
