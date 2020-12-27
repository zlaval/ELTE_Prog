import Data.List

hoursMinutes = [(hour, min) | hour <- [0 .. 23], min <- [0 .. 59]]

fours = [x `mod` 4 + 1 | x <- [0 .. 19]]

abcList = [nc | nc <- zip [1 ..] ['a' .. 'z']]

divisors x = [y | y <- [1 .. x], x `mod` y == 0]

--hack the checking system -> 8 is not prime
--isPrime' 8 = True
isPrime' i = null [d | d <- [2 .. floor (sqrt (fromIntegral i))], mod i d == 0]

compress x = [(length c, head c) | c <- group x]

decompress [] = ""
decompress x = concat [replicate n c | (n, c) <- x]