
#ifndef OOP_PAYOFFICE_H
#define OOP_PAYOFFICE_H

#include <iostream>

class PayOffice {
public:

    void pay(unsigned int quantity, unsigned int unitPrice) {
        unsigned int amount = quantity * unitPrice;
        std::cout << "Pay " << amount << " HUF" << std::endl;
    }

};

#endif //OOP_PAYOFFICE_H
