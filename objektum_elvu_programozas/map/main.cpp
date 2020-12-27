#include "Map.h"


int main() {
    Map map;

    std::cout << map.count() << std::endl;

    Item a1{1, "IntelliJ"};
    Item a2{4, "CLion"};
    Item a3{3, "DataGrip"};
    Item a4{2, "PyCharm"};
    Item a5{1, "Rider"};

    map.put(a1);
    map.put(a2);
    map.put(a3);
    map.put(a4);

    std::cout << map.count() << std::endl;

    std::cout << map << std::endl;

    bool result = map.put(a5);
    std::cout << result << std::endl;

    map.remove(4);
    std::cout << map << std::endl;

    result = map.remove(6);
    std::cout << result << std::endl;
    std::cout << map << std::endl;

    std::cout << map[3] << std::endl;

    std::cout << map[10] << std::endl;

    map.clear();
    std::cout << map << std::endl;
    std::cout << map.count() << std::endl;
    return 0;
}
