package com.zlrx.algo2.graph1

fun main() {

    val a = Vertex("a")
    val b = Vertex("b")
    val c = Vertex("c")
    val d = Vertex("d")
    val e = Vertex("e")
    val f = Vertex("f")

    a.addNeighbour(b)
    a.addNeighbour(c)
    b.addNeighbour(d)
    c.addNeighbour(e)
    e.addNeighbour(f)
    f.addNeighbour(c)

    detectCycle(a)
}

private fun <T> detectCycle(node: Vertex<T>) {
    node.state = State.VISITING
    println("Visiting $node")
    node.neighbour.forEach {
        if (it.state == State.VISITING) {
            println("Cycle detected when visiting $it from $node")
            return
        }
        if (it.state == State.NOT_VISITED) {
            detectCycle(it)
        }
    }
    node.state = State.VISITED
    println("Finish visiting $node")
}
