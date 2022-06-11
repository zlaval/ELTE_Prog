from socket import *
from select import *
import sys

socks = []
sockmap = { }

srv = socket(AF_INET, SOCK_STREAM)
srv.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
srv.bind(("0.0.0.0", 5555))
srv.listen()
socks.append(srv)

while True:
    readable, _, _ = select(socks, [], [], 0.0001)
    for s in readable:
        if s == srv:
            cli, addr = srv.accept()
            remote = socket(AF_INET, SOCK_STREAM)
            remote.connect((sys.argv[1], int(sys.argv[2])))
            sockmap[cli] = remote
            sockmap[remote] = cli
            socks += [remote, cli]
        else:
            msg = s.recv(10000)
            if msg:
                sockmap[s].sendall(msg)
            else:
                sockmap[s].close()
                s.close()
                socks.remove(s)
                socks.remove(sockmap[s])
                del sockmap[sockmap[s]]
                del sockmap[s]

