hoursMinutes = [(hour, min) | hour <- [0 .. 23], min <- [0 .. 59]]

twoPower = take 1 ([2 ^ x | x <- [0 ..], 2 ^ x > 10 ^ 20])

fac' n = fac 1 n 
fac ac 0 = ac
fac ac n = fac (ac*n) (n-1)
