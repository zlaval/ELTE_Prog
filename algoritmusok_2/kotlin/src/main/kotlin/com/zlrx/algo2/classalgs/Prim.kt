package com.zlrx.algo2.classalgs

const val Inf = Integer.MIN_VALUE

fun main() {
    val matrix = arrayOf(
        arrayOf(Inf, 6, 1, Inf, Inf, Inf),
        arrayOf(6, Inf, 4, 5, 2, 3),
        arrayOf(1, 4, Inf, 3, 1, Inf),
        arrayOf(Inf, 6, 3, Inf, 1, 6),
        arrayOf(Inf, 2, 1, 1, Inf, 5),
        arrayOf(Inf, 3, Inf, 6, 5, Inf),

        )

    val pam = Prim(matrix, false)
    pam.calculateSpanningTree()
}


data class PEdge(
    val source: Int,
    val target: Int,
    val weight: Int
)


class Prim(val adjMatrix: Array<Array<Int>>, val useChars: Boolean = false) {

    private val unvisitedVertices = mutableListOf<Int>()
    private val spTreeEdges = mutableListOf<Pair<Int, Int>>()
    private val underProcess = mutableListOf<PEdge>()

    private var edgeWeightArray = Array<Int?>(adjMatrix.size) { null }

    init {
        for (i in adjMatrix.indices) {
            unvisitedVertices.add(i)
        }
    }


    fun calculateSpanningTree() {
        printHeader()
        var cost = 0
        var vertex = 0
        unvisitedVertices[vertex] = Inf

        while (hasNotProcessed()) {

            for (target in adjMatrix[vertex].indices) {
                val weight = adjMatrix[vertex][target]
                if (weight != Inf && unvisitedVertices[target] != Inf) {
                    val minInTarget = underProcess.filter { it.target == target }.map { it.weight }.firstOrNull()
                    underProcess.add(PEdge(vertex, target, weight))
                    if (minInTarget == null || weight < minInTarget) {
                        edgeWeightArray[target] = weight
                    }
                }
            }

            val min = findMinEdge()
            if (unvisitedVertices[min.target] != Inf) {
                printRound(vertex)
                spTreeEdges.add(Pair(min.source, min.target))
                cost += min.weight
                vertex = min.target
                unvisitedVertices[vertex] = Inf
            }
        }
        printRound(vertex)
        printSpanningTree()
        println("Cost: $cost")
    }

    private fun printRound(vertex: Int) {
        print("        ${mapToChar(vertex)}       |")


        edgeWeightArray.forEach {
            if (it == null || it == Inf) {
                print("   |")
            } else {
                print(" $it |")
            }
        }


        var queuedNodes = ""
        for (i in 0 until unvisitedVertices.size) {
            if (unvisitedVertices[i] != Inf)
                queuedNodes += mapToChar(i) + " "
        }
        while (queuedNodes.length < 28) {
            queuedNodes += " "
        }

        print("<$queuedNodes>|")
        //TODO queue size

        edgeWeightArray.forEach {
            if (it == null || it == Inf) {
                print("   |")
            } else {
                print(" ${mapToChar(vertex)} |")
            }
        }
        edgeWeightArray = Array(adjMatrix.size) { null }
        println()
    }


    private fun findMinEdge(): PEdge {
        val edge = underProcess.minByOrNull { it.weight }!!
        underProcess.remove(edge)
        return edge
    }

    private fun printHeader() {
        println()

        print(" Processed edge |")
        for (i in adjMatrix.indices) {
            print(" ${mapToChar(i)} |")
        }

        print("            Queue            |")
        for (i in adjMatrix.indices) {
            print(" ${mapToChar(i)} |")
        }
        println()
        repeat(47 + (adjMatrix.size) * 8) {
            print("-")
        }
        println()
    }


    private fun printSpanningTree() {
        println("Route")
        println()
        for ((source, target) in spTreeEdges) {
            // val s = source + 1
            //val t = target + 1
            println("${mapToChar(source)} --> ${mapToChar(target)}")
        }
        println()
    }

    private fun hasNotProcessed() = unvisitedVertices.any { it != Inf }

    private fun mapToChar(value: Int): String =
        if (useChars) {
            val asci = value + 97
            asci.toChar().toString()
        } else {
            (value + 1).toString()
        }

}