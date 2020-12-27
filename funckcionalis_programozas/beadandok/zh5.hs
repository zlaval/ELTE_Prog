import Data.Char

upperHd ::[Char] -> Char
upperHd [] = '?'
upperHd (x : _) = (toUpper x)

changeHd :: [Char] -> Char
changeHd [] = '?'
changeHd (x : _)
  | isUpper x = (toLower x)
  | otherwise = (toUpper x)