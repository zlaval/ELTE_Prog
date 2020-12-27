#include "Bag.h"

#ifndef OOP_MENU_H
#define OOP_MENU_H


class Menu {
private:
    Bag* bag;
    void printMenu() const;
    void printBag() const;
    void createBag();
    void addElement();
    void removeElement();
    void printElementCount() ;
    void printElementsCountOccursOnce() const;
    void handleMenu(int code);
    bool isBagExists() const;
    void deleteBag();
    void printNoBagMessage() const;
    int readElement(std::string message="Enter the element:");
    void loadTest();
public:
    Menu();
    void run();
    ~Menu();
};


#endif //OOP_MENU_H
