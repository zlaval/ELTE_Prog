import json
import socket

data = {
    "fruits": ["Apple", "Orange", "Banana"],
    "size": 3
}


def send_message():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.connect(("127.0.0.1", 55555))
        # sock.send('Helló\n'.encode("utf-8"))
        # reply = sock.recv(2000)
        # print(reply)
        data_json = json.dumps(data)
        sock.send(data_json.encode())


if __name__ == '__main__':
    send_message()
