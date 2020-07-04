#include <string>
#include <fstream>
#include <sstream>

#ifndef OOP_CONTESTENUMERATOR_H
#define OOP_CONTESTENUMERATOR_H

struct ContestCompetitor {
    std::string contestId;
    std::string name;
    unsigned int carpNumber = 0;
    float carpWeightSum = 0.0f;
    bool onlyCarp = true;
};

enum FileError {
    OpenFileStreamError
};

class ContestEnumerator {
private:
    std::ifstream fis;
    ContestCompetitor cur;
    bool eof;
    void calculate(std::istringstream &is);
    static const std::string CARP_NAME;
public:
    ContestEnumerator(const std::string &file) throw(FileError);
    ~ContestEnumerator();
    void first() { next(); }
    void next();
    ContestCompetitor current() const { return cur; }
    bool end() const { return eof; }
};

#endif //OOP_CONTESTENUMERATOR_H
