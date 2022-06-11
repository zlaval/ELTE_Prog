from socket import socket, AF_INET, SOCK_DGRAM

with socket(AF_INET, SOCK_DGRAM) as pricelog:
    pricelog.bind(('localhost', 11111))

    while True:
        new_price, client_addr = pricelog.recvfrom(100)
        print(f'Price changed to {new_price.decode()}')
