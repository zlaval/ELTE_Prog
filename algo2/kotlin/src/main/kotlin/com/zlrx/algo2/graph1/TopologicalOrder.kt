package com.zlrx.algo2.graph1

import java.util.*

fun main() {


    val v0 = Vertex("0")
    val v1 = Vertex("1")
    val v2 = Vertex("2")
    val v3 = Vertex("3")
    val v4 = Vertex("4")
    val v5 = Vertex("5")

    v0.addNeighbour(v4);
    v0.addNeighbour(v3);
    v4.addNeighbour(v2);
    v4.addNeighbour(v1);
    v3.addNeighbour(v5);
    v3.addNeighbour(v4);
    v2.addNeighbour(v5);

    val result = topologicalOrder(v0)
    while (!result.isEmpty()) {
        print(result.pop().value)
        if (!result.isEmpty()) {
            print(" -> ")
        }
    }
}

fun <T> topologicalOrder(node: Vertex<T>): Stack<Vertex<T>> {
    val stack = Stack<Vertex<T>>()
    fun to(node: Vertex<T>) {
        node.state = State.VISITING
        node.neighbour.forEach {
            if (it.state == State.NOT_VISITED) {
                to(it)
            }
        }
        node.state = State.VISITED
        stack.push(node)
    }
    to(node)
    return stack
}