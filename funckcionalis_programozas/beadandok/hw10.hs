hasLongLines = any (>= 3) . map (length . words) . lines

powSum :: [Integer] -> Integer
powSum = foldr (+) 0 . map (^ 2) . filter (even) . filter (\x -> (mod x 3) == 0)

maximumOfMinimums :: Ord a => [[a]] -> a
maximumOfMinimums = maximum . map minimum

mapMap :: (a -> b) -> [[a]] -> [[b]]
mapMap = map . map