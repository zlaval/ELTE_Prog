package com.zlrx.classone

import kotlin.math.abs
import kotlin.math.pow

//  m*2^k
//  0.1___ *2K
//  ||m|| = t
// m=m1...mt
// k-<=k<=k+
// M=M(t,k-,k+) = 2^k * sum(mi *2^-i) U {0}
//m1=1
// M(1,-2,3) => +- 0.1___*2^k

fun main() {
    val computerNumbers = ComputerNumbers()
    computerNumbers.calculateSmallest(6, -10, 10)
    computerNumbers.calculateLargest(6, -10, 10)
}


class ComputerNumbers {


    fun calculateSmallest(t: Int, km: Int, kp: Int) {
        val abspow = 2.0.pow(abs(km))
        if (km < 0) {
            println("1/${2 * abspow}")
        } else {
            println("${abspow}/2")
        }
    }

    fun calculateLargest(t: Int, km: Int, kp: Int) {
        val abspow = 2.0.pow(abs(kp))

        val maxDivider = 2.0.pow(t).toInt()

        val dividend = (0 until t).sumOf { 2.0.pow(it) }

        if (kp < 0) {
            println("$dividend / ${maxDivider * abspow}")
        } else {
            println("${dividend * abspow} / $maxDivider")
        }

        val realResult = (dividend / maxDivider) * (2.0.pow(kp))
        println("Real result: $realResult")

    }

}

