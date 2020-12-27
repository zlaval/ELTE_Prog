/**
 * Zalan Toth
 * cz72ym
 * 2020.04.12
 * Pets (task 9.)
 */

#define NORMAL_MODE
#ifdef NORMAL_MODE

#include "simulator.h"

int main() {
    std::string filePath;
    std::cout << "Enter the path of the input file:";
    std::cin >> filePath;
    FileReader *fileReader = new FileReader(filePath);

    Simulator *simulator = new Simulator(fileReader);
    simulator->run();

    delete fileReader;
    delete simulator;
    return 0;
}
#else

#define CATCH_CONFIG_MAIN

#include "simulator_to_test.h"
#include "catch.hpp"

void test(std::string &filePath, void (*test)(std::vector<Pet *> *, std::vector<Pet *> *)) {

    FileReader *fileReader = new FileReader(filePath);

    SimulatorTest *simulator = new SimulatorTest(fileReader);
    simulator->run();

    std::vector<Pet *> *pets = simulator->getPets();
    std::vector<Pet *> *saddestPets = simulator->getSaddestPets();

    test(pets, saddestPets);

    delete fileReader;
    delete simulator;
}

TEST_CASE("Dead pet should stay dead") {
    std::string filePath = "test1.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 0);
             CHECK(pets->at(0)->isDead() == 1);
             CHECK(saddestPets->size() == 0);
         });
}

TEST_CASE("Vim should increase if the owner is happy. Owner could not be happier") {
    std::string filePath = "test2.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 11);
             CHECK(pets->at(1)->getVim() == 12);
             CHECK(pets->at(2)->getVim() == 13);

             CHECK(saddestPets->size() == 1);
             CHECK(saddestPets->at(0)->getVim() == 11);
             CHECK(saddestPets->at(0)->getType() == FISH_TYPE_NAME);
         });
}

TEST_CASE("Vim should not exceed 100") {
    std::string filePath = "test3.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 100);
             CHECK(pets->at(1)->getVim() == 100);
             CHECK(pets->at(2)->getVim() == 100);

             CHECK(saddestPets->size() == 3);
         });
}

TEST_CASE("Vim should decrease if the owner is not happy.") {
    std::string filePath = "test4.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 5);
             CHECK(pets->at(1)->getVim() == 1);
             CHECK(pets->at(2)->getVim() == 10);

             CHECK(saddestPets->size() == 1);
             CHECK(saddestPets->at(0)->getVim() == 1);
             CHECK(saddestPets->at(0)->getType() == BIRD_TYPE_NAME);
         });
}

TEST_CASE("Dog's vim should not change if the owner is in average mood.") {
    std::string filePath = "test5.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 7);
             CHECK(pets->at(1)->getVim() == 3);
             CHECK(pets->at(2)->getVim() == 20);

             CHECK(saddestPets->size() == 1);
             CHECK(saddestPets->at(0)->getVim() == 3);
             CHECK(saddestPets->at(0)->getType() == BIRD_TYPE_NAME);
         });
}

TEST_CASE("Saddest pets should be empty if all pets die. Pets should die if vim become 0.") {
    std::string filePath = "test6.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 0);
             CHECK(pets->at(1)->getVim() == 0);
             CHECK(pets->at(2)->getVim() == 0);

             CHECK(saddestPets->size() == 0);
         });
}

TEST_CASE("Owner's mood should increase if all pets' vim reach 5") {
    std::string filePath = "test7.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 11);
             CHECK(pets->at(1)->getVim() == 12);
             CHECK(pets->at(2)->getVim() == 13);

             CHECK(saddestPets->size() == 1);
             CHECK(saddestPets->at(0)->getVim() == 11);
             CHECK(saddestPets->at(0)->getType() == FISH_TYPE_NAME);
         });
}

TEST_CASE("SaddestPets should contains all living pets with min (1,100] vim.") {
    std::string filePath = "test8.txt";

    test(filePath,
         [](std::vector<Pet *> *pets, std::vector<Pet *> *saddestPets) -> void {
             CHECK(pets->at(0)->getVim() == 0);
             CHECK(pets->at(1)->getVim() == 19);
             CHECK(pets->at(2)->getVim() == 18);
             CHECK(pets->at(3)->getVim() == 33);
             CHECK(pets->at(4)->getVim() == 18);
             CHECK(pets->at(5)->getVim() == 0);

             CHECK(saddestPets->size() == 2);
             CHECK(saddestPets->at(0)->getVim() == 18);
             CHECK(saddestPets->at(1)->getVim() == 18);
             CHECK(saddestPets->at(0)->getType() == BIRD_TYPE_NAME);
             CHECK(saddestPets->at(1)->getType() == DOG_TYPE_NAME);
         });
}

#endif