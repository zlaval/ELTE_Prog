#include <vector>
#include <iostream>

#ifndef OOP_BAG_H
#define OOP_BAG_H


class Data {
public:
    int element;
    int count;
    Data(int e, int c) {
        this->count = c;
        this->element = e;
    }
};


class Bag {
private:
    std::vector<Data *> elements;
    int singleCardinalityCount;
    void handleSingleCardinality(int element, int previousValue);
    Data *getElement(int element) const;
    void removeFromVector(int element);
public:
    Bag();
    ~Bag();
    void put(int element);
    void remove(int element, int amount = 1);
    int getCount(int element) const;
    int getSingleCardinalityElementsCount() const;
    int size() const;

    enum Exceptions {
        INVALID_REMOVE_AMOUNT,ELEMENT_NOT_IN_THE_BAG
    };

    friend std::ostream &operator<<(std::ostream &o, const Bag &p);
};


#endif //OOP_BAG_H
