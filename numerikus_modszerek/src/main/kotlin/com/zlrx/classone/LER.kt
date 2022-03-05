package com.zlrx.classone


fun main() {
    val ler = LER()

    ler.solveLer(
        arrayOf(
            arrayOf(1, 2, -1),
            arrayOf(2, -1, 3),
            arrayOf(-1, 3, 1)
        ),

        arrayOf(4, 3, 6)
    )


}

class LER {


    fun solveLer(matrix: Array<Array<Int>>, vector: Array<Int>) {
        var row = 0;

        while (row < matrix.size - 1) {
            val genRowElement = matrix[row][row]
            for (index in (row + 1) until matrix.size) {
                val matrixRow = matrix[index]
                val matrixRowElement = matrixRow[row]
                val mul = matrixRowElement / genRowElement
                for (i in matrixRow.indices) {
                    matrix[index][i] -= matrix[row][i] * mul
                }
                vector[index] -= vector[row] * mul
            }
            row++
        }


        matrix.forEach { row ->
            row.forEach {
                print("$it ")
            }
            println()
        }

        println("***********************")
        vector.forEach {
            print("$it ")
        }

    }


}