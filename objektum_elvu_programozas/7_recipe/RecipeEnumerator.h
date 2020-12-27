#include <string>
#include <fstream>
#include <sstream>

#ifndef OOP_RECIPEENUMERATOR_H
#define OOP_RECIPEENUMERATOR_H

enum FileError {
    OpenFileStreamError
};

class RecipeEnumerator {
private:
    std::ifstream fis;
    bool eof;
    bool _end;
    bool cur;
    std::string curRecipe;

    bool readLine();

    struct Ingredient {
        std::string recipe;
        std::string name;
        unsigned int quantity;
    };

    Ingredient lastRead;
    static const std::string SUGAR_I18N;
public:
    RecipeEnumerator(const std::string &file) throw(FileError);

    ~RecipeEnumerator() { fis.close(); }

    void first() {
        readLine();
        next();
    };

    void next();

    bool current() const { return cur; };

    bool end() { return _end; };

};


#endif //OOP_RECIPEENUMERATOR_H
