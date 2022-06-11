import json
import sys

row_number = 1

topology = {}


class Edge:
    def __init__(self, node_one, node_two, capacity):
        self.connected_nodes = {node_one, node_two}
        self.capacity = capacity
        self.in_use = 0

    def acquire(self, required_capacity):
        if self.has_capacity(required_capacity):
            self.in_use = self.in_use + required_capacity

    def has_capacity(self, required_capacity):
        free = self.capacity - self.in_use
        if free >= required_capacity:
            return True
        else:
            return False

    def release(self, unused_capacity):
        self.in_use = self.in_use - unused_capacity


class Node:
    def __init__(self, name, is_switch):
        self.name = name
        self.is_switch = is_switch
        self.connections = set()

    def add_connection(self, edge):
        self.connections.add(edge)

    def release_chain(self, route, capacity):
        if len(route) > 1:
            next_node = route[1]
            edge = None
            for connection in self.connections:
                for name in connection.connected_nodes:
                    if name.name == next_node:
                        edge = connection
            if edge:
                edge.release(capacity)
                topology[next_node].release_chain(route[1:], capacity)

    def acquire_chain(self, route, capacity):
        if len(route) > 1:
            next_node = route[1]
            edge = None
            for connection in self.connections:
                for name in connection.connected_nodes:
                    if name.name == next_node:
                        edge = connection

            if not edge:
                return False
            else:
                if edge.has_capacity(capacity):
                    edge.acquire(capacity)
                    return topology[next_node].acquire_chain(route[1:], capacity)
                else:
                    return False
        else:
            return True


def read_file(file_name):
    with open(file_name, "r") as file:
        data = json.load(file)
        return data


def is_route_exists(data, end_points):
    possible_routes = data["possible-circuits"]
    for possible_route in possible_routes:
        if end_points[0] in possible_route and end_points[1] in possible_route:
            return True

    return False


def get_route(data, end_points):
    possible_routes = data["possible-circuits"]
    for possible_route in possible_routes:
        if end_points[0] in possible_route and end_points[1] in possible_route:
            return possible_route
    return []


def acquire_resource(data, demand, time):
    global row_number
    end_points = demand["end-points"]
    start_node = end_points[0]
    end_node = end_points[1]
    capacity = demand['demand']
    if is_route_exists(data, end_points):
        start_point = topology[start_node]
        route = get_route(data, end_points)
        result = start_point.acquire_chain(route, capacity)
        if result:
            demand['acquired'] = True
            print(f'{row_number}. igény foglalás: {start_node}<->{end_node} st:{time} - sikeres')
        else:
            print(f'{row_number}. igény foglalás: {start_node}<->{end_node} st:{time} - sikertelen')
        row_number += 1
    else:
        print(f'{row_number}. igény foglalás: {start_node}<->{end_node} st:{time} - sikertelen')
        row_number += 1


def release_resource(data, demand, time):
    global row_number
    start_point = topology[demand['end-points'][0]]
    key = 'acquired'
    capacity = demand['demand']
    if key in demand:
        end_points = demand["end-points"]
        start_node = end_points[0]
        end_node = end_points[1]
        route = get_route(data, end_points)
        start_point.release_chain(route, capacity)
        print(f'{row_number}. igény felszabadítás: {start_node}<->{end_node} st:{time}')
        row_number += 1


def main_loop(data):
    demands = data['simulation']['demands']
    for time in range(1, data['simulation']['duration'] + 1):
        for demand in demands:
            if time == demand['start-time']:
                acquire_resource(data, demand, time)
            elif time == demand["end-time"]:
                release_resource(data, demand, time)


def build_topology(data):
    end_points = data['end-points']
    for end_point in end_points:
        topology[end_point] = Node(end_point, False)

    switches = data['switches']
    for switch in switches:
        topology[switch] = Node(switch, True)

    links = data["links"]
    for link in links:
        points = link["points"]
        capacity = link["capacity"]
        first = topology[points[0]]
        second = topology[points[1]]
        edge = Edge(first, second, capacity)
        first.add_connection(edge)
        second.add_connection(edge)

    # possible_circuits = data["possible-circuits"]
    # for cl in possible_circuits:
    #     pairs = zip(cl, cl[1:])
    #     for pair in pairs:
    #         first = topology[pair[0]]
    #         second = topology[pair[1]]


def start(file_name):
    data = read_file(file_name)
    build_topology(data)
    main_loop(data)


input_json_name = sys.argv[1]
start(input_json_name)

# if __name__ == '__main__':
#     input_json_name = 'cs1.json'
#     start(input_json_name)
