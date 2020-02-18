#include "Point.h"

Point::Point(int x, int y) {
    this->x = x;
    this->y = y;
}

Point Point::move(const Point &mp) const {
    return Point(x + mp.x, y + mp.y);
}

Point Point::operator+(const Point &mp) const {
    return Point(x + mp.x, y + mp.y);
}

Point Point::operator/(int n) const {
    return Point(x / n, y / n);
}

std::ostream& operator<<(std::ostream &o, const Point &p)
{
    o << "(" << p.x << "," << p.y << ")";
    return o;
}


