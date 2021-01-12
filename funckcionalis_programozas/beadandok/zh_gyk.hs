import Data.Char

data Answer = Yes | No | Unsure deriving (Eq)

data Shape = Circle Double | Square Double | Rectangle Double Double

data Tree a = Leaf a | Node (Tree a) (Tree a) deriving (Eq, Show)

upAndDownList :: [Int]
upAndDownList = [1 .. 100] ++ reverse [1 .. 99]

upAndDownListToX :: Int -> [Int]
upAndDownListToX x = [1 .. x] ++ reverse [1 .. x -1]

repeat123 :: [Int]
repeat123 = take 79 (cycle [1, 2, 3])

evenSeventeen :: [Int]
evenSeventeen = [y | y <- [67 .. 978], y `mod` 17 == 0, even y]

absoluteNegatives :: [Int] -> [Int]
absoluteNegatives x = map abs $ filter (< 0) x

addSum :: [Int] -> [Int]
addSum x = sum x : x

mapAnswerToMaybeBool x
  | x == Yes = Just True
  | x == No = Just False
  | x == Unsure = Nothing

mapMaybeBoolToAnswer x
  | x == Just True = Yes
  | x == Just False = No
  | otherwise = Unsure

convertAnswers :: [(Int, Answer)] -> [(Int, Maybe Bool)]
convertAnswers = map (\(x, y) -> (x, mapAnswerToMaybeBool y))

convertAnswersBack :: [(Int, Maybe Bool)] -> [(Int, Answer)]
convertAnswersBack = map (\(x, y) -> (x, mapMaybeBoolToAnswer y))

lastMap :: (a -> a) -> [a] -> [a]
lastMap f [] = []
lastMap f (x : []) = f x : lastMap f []
lastMap f (x : xs) = x : lastMap f xs

area :: Shape -> Double
area (Circle x) = x * pi
area (Square x) = x * x
area (Rectangle x y) = x * y

zip3Lists :: [Int] -> [Int] -> [Int] -> [Int]
zip3Lists [] [] [] = []
zip3Lists (x : xs) (y : ys) (z : zs) = x * y * z : zip3Lists xs ys zs

minusGame :: (Int, Int) -> [(Int, Int)]
minusGame (0, b) = []
minusGame (a, 0) = []
minusGame (a, b) = if a >= b then (a - b, b) : minusGame (a - b, b) else (a, b - a) : minusGame (a, b - a)

decodeMessage :: [Integer] -> String
decodeMessage = map (chr . floor . sqrt . fromInteger)

convertTree :: Tree Int -> Tree Int
convertTree (Leaf x) = Leaf (x * x)
convertTree (Node l r) = Node (convertTree l) (convertTree r)

filterNeptun c l =
  let p = filter (\(a, b) -> a == c) l
   in case p of
        [] -> Nothing
        (x : xs) -> Just (snd x)

lookupNeptunCode :: String -> [(String, String)] -> Maybe String
lookupNeptunCode c l
  | length c /= 6 = Nothing
  | not (all isAlphaNum c) = Nothing
  | any isLower c = Nothing
  | otherwise = filterNeptun c l