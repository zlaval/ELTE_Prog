
#ifndef ALG1CPP_NODE_H
#define ALG1CPP_NODE_H

#include <string>

class Node {
private:
    std::string key;
    int level;
    Node *left = nullptr;
    Node *right = nullptr;
public:
    Node(std::string key, int level) {
        this->key = key;
        this->level = level;
    }

    Node(std::string key, int level, Node *left, Node *right) {
        this->key = key;
        this->level = level;
        this->left = left;
        this->right = right;
    }

    ~Node(){
        delete left;
        delete right;
    };

    Node *getLeft() {
        return left;
    }

    void setLeft(Node *node) {
        this->left = node;
    }

    Node *getRight() {
        return right;
    }
    void setRight(Node *node) {
        this->right = node;
    }

    std::string getKey() {
        return key;
    }

    int getLevel() {
        return level;
    }

};


#endif //ALG1CPP_NODE_H
