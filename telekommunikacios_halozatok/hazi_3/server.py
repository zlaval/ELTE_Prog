import random
import struct
import sys
from select import *
from socket import *

rand = random.randint(1, 100)
has_winner = False


def process_message(msg_packed, client):
    global has_winner
    signb, tip = struct.Struct("1s I").unpack(msg_packed)
    sign = signb.decode()
    iwin = False

    result_letter = b"N"
    if sign == "=" and tip == rand and not has_winner:
        result_letter = b"Y"
        has_winner = True
        iwin = True
        print(f'{client} win')
    elif sign == "=":
        result_letter = b"K"

    if sign == "<" and rand < tip:
        result_letter = b"I"
    elif sign == ">" and rand > tip:
        result_letter = b"I"

    msg = (result_letter, 0)
    if has_winner:
        if not iwin:
            msg = (b"V", 0)
    msg_packed = struct.Struct("1s I").pack(*msg)
    print(f'Send to {client}: {result_letter}')
    client.sendall(msg_packed)


def start(host, port):
    global rand
    global has_winner
    srv = socket(AF_INET, SOCK_STREAM)
    srv.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    srv.bind((host, int(port)))
    srv.listen()
    sockets = [srv]

    while True:
        readable, _, _ = select(sockets, [], [], 1)
        if len(sockets) == 1:
            rand = random.randint(1, 100)
            has_winner = False
            print(f'New random {rand}')

        for active in readable:
            if active == srv:
                cli, addr = active.accept()
                print("New client connected ", addr)
                sockets.append(cli)
            else:
                msg = active.recv(1000)
                if not msg:
                    active.close()
                    sockets.remove(active)
                    print("Client left")
                else:
                    process_message(msg, active)


hostarg = sys.argv[1]
portarg = sys.argv[2]
start(hostarg, portarg)
