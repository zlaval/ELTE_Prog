isSolution a b c = (a*c+b) == 0

isSolution' :: Integer -> Integer -> Integer -> Bool
isSolution' a b c = isSolution (fromInteger a) (fromInteger b) (fromInteger c)