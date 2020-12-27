
g (Just False) = Right False
g (Just True)  = Left "alma"
g _            = Right True

f _ (Just 5) = 5

answerf = [4]

answerg =[3]
