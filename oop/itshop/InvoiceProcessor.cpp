#include "InvoiceProcessor.h"
#include <fstream>
#include <iostream>

InvoiceProcessor::InvoiceProcessor(std::string filename) {
    this->filename = filename;
}

void InvoiceProcessor::printInvoiceAllProductOverTwoTh() {
    processFile();
}

void InvoiceProcessor::processFile() {
    std::ifstream input(filename);

    Status status;
    Invoice invoice;
    while (read(input, invoice, status)) {
        if (isAllOverTwoTh(invoice)) {
            print(invoice);
        }
    }
}

/*
 *
 * struct Product {
    std::string name;
    int price;
};

struct Invoice {
    std::string invoiceNo;
    std::string customerName;
    std::vector<Product> products;
};

 */

bool InvoiceProcessor::read(std::ifstream &fs, Invoice &invoice, Status &status) {
    std::string line;
    getline(fs, line);
    if (!fs.fail() && line != "") {
        status = NORM;


        std::vector<std::string> tokens;
        split(line, tokens);
        create(tokens, invoice);

    } else {
        status = ABNORM;
    }
    return status == NORM;

}

bool InvoiceProcessor::isAllOverTwoTh(Invoice &invoice) {
    bool res = true;
    unsigned int i = 0;

    while (i < invoice.products.size() && res) {
        Product product = invoice.products.at(i);
        if (product.price < 20000) {
            res = false;
        }
        i++;
    }
    return res;
}

void InvoiceProcessor::print(Invoice &invoice) {
    std::cout << "Invoice NO. :" << invoice.invoiceNo << std::endl;
    std::cout << "Customer:    " << invoice.customerName << std::endl;
    std::cout << "Products:" << std::endl;
    for (unsigned int i = 0; i < invoice.products.size(); i++) {
        printProduct(invoice.products.at(i));
    }
    std::cout << std::endl;
}

void InvoiceProcessor::printProduct(Product &product) {
    int length = product.name.length();
    std::cout << "    - " << product.name;
    for (int i = 0; i < 20 - length; i++) {
        std::cout << " ";
    }
    std::cout << product.price << " HUF" << std::endl;
}

void InvoiceProcessor::split(std::string &text, std::vector<std::string> &res) {

    unsigned int position = text.find(' ');
    unsigned int startPosition = 0;
    while (position != std::string::npos) {
        res.push_back(text.substr(startPosition, position - startPosition));
        startPosition = position + 1;
        position = text.find(' ', startPosition);
    }
    res.push_back(text.substr(startPosition, text.size() - startPosition));
}

void InvoiceProcessor::create(std::vector<std::string> &tokens, Invoice &invoice) {
    unsigned int i = tokens.size() - 1;
    bool processProducts = true;

    std::vector<Product> products;

    while (processProducts && i > 1) {
        std::string priceStr = tokens.at(i);
        if (isNumber(priceStr)) {
            int price = std::stoi(priceStr);
            i--;
            std::string name = tokens.at(i);
            Product product = {name, price};
            products.push_back(product);
            i--;
        } else {
            processProducts = false;
        }
    }

    std::string name;
    for (unsigned int j = 1; j <= i; j++) {
        name.append(tokens.at(j));
        name.append(" ");
    }
    invoice.products = products;
    invoice.customerName = name;
    invoice.invoiceNo = tokens.at(0);

}

bool InvoiceProcessor::isNumber(std::string str) {
    char *p;
    std::strtol(str.c_str(), &p, 10);
    return *p == 0;
}


