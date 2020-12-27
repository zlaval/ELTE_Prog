package com.zlrx.algo2.graph1

fun main() {
    val matrix = arrayOf(
        arrayOf(0, 1, 1, 1, 0, 1),
        arrayOf(0, 0, 1, 1, 1, 1),
        arrayOf(1, 1, 0, 1, 1, 0),
        arrayOf(0, 0, 0, 0, 0, 0),
        arrayOf(0, 1, 1, 1, 0, 1),
        arrayOf(0, 1, 1, 1, 0, 0)
    )
    println(findUSink(matrix) + 1)
}

private fun findUSink(matrix: Array<Array<Int>>): Int {
    var i = 0
    var j = 0
    while (i < matrix.size && j < matrix.size) {
        val element = matrix[i][j]
        if (element == 1 || i == j) {
            i++
        } else {
            j++
            i = 0
        }
        if (i == matrix.size) {
            var k = 0
            var zeroRow = true
            while (k < matrix.size) {
                if (matrix[j][k] == 1) {
                    zeroRow = false
                }
                k++
            }
            if (zeroRow) {
                return j
            }
        }
    }
    return -2
}



