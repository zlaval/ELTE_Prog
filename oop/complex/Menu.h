#include "Complex.h"
#ifndef OOP_MENU_H
#define OOP_MENU_H

enum OPERATION{ADD,SUB,MUL,DIV};

class Menu {
private:
    Complex* a;
    Complex* b;
    void printMenu() const;
    void handleMenuCode(int code);
    void readComplexA();
    void readComplexB();
    void add()  const;
    void sub()  const;
    void mul()  const;
    void div()  const;
    bool missingNumber()  const;
    void printResult(Complex result,OPERATION operation)  const;
    Complex* readComplex()  const;
public:
    Menu();
    void run();
    ~Menu();
};


#endif //OOP_MENU_H
