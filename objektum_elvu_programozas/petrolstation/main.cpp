#include "CarDriver.h"


int main() {
    CarDriver *carDriver = new CarDriver();
    PetrolStation *petrolStation = new PetrolStation(350);

    carDriver->refuel(petrolStation, 3, 24);

    delete carDriver;
    delete petrolStation;

}