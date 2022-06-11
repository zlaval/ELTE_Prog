import hashlib
import json
import time
import uuid
from datetime import datetime

runtimes = []
iterations = []
aggregated_result = []
prog_start = time.time()


def generate_random_hash(hash_fn):
    rand_str = str(uuid.uuid4())
    hash_val = hash_fn(rand_str.encode())
    return rand_str, hash_val.hexdigest()


def find_collision(hash_fn, char_count, place="BEGIN"):
    global runtimes
    global iterations
    run = True
    hash_fragments = {}
    round_count = 0
    start_time = datetime.now()
    st = time.time()
    while run:
        round_count += 1
        rand_str, hash_val = generate_random_hash(hash_fn)
        cycles = 1

        hash_fragment = ""
        if place == "BEGIN" or place == "ALL":
            hash_fragment = hash_val[0:char_count]
        elif place == "END":
            hash_fragment = hash_val[-char_count:]

        if place == "ALL":
            cycles = len(hash_val) - char_count

        for cycle in range(0, cycles):
            if cycle > 0:
                hash_fragment = hash_val[cycle:char_count + cycle]

            search_fragment = hash_fragment
            if place == "ALL":
                search_fragment = hash_fragment + "_" + str(cycle)

            if search_fragment in hash_fragments.keys():
                run = False
                file_name = "results/" + hash_fn.__name__ + "_" + place + "_" + str(char_count) + "_result" + str(
                    prog_start) + ".txt"
                mode = "a"
                if char_count == 1:
                    mode = "w"
                with open(file_name, mode) as fos:
                    if place != "ALL":
                        hash2 = hash_fragments[hash_fragment]["hash_val"]
                    else:
                        hash2 = hash_fn((hash_fragments[search_fragment]["rand_str"]).encode()).hexdigest()

                    val = json.dumps({
                        "hash_fn": hash_fn.__name__,
                        "char_count": char_count,
                        "type": place,
                        "round_count": round_count,
                        "collided_fragment": hash_fragment,
                        "str1": rand_str,
                        "str1_hash": hash_val,
                        "str2": hash_fragments[search_fragment]["rand_str"],
                        "str2_hash": hash2,
                        "runtime": str(time.time() - st),
                        "position": str(cycle)
                    }, indent=4)
                    fos.write(val)

                print(f'Found {char_count} character(s) long collision at the {place} with {hash_fn.__name__} fn.')
                print(f'Hash fragment: {hash_fragment}, Iteration: {round_count}, time: {datetime.now() - start_time}')
                print(f'First string/hash: {rand_str}, {hash_val}')
                print(
                    f'Second string/hash: {hash_fragments[search_fragment]["rand_str"]}, {hash2}')
                if place == "ALL":
                    print(f'position: {cycle}..{cycle + char_count}')
                print()
                iterations.append(round_count)
                runtimes.append(time.time() - st)
            else:
                if place == "ALL":
                    hash_fragments[search_fragment] = {
                        "rand_str": rand_str,
                        "position": str(cycle)
                    }
                else:
                    hash_fragments[hash_fragment] = {
                        "rand_str": rand_str,
                        "hash_val": hash_val,  # TODO remove for less memory usage
                    }
        # if round_count % 1000 == 0:
        #     print(f'{round_count}. iteration')
        # print(rand_str)
        # print(hash_val)


def aggregate_cycle(algo, t, char_count):
    global aggregated_result
    global runtimes
    global iterations
    avg_iter = sum(iterations) / len(iterations)
    avg_time = sum(runtimes) / len(runtimes)
    aggregated_result.append(
        {
            "hash_fn": algo.__name__,
            "place": t,
            "char_count": char_count,
            "avg_iteration": avg_iter,
            "avg_runtime": avg_time
        }
    )
    runtimes = []
    iterations = []


def print_result():
    global aggregated_result
    path = "results/aggregated_result" + str(prog_start) + ".txt"
    with open(path, "w") as fos:
        for item in aggregated_result:
            val = json.dumps(item, indent=4)
            fos.write(val)
            print(val)
            print()


def start():
    # hash_algos = [hashlib.md5, hashlib.sha1, hashlib.sha3_512]
    # types = ["BEGIN", "END", "ALL"]
    hash_algos = [hashlib.sha1]
    types = ["ALL"]
    for x in hash_algos:
        for t in types:
            for i in range(12, 13):
                for j in range(5):
                    find_collision(x, i, t)
                aggregate_cycle(x, t, i)
    print("THE END")
    print_result()

    # find_collision(hashlib.md5, 7, "BEGIN")
    # find_collision(hashlib.md5, 5, "END")
    # find_collision(hashlib.md5, 5, "ALL")


if __name__ == '__main__':
    start()
