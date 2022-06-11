import platform
import subprocess
import threading
import sys
import multiprocessing
import json
from datetime import datetime

now = datetime.now()
str_date = now.strftime("%Y%m%d")

ping_json = {
    "date": str_date,
    "system": "",
    "pings": [None] * 20
}

traceroute_json = {
    "date": str_date,
    "system": "",
    "traces": [None] * 20
}


def read_urls(file_name):
    all_url = []
    with open(file_name, "r") as file:
        for row in file:
            url = row.split(',')[1].rstrip("\n")
            all_url.append(url)
    return all_url[0:10] + all_url[-10:]


def determine_os():
    os = platform.system().lower()
    return os


def get_cpu_count():
    cpu_count = multiprocessing.cpu_count()
    return max(int(cpu_count / 2), 2)


def ping_url(url, os, index):
    command = ["ping", "-c", "10", url]
    if os == "windows":
        command = ["ping", "-n", "10", url]
    proc = subprocess.Popen(command, stdout=subprocess.PIPE)
    std_out, std_err = proc.communicate()
    result = std_out.decode("utf-8")
    ping_json["pings"][index] = {
        "target": url,
        "output": result
    }
    print("Ping {} has finished".format(url))


def trace_url(url, os, index):
    command = ["traceroute", url]
    if os == "windows":
        command = ["tracert", url]
    proc = subprocess.Popen(command, stdout=subprocess.PIPE)
    std_out, std_err = proc.communicate()
    result = std_out.decode("utf-8")
    traceroute_json["traces"][index] = {
        "target": url,
        "output": result
    }
    print("Trace {} has finished".format(url))


def start_workers(urls, os, cpu_count):
    index = 0
    while index < len(urls):
        threads = []
        count = 0
        for j in range(cpu_count):
            if index < len(urls):
                url = urls[index]
                print("Ping and Trace {}".format(url))
                ping_thread = threading.Thread(target=ping_url, args=(url, os, index))
                trace_thread = threading.Thread(target=trace_url, args=(url, os, index))
                threads.append(ping_thread)
                threads.append(trace_thread)
                ping_thread.start()
                trace_thread.start()
                index = index + 1
                count = count + 2
        for k in range(count):
            threads[k].join()


def write_result():
    print("Write results into file...")
    with open("ping.json", "w") as ping_out:
        json.dump(ping_json, ping_out, indent=2, sort_keys=False)
    with open("traceroute.json", "w") as traceroute_out:
        json.dump(traceroute_json, traceroute_out, indent=2, sort_keys=False)


def start(file_name):
    print("Application is started")
    urls = read_urls(file_name)
    os = determine_os()
    print("Run on {}".format(os))
    ping_json["system"] = os
    traceroute_json["system"] = os
    cpu_count = get_cpu_count()
    start_workers(urls, os, cpu_count)
    write_result()
    print("Application has finished")


csv_file_name = sys.argv[1]
start(csv_file_name)
