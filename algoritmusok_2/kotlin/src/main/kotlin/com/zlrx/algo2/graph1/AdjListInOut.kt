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
    val result = calculateInOut(adjList)
    printResult(result)
}

private fun calculateInOut(adjList: List<Edge?>): Pair<List<Int>, List<Int>> {
    val inEdge = ArrayList<Int>()
    val outEdge = ArrayList<Int>()
    for (i in adjList.indices) {
        inEdge.add(0)
        outEdge.add(0)
    }

    adjList.withIndex().forEach { (index, it) ->
        var edge = it
        while (edge != null) {
            val u = edge.value
            inEdge[u]++
            outEdge[index]++
            edge = edge.next
        }
    }

    return Pair(inEdge, outEdge)
}

private fun printResult(result: Pair<List<Int>, List<Int>>) {
    println("In edges")
    result.first.withIndex().forEach { (i, v) ->
        println("${i + 1}: $v")
    }

    println("out edges")
    result.second.withIndex().forEach { (i, v) ->
        println("${i + 1}: $v")
    }
}