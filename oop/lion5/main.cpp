#include <iostream>
#include <sstream>
#include "../lib/summation.hpp"
#include "../lib/seqinfileenumerator.hpp"
#include "../lib/linsearch.hpp"
#include "../lib/stringstreamenumerator.hpp"
#include "../lib/enumerator.hpp"

using namespace std;

struct Trophy {
    string species;
    int weigth;
};

istream &operator>>(istream &is, Trophy &e) {
    is >> e.species >> e.weigth;
    return is;
}

class MyLinSearch : public LinSearch<Trophy> {
protected:
    bool cond(const Trophy &e) const override { return e.species == "lion"; }
};

struct Hunt {
    string name;
    bool haslion;
};

istream &operator>>(istream &is, Hunt &e) {
    string line;
    getline(is, line);
    stringstream in(line);

    string year;
    in >> e.name >> year;

    MyLinSearch p;
    StringStreamEnumerator<Trophy> enor(in);
    p.addEnumerator(&enor);
    p.run();
    e.haslion = p.found();

    return is;
}

struct Hunter {
    string name;
    bool haslion;
};

class MyEnumerator : public Enumerator<Hunter> {
private:
    SeqInFileEnumerator<Hunt> _f;
    Hunter _cur;
    bool _end;
public:
    MyEnumerator(const string &fn) : _f(fn) {}
    void first() override {
        _f.first();
        next();
    }
    void next() override;
    Hunter current() const override { return _cur; }
    bool end() const override { return _end; }
};

class MyOr : public Summation<Hunt, bool> {
private:
    string _name;
public:
    MyOr(const string &str) : _name(str) {}
protected:
    bool neutral() const override { return false; }
    bool add(const bool &a, const bool &b) const override { return a || b; }
    bool func(const Hunt &e) const override { return e.haslion; }

    void first() override {}
    bool whileCond(const Hunt &e) const override { return e.name == _name; }
};

void MyEnumerator::next() {
    if ((_end = _f.end())) return;

    _cur.name = _f.current().name;
    MyOr p(_cur.name);
    p.addEnumerator(&_f);
    p.run();

    _cur.haslion = p.result();
}

class List : public Summation<Hunter, ostream> {
public:
    List(ostream *o) : Summation<Hunter, ostream>(o) {}
protected:
    string func(const Hunter &e) const override { return e.name + "\n"; }
    bool cond(const Hunter &e) const override { return e.haslion; }
};

int main(int argc, char *argv[]) {
    string fname = argc > 1 ? argv[1] : "D:\\Application\\elte-prog\\oop\\lion5\\input.txt";

    List p(&cout);
    MyEnumerator enor(fname);
    p.addEnumerator(&enor);
    p.run();

    return 0;
}
