import socket
import struct
import sys
import time


def start(bids):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.connect(("localhost", 55555))

        for bid in bids:
            msg = (b"BID", int(bid))
            print(f'Bid {bid}')
            msg_packed = struct.Struct("3s I").pack(*msg)
            sock.sendall(msg_packed)
            response_packed = sock.recv(1000)
            message, price = struct.Struct("3s I").unpack(response_packed)
            message=message.decode()
            print(f'The {price} price is {message}')
            time.sleep(1)

        sock.close()


user_bids = sys.argv
user_bids.pop(0)
start(user_bids)
