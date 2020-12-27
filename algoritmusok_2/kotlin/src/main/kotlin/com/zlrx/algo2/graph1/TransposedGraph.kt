package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.Edge

fun main() {
    val adjList = ArrayList<Edge?>()
    val a = Edge(1, Edge(3));
    val b = Edge(3, Edge(4));
    val c = Edge(0);
    val e = Edge(1, Edge(2));
    adjList.add(a)
    adjList.add(b)
    adjList.add(c)
    adjList.add(null)
    adjList.add(e)
    val result = transpose(adjList)
    printAdjList(result)
}

private fun transpose(adjList: List<Edge?>): List<Edge?> {
    val result = ArrayList<Edge?>()
    for (i in adjList.indices) {
        result.add(null)
    }
    adjList.withIndex().reversed().forEach { (i, v) ->
        var edge = v
        while (edge != null) {
            val u = edge.value
            val q = Edge(i)
            if (result[u] != null) {
                q.next = result[u]
            }
            result[u] = q
            edge = edge.next
        }
    }
    return result
}
