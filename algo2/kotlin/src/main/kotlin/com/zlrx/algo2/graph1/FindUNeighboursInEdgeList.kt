package com.zlrx.algo2.graph1

fun main() {
    val edgeList = arrayListOf(
        Edge(1, Edge(3)),
        Edge(0, Edge(3)),
        Edge(3),
        Edge(0, Edge(1, Edge(2)))
    )

    findNeighboursOf(2, edgeList)
}

private fun findNeighboursOf(u: Int, edgeList: List<Edge?>) {
    edgeList.withIndex().forEach { (i, e) ->
        var edge = e
        while (edge != null && edge.value < u) {
            edge = edge.next
        }
        if (edge != null && edge.value == u) {
            println(i+1)
        }
    }

}