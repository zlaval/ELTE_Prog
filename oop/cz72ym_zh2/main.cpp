#include <string>
#include <iostream>
#include "../lib/stringstreamenumerator.hpp"
#include "../lib/linsearch.hpp"
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/maxsearch.hpp"

const std::string DOCK = "DOCK";

struct Journal {
    int timestamp;
    std::string entry;
};

std::istream &operator>>(std::istream &is, Journal &journal) {
    is >> journal.timestamp >> journal.entry;
    return is;
}


struct SpaceShip {
    std::string name;
    std::string mission;
    int dockTimestamp;
    bool isDocked;
};

class DockSearch : public LinSearch<Journal> {
protected:
    bool cond(const Journal &e) const override {
        return e.entry == DOCK;
    }
};

std::istream &operator>>(std::istream &is, SpaceShip &spaceShip) {
    std::string line;
    getline(is, line);
    std::stringstream in(line);
    in >> spaceShip.name >> spaceShip.mission;

    StringStreamEnumerator<Journal> enor(in);
    DockSearch dockSearch;
    dockSearch.addEnumerator(&enor);
    dockSearch.run();
    if (dockSearch.found()) {
        spaceShip.isDocked = true;
        spaceShip.dockTimestamp = dockSearch.elem().timestamp;
    } else {
        spaceShip.isDocked = false;
    }
    return is;
}

class SpaceShipComparator {
public:
    bool operator()(const SpaceShip &l, const SpaceShip &r) {
        return l.dockTimestamp < r.dockTimestamp;
    }
};

class FindFastestSpaceShip : public MaxSearch<SpaceShip, SpaceShip, SpaceShipComparator> {
protected:
    SpaceShip func(const SpaceShip &e) const override {
        return e;
    }

    bool cond(const SpaceShip &e) const override {
        return e.isDocked;
    }
};

int main(int argc, char *argv[]) {
    try {
        std::string path = argc > 1 ? argv[1] : "D:\\Application\\elte-prog\\oop\\cz72ym_zh2\\test_2.txt";
        SeqInFileEnumerator<SpaceShip> enor(path);
        FindFastestSpaceShip findFastestSpaceShip;
        findFastestSpaceShip.addEnumerator(&enor);
        findFastestSpaceShip.run();

        if (findFastestSpaceShip.found()) {
            SpaceShip result = findFastestSpaceShip.optElem();
            std::cout << result.name
                      << " spaceship from mission "
                      << result.mission
                      << " is docker after "
                      << result.dockTimestamp
                      << " galactical day.";
        } else {
            std::cout << "No ship docker yet.";
        }


        return 0;
    } catch (...) {
        std::cout << "Missing file";
        return 1;
    }

}
