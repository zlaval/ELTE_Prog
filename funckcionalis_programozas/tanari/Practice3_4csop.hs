

-- a b c együtthatókkal ábrázolt másodfokú függvény, keressük a zérushelyeit

helper :: Double -> Double -> Double -> Double -> Double
helper a b c d = ((-b) + d * sqrt(b ^ 2 - 4 * a * c)) / (2 * a)

solve :: Double -> Double -> Double -> (Double, Double)
-- solve a b c = ( ((-b) + sqrt(b ^ 2 - 4 * a * c)) / (2 * a), ((-b) - sqrt(b ^ 2 - 4 * a * c)) / (2 * a) )
solve a b c = (helper a b c (-1), helper a b c 1)

-- függvény definiálása több egyenlettel
-- matek -> esetszétválasztással megadott függvény
and' :: Bool -> Bool -> Bool
and' True  True  = True
and' False True  = False
and' True  False = False
and' False False = False

and'' :: Bool -> Bool -> Bool
and'' True True = True
and'' False _   = False
and'' _ False   = False

and''' :: Bool -> Bool -> Bool
and''' True True = True
and''' _ _       = False

--badand :: Bool -> Bool -> Bool
--badand a b       = False
--badand _ _       = False
--badand True True = True

-- szamologep
-- calc 2 '+' 3 --> 5
calc :: Integer -> Char -> Integer -> Double
calc a '+' b = fromIntegral (a + b)
calc a '*' b = fromIntegral (a * b)
calc a '-' b = fromIntegral (a - b)
calc a '/' b = fromIntegral a / fromIntegral b


