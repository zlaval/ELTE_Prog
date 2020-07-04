#include <stdexcept>
#include "Bag.h"

/**
 * Create a new Bag
 */
Bag::Bag() : singleCardinalityCount(0) {}

/**
 * Delete the Bag content
 */
Bag::~Bag() {
    for (auto e:elements) {
        delete e;
    }
}

/**
 * Put an element into the Bag
 */
void Bag::put(int element) {
    Data *item = getElement(element);
    int originalCount = 0;
    if (item == nullptr) {
        item = new Data(element, 1);
        elements.push_back(item);
    } else {
        originalCount = item->count;
        item->count++;
    }
    handleSingleCardinality(item->count, originalCount);
}

/**
 * Remove an element from the Bag
 * @param element
 * @param amount of the element to remove in range 0 to count(element), default 1
 * @throw INVALID_REMOVE_AMOUNT if amount greater then element count
 */
void Bag::remove(int element, int amount) {
    Data *item = getElement(element);
    if (item != nullptr) {
        int originalCount = item->count;
        if (originalCount < amount) {
            throw Bag::INVALID_REMOVE_AMOUNT;
        }
        item->count -= amount;
        handleSingleCardinality(item->count, originalCount);
        if (item->count <= 0) {
            removeFromVector(element);
            delete item;
        }
    }else{
        throw Bag::ELEMENT_NOT_IN_THE_BAG;
    }
}

/**
 * Give the element count in the Bag
 * @param element
 * @return count of the element, 0 if not in the bag
 */
int Bag::getCount(int element) const {
    int count = 0;
    Data *item = getElement(element);
    if (item != nullptr) {
        count = item->count;
    }
    return count;
}

void Bag::handleSingleCardinality(int actualValue, int previousValue) {
    if (actualValue == 1 && previousValue != 1) {
        singleCardinalityCount++;
    } else if (actualValue != 1 && previousValue == 1) {
        singleCardinalityCount--;
    }
}

std::ostream &operator<<(std::ostream &o, const Bag &p) {
    for (unsigned int i = 0; i < p.elements.size(); i++) {
        o << p.elements.at(i)->element << " -> " << p.elements.at(i)->count << std::endl;
    }
    return o;
}
/**
 *
 * @return the size of the elements
 */
int Bag::size() const {
    return elements.size();
}

/**
 * @return Number of elements which occurs once in the Bag
 */
int Bag::getSingleCardinalityElementsCount() const {
    return singleCardinalityCount;
}

/**
 *
 * @param element
 * @return a pointer to the given element, or nullptr if element is not in the bag
 */
Data *Bag::getElement(int element) const {
    auto it = elements.begin();
    bool find = false;
    Data *item = nullptr;
    while (it != elements.end() && !find) {
        if (element == (*it)->element) {
            item = *it;
            find = true;
        }
        it++;
    }
    return item;
}
void Bag::removeFromVector(int element) {
    auto it = elements.begin();
    bool removed = false;
    while (it != elements.end() && !removed) {
        if (element == (*it)->element) {
            elements.erase(it);
            removed = true;
        }
        it++;
    }
}

