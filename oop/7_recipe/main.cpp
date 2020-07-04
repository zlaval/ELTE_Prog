#include "RecipeEnumerator.h"
#include <iostream>

bool isAllContainsSugar(std::string &file) {
    RecipeEnumerator enumerator(file);
    enumerator.first();
    bool result = true;
    while (result && !enumerator.end()) {
        result = enumerator.current();
        enumerator.next();
    }
    return result;
}

int main() {
    std::string filePath;
    std::cout << "Enter the name of the input file:";
    std::cin >> filePath;
    bool result = isAllContainsSugar(filePath);
    std::cout << "Does all contains sugar? Answer: " << (result ? "true" : "false") << std::endl;
    return 0;
}
