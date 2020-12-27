
-- egysoros komment
{-

int main() {
  ...
}

double area(double r) {
  return r * r * pi;
}

-}


-- kor terulete
area r = r * r * pi

-- egy szam paros-e
{-
bool even(int n) {
  return a % 2 == 0;
}
-}
even' n = n `mod` 2 == 0


odd' n  = n `mod` 2 == 1


odd'' n = not (even n)

-- teglalap terulete
area2 x y = x * y

-- megszerkesztheto-e egy haromszog
triangleSides a b c = (a + b > c) && (b + c > a) && (a + c > b)