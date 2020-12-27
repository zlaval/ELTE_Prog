#include "ContestEnumerator.h"

#ifndef OOP_ANGLERENUMERATOR_H
#define OOP_ANGLERENUMERATOR_H

struct Angler {
    std::string name;
    bool catchOnlyCarp = true;
};


class AnglerEnumerator {
private:
    Angler cur;
    ContestEnumerator contCur;
    bool eof;
public:
    AnglerEnumerator(const std::string &file) : contCur(file) {};
    void next();
    bool end() const { return eof; }

    void first() {
        contCur.first();
        next();
    }

    Angler current() const { return cur; }
};


#endif //OOP_ANGLERENUMERATOR_H
