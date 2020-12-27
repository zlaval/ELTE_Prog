#include "../lib/maxsearch.hpp"
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/stringstreamenumerator.hpp"
#include "../lib/summation.hpp"


#include <string>
#include <iostream>

struct BlackHole {
    std::string name;
    std::string probe;
    int lastMass;
    bool wasCloserThenThreeMLY;
};

struct Measure {
    std::string date;
    int mass;
    int distanceMLY;
};

std::istream &operator>>(std::istream &is, Measure &measure) {
    is >> measure.date >> measure.mass >> measure.distanceMLY;
}

struct AggregatedMeasure {
    bool close;
    int maxWeight;
};

class LookClosest : public Summation<Measure, AggregatedMeasure> {
private:
    AggregatedMeasure neutralElement;
public:
    LookClosest() {
        neutralElement.close = false;
        neutralElement.maxWeight = 0;
    }
protected:
    AggregatedMeasure func(const Measure &e) const override {
        AggregatedMeasure aggregatedMeasure;
        aggregatedMeasure.maxWeight = e.mass;
        aggregatedMeasure.close = e.distanceMLY < 3;
        return aggregatedMeasure;
    }
    AggregatedMeasure neutral() const override {
        return neutralElement;
    }
    AggregatedMeasure add(const AggregatedMeasure &a, const AggregatedMeasure &b) const override {
        AggregatedMeasure m;
        m.maxWeight = b.maxWeight;
        m.close = a.close || b.close;
        return m;
    }
};

std::istream &operator>>(std::istream &is, BlackHole &blackHole) {
    std::string line;
    getline(is, line);
    std::stringstream in(line);
    in >> blackHole.name >> blackHole.probe;
    StringStreamEnumerator<Measure> enor(in);
    LookClosest l;
    l.addEnumerator(&enor);
    l.run();
    blackHole.wasCloserThenThreeMLY = l.result().close;
    blackHole.lastMass = l.result().maxWeight;
    return is;
}

struct AggregatedBlackHoleData {
    std::string name;
    int sumMass;
    int db;
    double averageMass;
    bool wasClose;

    AggregatedBlackHoleData() {}
    AggregatedBlackHoleData(const BlackHole &e) {
        name = e.name;
        sumMass = e.lastMass;
        wasClose = e.wasCloserThenThreeMLY;
        averageMass = 0;
        db = 1;
    }

    AggregatedBlackHoleData(const std::string &name, double sumMass, bool wasClose) : name(name),
                                                                                      sumMass(sumMass),
                                                                                      wasClose(wasClose) {
        averageMass = 0;
        db = 1;
    }
};


//TODO calculate average and was closer
//then find max
class BlackHoleAggregator : public Summation<BlackHole, AggregatedBlackHoleData> {
private:
    std::string name;
public:
    BlackHoleAggregator(std::string name) {
        this->name = name;
    }
protected:
    AggregatedBlackHoleData func(const BlackHole &e) const override {
        return AggregatedBlackHoleData(e);
    }
    AggregatedBlackHoleData neutral() const override {
        return AggregatedBlackHoleData(_enor->current());
    }
    AggregatedBlackHoleData add(const AggregatedBlackHoleData &a, const AggregatedBlackHoleData &b) const override {
        AggregatedBlackHoleData result;
        result.name=a.name;
        result.wasClose = a.wasClose && b.wasClose;
        result.sumMass = a.sumMass + b.sumMass;
        result.db = a.db + 1;
        return result;
    }
    bool whileCond(const BlackHole &e) const override {
        return e.name == name;
    }
};

class BlackHoleEnumerator : public Enumerator<AggregatedBlackHoleData> {
private:
    bool eos;
    AggregatedBlackHoleData bh;
    SeqInFileEnumerator<BlackHole> enor;
public:
    BlackHoleEnumerator(const std::string filename) : enor(filename) {};
protected:
public:
    void first() override {
        enor.first();
        next();
    }

    void next() override {
        eos = enor.end();
        if (!enor.end()) {
            BlackHoleAggregator a(enor.current().name);
            a.addEnumerator(&enor);
            a.run();
            bh = a.result();
            bh.averageMass = (double) bh.sumMass / bh.db;
        }
    }

    bool end() const override {
        return eos;
    }

    AggregatedBlackHoleData current() const override {
        return bh;
    }

};

class BlackHoleComparator {
public:
    bool operator()(const AggregatedBlackHoleData &l, const AggregatedBlackHoleData &r) {
        return l.averageMass > r.averageMass;
    }
};

class BiggestBlackHole : public MaxSearch<AggregatedBlackHoleData, AggregatedBlackHoleData, BlackHoleComparator> {
protected:
    AggregatedBlackHoleData func(const AggregatedBlackHoleData &e) const override {
        return e;
    }
    bool cond(const AggregatedBlackHoleData &e) const override {
        return e.wasClose;
    }
};

int main(int argc, char *argv[]) {
    try {
        std::string path =
            argc > 1 ? argv[1] : "D:\\Application\\elte-prog\\oop\\2018_spring_blackhole_advanced\\input.txt";

        BlackHoleEnumerator enor(path);
        BiggestBlackHole blackHole;
        blackHole.addEnumerator(&enor);
        blackHole.run();
        if (blackHole.found()) {
            std::cout << "The most massive black hole got closest to Earth then 3 l.y. is "
                      << blackHole.optElem().name
                      << ". The average mass is " << blackHole.optElem().averageMass;
        } else {
            std::cout << "No data was provided";
        }

        return 0;
    } catch (...) {
        std::cout << "Missing file";
        return 1;
    }

}