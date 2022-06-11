import random


def start():
    while True:
        first = random.randint(1, 10)
        second = random.randint(1, 10)
        end = False
        while not end:
            try:
                title = f'{first} * {second} = '
                result = input(title)
                real_result = first * second
                if int(result) == real_result:
                    end = True
                    print("Jó lett")
                else:
                    print("Nem jó, újra")
            except:
                print("Számot írj be")


if __name__ == '__main__':
    start()
