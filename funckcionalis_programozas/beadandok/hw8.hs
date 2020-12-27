orList :: [Bool] -> Bool
orList = foldr (||) False

concatList :: [[a]] -> [a]
concatList = concat --foldr (++) [] 

minList :: (Foldable t, Ord a) => t a -> a
minList  = minimum 

allList :: (a -> Bool) -> [a] -> Bool
allList = all 

powSum :: [Integer] -> Integer
powSum l= foldr (+) 0 $ map (^2) $ filter ( (\x -> even x && x `mod` 3 == 0)) l

removeLast :: [a] -> [a]
removeLast [] = []
removeLast [x] = []
removeLast (x:xs) = x : removeLast xs

toSentence :: [String] -> String
toSentence l= removeLast (foldr (\x acc-> x ++ " " ++ acc) [] l) 