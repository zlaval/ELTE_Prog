
#ifndef OOP_FILE_HANDLER_H
#define OOP_FILE_HANDLER_H

#include <fstream>

enum FileError {
    OPEN_FILE
};

class FileReader {
private:
    std::ifstream fis;
    bool eof;
    std::string line;
public:

    FileReader(const std::string &fileName) throw(FileError) {
        fis.open(fileName);
        if (fis.fail()) {
            throw OPEN_FILE;
        }
    }

    ~FileReader() {
        fis.close();
    }

    void first() {
        next();
    }

    std::string &current() {
        return line;
    }

    bool end() {
        return eof;
    }

    void next() {
        std::string read;
        getline(fis, read);
        eof = fis.fail();
        if (!eof) {
            line = read;
        }
    }

};

#endif //OOP_FILE_HANDLER_H
