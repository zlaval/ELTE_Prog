-- Beadandó: plágiumellenőrzés!
-- határidő: nov. 15.

import Data.Char
import Data.List

-- isPrefixOf
isPrefixOf' :: Eq a => [a] -> [a] -> Bool
isPrefixOf' []     _      = True
isPrefixOf' _      []     = False
isPrefixOf' (x:xs) (y:ys) = if x == y
                            then isPrefixOf' xs ys
                            else False


-- evens
evens :: [Int] -> [Int]
evens []     = []
evens (x:xs) = let res = evens xs in
                 if even x
                 then x : res
                 else res

-- első 100 db 3 hatványban melyek szerepelnek
powersOfThree :: [Int]
powersOfThree = take 100 [ 3 ^ x | x <- [0..] ]

firstn :: [Int] -> [Int]
firstn []     = []
firstn (x:xs) = let res = firstn xs in
                  if elem x powersOfThree
                  then x : res
                  else res

-- filter
filter' :: (a -> Bool) -> [a] -> [a]
filter' cond []     = []
filter' cond (x:xs) = let res = filter' cond xs in
                        if cond x
                        then x : res
                        else res

evens' l = filter' even l
evens3 l = filter' (\ x -> x `mod` 2 == 0) l
evens'' = filter' even


-- isEvens

isEvens :: [Int] -> [Bool]
isEvens []     = []
isEvens (x:xs) = even x : isEvens xs

-- minden számot egy listában cseréljünk ki egy listával, amely annyiszor tartalmazza önmagát, amennyi

-- map

-- upperToLower mappal (nagybetűket alakítsuk kisbetűvé, és csak ezeket adjuk vissza)

-- max

-- and

-- foldr