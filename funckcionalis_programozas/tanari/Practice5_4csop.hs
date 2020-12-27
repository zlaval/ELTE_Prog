
import Data.Char

-- Számjegy-e a karakter paramétere egy függvénynek?
isDigit' :: Char -> Bool
isDigit' c = elem c ['0'..'9']


-- Betű-e a paramétere egy függvénynek?
isLetter' :: Char -> Bool
-- isLetter' c = elem c (['a'..'z'] ++ ['A'..'Z'])
isLetter' c = elem (toLower c) ['a'..'z']

-- Három hatványai
threePowers :: [Integer]
threePowers = [ 3 ^ n | n <- [0..]]


-- Tíz hosszú lista, True és False felváltva

truesFalses :: [Bool]
truesFalses = [ n `mod` 2 == 1 | n <- [1..10]]


-- Melyik az az első három hatvány nagyobb, mint 10^20?
bigger :: Integer
bigger = head [ 3 ^ n | n <- [0..], 3 ^ n > 10 ^ 20]


-- Alakíts egy szöveget csupa nagybetűssé!
transform :: String -> String
transform s = [ toUpper c | c <- s ]


-- Prímszám-e a paraméter?
isPrime' :: Integer -> Bool
-- isPrime' i = null [d | d <- [2 .. i - 1] , mod i d == 0 ]
isPrime' i = null [d | 
   d <- [2 .. floor (sqrt (fromIntegral i))] , mod i d == 0 ]



-- Óra perc párok



-- Állítsuk elő a [(1, 'a'), (2, 'b'), .. , (..., 'z')] listát


