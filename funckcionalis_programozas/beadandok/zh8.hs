odds x = filter odd x

isSquare :: (Floating a, RealFrac a) => a -> Bool
isSquare t = sqrt t == fromIntegral(floor (sqrt t))
squarenums x = map floor( filter isSquare x )