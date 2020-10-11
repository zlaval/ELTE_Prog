package com.zlrx.algo2.graph1

import com.zlrx.algo2.graph1.model.Edge

fun printEdgeList(edgeList: List<Edge?>) {
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

fun printResult(adjMtx: Array<Array<Int>>) {
    adjMtx.forEach { row ->
        row.forEach {
            print("$it ")
        }
        print(System.lineSeparator())
    }
}