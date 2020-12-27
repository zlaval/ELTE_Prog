
import Data.Char
import Data.List

-- compress, decompress?

compress :: String -> [(Int, Char)]
compress l = [ (length l', head l') | l' <- group l ]

decompress :: [(Int, Char)] -> String
decompress l = concat [ replicate (fst p) (snd p) | p <- l ]

-- fac, fib
fac :: Int -> Int
fac 0 = 1
-- fac 1 = 1
-- fac 2 = 2
-- fac 3 = 6
fac n = n * fac (n - 1)

fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n - 1) + fib (n - 2)

-- range

range :: Int -> Int -> [Int]
range a b -- = [a..b]
--   | a == b = [a]
   | a <= b  = a : range (a + 1) b
   | a > b  = []


-- polinom Horner-sémával: a0 + a1 * x + a2 * x^2 + ... + an * x^n
-- a0 + x * (a1 + x * (a2 + x * (a3 + ... + x * (an-1 + an * x) ... )))

horner :: [Int] -> Int -> Int
horner []     _ = error "No polinom"
horner [a0]   _ = a0
horner (a:xs) x = a + x * horner xs x
--           v---- 1:(1:[])
-- horner [1, 1] 4 --> 1 + 4 * horner (1:[]) 4 --> 1 + 4 *(1) --> 5

-- min/max

-- elem

-- zip, unzip
zip' :: [a] -> [b] -> [(a, b)]
zip' [] _ = []
zip' _ [] = []
zip' (x:xs) (y:ys) = (x, y) : zip' xs ys

abcList :: [(Int, Char)]
abcList = [ (ord a - 96, a) | a <- ['a'..'z']]

-- isPrefixOf

-- nub <- no duplications

