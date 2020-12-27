import Data.List

sucs x = map (+ 1) x

hasLongLines s = any (>= 3) $ map (length . words) (lines s)

hasNotTranslator :: [(String, String)] -> [(String, String, String)] -> [String]
hasNotTranslator x y = map (fst) $ filter (\t -> all (\(_, _, s) -> s /= snd t) y) x

simpleGrep :: String -> String -> [(String, Int)]
simpleGrep s x = filter (\(t, _) -> isPrefixOf s t) $ zip (lines x) [1 .. length x]

breakOn' :: [Char] -> Char -> [Char] -> ([Char], [Char])
breakOn' acc _ [] = (acc, [])
breakOn' acc c (x : xs) = if x == c then (acc, x : xs) else breakOn' (acc ++ [x]) c xs

breakOn c x = breakOn' "" c x

breakOn''' acc _ [] = (acc, [])
breakOn''' acc c (x : xs) = if x == c then (acc, xs) else breakOn''' (acc ++ [x]) c xs
breakOn'' c x = breakOn''' "" c x
splitOn _ [] = []
splitOn c x = let t = (breakOn'' c x) in (fst t) : splitOn c (snd t)