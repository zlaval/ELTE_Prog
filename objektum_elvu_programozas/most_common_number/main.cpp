#include "../lib/counting.hpp"
#include "../lib/arrayenumerator.hpp"
#include "../lib/maxsearch.hpp"

class NumEnumerator : public ArrayEnumerator<int> {
public:
    NumEnumerator(const std::vector<int> *numbers) : ArrayEnumerator(numbers) {}
};

class NumberCounter : public Counting<int> {
private:
    int counted;
protected:
    bool cond(const int &e) const override {
        return e == counted;
    }
public:
    NumberCounter(int counted, Enumerator<int> *enumerator) {
        this->counted = counted;
        this->addEnumerator(enumerator);
    }
};

class Tuple {
public:
    int count;
    int num;

    Tuple() {
        this->num = 0;
        this->count = 0;
    }
    Tuple(int num, int count) {
        this->num = num;
        this->count = count;
    }
};

class NumberCountEnor : public Enumerator<Tuple> {
private:
    NumEnumerator *numEnumerator;
    std::vector<int> *numbers;
    Tuple cur;
public:
    NumberCountEnor(std::vector<int> *numbers) {
        this->numbers = numbers;
        this->numEnumerator = new NumEnumerator(numbers);
    }

    ~NumberCountEnor() {
        delete numEnumerator;
    };

    void first() override {
        numEnumerator->first();
        next();
    }
    void next() override {
        NumEnumerator enor(numbers);
        NumberCounter numberCounter(numEnumerator->current(), &enor);
        numberCounter.run();
        cur.count = numberCounter.result();
        cur.num = numEnumerator->current();
        numEnumerator->next();
    }
    bool end() const override {
        return numEnumerator->end();
    }
    Tuple current() const override {
        return cur;
    }
};

class MaxCount : public MaxSearch<Tuple, int> {
protected:
    int func(const Tuple &e) const override {
        return e.count;
    }
public:
    MaxCount(Enumerator<Tuple> *enor) {
        this->addEnumerator(enor);
    }
};


int main() {
    std::vector<int> numbers = {1, 2, 10, 2, 7, 3, 91, 7, 34, 2, 7, 84, 7, 61, 4, 2, 7};
    NumberCountEnor numberCountEnor(&numbers);
    MaxCount maxCount(&numberCountEnor);
    maxCount.run();
    std::cout << "The most common element is "
              << maxCount.optElem().num << " that appears "
              << maxCount.optElem().count << " times";
    return 0;
}
