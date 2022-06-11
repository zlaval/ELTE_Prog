from socket import *
import hashlib

md5 = hashlib.md5()

with socket(AF_INET, SOCK_STREAM) as sock:
    sock.connect(("127.0.0.1", 55555))
    with open("data.txt", "rb") as fin:
        while True:
            buff = fin.read(1024)
            if not buff:
                break
            md5.update(buff)
            sock.send(buff)
        print("Cli Done", md5.hexdigest())
