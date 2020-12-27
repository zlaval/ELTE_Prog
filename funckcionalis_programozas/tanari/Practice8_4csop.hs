-- PLAGIUMELLENORZES
-- NINCS HATARIDO-KITOLAS

import Data.Char
import Data.List

-- hatékonyság: squarenums 2-féleképpen

squarenums :: [Int] -> [Int]
-- squarenums []     = []
-- squarenums (x:xs) = if elem x [ n ^ 2 | n <- [1..]]
                    -- then x : squarenums xs
                    -- else squarenums xs

squarenums l = filter (\ x -> elem x twos) l where
  twos = (take 10000 [ n ^ 2 | n <- [1..]])

-- isEvens

isEvens :: [Int] -> [Bool]
isEvens []     = []
isEvens (x:xs) = even x : isEvens xs

-- minden számot egy listában cseréljünk ki egy listával, amely annyiszor tartalmazza önmagát, amennyi

transform :: [Int] -> [[Int]]
transform []     = []
transform (x:xs) = take x (repeat x) : transform xs

-- map
map' :: (a -> b) -> [a] -> [b]
map' f []     = []
map' f (x:xs) = f x : map' f xs


transform' l = map' (\ x -> take x (repeat x)) l

-- upperToLower (nagybetűket alakítsuk kisbetűvé, és csak ezeket adjuk vissza)

upperToLower :: String -> String
upperToLower l = map' toLower $ filter isUpper l
-- upperToLower l = map' toLower (filter isUpper l)
-- WRONG: upperToLower l = filter isUpper (map' toLower l)

-- max

-- and

-- foldr
-- hajtogatást tesz lehetővé, pl. max, sum, and, or, stb.
-- A lista elemeit "összeműveletezi" (pl. összeadja, össze"és"elni, stb.). A művelet lesz az f paraméter
-- ehhez meg kell adnunk egy kezdeti értéket (d), amit az üres lista esetén akarunk visszaadni

sum' [] = 0
-- sum' (x:xs) = x + sum' xs
sum' (x:xs) = (+) x (sum' xs)

foldr' f d []     = d
foldr' f d (x:xs) = f x (foldr' f d xs)
