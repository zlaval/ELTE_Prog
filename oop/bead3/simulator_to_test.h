
#ifndef OOP_SIMULATOR_TO_TEST_H
#define OOP_SIMULATOR_TO_TEST_H

#include "simulator.h"

class SimulatorTest : public Simulator {
public:
    SimulatorTest(FileReader *fileReader) : Simulator(fileReader) {}
    std::vector<Pet *> *getPets() { return &pets; }
    std::vector<Pet *> *getSaddestPets() { return &saddestPets; }
};

#endif //OOP_SIMULATOR_TO_TEST_H
