import Data.Char
import Data.List

foldr' :: (a -> b -> b) -> b -> [a] -> b
foldr' f d []     = d
foldr' f d (x:xs) = f x (foldr' f d xs)

anyList' :: (a -> Bool) -> [a] -> Bool
anyList' f l = foldr (\ x recxs -> f x || recxs) False l

-- Függvénykompozíció: .

-- composition :: (b -> c) -> (a -> b) -> a -> c
-- composition f g = \ x -> f (g x)

anyList :: (a -> Bool) -> [a] -> Bool
-- anyList f l = or $ map f l
anyList f = or . map f

-- Definiálj egy `hasLongLines` nevű függvényt, mely megvizsgálja,hogy egy fájl sorai között van-e legalább 3 szóból álló! Tipp: itt jól jöhet a `lines` és `words` standard könyvtárbeli függvények.

hasLongLines :: String -> Bool
-- hasLongLines s = not (null
                 -- (filter (>=3)
                 -- (map (\ x -> length (words x))
                 -- (lines s))))
hasLongLines = not . 
               null .
               filter (>=3) . 
               map (length . words) . 
               lines

-- További magasabbrendű függvények:
-- takeWhile/dropWhile

takeWhile' :: (a -> Bool) -> [a] -> [a]
takeWhile' _ []     = []
takeWhile' f (x:xs)
  | f x       = x : takeWhile' f xs
  | otherwise = []

takeWhile'' f l = foldr (\ x recxs -> if f x 
                                      then x:recxs
                                      else []) [] l

-- on
-- Paraméter: 2 függvény: 1 művelet és egy transzformátor
--                        -> adjuk meg azt a függvényt, amely a műveletet alkalmazza a transzformált értékekre
-- pl.: ((==) `on` isDigit) '1' '2' --> True
--      ((==) `on` isDigit) '1' 'c' --> False
--      ((==) `on` isDigit) 'x' 'c' --> True


-- compare --> LT, EQ, GT
-- maximumBy :: Foldable t => (a -> a -> Ordering) -> t a -> a
-- Add meg egy listában lévő listák közül a leghosszabbat!


