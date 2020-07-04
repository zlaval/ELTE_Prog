#include "values.h"

#ifndef OOP_ENUMERATORS_H
#define OOP_ENUMERATORS_H

const std::string GOOD_MOOD = "j";
const std::string BAD_MOOD = "r";
const std::string AVERAGE_MOOD = "a";

class MoodEnumerator {
private:
    std::vector<std::string> moods;
    MOOD cur;
    bool eos;
    unsigned int index = 0;
public:
    MoodEnumerator(const std::string &moodsStr) {
        for (char const &c:moodsStr) {
            std::string mood;
            mood = c;
            moods.push_back(mood);
        }
    }

    void first() { next(); }

    void next() {
        eos = index >= moods.size();
        if (!eos) {
            std::string actual = moods.at(index);
            if (actual == GOOD_MOOD) {
                cur = GOOD;
            } else if (actual == BAD_MOOD) {
                cur = BAD;
            } else if (actual == AVERAGE_MOOD) {
                cur = AVERAGE;
            }
            index++;
        }
    }

    bool end() {
        return eos;
    };

    MOOD current() {
        return cur;
    };

};


#endif //OOP_ENUMERATORS_H
