#include "pet.h"

std::ostream &operator<<(std::ostream &os, const Pet &pet) {
    if (pet.isDead()) {
        std::cout << pet.name << " the " << pet.getType() << " is already dead" << std::endl;
    } else {
        std::cout << pet.name << " the " << pet.getType() << " has " << pet.vim << " points of vim" << std::endl;
    }
    return os;
}

Pet::Pet(const std::string &name, const int vim) {
    this->name = name;
    this->vim = vim > MAX_VIM ? MAX_VIM : vim;
    this->dead = vim == MIN_VIM;
}

bool Pet::isDead() const {
    return dead;
}

void Pet::adjustVim(int mood) {
    if (!dead) {
        selectMoodToVim(mood);
        calculateDeath();
    }
}

int Pet::getVim() const {
    return vim;
}

void Pet::selectMoodToVim(int mood) {
    switch (mood) {
        case GOOD:
            setGoodMoodVim();
            break;
        case BAD:
            setBadMoodVim();
            break;
        case AVERAGE:
            setAverageMoodVim();
            break;
        default:
            break;
    }
}

void Pet::calculateDeath() {
    if (vim < MIN_VIM) {
        vim = MIN_VIM;
    } else if (vim > MAX_VIM) {
        vim = MAX_VIM;
    }
    dead = vim == MIN_VIM;
}

Fish::Fish(const std::string &name, int vim) : Pet(name, vim) {}

void Fish::setGoodMoodVim() {
    vim++;
}

void Fish::setBadMoodVim() {
    vim -= 5;
}

void Fish::setAverageMoodVim() {
    vim -= 3;
}

std::string Fish::getType() const {
    return FISH_TYPE_NAME;
}

Dog::Dog(const std::string &name, int vim) : Pet(name, vim) {}

void Dog::setGoodMoodVim() {
    vim += 3;
}

void Dog::setBadMoodVim() {
    vim -= 10;
}

void Dog::setAverageMoodVim() {
}

std::string Dog::getType() const {
    return DOG_TYPE_NAME;
}

Bird::Bird(const std::string &name, int vim) : Pet(name, vim) {}

void Bird::setGoodMoodVim() {
    vim += 2;
}

void Bird::setBadMoodVim() {
    vim -= 3;
}

void Bird::setAverageMoodVim() {
    vim--;
}

std::string Bird::getType() const {
    return BIRD_TYPE_NAME;
}

