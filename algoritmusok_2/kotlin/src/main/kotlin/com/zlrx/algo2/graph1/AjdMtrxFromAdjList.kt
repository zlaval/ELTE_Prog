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
    val result = transform(adjList)
    printResult(result)
}

fun transform(adjList: List<Edge?>): Array<Array<Int>> {
    val array = Array(adjList.size) { Array(adjList.size) { 0 } }
    adjList.withIndex().forEach { (i, e) ->
        var edge = e
        while (edge != null) {
            array[i][edge.value] = 1
            edge = edge.next
        }
    }
    return array
}
