isSpace c = c == ' '

stretch' a b c = (a * c, b * c)

stretch = uncurry stretch'

or' False False = False
or' _ _ = True

distance (a1, b1) (a2, b2) = sqrt (((a1 - a2) ^ 2) + ((b1 - b2) ^ 2))
