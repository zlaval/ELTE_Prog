import Data.Char

type Stack a = [a]

push :: a -> Stack a -> Stack a
push a x = a : x

top :: Stack a -> Maybe a
top [] = Nothing
top (x : _) = Just x

pop :: Stack a -> Maybe (Stack a)
pop [] = Nothing
pop (_ : xs) = Just xs

top2 :: Stack a -> Either a String
top2 [] = Right "Error: empty stack"
top2 (x : _) = Left x

pop2 :: Stack a -> Either (Stack a) String
pop2 [] = Right "Error: empty stack"
pop2 (_ : xs) = Left xs

at :: [a] -> Int -> Maybe a
at [] _ = Nothing
at (x : xs) i
  | i == 0 = Just x
  | otherwise = at xs (i -1)

at2 :: [a] -> Int -> Either a String
at2 x i = at2' x i i

at2' [] _ n = Right ("Error: invalid index: " ++ show n)
at2' (x : xs) i n
  | i == 0 = Left x
  | otherwise = at2' xs (i -1) n

count :: (a -> Bool) -> [a] -> Int
count f = foldr (\_ acc -> acc + 1) 0 . filter f

transformFirst :: String -> Maybe String
transformFirst [] = Nothing
transformFirst (x : xs)
  | isUpper x = Just (toLower x : xs)
  | isLower x = Just (toUpper x : xs)
  | otherwise = Just xs

negativeFilter :: (a -> Bool) -> [a] -> [a]
negativeFilter _ [] = []
negativeFilter f (x : xs) = if not (f x) then x : negativeFilter f xs else negativeFilter f xs

first :: (a -> Bool) -> [a] -> Maybe a
first _ [] = Nothing
first f (x : xs)
  | f x = Just x
  | otherwise = first f xs

data Tool = Hammer Int | Wrench Int | Screwdriver deriving (Eq, Show)

createTool :: String -> Int -> Maybe Tool
createTool n s
  | s < 1 = Nothing
  | n == "Hammer" = Just (Hammer s)
  | n == "Screwdriver" = Just Screwdriver
  | n == "Wrench" = Just (Wrench s)
  | otherwise = Nothing