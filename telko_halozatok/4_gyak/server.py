import json
import socket


def receive_message():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as srv:
        srv.bind(('0.0.0.0', 55555))
        srv.listen()
        cli, cli_address = srv.accept()
        # msg = cli.recv(2000)
        # print(msg.decode("utf-8"), cli_address)
        # cli.send(b'Hello client\n')
        msg = cli.recv(2000)
        data = json.loads(msg.decode())
        print(data)
        cli.close()


if __name__ == '__main__':
    receive_message()
