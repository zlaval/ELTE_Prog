import random
import socket
import struct
import sys
import time
import uuid

client_uuid = uuid.uuid4()
min_tip = 1
max_tip = 100
actual_tip = 50
response = ""
last_tip = 50


def calculate_tip():  # todo kicsit csiszolni még
    global actual_tip
    global last_tip
    actual_tip = int((min_tip + max_tip) / 2)
    actual_sign = b"<"

    if min_tip == max_tip or (min_tip + 1) == max_tip or (min_tip + 2) == max_tip:
        actual_sign = b"="

    if actual_tip == last_tip:
        actual_tip = actual_tip + 1
    last_tip = actual_tip
    print(f'Client {client_uuid} send my tip {actual_tip} using {actual_sign}')
    return actual_sign, actual_tip  # actual_sign.decode()


def process_response(response_packed):
    global response
    global min_tip
    global max_tip
    character, _ = struct.Struct("1s I").unpack(response_packed)
    print(f'Response: {character}')
    response = character.decode()  # .encode()
    if response == "I":
        max_tip = actual_tip
    elif response == "N":
        min_tip = actual_tip


def start(host, port):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.connect((host, int(port)))
        while True:
            if response == "V" or response == "Y" or response == "K":
                print(f'Game end {response}')
                break
            sleep_time = random.randint(1, 5)
            time.sleep(sleep_time)
            msg = calculate_tip()
            msg_packed = struct.Struct("1s I").pack(*msg)
            sock.sendall(msg_packed)
            response_packed = sock.recv(1000)
            process_response(response_packed)

        sock.close()


hostarg = sys.argv[1]
portarg = sys.argv[2]
start(hostarg, portarg)
