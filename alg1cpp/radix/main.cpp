#include "C2L.h"
#include "BRBack.h"
#include "BRForward.h"
#include "Radix.h"
#include "CountingSort.h"


int main() {
//    std::string a[7] = {"101", "001", "011", "110", "010", "111", "000"};
//    BRBack back(a, 7, 3);
//    back.binRadix();

//    std::string b[7] = {"101", "001", "011", "110", "010", "111", "000"};
//    BRForward forward(b, 7, 3);
//    forward.binRadFw();


//    C2L *list = new C2L();
//    list->add("101");
//    list->add("013");
//    list->add("310");
//    list->add("323");
//    list->add("003");
//    list->add("220");
//    list->add("211");
//
//    Radix radix;
//    radix.radix(list, 3, 4);
//    list->print();
//
//    delete list;

    std::string b[6] = {"11", "20", "10", "23", "21", "30"};

    CountingSort c;

    c.sort(b,6,2,4); //d=max num+1

    return 0;
}
