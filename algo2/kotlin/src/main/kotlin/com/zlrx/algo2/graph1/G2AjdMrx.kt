package com.zlrx.algo2.graph1

fun main() {
    val matrix = arrayOf(
        arrayOf(0, 1, 0, 1, 0),
        arrayOf(0, 0, 0, 1, 1),
        arrayOf(1, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0),
        arrayOf(0, 1, 1, 0, 0)
    )

    val result = toG2(matrix)
    printResult(result)

}

private fun toG2(matrix: Array<Array<Int>>): Array<Array<Int>> {
    val result = Array(matrix.size) { Array(matrix.size) { 0 } }
    matrix.withIndex().forEach { (i, row) ->
        row.withIndex().forEach { (j, value) ->
            if (value == 1) {
                matrix[j].withIndex().forEach { (k, v) ->
                    if (v == 1 && i!=k) {
                        result[i][k] = 1
                    }
                }
            }
        }
    }
    return result
}


private fun printResult(adjMtx: Array<Array<Int>>) {
    adjMtx.forEach { row ->
        row.forEach {
            print("$it ")
        }
        print(System.lineSeparator())
    }
}