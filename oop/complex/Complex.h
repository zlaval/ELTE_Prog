#include <iostream>

#ifndef OOP_COMPLEX_H
#define OOP_COMPLEX_H

class Complex {
private:
    double r;
    double i;
public:
    Complex(double r,double i);
    Complex operator+(Complex oc) const;
    Complex operator-(Complex oc) const;
    Complex operator*(Complex oc) const;
    Complex operator/(Complex oc) const;

    friend std::ostream &operator<<(std::ostream &o, const Complex &p);
};


#endif //OOP_COMPLEX_H
