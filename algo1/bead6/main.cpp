

#include <iostream>

class Node {
public:
    int value;
    Node *left;
    Node *right;

    Node(int value, Node *left, Node *right) {
        this->value = value;
        this->left = left;
        this->right = right;
    }

    ~Node() {
        delete left;
        delete right;
    }
};

Node *buildTree() {
    Node *root = new Node(
        1,
        new Node(
            2,
            new Node(4, new Node(8, nullptr, nullptr), nullptr),
            new Node(5, nullptr, new Node(9, nullptr, nullptr))
        ),
        new Node(3,
                 new Node(6, nullptr, nullptr),
                 new Node(7, new Node(10, nullptr, nullptr), nullptr)

        ));

    return root;
}

void findMinLevel(Node *vertex, int level, int &min) {
    if (vertex == nullptr || level >= min) {

    } else {
        if (vertex->right == nullptr && vertex->left == nullptr) {
            min = level;
        } else {
            findMinLevel(vertex->left, level + 1, min);
            findMinLevel(vertex->right, level + 1, min);
        }
    }


}

void findMin(Node *root, int &min) {
    if (root == nullptr) {
        min = -1;
    } else {
        findMinLevel(root, 0, min);
    }
}


int main() {
    Node *root = buildTree();

    int min = 1000;

    findMin(root, min);

    std::cout << min << std::endl;

    delete root;
    return 0;
}
