data Sign = Rock | Paper | Scissors deriving (Eq, Show)

convert :: Sign -> Sign
convert Rock = Paper
convert Paper = Scissors
convert Scissors = Rock

signListConverter :: [Sign] -> [Sign]
signListConverter [] = []
signListConverter (x : xs) = (convert x) : signListConverter xs

--abs, cycle, filter, foldl, foldr, map, repeat, sum, take, zip, zipWith.
type Player = (String, [Sign])

anna :: Player
anna = ("Anna", cycle [Rock, Paper, Scissors])

john :: Player
john = ("John", repeat Paper)

george :: Player
george = ("George", georgeStrategy [1 ..])

georgeStrategy = map georgeStrategyHelper

georgeStrategyHelper x
  | x `mod` 7 == 5 = Rock
  | x `mod` 3 == 0 = Paper
  | otherwise = Scissors

data Result = Player1 | Player2 | Draw deriving (Eq, Show)

compareSign :: Sign -> Sign -> Result
compareSign Rock Scissors = Player1
compareSign Scissors Paper = Player1
compareSign Paper Rock = Player1
compareSign f s = if f == s then Draw else Player2

compareSigns :: [Sign] -> [Sign] -> [Result]
compareSigns x y = let pairs = zip x y in map (\(a, b) -> compareSign a b) pairs

fightPlayers :: Player -> Player -> [Result]
fightPlayers a b = compareSigns (snd a) (snd b)

evaluateResult :: Result -> Int
evaluateResult Player1 = 1
evaluateResult Player2 = (-1)
evaluateResult Draw = 0

calculatePoints :: [Result] -> Int
calculatePoints x = foldr ((+) . evaluateResult) 0 x

fightPlayersUntil :: Player -> Player -> Int -> [Result]
fightPlayersUntil p1 p2 round = take round (fightPlayers p1 p2)

tournament :: Player -> Player -> Int -> (String, Int)
tournament p1 p2 round = let points = calculatePoints (fightPlayersUntil p1 p2 round) in if points > 0 then (fst p1, points) else if points < 0 then (fst p2, abs points) else ((fst p1) ++ "/" ++ (fst p2), points)