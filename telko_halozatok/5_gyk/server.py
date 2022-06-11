from socket import *
import hashlib

md5 = hashlib.md5()

with socket(AF_INET, SOCK_STREAM) as srv:
    srv.bind(("0.0.0.0", 55555))
    srv.listen()
    cli, _ = srv.accept()
    with open("recv.txt", "wb") as fout:
        while True:
            msg = cli.recv(1024)
            if not msg:
                break
            md5.update(msg)
            fout.write(msg)
        print("SRV DONE", md5.hexdigest())
