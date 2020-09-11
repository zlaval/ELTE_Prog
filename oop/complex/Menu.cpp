#include <iostream>
#include <string>
#include "Menu.h"

Menu::Menu() {
    this->a = nullptr;
    this->b = nullptr;
}

void Menu::run() {
    int code = 0;
    do {
        printMenu();
        std::cin >> code;
        handleMenuCode(code);
    } while (code != 0);
}

Menu::~Menu() {
    if (a != nullptr) {
        delete a;
    }
    if (b != nullptr) {
        delete b;
    }
}

void Menu::printMenu() const {
    std::cout << "0 - exit" << std::endl;
    std::cout << "1 - read first complex number (a)" << std::endl;
    std::cout << "2 - read second complex number (b)" << std::endl;
    std::cout << "3 - calculate a+b" << std::endl;
    std::cout << "4 - calculate a-b" << std::endl;
    std::cout << "5 - calculate a*b" << std::endl;
    std::cout << "6 - calculate a/b" << std::endl;
}

void Menu::handleMenuCode(int code) {
    switch (code) {
        case 1:
            readComplexA();
            break;
        case 2:
            readComplexB();
            break;
        case 3:
            add();
            break;
        case 4:
            sub();
            break;
        case 5:
            mul();
            break;
        case 6:
            div();
            break;
        default:
            break;
    }
}

void Menu::readComplexA() {
    a = readComplex();
}

void Menu::readComplexB() {
    b = readComplex();
}

Complex *Menu::readComplex() const {
    double real;
    double imaginary;
    std::cout << "Enter the real part: ";
    std::cin >> real;
    std::cout << std::endl;
    std::cout << "Enter the imaginary part: ";
    std::cin >> imaginary;
    std::cout << std::endl;
    return new Complex(real, imaginary);
}

void Menu::printResult(Complex result, OPERATION operation) const {
    std::string op = "";
    switch (operation) {
        case ADD:
            op = "+";
            break;
        case SUB:
            op = "-";
            break;
        case MUL:
            op = "*";
            break;
        case DIV:
            op = "/";
            break;
    }

    std::cout << *a << " " << op << " " << *b << " = " << result << std::endl;
}

void Menu::add() const {
    if (missingNumber()) return;
    Complex c = *a + *b;
    printResult(c, ADD);
}

void Menu::sub() const {
    if (missingNumber()) return;
    Complex c = *a - *b;
    printResult(c, SUB);
}

void Menu::mul() const {
    if (missingNumber()) return;
    Complex c = *a * *b;
    printResult(c, MUL);
}

void Menu::div() const {
    if (missingNumber()) return;
    Complex c = *a / *b;
    printResult(c, DIV);
}

bool Menu::missingNumber() const {
    if (a == nullptr || b == nullptr) {
        std::cout << "Please enter the (a) and (b) complex numbers first!" << std::endl;
        return true;
    }
    return false;
}
