
#ifndef ALG1CPP_BINTREEALGS_H
#define ALG1CPP_BINTREEALGS_H

#include <queue>
#include <iostream>
#include "Node.h"

class BinTreeAlgs {
public:

    void preOrder(Node *node) {
        if (node != nullptr) {
            std::cout << node->getKey() << " ";
            preOrder(node->getLeft());
            preOrder(node->getRight());
        }
    }

    void inOrder(Node *node) {
        if (node != nullptr) {
            inOrder(node->getLeft());
            std::cout << node->getKey() << " ";
            inOrder(node->getRight());
        }
    }

    void postOrder(Node *node) {
        if (node != nullptr) {
            postOrder(node->getLeft());
            postOrder(node->getRight());
            std::cout << node->getKey() << " ";
        }
    }

    void levelOrder(Node *root) {
        if (root != nullptr) {
            std::queue<Node *> nodes;
            nodes.push(root);
            while (!nodes.empty()) {
                Node *actual = nodes.front();
                nodes.pop();
                std::cout << actual->getKey() << " ";
                if (actual->getLeft() != nullptr) {
                    nodes.push(actual->getLeft());
                }
                if (actual->getRight() != nullptr) {
                    nodes.push(actual->getRight());
                }
            }
        }
        std::cout << std::endl;
    }

    int treeHeight(Node *root) {
        if (root != nullptr) {
            int left = treeHeight(root->getLeft());
            int right = treeHeight(root->getRight());
            return 1 + std::max(left, right);
        } else {
            return -1;
        }
    }

    int leafCount(Node *root) {
        if (root != nullptr) {
            if (root->getLeft() == nullptr && root->getRight() == nullptr) {
                return 1;
            }
            return leafCount(root->getLeft()) + leafCount(root->getRight());
        }
        return 0;
    }

};

#endif //ALG1CPP_BINTREEALGS_H
