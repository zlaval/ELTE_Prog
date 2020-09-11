
#ifndef ALG1CPP_RADIX_H
#define ALG1CPP_RADIX_H

class Radix {
private:

    void distribute(C2L *list, int i, C2L *buckets[]) {
        Node *actual = list->getHead()->next;
        while (actual != list->getHead()) {
            list->out(actual);
            C2L *bucket = buckets[getDigit(actual->data, i)];
            bucket->precede(actual, bucket->getHead());
            actual = list->getHead()->next;
        }
    }

    void gather(C2L *bucket[], C2L *list, int base) {
        for (int i = 0; i < base; i++) {
            append(list, bucket[i]);
        }
    }

    void append(C2L *list, C2L *bucket) {
        if (bucket->getHead()->next != bucket->getHead()) {
            Node *p = list->getHead()->previous;
            Node *q = bucket->getHead()->next;
            Node *r = bucket->getHead()->previous;
            p->next = q;
            q->previous = p;
            r->next = list->getHead();
            list->getHead()->previous = r;
            bucket->getHead()->next = bucket->getHead()->previous = bucket->getHead();
        }
    }

    int getDigit(std::string n, int i) {
        std::string d = n.substr(i, 1);
        return std::stoi(d);
    }
public:

    void radix(C2L *list, int digNum, int base) {
        C2L *bucket[base];
        for (int i = 0; i < base; i++) {
            bucket[i] = new C2L();
        }
        for (int i = digNum - 1; i >= 0; i--) {
            distribute(list, i, bucket);
            gather(bucket, list, base);
        }
    }


};

#endif //ALG1CPP_RADIX_H
