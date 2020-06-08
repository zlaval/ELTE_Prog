#include <string>
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/summation.hpp"
#include "../lib/stringstreamenumerator.hpp"


struct Catch {
    std::string breed;
    int weight;
};

std::istream &operator>>(std::istream &is, Catch &c) {
    is >> c.breed >> c.weight;
    return is;
}

struct Competition {
    std::string anglerName;
    std::string competition;
    bool catchTwoCarpAboveFiveKg;
};

class SumCarps : public Summation<Catch, int> {
public:
    SumCarps() : Summation<Catch, int>() {};
protected:
    int neutral() const override {
        return 0;
    }
    int add(const int &a, const int &b) const override {
        return a + 1;
    }
    bool cond(const Catch &e) const override {
        return e.breed == "ponty" && e.weight > 5;
    }
    int func(const Catch &e) const override {
        return 0;
    }
};

std::istream &operator>>(std::istream &is, Competition &competition) {
    std::string line;
    getline(is, line);
    std::stringstream in(line);
    in >> competition.anglerName;
    in >> competition.competition;

    StringStreamEnumerator<Catch> catchEnor(in);
    SumCarps c;
    c.addEnumerator(&catchEnor);
    c.run();
    competition.catchTwoCarpAboveFiveKg = c.result() >= 2;
    return is;
}

struct Angler {
    std::string name;
    bool catchOnAll;
};

class AnglerLoop : public Summation<Competition, bool> {
private:
    std::string name;
public:
    AnglerLoop(const std::string &name) {
        this->name = name;
    }
protected:
    bool neutral() const override { return true; }
    bool add(const bool &a, const bool &b) const override { return a && b; }
    bool func(const Competition &e) const override { return e.catchTwoCarpAboveFiveKg; }

    void first() override {}
    bool whileCond(const Competition &e) const override { return e.anglerName == name; }

};

class AnglerEnumerator : public Enumerator<Angler> {
private:
    SeqInFileEnumerator<Competition> enor;
    Angler angler;
    bool eos;
public:
    AnglerEnumerator(const std::string &fileName) : enor(fileName){};

protected:
    void first() override {
        enor.first();
        next();
    }
    void next() override {
        eos = enor.end();
        if (!enor.end()) {
            angler.name = enor.current().anglerName;
            AnglerLoop a(angler.name);
            a.addEnumerator(&enor);
            a.run();
            angler.catchOnAll = a.result();
        }
    }
    bool end() const override {
        return eos;
    }
    Angler current() const override {
        return angler;
    }

};

class Print : public Summation<Angler, std::ostream> {
public:
    Print(std::ostream *os) : Summation<Angler, std::ostream>(os) {}
protected:
    std::string func(const Angler &e) const override { return e.name + "\n"; }
    bool cond(const Angler &e) const override { return e.catchOnAll; }
};


int main(int argc, char *argv[]) {
    try {
        std::string path = argc > 1 ? argv[1] : "D:\\Application\\elte-prog\\oop\\task1\\input.txt";
        Print print(&std::cout);
        AnglerEnumerator enumerator(path);
        print.addEnumerator(&enumerator);
        print.run();
        return 0;
    } catch (...) {
        std::cout<<"Missing file";
        return 1;
    }

}