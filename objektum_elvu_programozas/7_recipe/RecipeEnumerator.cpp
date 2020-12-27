#include "RecipeEnumerator.h"

const std::string RecipeEnumerator::SUGAR_I18N = "cukor";

RecipeEnumerator::RecipeEnumerator(const std::string &file) throw(FileError) {
    fis.open(file);
    if (fis.fail()) {
        throw OpenFileStreamError;
    }
}

void RecipeEnumerator::next() {
    _end = eof;
    if (!eof) {
        curRecipe = lastRead.recipe;
        cur = lastRead.name == SUGAR_I18N;
        while (readLine() && curRecipe == lastRead.recipe) {
            cur |= lastRead.name == SUGAR_I18N;
        }
    }
}

bool RecipeEnumerator::readLine() {
    std::string line;
    getline(fis, line);
    eof = fis.fail();
    if (!eof) {
        std::istringstream is(line);
        is >> lastRead.recipe >> lastRead.name >> lastRead.quantity;
    }
    return !eof;
}


