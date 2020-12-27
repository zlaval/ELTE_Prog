#include <vector>
#include <iostream>
#include <string>

#ifndef OOP_MAP_H
#define OOP_MAP_H

struct Item {
    int key;
    std::string value;
};

struct SearchResult {
    bool found;
    unsigned int index;
};

class Map {
private:
    std::vector<Item> items;
    SearchResult findItemIndex(int key) const;

public:
    void clear();
    int count() const;
    bool put(Item element);
    bool remove(int key);
    std::string operator[](int key) const;

    friend std::ostream &operator<<(std::ostream &o, const Map &p);
};


#endif //OOP_MAP_H
