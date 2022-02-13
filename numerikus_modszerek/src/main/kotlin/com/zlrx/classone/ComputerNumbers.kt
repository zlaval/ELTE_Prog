package com.zlrx.classone

import java.lang.Integer.min
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
    computerNumbers.calculateSmallest(6, -3, 3)
    computerNumbers.calculateLargest(6, -3, 3)
    computerNumbers.calculateFlInt(137, 6, -10, 10)
    computerNumbers.calculateFlFraction(17, 8, -5, 5)
    computerNumbers.calculateFlFraction(167, 8, -5, 5)
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

    //(1-2^-k+)*2^k+
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

    fun calculateFlInt(number: Int, t: Int, km: Int, kp: Int) {
        //Integer.toBinaryString
        var num = number
        var result = ""
        while (num > 0) {
            val mod = num % 2
            num = (num - mod) / 2
            result += mod
        }
        result = result.reversed()
        val characteristic = min(result.length, kp)
        val roundDigit = result.substring(t until t + 1)

        val digits = if (roundDigit == "1") {
            val value = result.substring(0 until t)
            val intVal = Integer.parseInt(value, 2)
            val strVal = Integer.toBinaryString(intVal + 1)
            strVal.substring(0 until t)
        } else {
            result.substring(0 until t)
        }

        val resultBinStr = digits + (0 until (characteristic - digits.length)).joinToString("") { "0" }
        val resultInt = Integer.parseInt(resultBinStr, 2)
        println("[$digits | $characteristic] = $resultInt")
    }

    fun calculateFlFraction(fraction: Int, t: Int, km: Int, kp: Int) {
        val barrier = ("1" + (0 until fraction.toString().length).joinToString("") { "0" }).toInt()

        var significantBitCount = 0
        var bitCount = 0
        var number = fraction
        var result = ""
        while (significantBitCount < t && number != 0) {
            number *= 2
            if (number > barrier) {
                result += "1"
                significantBitCount++
                number -= barrier
            } else {
                result += "0"
                if(significantBitCount>0){
                    significantBitCount++
                }
            }
            bitCount++

        }

        val c = -1 * (bitCount - significantBitCount)
        val significantBits = result.substring(bitCount - significantBitCount)

        println("fl(0.$fraction) = [$significantBits | $c]")

    }

}

