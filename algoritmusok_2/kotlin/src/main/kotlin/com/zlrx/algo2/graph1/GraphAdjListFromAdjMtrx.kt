package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.Edge

fun main() {
    val matrix = arrayListOf<List<Int>>(
        arrayListOf(0, 1, 0, 1, 0),
        arrayListOf(0, 0, 0, 1, 1),
        arrayListOf(1, 0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0, 0),
        arrayListOf(0, 1, 1, 0, 0)
    )
    printAdjList(toAdjacencyMatrix(matrix))
}

private fun toAdjacencyMatrix(matrix: List<List<Int>>): List<Edge?> {
    val adjList = ArrayList<Edge?>()
    for (i in 1..matrix.size) {
        adjList.add(null)
    }

    matrix.withIndex().forEach { (index, row) ->
        row.withIndex().reversed().forEach { (j, value) ->
            if (value != 0) {
                val edge = Edge(j)
                if (adjList[index] != null) {
                    edge.next = adjList[index]
                }
                adjList[index] = edge
            }
        }
    }
    return adjList
}
