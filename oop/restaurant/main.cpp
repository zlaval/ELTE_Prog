#include "FoodOrderEnumerator.h"
#include <iostream>

FoodOrder findMax(std::string &filePath) {
    FoodOrderEnumerator enumerator(filePath);
    enumerator.first();
    FoodOrder max = enumerator.current();
    enumerator.next();
    while (!enumerator.end()) {
        if (max.taking < enumerator.current().taking) {
            max = enumerator.current();
        }
        enumerator.next();
    }
    return max;
}

int main() {
    std::string filePath;
    std::cout << "Enter the name of the input file:";
    std::cin >> filePath;
    FoodOrder max = findMax(filePath);
    std::cout << "Highest profit on " << max.name << " " << max.taking << std::endl;
    return 0;
}
