data MyList a = Cons a (MyList a) | Nil deriving (Eq, Show)

data Tree1 a = Node1 (Tree1 a) (Tree1 a) | Leaf1 a deriving (Eq, Show)

data Tree2 a = Node2 (Tree2 a) a (Tree2 a) | Leaf2 a deriving (Eq, Show)

multMyList :: MyList Int -> Int
multMyList Nil = 1
multMyList (Cons x xs) = x * multMyList xs

multTree1 :: Tree1 Int -> Int
multTree1 (Leaf1 x) = x
multTree1 (Node1 a b) = multTree1 a * multTree1 b

multTree2 :: Tree2 Int -> Int
multTree2 (Leaf2 x) = x
multTree2 (Node2 a x b) = multTree2 a * x * multTree2 b

transformMyList :: MyList Int -> MyList Int
transformMyList Nil = Nil
transformMyList (Cons x xs) = Cons (x + 5) (transformMyList xs)

transformTree1 :: Tree1 Int -> Tree1 Int
transformTree1 (Leaf1 x) = Leaf1 (x + 5)
transformTree1 (Node1 a b) = Node1 (transformTree1 a) (transformTree1 b)

transformTree2 :: Tree2 Int -> Tree2 Int
transformTree2 (Leaf2 x) = Leaf2 (x + 5)
transformTree2 (Node2 a x b) = Node2 (transformTree2 a) (x + 5) (transformTree2 b)

filterEvenMyList :: MyList Int -> [Int]
filterEvenMyList Nil = []
filterEvenMyList (Cons x xs) = if even x then x : filterEvenMyList xs else filterEvenMyList xs

treeConvert :: Tree1 Int -> Tree2 Int
treeConvert  (Leaf1 x) = Leaf2 x
treeConvert  (Node1 a b) = Node2 (treeConvert  a) 0 (treeConvert  b) 

filterEvenTree1 :: Tree1 Int -> [Int]
filterEvenTree1 (Leaf1 x) = [x|even x]
filterEvenTree1 (Node1 a b)=filterEvenTree1 a ++ filterEvenTree1 b

filterEvenTree2 :: Tree2 Int -> [Int]
filterEvenTree2 (Leaf2 x) = [x|even x]
filterEvenTree2 (Node2 a x b)=filterEvenTree2 a ++ filterEvenTree2 (Leaf2 x) ++ filterEvenTree2 b
