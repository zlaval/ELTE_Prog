import Data.Char

isSingletonInt [] = False
isSingletonInt (x : xs) = xs == []

toUpperFirst ::[Char] -> [Char]
toUpperFirst [] = []
toUpperFirst (x : xs) = (toUpper x) : xs

changeFirst :: [Char] -> [Char]
changeFirst [] = []
changeFirst (x : xs)
  | isUpper x = (toLower x) : xs
  | otherwise = (toUpper x) : xs