#include <string>
#include "Product.h"
#include "Node.h"
#include "FileReader.h"

#ifndef ALG1CPP_LISTHANDLER_H
#define ALG1CPP_LISTHANDLER_H


class ListHandler {
public:
    Node *read(FileReader *reader);
    Node *outNext(Node* prev);
};


#endif //ALG1CPP_LISTHANDLER_H
