from socket import *
from select import *

srv = socket(AF_INET, SOCK_STREAM)
srv.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
srv.bind(("0.0.0.0", 5555))
srv.listen()

sockets = [srv]

while True:
    readable, _, _ = select(sockets, [], [], 1)
    for active in readable:
        if active == srv:
            cli, addr = active.accept()
            print("New client ", addr)
            sockets.append(cli)
        else:
            msg = active.recv(1000)
            if not msg:
                active.close()
                sockets.remove(active)
                print("Client left")
            else:
                for s in sockets:
                    if s != active and s != srv:
                        s.send(msg)

