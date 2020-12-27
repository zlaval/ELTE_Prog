
#ifndef OOP_DISPENSER_H
#define OOP_DISPENSER_H


#include <iostream>

class Dispenser {
private:
    unsigned int seq;
    unsigned int display;
public:

    Dispenser(unsigned int seq) {
        display = 0;
        this->seq = seq;
    }

    void fill(unsigned int liter) {
        std::cout << "Filling the tank with " << liter << " l of petrol on the " << seq << ". dispenser" << std::endl;
        display = liter;
    }

    void complete() {
        std::cout << "Reset the display to 0" << std::endl;
        display = 0;
    }

    unsigned int getQuantity() const {
        return display;
    }

};


#endif //OOP_DISPENSER_H
