package com.zlrx.algo2.classalgs


const val NA = Integer.MAX_VALUE

fun main() {

//    val matrix = arrayListOf<List<Int>>(
//        arrayListOf(NA, 2, 4, NA, NA, NA, NA),
//        arrayListOf(NA, NA, NA, -1, 0, NA, NA),
//        arrayListOf(NA, 2, NA, NA, NA, NA, NA),
//        arrayListOf(NA, NA, 1, NA, 2, 3, NA),
//        arrayListOf(NA, NA, NA, NA, NA, 1, 3),
//        arrayListOf(NA, NA, NA, NA, NA, NA, -2),
//        arrayListOf(NA, NA, NA, NA, NA, NA, NA)
//    )

    val matrix = arrayListOf<List<Int>>(
        arrayListOf(NA, 3, NA, 6, 2, NA),
        arrayListOf(NA, NA, 1, NA, NA, NA),
        arrayListOf(NA, NA, NA, 1, NA, NA),
        arrayListOf(NA, -2, NA, NA, 1, NA),
        arrayListOf(NA, -2, NA, NA, NA, 3),
        arrayListOf(1, NA, NA, NA, NA, NA)
    )

    val bf = BellmanFord(matrix, 0)
    bf.computePath()
}


class BellmanFord(private val matrix: List<List<Int>>, private val start: Int, val userChar: Boolean = true) {

    private val d = Array(matrix.size) { NA }
    private val pi = Array(matrix.size) { NA }
    private val q = mutableListOf<Int>()
    private var iteration = 0
    private var iterationElements = 1
    private var nextIterationElements = 0
    fun computePath() {
        d[start] = 0
        pi[start] = -52
        q.add(start)
        printHeader()
        while (q.isNotEmpty()) {
            val u = q.removeAt(0)
            val row = matrix[u]
            iterationElements--
            row.forEachIndexed { index, edge ->
                if (edge != NA) {
                    val pathWeightToEdge = d[u] + edge
                    if (d[index] > pathWeightToEdge) {
                        d[index] = pathWeightToEdge
                        pi[index] = u
                        if (!q.contains(index)) {
                            nextIterationElements++
                            q.add(index)
                        }
                    }
                }
            }
            printCycle(" ${mapToChar(u)}:${d[u]}:${iteration} ")
            if (iterationElements == 0) {
                iterationElements = nextIterationElements
                nextIterationElements = 0
                iteration++
            }
        }
    }

    private fun printHeader() {
        println("            dist                            pi  ")
        for (i in matrix.indices) {
            print(" ${mapToChar(i)} |")
        }
        print(" || ")
        for (i in matrix.indices) {
            print(" ${mapToChar(i)} |")
        }
        print(" || ")
        print("  iter.  ")
        print(" || ")
        print(" exp")
        print(" || ")
        print(" queue  ")
        println()
        println("-------------------------------------------------------------------------------------------------------")
    }

    private fun printCycle(str: String) {

        for (weight in d) {
            if (weight == NA) {
                print(" ∞ |")
            } else {
                print(" $weight |")
            }
        }
        print(" || ")
        for (parent in pi) {
            if (parent == NA) {
                print(" ∞ |")
            } else {
                print(" ${mapToChar(parent)} |")
            }

        }
        print(" || ")
        print(" $str ")
        print(" || ")
        print(" $iteration ")
        print(" || ")
        print("<")
        for (element in q) {
            print("$element, ")
        }
        print(">")
        println()
    }


    fun mapToChar(value: Int): String =
        if (userChar) {
            val asci = value + 97
            asci.toChar().toString()
        } else {
            value.toString()
        }


}