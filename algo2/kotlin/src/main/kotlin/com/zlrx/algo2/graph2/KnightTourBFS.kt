package com.zlrx.algo2.graph2

import java.util.*

const val boardSizeX = 8
const val boardSizeY = 8
val xMoves = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
val yMoves = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)


fun main() {
    val knightTour = KnightTour(Pair(1, 3), Pair(6, 6))
    val result = knightTour.solve()
    println(result)

}

class KnightTour(val knightPlace: Pair<Int, Int>, val finish: Pair<Int, Int>) {

    private val board: Array<Array<Int>> = Array(boardSizeX) { Array(boardSizeY) { -1 } }


    fun solve(): Int {

        val queue = LinkedList<Pair<Int, Int>>()
        board[knightPlace.first][knightPlace.second] = 0
        queue.add(Pair(knightPlace.first, knightPlace.second))
        while (queue.isNotEmpty()) {
            val actual = queue.remove()
            for (i in 0 until 8) {
                val nextX = actual.first + xMoves[i]
                val nextY = actual.second + yMoves[i]
                if (isStepValid(nextX, nextY)) {
                    if (board[nextX][nextY] == -1) {
                        board[nextX][nextY] = board[actual.first][actual.second] + 1
                        queue.add(Pair(nextX, nextY))
                    }
                }
            }
        }
        printBoard()
        return board[finish.first][finish.second]
    }

    private fun printBoard() {
        board.withIndex().forEach { (i, arr) ->
            arr.withIndex().forEach { (j, v) ->
                if (i == finish.first && j == finish.second) {
                    print(" >$v< ")
                } else {
                    print("  $v  ")
                }
            }
            print(System.lineSeparator())
        }

    }

    private fun isStepValid(nextX: Int, nextY: Int): Boolean {
        if (nextX < 0 || nextX >= boardSizeX) {
            return false
        }
        if (nextY < 0 || nextY >= boardSizeY) {
            return false
        }
        return true
    }

}