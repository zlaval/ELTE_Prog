import Data.Char
import Data.List

{-
Tudnivalok: 
Tovabbi hasznos fuggvenyek:
 - (!!) fuggveny -> lista indexelese, 
 - zipWith -> zip, de tetszoleges fuggveny segitsegevel (nem feltetlenul parra alakitassal)
-}

-- Konkatenalj ossze 2 Maybe [a] erteket!
concatMaybe :: Maybe [a] -> Maybe [a] -> Maybe [a]
concatMaybe Nothing _ = Nothing
concatMaybe _ Nothing = Nothing
concatMaybe (Just xs) (Just ys) = Just (xs ++ ys)

-- 3 : 4 : 5 : 6 : []
-- [] , x : xs
-- int lista tipus
data IntList = IntNil | IntCons Int IntList deriving Show
-- data IntList = IntNil Int | IntCons Int IntList deriving Show
-- data IntStream = IntStr Int IntList deriving Show <- Streamek


-- lista tipus
data MyList a = Nil | Cons a (MyList a) deriving Show

-- hd, show
hd :: MyList a -> Maybe a
hd Nil         = Nothing
hd (Cons x xs) = Just x

--          .                 node             Cons
--         / \               /    \           / \
--        .   .           node     node      1   Cons
--       / \ / \          /  \    /  \          / \
--      .  5 6  7      node leaf leaf leaf     2   Nil
--     / \             /  \
--    8   9         leaf  leaf
--      ^
--      |
-- Node (Node (Node (Leaf 8) (Leaf 9)) (Leaf 5)) (Node (Leaf 6) (Leaf 7))
-- binaris fa tipus (levelekben ertekek)
data Tree1 a = Node (Tree1 a) (Tree1 a) | Leaf a deriving Show

example_tree1 :: Tree1 Int
example_tree1 = Node (Node (Node (Leaf 8) (Leaf 9)) (Leaf 5)) (Node (Leaf 6) (Leaf 7))

-- add ossze az osszes elemet egy Int-eket tartalmazo a fanak
sumTree :: Tree1 Int -> Int
sumTree (Leaf x)   = x
sumTree (Node l r) = sumTree l + sumTree r

-- paros elem -> true, paratlan -> false egy Int faban
evenTree :: Tree1 Int -> Tree1 Bool
evenTree (Leaf x)   = Leaf (even x)
evenTree (Node l r) = Node (evenTree l) (evenTree r)

-- binaris fa tipus (minden csucsban ertek)
data Tree2 a = Node2 (Tree2 a) a (Tree2 a) | Leaf2 a deriving Show

sumTree2 :: Tree2 Int -> Int
sumTree2 (Leaf2 x)     = x
sumTree2 (Node2 l x r) = sumTree2 l + x + sumTree2 r

evenTree2 :: Tree2 Int -> Tree2 Bool
evenTree2 (Leaf2 x)     = Leaf2 (even x)
evenTree2 (Node2 l x r) = Node2 (evenTree2 l) (even x) (evenTree2 r)

example_tree2 :: Tree2 Int
example_tree2 = Node2 (Leaf2 3) 2 (Leaf2 4)
{-
   2
  / \
 3   4
-}
-- kulonbozo ertekek a levelekben es a csucsokban
data Tree3 a b = Node3 (Tree3 a b) a (Tree3 a b) | Leaf3 b 
    deriving Show

