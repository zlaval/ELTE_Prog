orList :: [Bool] -> Bool
orList = foldr (||) False

anyList :: (a -> Bool) -> [a] -> Bool
anyList f l = orList $ map f l