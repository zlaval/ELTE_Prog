#include "../lib/maxsearch.hpp"
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/stringstreamenumerator.hpp"
#include "../lib/summation.hpp"


#include <string>
#include <iostream>

struct BlackHole {
    std::string name;
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
    in >> blackHole.name;
    StringStreamEnumerator<Measure> enor(in);
    LookClosest l;
    l.addEnumerator(&enor);
    l.run();
    blackHole.wasCloserThenThreeMLY = l.result().close;
    blackHole.lastMass = l.result().maxWeight;
    return is;
}

class BiggestBlackHole : public MaxSearch<BlackHole, int> {
protected:
    int func(const BlackHole &e) const override {
        return e.lastMass;
    }

    bool cond(const BlackHole &e) const override {
        return e.wasCloserThenThreeMLY;
    }

};

int main(int argc, char *argv[]) {
    try {
        std::string path =
            argc > 1 ? argv[1] : "D:\\Application\\elte-prog\\oop\\2018_spring_blackhole_basic\\input.txt";
        SeqInFileEnumerator<BlackHole> enor(path);
        BiggestBlackHole blackHole;
        blackHole.addEnumerator(&enor);
        blackHole.run();
        if (blackHole.found()) {
            std::cout << "The most massive black hole got closest to Earth then 3 l.y. is "
                      << blackHole.optElem().name
                      << ". The mass is " << blackHole.optElem().lastMass;
        } else {
            std::cout << "No data was provided";
        }

        return 0;
    } catch (...) {
        std::cout << "Missing file";
        return 1;
    }

}