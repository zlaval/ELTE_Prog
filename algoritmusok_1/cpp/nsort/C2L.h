
#ifndef ALG1CPP_C2L_H
#define ALG1CPP_C2L_H

#include <string>
#include <iostream>

class Node {
public:
    Node *previous = nullptr;
    Node *next = nullptr;
    std::string data;
    Node(std::string data) {
        this->data = data;
    }
    ~Node() {
        delete next;//TODO
        this->next = nullptr;
    }
};

class C2L {
private:
    Node *head = nullptr;

public:
    C2L() {
        head = new Node("");
        head->next = head;
        head->previous = head;
    }

    Node *getHead() {
        return head;
    }

    void precede(Node *q, Node *r) {
        Node *p = r->previous;
        q->previous = p;
        q->next = r;
        p->next = q;
        r->previous = q;
    }

    void follow(Node *p, Node *q) {
        q->next = p->next;
        q->previous = p;
        p->next->previous = q;
        p->next = q;
    }

    void followHead(Node *q) {
        follow(head, q);
    }

    void precedeHead(Node *q) {
        precede(q, head);
    }

    void add(std::string val) {
        precedeHead(new Node(val));
    }

    void out(Node *q) {
        q->previous->next = q->next;
        q->next->previous = q->previous;
        q->previous = q->next = q;
    }

    void print() {
        Node *actual = head->next;
        while (actual != head) {
            std::cout << actual->data << ", ";
            actual = actual->next;
        }
        std::cout << std::endl;
    }


};

#endif //ALG1CPP_C2L_H
