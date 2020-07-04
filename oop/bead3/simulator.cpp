#include "simulator.h"

//dependency inversion (dependency injection)
Simulator::Simulator(FileReader *fileReader) {
    this->fileReader = fileReader;
    petFactory = PetFactory::getInstance();
}

Simulator::~Simulator() {
    delete petFactory;
    for (auto pet :pets) {
        delete pet;
    }
}
void Simulator::run() {
    constructPets();
    printDay(0);
    runSimulation();
    findSaddestLivingPet();
    printSaddestLivingPets();
}

void Simulator::constructPets() {
    fileReader->first();
    int numberOfPetsToConstruct = std::stoi(fileReader->current());
    while (numberOfPetsToConstruct > 0) {
        fileReader->next();
        Pet *pet = petFactory->createPet(fileReader->current());
        pets.push_back(pet);
        numberOfPetsToConstruct--;
    }
}

void Simulator::runSimulation() {
    fileReader->next();
    MoodEnumerator moodEnumerator(fileReader->current());
    moodEnumerator.first();
    int day = 1;
    while (!moodEnumerator.end()) {
        int mood = moodEnumerator.current();
        if (mood != GOOD && isAllLivingPetsVimReachFive()) {
            mood++;
        }
        for (auto pet:pets) {
            pet->adjustVim(mood);
        }
        printDay(day);
        moodEnumerator.next();
        day++;
    }
}

void Simulator::findSaddestLivingPet() {
    auto it = pets.begin();
    saddestPets.clear();
    while (it != pets.end()) {
        if (!(*it)->isDead()) {
            if (saddestPets.empty()) {
                saddestPets.push_back(*it);
            } else {
                //cognitive complexity maybe too high here
                Pet *actualSaddestPet = saddestPets.at(0);
                if ((*it)->getVim() < actualSaddestPet->getVim()) {
                    saddestPets.clear();
                    saddestPets.push_back(*it);
                } else if ((*it)->getVim() == actualSaddestPet->getVim()) {
                    saddestPets.push_back(*it);
                }
            }
        }
        it++;
    }
}

bool Simulator::isAllLivingPetsVimReachFive() {
    bool res = true;
    auto it = pets.begin();
    while (res && it != pets.end()) {
        Pet *pet = (*it);
        if (!pet->isDead()) {
            res = pet->getVim() >= 5;
        }
        it++;
    }
    return res;
}

void Simulator::printDay(int day) {
    std::cout << "********************DAY_" << day << "***********************" << std::endl;
    for (auto pet:pets) {
        std::cout << *pet;
    }
}
void Simulator::printSaddestLivingPets() {
    std::cout << "_______________SADDEST PET(S)___________________" << std::endl;
    if (saddestPets.empty()) {
        std::cout << "All pets are dead!" << std::endl;
    }
    for (auto saddestPet:saddestPets) {
        std::cout << *saddestPet << std::endl;
    }
}



