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
    server.bind((server_address, int(server_port)))
    server.listen()

    checksum_server.connect((checksum_address, int(checksum_port)))

    cli, cli_address = server.accept()
    with open(file_path, "wb") as fos:
        while True:
            msg = cli.recv(1024)
            if not msg:
                break
            fos.write(msg)
            md5.update(msg)
        checksum = md5.hexdigest()
        csm_msg = f'KI|{file_id}'.encode()
        checksum_server.send(csm_msg)
        response = checksum_server.recv(1024).decode()
        ln, cs = response.split("|")
        if cs == checksum:
            print("CSUM OK")
        else:
            print("CSUM CORRUPTED")
    checksum_server.close()
