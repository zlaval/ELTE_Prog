package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.Edge

fun main() {
    val adjList = arrayListOf(
        Edge(1, Edge(3)),
        Edge(3, Edge(4)),
        Edge(0),
        null,
        Edge(1, Edge(2))
    )

    val result = toG2(adjList)
    printAdjList(result)
}

private fun toG2(adjList: List<Edge?>): List<Edge?> {
    val result = ArrayList<Edge?>()
    for (i in adjList.indices) {
        result.add(null)
    }
    adjList.withIndex().forEach { (i, e) ->
        var edge = e
        while (edge != null) {
            var g2edge = adjList[edge.value]
            while (g2edge != null) {
                if (g2edge.value != i) {
                    val newEdge = Edge(g2edge.value)
                    if (result[i] != null) {
                        newEdge.next = result[i]
                    }
                    result[i] = newEdge
                }
                g2edge = g2edge.next
            }
            edge = edge.next
        }

    }
    return result
}
