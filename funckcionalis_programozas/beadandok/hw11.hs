data Season = Spring | Summer | Autumn | Winter deriving (Show)

isNextSeason :: Season -> Season -> Bool
isNextSeason Spring Summer = True
isNextSeason Summer Autumn = True
isNextSeason Autumn Winter = True
isNextSeason Winter Spring = True
isNextSeason _ _ = False

data Shape = Circle Double | Rectangle Double Double | Triangle Double Double

area :: Shape -> Double
area (Circle r) = r * r * pi
area (Rectangle a b) = a * b
area (Triangle a m) = (a * m) / 2

data Time = T Int Int deriving (Eq, Ord)

showTime :: Time -> String
showTime (T a b) = show a ++ "." ++ show b

instance Show Time where
  show = showTime

eqTime :: Time -> Time -> Bool
eqTime (T a1 b1) (T a2 b2) = a1 == a2 && b1 == b2

isEarlier :: Time -> Time -> Bool
isEarlier (T a1 b1) (T a2 b2) = a1 < a2 || (a1 == a2 && b1 < b2)

isBetween :: Time -> Time -> Time -> Bool
isBetween a b c = (isEarlier a b && isEarlier b c) || (isEarlier c b && isEarlier b a)

time :: Int -> Int -> Time
time a b 
    | a<0 || a > 23 = error $ "invalid hour: " ++ show a
    | b<0 || b > 59 = error $ "invalid minute: " ++ show b
    | otherwise = T a b