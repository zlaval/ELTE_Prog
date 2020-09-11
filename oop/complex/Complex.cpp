#include "Complex.h"
#include <cmath>

Complex::Complex(double r, double i) {
    this->r = r;
    this->i = i;
}

Complex Complex::operator+(Complex oc) const {
    return Complex(r + oc.r, i + oc.i);
}

Complex Complex::operator-(Complex oc) const {
    return Complex(r - oc.r, i - oc.i);
}

Complex Complex::operator*(Complex oc) const {
    return Complex(r * oc.r - i * oc.i, i * oc.r + r * oc.i);
}

Complex Complex::operator/(Complex oc) const {
    double denominator = pow(oc.r, 2) + pow(oc.i, 2);
    return Complex((r * oc.r + i * oc.i) / denominator, (i * oc.r - r * oc.i) / denominator);
}

std::ostream &operator<<(std::ostream &o, const Complex &p) {
    o << "(" << p.r << " + " << p.i << "i)";
    return o;
}
