package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.Edge

fun main() {
    val edgeList = ArrayList<Edge?>()
    val a = Edge(1, Edge(3));
    val b = Edge(3, Edge(4));
    val c = Edge(0);
    val e = Edge(1, Edge(2));
    edgeList.add(a)
    edgeList.add(b)
    edgeList.add(c)
    edgeList.add(null)
    edgeList.add(e)
    val result = transpose(edgeList)
    printEdgeList(result)
}

private fun transpose(edgeList: List<Edge?>): List<Edge?> {
    val result = ArrayList<Edge?>()
    for (i in edgeList.indices) {
        result.add(null)
    }
    edgeList.withIndex().reversed().forEach { (i, v) ->
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
