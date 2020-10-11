package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.Edge

fun main() {
    val edgeList = arrayListOf(
        Edge(1, Edge(3)),
        Edge(3, Edge(4)),
        Edge(0),
        null,
        Edge(1, Edge(2))
    )

    val result = toG2(edgeList)
    printEdgeList(result)
}

private fun toG2(edgeList: List<Edge?>): List<Edge?> {
    val result = ArrayList<Edge?>()
    for (i in edgeList.indices) {
        result.add(null)
    }
    edgeList.withIndex().forEach { (i, e) ->
        var edge = e
        while (edge != null) {
            var g2edge = edgeList[edge.value]
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
