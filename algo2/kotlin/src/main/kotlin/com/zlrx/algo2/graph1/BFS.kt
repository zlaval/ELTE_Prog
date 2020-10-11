package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.State
import com.zlrx.algo2.graph1.model.Vertex
import java.util.*


fun main() {
    val a = Vertex("a")
    val b = Vertex("b")
    val c = Vertex("c")
    val d = Vertex("d")
    val e = Vertex("e")
    val f = Vertex("f")

    a.addNeighbour(b)
    b.addNeighbour(c)
    b.addNeighbour(e)
    c.addNeighbour(e)
    d.addNeighbour(a)
    e.addNeighbour(d)
    f.addNeighbour(c)
    f.addNeighbour(e)

    bfs(a)
    println(a)
    println(b)
    println(c)
    println(d)
    println(e)
    println(f)
}

private fun <T> bfs(root: Vertex<T>) {
    val queue = LinkedList<Vertex<T>>()
    root.state = State.VISITING
    root.distance = 0
    queue.add(root)
    while (queue.isNotEmpty()) {
        val actual = queue.remove()
        actual.neighbour.forEach {
            if (it.state == State.NOT_VISITED) {
                it.state = State.VISITING
                it.distance = actual.distance + 1
                queue.add(it)
            }
        }
        actual.state = State.VISITED
    }
}
