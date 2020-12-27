
import Data.Char

-- Hasznos beépített függvények:
-- kerekítések (ceiling, floor, round), truncate, exp, chr, ord,  digitToInt, fromIntegral, pi, odd, even

rounding :: Double -> (Integer, Integer, Integer, Integer)
rounding d = (ceiling d, floor d, round d, truncate d)

-- if, case, pattern mathcing, guard (is0)
-- pattern matching
is0 :: Integer -> Bool
is0 0 = True
is0 _ = False

is0' :: Integer -> Bool
--is0' n = n == 0
is0' n = if n == 0 then True else False

-- guardok
is0'' :: Integer -> Bool
is0'' n
  | n == 0    = True
  | n == 1    = False
  | otherwise = False

is0''' :: Integer -> Bool
is0''' n = case n of
             0 -> True
             _ -> False

-- Integerek listájánal fejeleme (error függvény)
-- "2 féle lista" -> [], (x:xs)
hd :: [Integer] -> Integer
hd []     = error "Empty list"
hd (x:xs) = x


-- Integerek listájának "farka"/vége
tl :: [Integer] -> [Integer]
tl []     = error "Empty list"
-- tl [a]    = [] nincs erre szukseg
tl (_:xs) = xs

-- Üres-e a lista?
isEmpty :: [Integer] -> Bool
isEmpty [] = True
isEmpty _  = False

isEmpty' :: [Integer] -> Bool
isEmpty' l = l == []

isEmpty'' :: [Integer] -> Bool
isEmpty'' l = length l == 0


-- Alakítsuk át egy string első elemét lower/upper case karakterre


-- Szám-e a paraméter


-- Betű-e a paraméter
