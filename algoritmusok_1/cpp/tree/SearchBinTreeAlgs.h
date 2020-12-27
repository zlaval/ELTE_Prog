#include "Node.h"

#ifndef ALG1CPP_SEARCHBINTREEALGS_H
#define ALG1CPP_SEARCHBINTREEALGS_H

class SearchBinTreeAlgs {
private:
    int toNumber(std::string n) {
        return std::stoi(n);
    }
public:
    Node *search(Node *root, std::string key) {
        Node *actual = root;
        while (actual != nullptr && actual->getKey() != key) {
            if (toNumber(actual->getKey()) > toNumber(key)) {
                actual = actual->getLeft();
            } else {
                actual = actual->getRight();
            }
        }
        return actual;
    }

};


#endif //ALG1CPP_SEARCHBINTREEALGS_H
