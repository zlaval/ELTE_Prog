
#ifndef OOP_SIMULATOR_H
#define OOP_SIMULATOR_H

#include <vector>
#include "pet.h"
#include "file_handler.h"
#include "enumerator.h"

class Simulator {
private:
    FileReader *fileReader = nullptr;
    PetFactory *petFactory = nullptr;
    void constructPets();
    void printDay(int day);
    bool isAllLivingPetsVimReachFive();
    void findSaddestLivingPet();
    void runSimulation();
    void printSaddestLivingPets();
protected:
    //protected for testing purpose only
    std::vector<Pet *> pets;
    std::vector<Pet *> saddestPets;
public:
    Simulator(FileReader *fileReader);
    ~Simulator();
    void run();
    std::vector<Pet *> *getPets() { return &pets; }
};

#endif //OOP_SIMULATOR_H
