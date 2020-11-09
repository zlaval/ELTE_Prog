package com.zlrx.algo2.graph2


data class PVertex(
    val vertex: Int,
    val weight: Int,
    val next: PVertex? = null
)

data class PEdge(
    val source: Int,
    val target: Int,
    val weight: Int
)

//infinite
const val I = Integer.MIN_VALUE

fun main() {
    val matrix = arrayOf(
        arrayOf(I, 2, 1, 5, I, I, I),
        arrayOf(2, I, I, 0, 3, I, I),
        arrayOf(1, I, I, 3, I, I, I),
        arrayOf(5, 0, 3, I, 2, 1, 5),
        arrayOf(I, 3, I, 2, I, I, 2),
        arrayOf(I, I, I, 1, I, I, 4),
        arrayOf(I, I, I, 5, 2, 4, I)
    )

    val pam = PrimAdjacencyMatrix(matrix)
    pam.calculateSpanningTree()


    println("****************")

    val adjList: Array<PVertex?> = arrayOf(
        PVertex(1, 2, PVertex(2, 1, PVertex(3, 5))),
        PVertex(0, 2, PVertex(3, 0, PVertex(4, 3))),
        PVertex(0, 1, PVertex(2, 3)),
        PVertex(0, 5, PVertex(1, 0, PVertex(2, 3, PVertex(4, 2, PVertex(5, 1, PVertex(6, 5)))))),
        PVertex(1, 3, PVertex(3, 2, PVertex(6, 2))),
        PVertex(3, 1, PVertex(6, 2)),
        PVertex(3, 5, PVertex(4, 2, PVertex(5, 4)))
    )

    val pal = PrimAdjacencyList(adjList)
    pal.calculateSpannigTree()
}


class PrimAdjacencyList(val adjList: Array<PVertex?>) {

    private val underProcess = mutableListOf<PEdge>()
    private val spTreeEdges = mutableListOf<PEdge>()
    private val unvisitedVertices = Array(adjList.size) { i -> i }

    fun calculateSpannigTree() {
        var cost = 0
        var vertex = 0
        var adj = adjList[vertex]
        adjList[vertex] = null
        unvisitedVertices[vertex] = I
        while (hasUnprocessedElement()) {

            while (adj != null) {
                if (unvisitedVertices[adj.vertex] != I) {
                    underProcess.add(PEdge(vertex, adj.vertex, adj.weight))
                }
                adj = adj.next
            }

            val min = findMinEdge()

            if (unvisitedVertices[min.target] != I) {
                spTreeEdges.add(min)
                cost += min.weight
                vertex = min.target
                unvisitedVertices[vertex] = I
                adj = adjList[vertex]
                adjList[vertex] = null
            }
        }

        printSpanningTree()
        println("Cost: $cost")
    }

    private fun findMinEdge(): PEdge {
        val edge = underProcess.minByOrNull { it.weight }!!
        underProcess.remove(edge)
        return edge
    }


    private fun hasUnprocessedElement() = adjList.any { it != null }

    private fun printSpanningTree() {
        for ((source, target) in spTreeEdges) {
            val s = source + 1
            val t = target + 1
            println("$s --> $t")
        }
    }

}

class PrimAdjacencyMatrix(val adjMatrix: Array<Array<Int>>) {

    private val unvisitedVertices = mutableListOf<Int>()
    private val spTreeEdges = mutableListOf<Pair<Int, Int>>()
    private val underProcess = mutableListOf<PEdge>()

    init {
        for (i in adjMatrix.indices) {
            unvisitedVertices.add(i)
        }
    }


    fun calculateSpanningTree() {
        var cost = 0
        var vertex = 0
        unvisitedVertices[vertex] = I

        while (hasNotProcessed()) {
            for (target in adjMatrix[vertex].indices) {
                val weight = adjMatrix[vertex][target]
                if (weight != I && unvisitedVertices[target] != I) {
                    underProcess.add(PEdge(vertex, target, weight))
                }
            }

            val min = findMinEdge()
            if (unvisitedVertices[min.target] != I) {
                spTreeEdges.add(Pair(min.source, min.target))
                cost += min.weight
                vertex = min.target
                unvisitedVertices[vertex] = I
            }

        }
        printSpanningTree()
        println("Cost: $cost")
    }

    private fun findMinEdge(): PEdge {
        val edge = underProcess.minByOrNull { it.weight }!!
        underProcess.remove(edge)
        return edge
    }


    private fun printSpanningTree() {
        for ((source, target) in spTreeEdges) {
            val s = source + 1
            val t = target + 1
            println("$s --> $t")
        }
    }

    private fun hasNotProcessed() = unvisitedVertices.any { it != I }


}

