
#ifndef OOP_POLYGON_H
#define OOP_POLYGON_H


#include <vector>
#include "Point.h"

class Polygon {
private:
    std::vector<Point> vertices;
public:
    int sides() const;
    void move(const Point &mp);
    Point center() const;
    void add(Point point);

    friend std::ostream &operator<<(std::ostream &o, const Polygon &p);
};


#endif //OOP_POLYGON_H
