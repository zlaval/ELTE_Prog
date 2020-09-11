
#ifndef ALG1CPP_NODE_H
#define ALG1CPP_NODE_H

class Node {
public:
    Product *product = nullptr;
    Node *next = nullptr;

    Node() {

    }

    Node(Product *product) {
        this->product = product;
    }

    ~Node() {
        delete next;
        delete product;
    }

};

#endif //ALG1CPP_NODE_H
