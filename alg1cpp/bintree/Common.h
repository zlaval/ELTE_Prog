
#ifndef ALG1CPP_COMMON_H
#define ALG1CPP_COMMON_H

#include <sstream>

class Common{
protected:
    void parseSpacedExpression(std::vector<std::string> &expression, const std::string &str) {
        std::istringstream is(str);
        while (!is.eof()) {
            std::string tmp;
            is >> tmp;
            expression.push_back(tmp);
        }
    }

};

#endif //ALG1CPP_COMMON_H
