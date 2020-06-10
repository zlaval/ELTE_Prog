#include <string>
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/summation.hpp"
#include "../lib/stringstreamenumerator.hpp"
#include "../lib/maxsearch.hpp"
#include "../lib/linsearch.hpp"


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
    std::string biggestFishBreed;
    int biggestFishWeight;
    bool catchCarpOverThreeKg;
};

class CompareFishSize {
public:
    bool operator()(const Catch &l, const Catch &r) {
        return l.weight > r.weight;
    }
};

class BiggestFish : public MaxSearch<Catch, Catch, CompareFishSize> {
protected:

    Catch func(const Catch &e) const override {
        return e;
    }
};

class SearchCarp : public LinSearch<Catch, false> {
protected:
    bool cond(const Catch &e) const override {
        return e.breed == "ponty" && e.weight > 3;
    }
};


std::istream &operator>>(std::istream &is, Competition &competition) {
    std::string line;
    getline(is, line);
    std::stringstream in(line);
    in >> competition.anglerName;
    in >> competition.competition;
    StringStreamEnumerator<Catch> catchEnor(in);
    BiggestFish b;
    b.addEnumerator(&catchEnor);
    b.run();
    if (b.found()) {
        competition.biggestFishBreed = b.optElem().breed;
        competition.biggestFishWeight = b.optElem().weight;
    }else{
        competition.biggestFishBreed = "";
        competition.biggestFishWeight = 0;
    }

    std::stringstream in2(line);
    std::string tmp;
    in2 >> tmp >> tmp;
    StringStreamEnumerator<Catch> catchEnor2(in2);
    SearchCarp searchCarp;
    searchCarp.addEnumerator(&catchEnor2);
    searchCarp.run();
    competition.catchCarpOverThreeKg = searchCarp.found();
    return is;
}

struct Angler {
    std::string name;
    std::string maxBreed;
    int maxWeight;
    bool catchOnAll;
};

class CompetitionSum {
public:
    std::string maxBreed;
    int maxWeight;
    bool catchOnAll;

    CompetitionSum() {}

    CompetitionSum(const Competition &c) {
        maxBreed = c.biggestFishBreed;
        maxWeight = c.biggestFishWeight;
        catchOnAll = c.catchCarpOverThreeKg;
    }

    CompetitionSum max(const CompetitionSum &a) const {
        CompetitionSum res;
        if (this->maxWeight > a.maxWeight) {
            res.maxWeight = maxWeight;
            res.maxBreed = maxBreed;
        } else {
            res.maxBreed = a.maxBreed;
            res.maxWeight = a.maxWeight;
        }
        res.catchOnAll = this->catchOnAll && a.catchOnAll;
        return res;
    }

};

class AnglerLoop : public Summation<Competition, CompetitionSum> {
private:
    std::string name;
    CompetitionSum neutralElement;
public:
    AnglerLoop(const std::string &name) {
        this->name = name;
        neutralElement.catchOnAll = true;
        neutralElement.maxBreed = "";
        neutralElement.maxWeight = 0;
    }
protected:
    CompetitionSum neutral() const override { return neutralElement; }

    CompetitionSum func(const Competition &e) const override {
        CompetitionSum c(e);
        return c;
    }
    CompetitionSum add(const CompetitionSum &a, const CompetitionSum &b) const override {
        return a.max(b);
    }

    void first() override {}
    bool whileCond(const Competition &e) const override { return e.anglerName == name; }

};

class AnglerEnumerator : public Enumerator<Angler> {
private:
    SeqInFileEnumerator<Competition> enor;
    Angler angler;
    bool eos;
public:
    AnglerEnumerator(const std::string &fileName) : enor(fileName) {};

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
            CompetitionSum c = a.result();
            angler.catchOnAll = c.catchOnAll;
            angler.maxBreed = c.maxBreed;
            angler.maxWeight = c.maxWeight;
        }
    }
    bool end() const override {
        return eos;
    }
    Angler current() const override {
        return angler;
    }

};

class CompareBiggestFishesSize {
public:
    bool operator()(const Angler &l, const Angler &r) {
        return l.maxWeight > r.maxWeight;
    }
};


class BiggestFishOnCompetitions: public MaxSearch<Angler,Angler,CompareBiggestFishesSize>{
public:
protected:
    Angler func(const Angler &e) const override {
        return e;
    }

    bool cond(const Angler &e) const override {
        return e.catchOnAll;
    }
};

int main(int argc, char *argv[]) {
    try {
        std::string path = argc > 1 ? argv[1] : "D:\\Application\\elte-prog\\oop\\task2\\input.txt";
        BiggestFishOnCompetitions biggestFishOnCompetitions;
        AnglerEnumerator enumerator(path);
        biggestFishOnCompetitions.addEnumerator(&enumerator);
        biggestFishOnCompetitions.run();
        if(biggestFishOnCompetitions.found()){
            Angler res=biggestFishOnCompetitions.optElem();
            std::cout<<"A legnagyobb halat "<<res.name<<" fogta, egy "<<res.maxBreed<<"-t ami "<<res.maxWeight<<" kg volt.";
        }else{
            std::cout<<"Senki nem fogott halat";
        }

        return 0;
    } catch (...) {
        std::cout << "Missing file";
        return 1;
    }

}