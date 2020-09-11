#include <string>
#include <iostream>
#include "../lib/stringstreamenumerator.hpp"
#include "../lib/linsearch.hpp"
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/counting.hpp"

const std::string ERROR = "ERROR";

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
    int errorCount;
};

class CountErrors : public Summation<Journal, int> {
protected:
    int neutral() const final override { return 0; }
    int add(const int &a, const int &b) const final override { return a + b; }
    int func(const Journal &e) const final override { return 1; }
    bool cond(const Journal &e) const override {
        return e.entry == ERROR;
    }
};


std::istream &operator>>(std::istream &is, SpaceShip &spaceShip) {
    std::string line;
    getline(is, line);
    std::stringstream in(line);
    in >> spaceShip.name >> spaceShip.mission;
    spaceShip.errorCount = 0;
    StringStreamEnumerator<Journal> enor(in);
    CountErrors countErrors;
    countErrors.addEnumerator(&enor);
    countErrors.run();
    spaceShip.errorCount = countErrors.result();
    return is;
}


struct MissionData {
    std::string name;
    int errorCount;

    MissionData() {
    }

    MissionData(const SpaceShip &s) {
        name = s.name;
        errorCount = s.errorCount;
    }

};

class MissionAggregator : public Summation<SpaceShip, MissionData> {
private:
    std::string mission;
public:
    MissionAggregator(const std::string mission) {
        this->mission = mission;
    }
protected:
    bool whileCond(const SpaceShip &current) const override {
        return mission == current.mission;
    }

    MissionData func(const SpaceShip &e) const override {
        return MissionData(e);
    }
    MissionData neutral() const override {
        return MissionData(_enor->current());
    }
    MissionData add(const MissionData &a, const MissionData &b) const override {
        MissionData missionData;
        missionData.errorCount = a.errorCount + b.errorCount;
        return missionData;
    }
};

class MissionEnumerator : public Enumerator<MissionData> {
private:
    bool eos;
    MissionData data;
    SeqInFileEnumerator<SpaceShip> enor;
public:
    MissionEnumerator(const std::string filename) : enor(filename) {};
protected:
    void first() override {
        enor.first();
        next();
    }

    void next() override {
        eos = enor.end();
        if (!enor.end()) {
            MissionAggregator a(enor.current().mission);
            a.addEnumerator(&enor);
            a.run();
            data = a.result();
        }
    }

    bool end() const override {
        return eos;
    }

    MissionData current() const override {
        return data;
    }


};

struct AggregatedData {
    int errorCountSum;
    int failedMissionCount;

    AggregatedData() {
        errorCountSum = 0;
        failedMissionCount = 0;
    }

    AggregatedData(const MissionData &e) {
        errorCountSum = e.errorCount;
        failedMissionCount = e.errorCount > 0 ? 1 : 0;
    }
};

class ErrorAggregator : public Summation<MissionData, AggregatedData> {
protected:
    AggregatedData func(const MissionData &e) const override {
        return AggregatedData(e);
    }
    AggregatedData neutral() const override {
        return AggregatedData();
    }
    AggregatedData add(const AggregatedData &a, const AggregatedData &b) const override {
        AggregatedData result;
        if (b.errorCountSum > 0) {
            result.failedMissionCount = a.failedMissionCount + 1;
        } else {
            result.failedMissionCount = a.failedMissionCount;
        }
        result.errorCountSum = a.errorCountSum + b.errorCountSum;
        return result;
    }
};

int main(int argc, char *argv[]) {
    try {
        std::string path = argc > 1 ? argv[1] : "D:\\Application\\elte-prog\\oop\\cz72ym_zh2_5\\test_1.txt";
        MissionEnumerator enor(path);
        ErrorAggregator errorAggregator;
        errorAggregator.addEnumerator(&enor);
        errorAggregator.run();

        AggregatedData result = errorAggregator.result();
        if (result.failedMissionCount > 0) {
            double average = (double) result.errorCountSum / result.failedMissionCount;
            std::cout << "On " << result.failedMissionCount << " failed mission, " << result.errorCountSum
                      << " errors happend. Average " << average << " error/mission.";
        } else {
            std::cout << "No error in the logs.";
        }

        return 0;
    } catch (...) {
        std::cout << "Missing file";
        return 1;
    }

}
