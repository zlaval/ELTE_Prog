from socket import *
from select import *

srv = socket(AF_INET, SOCK_DGRAM)
srv.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
srv.bind(("0.0.0.0", 5555))

clients = [ ]

while True:
    msg, addr = srv.recvfrom(1000)
    if addr not in clients:
        clients.append(addr)
        print("New client: ", addr)
    for c in clients:
        if c != addr:
            srv.sendto(msg, c)

