xor True True = False
xor False False = False
xor _ _ = True

modDiv a b = (a `mod` b, a `div` b)