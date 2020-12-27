#include "ContestEnumerator.h"

const std::string ContestEnumerator::CARP_NAME = "Ponty";

ContestEnumerator::ContestEnumerator(const std::string &file) throw(FileError) {
    fis.open(file);
    if (fis.fail()) {
        throw OpenFileStreamError;
    }
}

ContestEnumerator::~ContestEnumerator() {
    fis.close();
}

void ContestEnumerator::next() {
    cur.carpNumber = 0;
    cur.carpWeightSum = 0.0f;
    cur.onlyCarp = true;
    std::string line;
    getline(fis, line);
    eof = fis.fail();
    if (!eof) {
        std::istringstream is(line);
        is >> cur.name >> cur.contestId;
        calculate(is);
    }
}

void ContestEnumerator::calculate(std::istringstream &is) {
    std::string fish;
    float weight;
    is >> fish >> weight;
    while (!is.fail()) {
        if (fish == CARP_NAME) {
            cur.carpNumber++;
            cur.carpWeightSum += weight;
        } else {
            cur.onlyCarp = false;
        }

        is >> fish >> weight;
    }
}


