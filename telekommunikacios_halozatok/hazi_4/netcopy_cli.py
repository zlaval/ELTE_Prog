import hashlib
import socket
import sys

server_address = sys.argv[1]
server_port = sys.argv[2]
checksum_address = sys.argv[3]
checksum_port = sys.argv[4]
file_id = sys.argv[5]
file_path = sys.argv[6]

md5 = hashlib.md5()

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server, \
        socket.socket(socket.AF_INET, socket.SOCK_STREAM) as checksum_server:
    server.connect((server_address, int(server_port)))
    checksum_server.connect((checksum_address, int(checksum_port)))

    with open(file_path, "rb") as fin:
        while True:
            buffer = fin.read(1024)
            if not buffer:
                break
            server.send(buffer)
            md5.update(buffer)
        checksum = md5.hexdigest()
        msg = f'BE|{file_id}|60|{len(checksum)}|{checksum}'.encode()
        response = checksum_server.send(msg)
    checksum_server.close()
    server.close()
