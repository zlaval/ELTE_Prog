-- Ures tipus: Void

-- JAvito: pentek -- kedd

data Time = T Int Int

-- showTime (instance-val szebb lenne)
instance Show Time where
  show (T a b) = show a ++ "." ++ show b

-- smart constructor --> time, csak valid idopontokkal lehessen Time-ot letrehozni
time :: Int -> Int -> Time
time a b
  | a < 0 || a > 23 || b < 0 || b > 59 = error "Invalid time"
  | otherwise                          = T a b

-- Exception dobasa helyett maradjunk "tisztak"... Milyen tipust lehet hasznalni ehhez?
data TimeError = ValidTime Time | InvalidTime deriving Show

time' :: Int -> Int -> TimeError
time' a b
  | a < 0 || a > 23 || b < 0 || b > 59 = InvalidTime
  | otherwise                          = ValidTime (T a b)

-- data Maybe a = Just a | Nothing
time'' :: Int -> Int -> Maybe Time
time'' a b
  | a < 0 || a > 23 || b < 0 || b > 59 = Nothing
  | otherwise                          = Just (T a b)


-- Mashol is tudjuk hasznalni: pl. biztonsagos div, hd, tl, stb.
safeDiv :: Int -> Int -> Maybe Int
safeDiv n 0 = Nothing
safeDiv n d = Just $ n `div` d

-- safeHd :: [a] -> ???
-- safeHd = undefined

-- Keszitsunk egy map-ot: kulcs-ertek parok listaja. A kulcsok legyenek Intek, ertekek tetszoleges tipusuak.
-- Legyen put, get muvelet (egyszeruseg kedveert csak (==)-t vizsgaljunk)

type Map a = [(Int, a)]

put :: Int -> a -> Map a -> Map a
put k v []            = [(k,v)]
put k v ((k',v'):xs)
  | k == k' = (k, v) : xs
  | k /= k' = (k', v') : put k v xs

get :: Int -> Map a -> Maybe a
get k []            = Nothing
get k ((k', v'):xs)
  | k == k' = Just v'
  | k /= k' = get k xs

-- Hogyan tudnank error uzenetet is tarsitani az elozoekhez? --> Sum tipus

data Error a = Exc String | Ok a deriving Show

get' :: Int -> Map a -> Error a
get' k []            = Exc (show k ++ ": Key not found")
get' k ((k', v'):xs)
  | k == k' = Ok v'
  | k /= k' = get' k xs

-- data Either a b = Left a | Right b
type Error' a = Either String a


-- 2 vagy 3D-s alakzatot rajzoljunk? (Input : Shape, magassag)

data Shape2D = Circle Double               -- sugar
             | Rectangle Double Double     -- oldalak hossza
  deriving Show
data Shape3D = Cylinder Shape2D Double     -- sugar, magassag
             | Prism Shape2D Double        -- alapok hossza, magassag
  deriving Show

draw :: Shape2D -> Double -> Either Shape2D Shape3D
draw s d
  | d == 0.0  = Left s
  | otherwise = Right $
      case s of
        Circle _      -> Cylinder s d
        Rectangle _ _ -> Prism s d

-- Definialjunk lista tipust: Int-eket tartalmazo lista

-- hd, tl, stb...