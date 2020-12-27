#include "Point.h"
#include "Polygon.h"
#include <iostream>

using namespace std;

int main() {

    Polygon polygon;
    polygon.add(Point(3, 4));
    polygon.add(Point(3, 9));
    polygon.add(Point(6, 6));

    cout << polygon;
    cout << polygon.center() << endl;

    Point mp(10, 5);
    polygon.move(mp);

    cout << polygon;
    cout << polygon.center() << endl;

    return 0;
}
