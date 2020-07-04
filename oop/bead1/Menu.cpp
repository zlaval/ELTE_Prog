#include "Menu.h"
#include <string>


Menu::Menu() {
    this->bag = nullptr;
}

Menu::~Menu() {
    deleteBag();
}

void Menu::run() {
    int code = 0;
    do {
        printMenu();
        code = readElement("Enter the menu number:");
        handleMenu(code);
    } while (code != 0);

}

void Menu::printMenu() const {
    std::cout << std::endl;
    std::cout << "***********************************************" << std::endl;
    std::cout << "* 0 - Exit                                    *" << std::endl;
    std::cout << "* 1 - Create new Bag                          *" << std::endl;
    std::cout << "* 2 - Add element                             *" << std::endl;
    std::cout << "* 3 - Remove element                          *" << std::endl;
    std::cout << "* 4 - Print the Bag                           *" << std::endl;
    std::cout << "* 5 - Print element count                     *" << std::endl;
    std::cout << "* 6 - Print count of the elements occurs once *" << std::endl;
    std::cout << "* 7 - Load test                               *" << std::endl;
    std::cout << "***********************************************" << std::endl;
    std::cout << std::endl;
}

void Menu::handleMenu(int code) {
    switch (code) {
        case 0:
            std::cout << "Bye";
            break;
        case 1:
            createBag();
            break;
        case 2:
            addElement();
            break;
        case 3:
            removeElement();
            break;
        case 4:
            printBag();
            break;
        case 5:
            printElementCount();
            break;
        case 6:
            printElementsCountOccursOnce();
            break;
        case 7:
            loadTest();
            break;
        default:
            std::cout << "Invalid menu item" << std::endl;
            break;
    }
}

void Menu::printBag() const {
    if (isBagExists()) {
        std::cout << *bag << std::endl;
    } else {
        printNoBagMessage();
    }
}

void Menu::createBag() {
    deleteBag();
    try {
        bag = new Bag();
        std::cout << "A new Bag created" << std::endl;
    } catch (...) {
        std::cout << "Cannot create the bag" << std::endl;
    }
}

void Menu::addElement() {
    if (isBagExists()) {
        int element = readElement();
        bag->put(element);
    } else {
        printNoBagMessage();
    }
}

void Menu::removeElement() {
    if (isBagExists()) {
        int element = readElement();
        int amount = readElement("Enter the amount of deletable element:");
        try {
            bag->remove(element, amount);
        } catch (Bag::Exceptions) {
            std::cout << "Cannot remove " << amount << " element because it is greater then the element count."
                      << std::endl;
        }
    } else {
        printNoBagMessage();
    }
}

void Menu::printElementCount() {
    if (isBagExists()) {
        int element = readElement();
        std::cout << "The count of the element " << element << " in the Bag is " << bag->getCount(element)
                  << std::endl;
    } else {
        printNoBagMessage();
    }
}

void Menu::printElementsCountOccursOnce() const {
    if (isBagExists()) {
        std::cout << "The number of elements occurs only once is " << bag->getSingleCardinalityElementsCount()
                  << std::endl;
    } else {
        printNoBagMessage();
    }
}

bool Menu::isBagExists() const {
    return bag != nullptr;
}

void Menu::deleteBag() {
    if (isBagExists()) {
        delete bag;
        bag = nullptr;
    }
}

void Menu::printNoBagMessage() const {
    std::cout << "Please create a Bag before use it!" << std::endl;
}

int Menu::readElement(std::string message) {
    bool fail;
    int element;
    do {
        std::cout << message;
        std::cin >> element;
        fail = std::cin.fail();
        if (fail) {
            std::cin.clear();
            std::cout << "Please enter valid integer" << std::endl;
        }
        std::string garbage;
        getline(std::cin, garbage);
    } while (fail);
    return element;
}

void Menu::loadTest() {
    Bag *testBag = new Bag();
    int i = 0;
    try {
        for (; i < 2147483647; i++) {
            testBag->put(i);
            if (i % 1000 == 0) {
                std::cout << i << " element already in the Bag" << std::endl;
            }
        }
        std::cout << "Test finished successfully" << std::endl;
        delete testBag;
    } catch (...) {
        std::cout << std::endl << "Run out of memory, last success element (" << i << ")" << std::endl;
        delete testBag;
    }
}
