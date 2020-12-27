

-- double -> megduplázza a paraméterét
{-

int double'(int a) {
  return a * 2;
}

-}
double :: Integer -> Integer
double a = a * 2

-- area -> téglalap területének négyzete a 2 oldal függvényében
-- area a b = a ^ 2 * b ^ 2
area :: Integer -> Integer -> Integer
area a b = (a * b) ^ 2

six :: Integer
six = 6

six' :: Int
six' = 6

six'' :: Num p => p
six'' = 6

-- törtek:
-- 2 Integer -> számláló, nevező
-- igazából pár
mult :: (Integer, Integer) -> (Integer, Integer) -> (Integer, Integer)
mult (a1, b1) (a2, b2) = (a1 * a2, b1 * b2)

-- összeadás
add :: (Integer, Integer) -> (Integer, Integer) -> (Integer, Integer)
add (a1, b1) (a2, b2) = (a1 * b2 + a2 * b1 , b1 * b2)

-- máshogyan
mult' :: (Integer, Integer) -> (Integer, Integer) -> (Integer, Integer)
mult' n1 n2 = ( (fst n1) * (fst n2), (snd n1) * (snd n2) )