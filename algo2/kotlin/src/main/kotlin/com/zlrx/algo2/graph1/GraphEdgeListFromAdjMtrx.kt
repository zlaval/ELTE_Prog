package com.zlrx.algo2.graph1

fun main() {
    val matrix = arrayListOf<List<Int>>(
        arrayListOf(0, 1, 0, 1, 0),
        arrayListOf(0, 0, 0, 1, 1),
        arrayListOf(1, 0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0, 0),
        arrayListOf(0, 1, 1, 0, 0)
    )
    printEdgeList(toAdjacencyMatrix(matrix))
}

private fun toAdjacencyMatrix(matrix: List<List<Int>>): List<Edge?> {
    val edgeList = ArrayList<Edge?>()
    for (i in 1..matrix.size) {
        edgeList.add(null)
    }

    matrix.withIndex().forEach { (index, row) ->
        row.withIndex().reversed().forEach { (j, value) ->
            if (value != 0) {
                val edge = Edge(j)
                if (edgeList[index] != null) {
                    edge.next = edgeList[index]
                }
                edgeList[index] = edge
            }
        }
    }
    return edgeList
}

private fun printEdgeList(edgeList: List<Edge?>) {
    edgeList.withIndex().forEach { (i, value) ->
        var edge = value
        print("${i + 1} -> ")
        while (edge != null) {
            print("${edge.value + 1}")
            if (edge.next != null) {
                print(" -> ")
            }
            edge = edge.next
        }
        println()
    }
}



