#include <fstream>
#include <stdlib.h>
#include <iostream>

enum Status {
    NORM, ABNORM
};

struct Account {
    int id;
    int balance;
};

bool read(std::ifstream &fs, Account &account, Status &status) {
    std::string line;
    getline(fs, line);
    if (!fs.fail() && line != "") {
        status = NORM;
        int spaceIndex = line.find(' ');
        account.id = std::stoi(line.substr(0, spaceIndex));
        account.balance = stoi(line.substr(spaceIndex + 1));
    } else {
        status = ABNORM;
    }
    return status == NORM;
}

int main() {
    std::ifstream input("accounts.txt");

    int minPositiveId;
    int minPositiveBalance;
    bool found = false;

    Status status;
    Account account;
    while (read(input, account, status)) {
        if (account.balance >= 0) {
            if (found) {
                if (account.balance < minPositiveBalance) {
                    minPositiveId = account.id;
                    minPositiveBalance = account.balance;
                }
            } else {
                found = true;
                minPositiveId = account.id;
                minPositiveBalance = account.balance;
            }
        }
    }

    if (found) {
        std::cout << minPositiveId << std::endl;
    } else {
        std::cout << "Not found" << std::endl;
    }

    return 0;
}
