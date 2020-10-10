package com.zlrx.algo2.graph1

fun main() {
    val a = Vertex("a")
    val b = Vertex("b")
    val c = Vertex("c")
    val d = Vertex("d")
    val e = Vertex("e")
    val f = Vertex("f")

    a.addNeighbour(b)
    a.addNeighbour(e)
    b.addNeighbour(c)
    b.addNeighbour(e)
    d.addNeighbour(a)
    e.addNeighbour(c)
    e.addNeighbour(d)
    f.addNeighbour(c)
    f.addNeighbour(e)

    dfs(a)
    println(a)
    println(b)
    println(c)
    println(d)
    println(e)
    println(f)

}

fun <T> dfs(root: Vertex<T>) {
    fun <T> dfsInternal(node: Vertex<T>) {
        node.neighbour.forEach {
            if (it.state == State.NOT_VISITED) {
                it.state = State.VISITING
                it.distance = node.distance + 1
                dfsInternal(it)
            }
        }
        node.state = State.VISITED
    }
    root.state = State.VISITING
    root.distance = 0
    dfsInternal(root)
}

