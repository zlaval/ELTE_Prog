package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.Edge

fun main() {
    val adjList = arrayListOf(
        Edge(1, Edge(3)),
        Edge(0, Edge(3)),
        Edge(3),
        Edge(0, Edge(1, Edge(2)))
    )

    findNeighboursOf(2, adjList)
}

private fun findNeighboursOf(u: Int, adjList: List<Edge?>) {
    adjList.withIndex().forEach { (i, e) ->
        var edge = e
        while (edge != null && edge.value < u) {
            edge = edge.next
        }
        if (edge != null && edge.value == u) {
            println(i+1)
        }
    }

}