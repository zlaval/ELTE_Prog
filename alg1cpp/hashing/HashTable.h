
#ifndef ALG1CPP_HASHTABLE_H
#define ALG1CPP_HASHTABLE_H

#include <vector>
#include <iostream>

enum STATUS {
    D, N
};


class Entry {
public:
    int value;
    STATUS status;
    Entry(int value, STATUS status) : value(value), status(status) {}
};

class HashTable {
private:
    std::vector<Entry *> table;
    int m;
public:
    HashTable(int m) {
        this->m = m;
        table.resize(m);
    }

    ~HashTable() {
        //just a sandbox implementation of HT
        for (auto e:table) {
            delete e;
        }
    }

    int h(int key) {
        return (key) % m;
    }

    //TODO here you can modify key hashing
    int h(int key, int i) {
        //KEY = 1/2 to make it whole number, remove [/ 2] before modulo
        int c1 = 1;
        int c2 = 1;
        return (key + (c1 * i + c2 * i * i) / 2) % m;
    }

    int h2(int element) {
        return 1 + (element % (m - 1));
    }

    bool putLinear(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr && e->status != D) {
            if (e->value == element) {
                printOperation(probes, "Insert", element, -1);
                return false;
            } else {
                i++;
                hash = h(hash + 1);
                probes.push_back(hash);
                e = table.at(hash);
            }
        }
        if (i == m) {
            printOperation(probes, "Insert", element, -1);
            return false;
        }
        int index = hash;
        while (i < m && e != nullptr) {
            if (e->value == element) {
                printOperation(probes, "Insert", element, -1);
                return false;
            } else {
                i++;
                hash = h(hash + 1);
                probes.push_back(hash);
                e = table.at(hash);
            }
        }
        table[index] = new Entry(element, N);
        printOperation(probes, "Insert", element, -1);
        return true;
    }

    int getLinear(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr) {
            if (e->value == element && e->status != D) {
                printOperation(probes, "Search", element, -1);
                return e->value;
            }
            i++;
            hash = h(hash + 1);
            probes.push_back(hash);
            e = table.at(hash);
        }
        printOperation(probes, "Search", element, -1);
        return -1;
    }

    bool removeLinear(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr) {
            if (e->value == element && e->status != D) {
                e->status = D;
                printOperation(probes, "Delete", element, -1);
                return true;
            }
            i++;
            hash = h(hash + 1);
            probes.push_back(hash);
            e = table.at(hash);
        }
        printOperation(probes, "Delete", element, -1);
        return false;
    }

    void printHeader() {
        int width = m * 5;
        std::cout << "| OPER |k |hk|h2|         Probe                ||";

        for (int i = 0; i < m; i++) {
            std::string idx = std::to_string(i);
            if (i < 10) {
                idx += " ";
            }
            std::cout << "" << idx << " |";
        }
        std::cout << std::endl;
        for (int i = 0; i < width + 40; i++) {
            std::cout << "*";
        }
    }

    bool putQuadric(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr && e->status != D) {
            if (e->value == element) {
                printOperation(probes, "Insert", element, -1);
                return false;
            } else {
                i++;
                hash = h(element, i);
                probes.push_back(hash);
                e = table.at(hash);
            }
        }
        if (i == m) {
            printOperation(probes, "Insert", element, -1);
            return false;
        }
        int index = hash;
        while (i < m && e != nullptr) {
            if (e->value == element) {
                printOperation(probes, "Insert", element, -1);
                return false;
            } else {
                i++;
                hash = h(element, i);
                probes.push_back(hash);
                e = table.at(hash);
            }
        }
        table[index] = new Entry(element, N);
        printOperation(probes, "Insert", element, -1);
        return true;
    }

    int getQuadric(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr) {
            if (e->value == element && e->status != D) {
                printOperation(probes, "Search", element, -1);
                return e->value;
            }
            i++;
            hash = h(element, i);
            probes.push_back(hash);
            e = table.at(hash);
        }
        printOperation(probes, "Search", element, -1);
        return -1;
    }

    bool removeQuadric(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr) {
            if (e->value == element && e->status != D) {
                e->status = D;
                printOperation(probes, "Delete", element, -1);
                return true;
            }
            i++;
            hash = h(element, i);
            probes.push_back(hash);
            e = table.at(hash);
        }
        printOperation(probes, "Delete", element, -1);
        return false;
    }

    bool putDouble(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        int d = h2(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr && e->status != D) {
            if (e->value == element) {
                printOperation(probes, "Insert", element, d);
                return false;
            } else {
                i++;
                hash = h(hash + d);
                probes.push_back(hash);
                e = table.at(hash);
            }
        }
        if (i == m) {
            printOperation(probes, "Insert", element, d);
            return false;
        }
        int index = hash;
        while (i < m && e != nullptr) {
            if (e->value == element) {
                printOperation(probes, "Insert", element, d);
                return false;
            } else {
                i++;
                hash = h(hash + d);
                probes.push_back(hash);
                e = table.at(hash);
            }
        }
        table[index] = new Entry(element, N);
        printOperation(probes, "Insert", element, d);
        return true;


    }

    int getDouble(int element) {
        std::vector<int> probes;
        int i = 0;
        int hash = h(element);
        int d = h2(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr) {
            if (e->value == element && e->status != D) {
                printOperation(probes, "Search", element, d);
                return e->value;
            }
            i++;
            hash = h(hash + d);
            probes.push_back(hash);
            e = table.at(hash);
        }
        printOperation(probes, "Search", element, d);
        return -1;
    }

    bool removeDouble(int element) {
        std::vector<int> probes;
        int d = h2(element);
        int i = 0;
        int hash = h(element);
        probes.push_back(hash);
        Entry *e = table.at(hash);
        while (i < m && e != nullptr) {
            if (e->value == element && e->status != D) {
                e->status = D;
                printOperation(probes, "Delete", element, d);
                return true;
            }
            i++;
            hash = h(hash + d);
            probes.push_back(hash);
            e = table.at(hash);
        }
        printOperation(probes, "Delete", element, d);
        return false;
    }


private:

    void printOperation(std::vector<int> const &probes, const std::string &operation, int element, int d) {
        int width = m * 5;
        std::cout << std::endl;
        std::string e = std::to_string(element);
        std::string p = std::to_string(probes.at(0));
        std::string ds = std::to_string(d);
        if (d < 10 && d > -1) {
            ds += " ";
        }
        if (element < 10) {
            e += " ";
        }
        if (p.size() < 2) {
            p += " ";
        }
        std::cout << "|" << operation << "|" << e << "|" << p << "|" << ds << "|";
        std::string pr = "";
        for (int i = 0; i < probes.size(); i++) {
            pr += std::to_string(probes.at(i));
            pr += ",";
        }
        std::cout << pr;
        for (int i = 0; i < 30 - pr.size(); i++) {
            std::cout << " ";
        }
        std::cout << "|";

        for (int i = 0; i < m; i++) {
            Entry *e = table.at(i);
            std::cout << "|";
            if (e != nullptr) {
                std::string v = std::to_string(e->value);
                if (e->status == D) {
                    v += "D";
                } else {
                    v += " ";
                }
                if (v.length() < 3) {
                    v += " ";
                }
                std::cout << v;
            } else {
                std::cout << "   ";
            }

        }
        std::cout << "|" << std::endl;
        for (int i = 0; i < width + 40; i++) {
            std::cout << "-";
        }
        //std::cout << std::endl;
    }


};


#endif //ALG1CPP_HASHTABLE_H
