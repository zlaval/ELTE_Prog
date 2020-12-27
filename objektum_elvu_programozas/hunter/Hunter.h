#include <string>
#include <vector>
#include "Trophy.h"

#ifndef OOP_HUNTER_H
#define OOP_HUNTER_H


class Hunter {
private:
    std::string name;
    int age;
    std::vector<Trophy *> trophies;
public:
    Hunter(const std::string &name, int age) {
        this->name = name;
        this->age = age;
    }

    int getAge() const { return age; }
    std::string getName() const { return name; }
    void addTrophy(Trophy *trophy) {
        trophies.push_back(trophy);
    }
    int countOfMaleLions() {
        int count = 0;
        for (auto trophy : trophies) {
            if (isLion(trophy) && trophy->getSpecialAttrib() == Lion::GENDER::MALE) {
                count++;
            }
        }
        return count;
    }

    bool isLion(Trophy *t) {
        return dynamic_cast<Lion *>(t) != nullptr;
    }

};


#endif //OOP_HUNTER_H
