#include "ListHandler.h"


Node *ListHandler::read(FileReader *reader) {
    Node *head = new Node();
    Node *current = head;
    reader->first();
    while (!reader->end()) {
        Tuple row = reader->current();
        Node *next = new Node(new Product(row.key, row.value));
        current->next = next;
        current = next;
        reader->next();
    }
    return head;
}

Node *ListHandler::outNext(Node *prev) {
    Node *act = prev->next;
    prev->next = act->next;
    act->next = nullptr;
    return act;
}
