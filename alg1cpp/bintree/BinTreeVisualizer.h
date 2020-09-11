#ifndef ALG1CPP_BINTREEVISUALIZER_H
#define ALG1CPP_BINTREEVISUALIZER_H

#include <math.h>

class BinTreeVisualizer {
private:
    BinTreeAlgs *algs;

    void writeSpaces(int num) {
        for (int i = 0; i < num; i++) {
            std::cout << " ";
        }
    }
public:

    BinTreeVisualizer() {
        algs = new BinTreeAlgs();
    }

    ~BinTreeVisualizer() {
        delete algs;
    }


    //TODO print as normal tree, refactor
    void printTree(Node *root) {
        int height = algs->treeHeight(root);
        std::vector<Node *> dummies;

        int lastLevel = 0;
        int numInThisLevel = 0;

        std::queue<Node *> queue;
        queue.push(root);
        while (!queue.empty()) {
            Node *node = queue.front();
            queue.pop();
            if (lastLevel < node->getLevel()) {
                std::cout << std::endl;
                numInThisLevel = 0;
            }
            std::string key = node->getKey();
            int space = pow(2, (height - node->getLevel())) - 1;
            if (numInThisLevel > 0) {
                space *= 2;
            }

            writeSpaces(space);
            if (key.length() < 2) {
                key += " ";
            }
            std::cout << key;

            if (node->getLeft() != nullptr) {
                queue.push(node->getLeft());
            } else if (node->getLevel() < height) {
                Node *a = new Node(" ", node->getLevel() + 1);
                dummies.push_back(a);
                queue.push(a);
            }
            if (node->getRight() != nullptr) {

                queue.push(node->getRight());
            } else if (node->getLevel() < height) {
                Node *a = new Node(" ", node->getLevel() + 1);
                dummies.push_back(a);
                queue.push(a);
            }
            numInThisLevel++;
            lastLevel = node->getLevel();
        }
        for (auto a:dummies) {
            delete a;
        }
    }

};


#endif //ALG1CPP_BINTREEVISUALIZER_H
