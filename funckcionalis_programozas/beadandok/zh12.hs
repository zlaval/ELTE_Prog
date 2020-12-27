data Answer = Yes | No | DontKnow deriving (Eq)

andAnswer :: Answer -> Answer -> Answer
andAnswer Yes Yes = Yes
andAnswer No _ = No
andAnswer _ No = No
andAnswer DontKnow _ = DontKnow
andAnswer _ DontKnow = DontKnow


answer :: [Int]
answer = [3]