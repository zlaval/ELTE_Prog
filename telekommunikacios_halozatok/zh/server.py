import struct
from select import *
from socket import *

actual_price = 0


def process_message(msg_packed, client, pl):
    global actual_price

    m, bid = struct.Struct("3s I").unpack(msg_packed)

    text = b"LOW"
    if bid > actual_price:
        actual_price = bid
        text = b"OK"
        pl.sendto(str(actual_price).encode(), ('localhost', 11111))

    msg = (text, actual_price)
    print(f'Send to {client}: {text} actual price {actual_price}, bid was {bid}')
    new_packed_message = struct.Struct("3s I").pack(*msg)
    client.sendall(new_packed_message)


def start():
    global actual_price
    srv = socket(AF_INET, SOCK_STREAM)
    srv.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    srv.bind(("localhost", 55555))
    srv.listen()
    sockets = [srv]

    pl = socket(AF_INET, SOCK_DGRAM)

    while actual_price < 1000000:
        readable, _, _ = select(sockets, [], [], 1)
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
                    process_message(msg, active, pl)


start()
