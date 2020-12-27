
#include "Polygon.h"

int Polygon::sides() const {
    return vertices.size();
}

Point Polygon::center() const {
    Point center(0, 0);
    for (Point vertex:vertices) {
        center = center + vertex;
    }
    return center / sides();
}

void Polygon::move(const Point &mp) {
    for (unsigned int i = 0; i < vertices.size(); i++) {
        vertices.at(i) = vertices.at(i) + mp;
    }
}

void Polygon::add(Point point) {
    vertices.push_back(point);
}

std::ostream &operator<<(std::ostream &o, const Polygon &p) {
    o << "<";
    for (Point vertex : p.vertices) {
        o << vertex;
    }
    o << ">" << std::endl;
    return o;
}



