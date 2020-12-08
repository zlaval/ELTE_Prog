package com.zlrx.algo2.classalgs

fun main() {
//    val matrix = arrayListOf<List<Int>>(
//        arrayListOf(NA, 5, NA, 4, 2, NA),
//        arrayListOf(NA, NA, 1, 1, 2, NA),
//        arrayListOf(NA, NA, NA, NA, 1, NA),
//        arrayListOf(NA, NA, NA, NA, NA, NA),
//        arrayListOf(NA, NA, NA, 2, NA, 1),
//        arrayListOf(NA, NA, 3, NA, NA, NA)
//    )

    val matrix = arrayListOf<List<Int>>(
        arrayListOf(NA, 1, NA, NA, 3, NA, NA),
        arrayListOf(NA, NA, 3, NA, 1, NA, NA),
        arrayListOf(NA, NA, NA, 2, NA, 6, NA),
        arrayListOf(NA, NA, NA, NA, NA, NA, 1),
        arrayListOf(NA, NA, NA, NA, NA, NA, NA),
        arrayListOf(NA, 2, NA, NA, 2, NA, NA),
        arrayListOf(NA, NA, 1, NA, NA, 1, NA),
    )

    val dijkstra = Dijkstra(matrix, 0)
    dijkstra.calculatePath()
}


class Dijkstra(val matrix: List<List<Int>>, val start: Int, val useChars: Boolean = true) {

    private val d = Array(matrix.size) { NA }
    private val pi = Array(matrix.size) { NA }
    private var q = mutableListOf<Int>()

    fun calculatePath() {
        printHeader()
        d[start] = 0
        pi[start] = -52
        q.add(start)
        while (q.isNotEmpty()) {
            val u = q.removeMin()
            val row = matrix[u]
            row.forEachIndexed { index, edge ->
                if (edge != NA) {
                    val distance = d[u] + edge
                    if (distance < d[index]) {
                        d[index] = distance
                        pi[index] = u
                        if (!q.contains(index)) {
                            q.add(index)
                        }
                        q.adjust()
                    }
                }
            }
            printRound(" ${mapToChar(u)}:${d[u]} ")
        }
    }

    private fun MutableList<Int>.adjust() {
        q = q.map { Pair(it, d[it]) }
            .sortedBy { it.second }
            .map { it.first }
            .toMutableList()
    }

    private fun MutableList<Int>.removeMin(): Int {
        val min = this[0]
        this.removeAt(0)
        return min
    }

    private fun printHeader() {
        println("            distQ                           pi  ")
        for (i in matrix.indices) {
            print(" ${mapToChar(i)} |")
        }
        print(" || ")
        for (i in matrix.indices) {
            print(" ${mapToChar(i)} |")
        }
        print(" || ")
        print("  iter.d ")
        print(" || ")
        print(" queue  ")
        println()
        println("-------------------------------------------------------------------------------------------------------")
    }

    private fun printRound(str: String) {

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
        print("<")
        for (element in q) {
            print("${mapToChar(element)}, ")
        }
        print(">")
        println()
    }

    private fun mapToChar(value: Int): String =
        if (useChars) {
            val asci = value + 97
            asci.toChar().toString()
        } else {
                val v=value+1
            if(v==-51){
                "-"
            }else{
                v.toString()
            }

        }

}


