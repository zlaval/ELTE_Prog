import Data.Char

data Clock = Tick Int | Tock Int | Boom deriving (Eq, Show)

tripletFromList :: [a] -> (a, a, a)
tripletFromList (a : _ : b : _ : c : _) = (a, b, c)

downAndUpList :: Int -> [Int]
downAndUpList n= [n,n-7..(-n)]++ [-n+1,-n+6..n]

countLetters :: String -> Int
countLetters l = sum [1 | x <- l, elem x (['a' .. 'z'] ++ ['A' .. 'Z'])]

calculatePoints :: [(Char, Char, Int)] -> Int
calculatePoints [] = 0
calculatePoints ((a, b, c) : xs) = if a == b then c + calculatePoints xs else calculatePoints xs

minmaxList :: [Int] -> [Int]
minmaxList l = minimum l : l ++ [maximum l]

pairSum :: [Int] -> [Int]
pairSum [] = []
pairSum [x] = [x]
pairSum (x : y : xs) = (x + y) : pairSum xs

addMaybe :: Maybe Int -> Maybe Int -> Maybe Int
addMaybe _ Nothing = Nothing
addMaybe Nothing _ = Nothing
addMaybe (Just x) (Just y) = Just (x + y)

addMaybeList :: [Maybe Int] -> Maybe Int
addMaybeList [] = Just 0
addMaybeList x = if elem Nothing x then Nothing else Just (foldr (\(Just x) a -> a + x) 0 x)

evenOnly :: [Int] -> [Int]
evenOnly = filter (<= 10) . map (\x -> if even x then x * 2 else x)

collatz :: Int -> [Int]
collatz 1 = [1]
collatz x
  | even x = x : collatz (x `div` 2)
  | otherwise = x : collatz (3 * x + 1)

countDown :: Int -> [Clock]
countDown 0 = [Boom]
countDown x
  | even x = Tick x : countDown (x -1)
  | otherwise = Tock x : countDown (x -1)

avg :: [Int] -> Maybe Double
avg [] = Nothing
avg x =  Just (fromIntegral (sum x) / fromIntegral(length x))

data Storage = Crate Double Double Double | Barrel Double Double | Container [Storage]

volume :: Storage -> Double
volume (Crate a b c)= a*b*c
volume (Barrel m r)= m*r*r*pi
volume (Container x) = foldr (\e acc-> volume e + acc) 0 x


data Tree a = Leaf a | Node (Tree a) (Tree a) deriving (Eq, Show)

flipEitherTree :: Tree (Either a b) -> Tree (Either b a)
flipEitherTree (Leaf (Left a)) = Leaf (Right a)
flipEitherTree (Leaf (Right a)) = Leaf (Left a)
flipEitherTree (Node a b) = Node (flipEitherTree a) (flipEitherTree b)


breakOn acc _ [] = (acc, [])
breakOn acc c (x : xs) = if x == c then (acc, xs) else breakOn (acc ++ [x]) c xs
splitOn _ [] = []
splitOn c x = let t = (breakOn "" c x) in (fst t) : splitOn c (snd t)

separateEmail :: String -> (String, [String])
separateEmail s= let x = splitOn '@' s in ( head x , if length x == 2 then  splitOn '.'  (head (tail x)) else [] )