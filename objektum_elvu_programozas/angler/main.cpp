#include <string>
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/stringstreamenumerator.hpp"
#include "../lib/counting.hpp"
#include "../lib/linsearch.hpp"

struct Angler {
    std::string name;
    std::string competition;
    bool catchFish;
};

struct Catch {
    std::string breed;
    int weight;
};

std::istream &operator>>(std::istream &is, Catch &c) {
    is >> c.breed >> c.weight;
    return is;
}

std::istream &operator>>(std::istream &is, Angler &angler) {
    std::string line;
    getline(is, line);
    std::stringstream in(line);
    in >> angler.name;
    in >> angler.competition;

    StringStreamEnumerator<Catch> catchEnor(in);
    Counting<Catch> c;
    c.addEnumerator(&catchEnor);
    c.run();
    angler.catchFish = c.result() != 0;
    return is;
}

class SearchNoobAngler : public LinSearch<Angler> {
protected:
    bool cond(const Angler &e) const override {
        return !e.catchFish;
    }
};

int main(int argc, char *argv[]) {
    std::string fileName = argc > 1 ? argv[1] : "input.txt";
    SeqInFileEnumerator<Angler> enor(fileName);
    SearchNoobAngler searchNoobAngler;
    searchNoobAngler.addEnumerator(&enor);
    searchNoobAngler.run();

    if (searchNoobAngler.found()) {
        Angler res = searchNoobAngler.elem();
        std::cout << res.name << " not catch any fish on competition " << res.competition;
    } else {
        std::cout << "Every angler catch fish on every competition";
    }
    return 0;
}