#include "FoodOrderEnumerator.h"

FoodOrderEnumerator::FoodOrderEnumerator(const std::string file) throw(FileError) {
    fis.open(file);
    if (fis.fail()) {
        throw OpenFileStreamError;
    }
}

FoodOrderEnumerator::~FoodOrderEnumerator() {
    fis.close();
}

void FoodOrderEnumerator::next() {
    _end = eof;
    if (!eof) {
        cur.name = lastRead.name;
        cur.taking = lastRead.unitPrice * lastRead.portions;
        while (readLine() && cur.name == lastRead.name) {
            cur.taking += (lastRead.portions * lastRead.unitPrice);
        }
    }
}

bool FoodOrderEnumerator::readLine() {
    std::string line;
    getline(fis, line);
    eof = fis.fail();
    if (!eof) {
        std::istringstream is(line);
        is >> lastRead.seq >> lastRead.name >> lastRead.timestamp >> lastRead.portions >> lastRead.unitPrice;
    }
    return !eof;
}

