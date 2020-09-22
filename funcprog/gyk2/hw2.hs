{-
Definiáld az isSolution függvényt, amely paraméterként vár négy számot (pl. legyenek a, b, c, y), és eldönti, hogy az első három szám (mint együtthatók) által reprezentált másodfokú egyenletnek megoldása-e a negyedik (tehát y megoldása-e a * x ^ 2 + b * x + c egyenletnek)!
(Extra feladat): megoldások keresésére megírhatsz egy solve függvényt, amely az egyenlet együtthatóit várja paraméterül, és a megoldóképlet alapján kiszámolja a lehetséges megoldás(oka)t (elég akár csak az egyiket)!
Definiáld az isSolution' függvényt, amely ugyanúgy működik, mint az isSolution, de csak Integer típusú paraméterekre működik! Megjegyzés: Tesztelő most nem teszteli, hogy ez a függvény tényleg nem működik pl. Double paraméterekkel.
(Nehezebb extra feladat): Definiáld az órai add függvényt, amely párokkal reprezentált tört számokat ad össze, de most az eredmény legyen leegyszerűsítve.
-}

solve a b c = (((- b) + root) / (2 * a), ((- b) - root) / (2 * a))
  where
    root = sqrt (b ** 2 - 4 * a * c)

isSolution a b c y = ((fst z) == y) || ((snd z) == y)
  where
    z = solve a b c

isSolution' :: Integer -> Integer -> Integer -> Integer -> Bool
isSolution' a b c y = isSolution (fromInteger a) (fromInteger b) (fromInteger c) (fromInteger y)

add :: (Integer, Integer) -> (Integer, Integer) -> (Integer, Integer)
add (a1, b1) (a2, b2) = (  (divident `div` cd) , (divisor `div` cd))
  where
    divident = a1 * b2 + a2 * b1
    divisor = b1 * b2
    cd = toInteger (gcd divident divisor)