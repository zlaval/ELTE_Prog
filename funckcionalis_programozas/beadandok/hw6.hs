mult 0 b = 0
mult 1 b = b
mult a b = b + mult (a -1) b

mult' a b
  | a < 0 = (-1) * mult (abs a) b
  | otherwise = mult a b

pow a 0 = 1
pow a b = a * pow a (b -1)

zip' [] _ = []
zip' _ [] = []
zip' (x : xs) (y : ys) = (x, y) : zip' xs ys

unzip' :: [(a, b)] -> ([a], [b])
unzip' [] = ([], [])
unzip' (x : xs) = (fst x : (fst (unzip' xs)), snd x : (snd (unzip' xs)))

last' [] = error "empty list does not have last element"
last' (x : []) = x
last' (_ : xs) = last' xs

maximum' [] = error "empty list does not have maximum element"
maximum' (x : []) = x
maximum' (x : y : xs) = maximum' ((max x y) : xs)