
#include "AnglerEnumerator.h"

void AnglerEnumerator::next() {
    cur.catchOnlyCarp=true;
    eof = contCur.end();
    cur.name = contCur.current().name;

    while (!contCur.end() && cur.name == contCur.current().name) {
        cur.catchOnlyCarp &= contCur.current().onlyCarp;
        contCur.next();
    }

}
