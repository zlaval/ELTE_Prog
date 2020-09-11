
#ifndef OOP_CARDRIVER_H
#define OOP_CARDRIVER_H

#include "PetrolStation.h"

class CarDriver {
public:

    void refuel(PetrolStation *petrolStation, unsigned int seq, unsigned int liter) {
        std::cout << "The driver arrived to the station" << std::endl;
        petrolStation->fill(seq, liter);
        petrolStation->pay(seq);
        std::cout << "The driver left the station" << std::endl;
    }

};


#endif //OOP_CARDRIVER_H
