#include "values.h"
#include <sstream>
#include <iostream>

#ifndef OOP_PET_H
#define OOP_PET_H

class Pet {
private:
    std::string name;
    bool dead;

    void selectMoodToVim(int mood);
    void calculateDeath();

    static const int MIN_VIM = 0;
    static const int MAX_VIM = 100;
protected:
    int vim;

    virtual void setGoodMoodVim() {};
    virtual void setBadMoodVim() {};
    virtual void setAverageMoodVim() {};
public:
    Pet(const std::string &name, int vim);
    bool isDead() const;
    int getVim() const;
    void adjustVim(int mood);
    std::string getName() const { return name; }

    virtual ~Pet() {
    };
    virtual std::string getType() const {};

    friend std::ostream &operator<<(std::ostream &os, const Pet &pet);
};

class Fish : public Pet {
private:
    void setGoodMoodVim() override;
    void setBadMoodVim() override;
    void setAverageMoodVim() override;
public:
    std::string getType() const override;
    Fish(const std::string &name, int vim);
};

class Dog : public Pet {
private:
    void setGoodMoodVim() override;
    void setBadMoodVim() override;
    void setAverageMoodVim() override;
public:
    std::string getType() const override;
    Dog(const std::string &name, int vim);
};

class Bird : public Pet {
private:
    void setGoodMoodVim() override;
    void setBadMoodVim() override;
    void setAverageMoodVim() override;
public:
    std::string getType() const override;
    Bird(const std::string &name, int vim);
};

enum UNKNOWN_TYPE_ERROR {
    UnknownType
};

const std::string FISH_TYPE = "H";
const std::string BIRD_TYPE = "M";
const std::string DOG_TYPE = "K";

//factory pattern
class PetFactory {
private:
    PetFactory() {}
public:
    ~PetFactory() {}
    //singleton pattern
    static PetFactory *getInstance() {
        static PetFactory instance;
        return &instance;
    }

    Pet *createPet(std::string line) {
        std::istringstream is(line);
        std::string type;
        std::string name;
        int vim;
        is >> type >> name >> vim;

        if (type == FISH_TYPE) {
            return new Fish(name, vim);
        } else if (type == BIRD_TYPE) {
            return new Bird(name, vim);
        } else if (type == DOG_TYPE) {
            return new Dog(name, vim);
        }
        throw UnknownType;
    }

};

#endif //OOP_PET_H
