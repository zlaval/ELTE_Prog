#include "FileReader.h"

void FileReader::next() {
    std::string line;
    getline(fis, line);
    eof = fis.fail();
    if (!eof) {
        std::istringstream is(line);
        is >> cur.key >> cur.value;
    }
}
