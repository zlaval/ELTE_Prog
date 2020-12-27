#include "HashTable.h"


int main() {
//    HashTable hashTable(11);
//    hashTable.printHeader();
//    hashTable.putLinear(24);
//    hashTable.putLinear(16);
//    hashTable.putLinear(57);
//    hashTable.putLinear(32);
//    hashTable.putLinear(15);
//    hashTable.removeLinear(57);
//    hashTable.putLinear(21);
//    int a = hashTable.getLinear(2);
//    hashTable.putLinear(2);
//    bool b = hashTable.putLinear(2);
//    int c = hashTable.getLinear(21);
//    std::cout << std::endl;
//    std::cout << a << " " << b << " " << c << std::endl;
//    std::cout << "=====================================================================" << std::endl;

    HashTable hashTable2(8);
    hashTable2.printHeader();
    hashTable2.putQuadric(13);
    hashTable2.putQuadric(20);
    hashTable2.putQuadric(31);
    hashTable2.putQuadric(87);
    hashTable2.putQuadric(12);
    hashTable2.removeQuadric(31);
    hashTable2.getQuadric(12);
    hashTable2.getQuadric(15);
    hashTable2.putQuadric(4);
    hashTable2.removeQuadric(10);
    hashTable2.putQuadric(35);
    hashTable2.putQuadric(10);
    std::cout <<std::endl;
    std::cout << "=====================================================================" << std::endl;

//    HashTable hashTable3(11);
//    hashTable3.printHeader();
//    hashTable3.putDouble(23);
//    hashTable3.putDouble(42);
//    hashTable3.putDouble(31);
//    hashTable3.putDouble(110);
//    hashTable3.putDouble(55);
//    hashTable3.removeDouble(55);
//    hashTable3.getDouble(72);
//    hashTable3.putDouble(14);
//    hashTable3.removeDouble(22);
//    hashTable3.removeDouble(110);
//    hashTable3.putDouble(13);

    return 0;
}
