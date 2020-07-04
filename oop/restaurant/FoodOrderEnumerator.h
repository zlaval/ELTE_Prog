#include <string>
#include <fstream>
#include <sstream>


#ifndef OOP_FOODORDERENUMERATOR_H
#define OOP_FOODORDERENUMERATOR_H

struct FoodOrder {
    std::string name;
    unsigned int taking = 0;
};

enum FileError {
    OpenFileStreamError
};

class FoodOrderEnumerator {
private:
    std::ifstream fis;
    bool eof;
    FoodOrder cur;
    bool _end;

    bool readLine();

    struct SingleOrder {
        unsigned int seq;
        std::string name;
        std::string timestamp;
        unsigned int portions;
        unsigned int unitPrice;
    };

    SingleOrder lastRead;
public:
    FoodOrderEnumerator(const std::string file) throw(FileError);
    ~FoodOrderEnumerator();

    void first() {
        readLine();
        next();
    };

    void next();
    FoodOrder current() const { return cur; }
    bool end() const { return _end; };
};


#endif //OOP_FOODORDERENUMERATOR_H
