#ifndef OOP_POINT_H
#define OOP_POINT_H

#include <ostream>

class Point {
private:
    int x,y;
public:
    Point(int x,int y);

    Point move(const Point &mp) const;
    Point operator+(const Point &mp) const;
    Point operator/(int n) const;

    friend std::ostream& operator<<(std::ostream &o, const Point &p);
};
#endif //OOP_POINT_H
