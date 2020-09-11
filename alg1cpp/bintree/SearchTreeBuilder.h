
#ifndef ALG1CPP_SEARCHTREEBUILDER_H
#define ALG1CPP_SEARCHTREEBUILDER_H

class SearchTreeBuilder : public Common {
private:

    //TODO bugfix last element missing
    Node *preSearchTreeRec(std::vector<std::string> &preOrder, int from, int to, int level) {
        if (from >= to) {
            return nullptr;
        }
        Node *node = new Node(preOrder.at(from), level);
        int rightChildIndex = nextBiggerIndex(preOrder, from, to, preOrder.at(from));

        Node *left = preSearchTreeRec(preOrder, from + 1, rightChildIndex, level + 1);
        node->setLeft(left);
        if (rightChildIndex > -1) {
            Node *right = preSearchTreeRec(preOrder, rightChildIndex, to, level + 1);
            node->setRight(right);
        }
        return node;
    }

    int nextBiggerIndex(std::vector<std::string> &preOrder, int from, int to, std::string element) {
        int e = stoi(element);
        for (int i = from; i <= to; i++) {
            if (stoi(preOrder.at(i)) > e) {
                return i;
            }
        }
        return -1;
    }

public:
    Node *buildSearchTreePreOrder(const std::string &expression) {
        std::vector<std::string> preOrder;
        parseSpacedExpression(preOrder, expression);
        return preSearchTreeRec(preOrder, 0, preOrder.size() - 1, 0);
    }

    //TODO implement
    Node *buildSearchTreePostOrder(std::string &expression) {
        std::vector<std::string> postOrder;
        std::stack<Node *> nodes;
        parseSpacedExpression(postOrder, expression);
        //TODO
        return nullptr;
    }


};

#endif //ALG1CPP_SEARCHTREEBUILDER_H
