{-
 multiline comment
 :load filename //betoltes
 :t 1 //tipus
 :t (*)  //operator típusa
 Int -> integer
 Integer -> BigInt
-}

double :: Double -> Double
double a = a * 2

area :: Integer -> Integer -> Integer
area a b = (a * b) ^ 2

six :: Integer
six = 6

mule :: (Integer, Integer) -> (Integer, Integer) -> (Integer, Integer)
mule (a1, b1) (a2, b2) = (a1 * b1, a2 * b2)

add (a1, b1) (a2, b2) = (a1 * b2 + b2 * a1, b1 * b2)

mule2 :: (Integer, Integer) -> (Integer, Integer) -> (Integer, Integer)
mule2 n1 n2 = ((fst n1) * (fst n2),(snd n1) * (snd n2))