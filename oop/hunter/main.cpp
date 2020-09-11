#include <iostream>
#include "Hunter.h"


int main() {

    Hunter *hunter = new Hunter("Joe Doe", 40);

    Trophy *maleLion1 = new Lion("Africa", "2012.10.30", 220, Lion::GENDER::MALE);
    Trophy *maleLion2 = new Lion("Africa", "2020.03.13", 150, Lion::GENDER::MALE);
    Trophy *femaleLion = new Lion("Africa", "2020.01.12", 130, Lion::GENDER::FEMALE);
    Trophy *elephant = new Elephant("India", "2011.04.07", 2500, 55);
    Trophy *rhino = new Rhino("Africa", "2016.12.31", 1700, 1);


    hunter->addTrophy(maleLion1);
    hunter->addTrophy(elephant);
    hunter->addTrophy(maleLion2);
    hunter->addTrophy(rhino);
    hunter->addTrophy(femaleLion);

    std::cout << "Number of male lions: " << hunter->countOfMaleLions() << std::endl;

    delete maleLion1;
    delete maleLion2;
    delete femaleLion;
    delete elephant;
    delete rhino;
    delete hunter;
    return 0;
}
