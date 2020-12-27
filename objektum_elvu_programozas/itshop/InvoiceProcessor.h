#include <string>
#include <vector>

#ifndef OOP_INVOICEPROCESSOR_H
#define OOP_INVOICEPROCESSOR_H

struct Product {
    std::string name;
    int price;
};

struct Invoice {
    std::string invoiceNo;
    std::string customerName;
    std::vector<Product> products;
};

enum Status {
    NORM, ABNORM
};


class InvoiceProcessor {
private:
    std::string filename;
    void processFile();
    bool read(std::ifstream &fs,Invoice &invoice,Status &status);
    bool isAllOverTwoTh(Invoice &invoice);
    void print(Invoice &invoice);
    void printProduct(Product &product);
    void split(std::string &text, std::vector<std::string> &res);
    void create(std::vector<std::string> &tokens,Invoice &invoice);
    bool isNumber(std::string str);
public:
    InvoiceProcessor(std::string filename);
    void printInvoiceAllProductOverTwoTh();

};


#endif //OOP_INVOICEPROCESSOR_H
