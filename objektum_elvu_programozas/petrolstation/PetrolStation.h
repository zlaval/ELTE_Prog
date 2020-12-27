#include <vector>
#include "Dispenser.h"
#include "PayOffice.h"


#ifndef OOP_PETROLSTATION_H
#define OOP_PETROLSTATION_H

enum Error {
    NO_SUCH_STATION_ERROR
};

class PetrolStation {
private:
    std::vector<Dispenser *> dispensers;
    std::vector<PayOffice *> payOffices;
    int unitPrice;
public:

    PetrolStation(unsigned int unitPrice) {
        this->unitPrice = unitPrice;
        payOffices.push_back(new PayOffice());
        payOffices.push_back(new PayOffice());
        for (int i = 0; i < 6; i++) {
            dispensers.push_back(new Dispenser(i));
        }
    }

    ~PetrolStation() {
        for (auto dispenser : dispensers) {
            delete dispenser;
        }
        for (auto payOffice:payOffices) {
            delete payOffice;
        }

    }

    void pay(unsigned int seq) {
        Dispenser *dispenser = dispensers.at(seq);
        PayOffice *payOffice = payOffices.at(seq % 2);
        payOffice->pay(dispenser->getQuantity(), unitPrice);
        dispenser->complete();
    }

    void fill(unsigned int seq, unsigned int liter) {
        if (seq >= dispensers.size()) {
            throw NO_SUCH_STATION_ERROR;
        }
        dispensers.at(seq)->fill(liter);
        std::cout << "The tank was filled" << std::endl;
    }

};

#endif //OOP_PETROLSTATION_H
