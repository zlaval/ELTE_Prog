#include <fstream>
#include <sstream>

#ifndef ALG1CPP_FILEREADER_H
#define ALG1CPP_FILEREADER_H

struct Tuple {
    int key;
    unsigned int value;
};

class FileReader {
private:
    Tuple cur;
    bool eof;
    std::ifstream fis;
public:
    FileReader(std::string fileName) {
        fis.open(fileName);
        //TODO exception handling
    };

    ~FileReader() {
        fis.close();
    }

    void first() {
        next();
    };

    void next();

    bool end() const { return eof; };

    Tuple current() const { return cur; };
};


#endif //ALG1CPP_FILEREADER_H
