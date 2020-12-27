answer = False

pow a 0 = 1
pow a b = a * pow a (b -1)