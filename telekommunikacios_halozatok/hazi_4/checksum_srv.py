import datetime
import sys
from select import *
from socket import *

server_address = sys.argv[1]
server_port = sys.argv[2]

checksums = {}


def process_data(message):
    op = message.split("|")[0]
    resp = b''
    if op == "BE":
        op, id, ttl, ln, cs = message.split("|")
        time_to_leave = datetime.datetime.now() + datetime.timedelta(seconds=int(ttl))
        checksums[id] = {
            'ttl': time_to_leave,
            'ln': ln,
            'cs': cs
        }
        resp = b'OK'

    elif op == "KI":
        op, id = message.split("|")
        css = checksums[id]
        if not css:
            resp = b'0|'
        else:
            if css['ttl'] < datetime.datetime.now():
                del checksums[id]
                resp = b'0|'
            else:
                a = css['ln']
                b = css['cs']
                resp = f'{a}|{b}'.encode()
    return resp


with socket(AF_INET, SOCK_STREAM) as server:
    server.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    server.bind((server_address, int(server_port)))
    server.listen()
    sockets = [server]

    while True:
        readable, _, _ = select(sockets, [], [], 1)
        for active in readable:
            if active == server:
                cli, addr = active.accept()
                sockets.append(cli)
            else:
                msg = active.recv(1000)
                if not msg:
                    active.close()
                    sockets.remove(active)
                else:
                    response = process_data(msg.decode())
                    active.sendall(response)
                    active.close()
                    sockets.remove(active)
