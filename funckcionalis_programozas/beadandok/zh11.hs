powSum :: [Integer] -> Integer
powSum = foldr (+) 0 . map (^ 2) . filter (even) . filter (\x -> (mod x 3) == 0)

answer = [1,3,4]

