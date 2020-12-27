
#ifndef ALG1CPP_BINTREEBUILDER_H
#define ALG1CPP_BINTREEBUILDER_H

#include <sstream>
#include <stack>
#include "Common.h"

class BinTreeBuilder: public Common{
private:

    int findNextNodeIndex(std::vector<std::string> &expression, int from, int to) {
        int numberOfParenthesis = 0;
        if (expression.at(from) != "(" && expression.at(from) != ")") {
            return from;
        }
        while (from < to) {
            if (expression.at(from) == "(") {
                numberOfParenthesis++;
            } else if (expression[from] == ")") {
                numberOfParenthesis--;
            }
            from++;
            if (numberOfParenthesis == 0) {
                return from;
            }
        }
        return -1;
    }

    void parseParenthesesExpression(std::vector<std::string> &v, const std::string &expression) {
        std::string tmp;
        tmp = "";
        for (char const &c:expression) {
            std::string s;
            s = c;
            if (s == "(" || s == ")") {
                if (tmp.length() > 0) {
                    v.push_back(tmp);
                }
                v.push_back(s);
                tmp = "";
            } else {
                tmp += c;
            }
        }
        if (tmp.length() > 0) {
            v.push_back(tmp);
        }
    }

    Node *buildBinTreeFromParenthesesExpression(std::vector<std::string> &expression, int from, int to, int level) {
        if (from >= to) {
            return nullptr;
        }
        int nodeIndex = findNextNodeIndex(expression, from + 1, to - 1);
        Node *root = new Node(expression.at(nodeIndex), level);
        Node *left = buildBinTreeFromParenthesesExpression(expression, from + 1, nodeIndex - 1, level + 1);
        Node *right = buildBinTreeFromParenthesesExpression(expression, nodeIndex + 1, to - 1, level + 1);
        root->setLeft(left);
        root->setRight(right);
        return root;
    }

    Node *buildFromPreOrderTraversal(int &preIndex,
                                     int from,
                                     int to,
                                     std::vector<std::string> &pre,
                                     std::vector<std::string> &in,
                                     int level) {
        if (preIndex > pre.size() - 1) {
            return nullptr;
        }

        int index = findInOrderIndex(in, pre.at(preIndex), from, to);
        Node *node = nullptr;
        if (index > -1) {
            node = new Node(pre.at(preIndex), level);
            preIndex = preIndex + 1;
            Node *left = buildFromPreOrderTraversal(preIndex, from, index - 1, pre, in, level + 1);
            Node *right = buildFromPreOrderTraversal(preIndex, index + 1, to, pre, in, level + 1);
            node->setLeft(left);
            node->setRight(right);
        }
        return node;
    }

    int findInOrderIndex(std::vector<std::string> v, const std::string str, int from, int to) {
        int i = from;
        bool find = false;
        while (i <= to && !find) {
            if (v.at(i) == str) {
                return i;
            }
            i++;
        }
        return -1;
    }


    Node *buildFromPostOrderTraversal(int &postIndex,
                                      int from,
                                      int to,
                                      std::vector<std::string> &pos,
                                      std::vector<std::string> &in,
                                      int level) {
        if (postIndex < 0) {
            return nullptr;
        }

        int index = findInOrderIndex(in, pos.at(postIndex), from, to);
        Node *node = nullptr;
        if (index > -1) {

            node = new Node(pos.at(postIndex), level);

            postIndex = postIndex - 1;
            Node *right = buildFromPostOrderTraversal(postIndex, index + 1, to, pos, in, level + 1);
            Node *left = buildFromPostOrderTraversal(postIndex, from, index - 1, pos, in, level + 1);
            node->setLeft(left);
            node->setRight(right);
        }
        return node;
    }

public:

    Node *buildFromParenthesesExpression(const std::string &expression) {
        std::vector<std::string> v;
        parseParenthesesExpression(v, expression);
        Node *root = buildBinTreeFromParenthesesExpression(v, 0, v.size() - 1, 0);
        return root;
    }

    Node *buildFromPreOrderTraversal(const std::string &preOrder, const std::string &inOrder) {
        std::vector<std::string> prev;
        std::vector<std::string> inv;
        std::stack<Node *> nodes;
        parseSpacedExpression(prev, preOrder);
        parseSpacedExpression(inv, inOrder);
        int pi = 0;
        return buildFromPreOrderTraversal(pi, 0, prev.size() - 1, prev, inv, 0);
    }

    Node *buildFromPostOrderTraversal(const std::string &postOrder, const std::string &inOrder) {
        std::vector<std::string> posv;
        std::vector<std::string> inv;
        std::stack<Node *> nodes;
        parseSpacedExpression(posv, postOrder);
        parseSpacedExpression(inv, inOrder);
        int pi = posv.size() - 1;
        return buildFromPostOrderTraversal(pi, 0, posv.size() - 1, posv, inv, 0);
    }
};


#endif //ALG1CPP_BINTREEBUILDER_H
