
#include "Map.h"

void Map::clear() {
    items.clear();
}

int Map::count() const {
    return items.size();
}

bool Map::put(Item element) {
    SearchResult result = findItemIndex(element.key);
    if (result.found) {
        return false;
    }
    items.push_back(element);
    return true;
}

bool Map::remove(int key) {
    SearchResult result = findItemIndex(key);
    if (result.found) {
        auto it = items.begin();
        std::advance(it, result.index);
        items.erase(it);
        return true;
    }
    return false;
}

std::string Map::operator[](int key) const {
    SearchResult result = findItemIndex(key);
    if (result.found) {
        return items.at(result.index).value;
    }
    //TODO handle not found with Optional return type
    return "null";
}

std::ostream &operator<<(std::ostream &o, const Map &p) {
    for (const auto &item : p.items) {
        o << item.key << " -> " << item.value << std::endl;
    }
    return o;
}

SearchResult Map::findItemIndex(int key) const {
    bool found = false;
    unsigned int index = 0;
    while (index < items.size() && !found) {
        Item item = items.at(index);
        if (item.key == key) {
            found = true;
        } else {
            index++;
        }
    }
    SearchResult result{found, index};
    return result;
}
