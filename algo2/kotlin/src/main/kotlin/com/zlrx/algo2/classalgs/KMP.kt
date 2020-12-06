package com.zlrx.algo2.classalgs

fun main() {
    //val text = "-ABABABACA".toCharArray().map { it.toString() }
    //val pattern = "-ABABAC".toCharArray().map { it.toString() }


  //  val text = "-ABABABABBABABABBAB".toCharArray().map { it.toString() }
  //  val pattern = "-BABABBAB".toCharArray().map { it.toString() }


    val text = "-ABABABAABABAABABABAABABBABA".toCharArray().map { it.toString() }
    val pattern = "-BABAABAB".toCharArray().map { it.toString() }

    val kpm = KMP(text, pattern)
    kpm.kmp()
}


class KMP(val text: List<String>, val pattern: List<String>) {

    private val next = Array(pattern.size) { 0 }
    private val s = mutableListOf<Int>()
    private var tmp = Array<String?>(pattern.size) { null }

    fun kmp() {
        init()
        run()
    }

    private fun run() {
        var compares=0
        tmp = Array(text.size) { null }
        printKmpHeader()
        var i = 0
        var j = 0
        var cpc = 1
        while (i < text.size - 1) {
            tmp[i+1]=pattern[j+1]
            compares++
            if (text[i + 1] == pattern[j + 1]) {
                i++
                j++
                if (j == pattern.size - 1) {
                    printKmpRound((i + 1 - pattern.size).toString(), cpc)
                    cpc = 0
                    s.add(i + 1 - pattern.size)
                    j = next[pattern.size - 1]
                    for(k in 0 .. (i-j)){
                        tmp[k]=null
                    }
                }

            } else if (j == 0) {
                printKmpRound("  ", cpc)
                tmp= Array(text.size) { null }
                cpc = 0
                i++
            } else {
                printKmpRound("  ", cpc)
                j = next[j]
                for(k in 0 .. (i-j)){
                    tmp[k]=null
                }
                cpc = 0

            }
            cpc++

        }
        printKmpRound("  ", cpc-1)
        printKmpResult()
        println("Compares count: $compares")
    }

    private fun printKmpRound(s: String, compareCount: Int) {
        if(s!="  " &&s.toInt()<10){
            print(" ")
        }
        print(" $s |    $compareCount     |")
        for (i in 1 until tmp.size) {
            val c = tmp[i]
            if (c == null) {
                print("   |")
            } else {
                print(" $c |")
            }
        }
        println()
    }

    private fun printKmpHeader() {
        println()
        println("KMP rounds")
        print(" s  | comp.cnt |")
        for (i in 1 until text.size) {
            print(" ${text[i]} |")
        }
        println()
        repeat(17 + (text.size - 1) * 4) {
            print("-")
        }
        println()
    }


    private fun printKmpResult() {
        println()
        println("Result: ")
        print("S = {")
        for (et in s) {
            print("$et, ")
        }
        print("}")
        println()
    }


    private fun init() {
        next[1] = 0;
        var i = 0;
        var j = 1;
        printInitHeader()

        while (j < pattern.size - 1) {
            tmp[j + 1] = pattern[i + 1]
            when {
                pattern[i + 1] == pattern[j + 1] -> {

                    printInit(i, j)
                    j++;
                    i++;
                    next[j] = i
                }
                i == 0 -> {
                    printInit(i, j)
                    j++
                    next[j] = 0
                    tmp = Array(pattern.size) { null }
                }
                else -> {
                    printInit(i, j)
                    i = next[i]
                    for (k in 0..(j - i)) {
                        tmp[k] = null
                    }
                }
            }

        }
        printInit(i, j)
        printInitResult()
    }

    private fun printInitResult() {
        println()
        println("Init result")
        print("j          =|")
        for (i in 1 until pattern.size) {
            print(" $i |")
        }
        println()
        print("Pattern[j] =|")
        for (i in 1 until pattern.size) {
            print(" ${pattern[i]} |")
        }

        println()
        print("next[j]    =|")
        for (i in 1 until next.size) {
            print(" ${next[i]} |")
        }
        println()
    }

    private fun printInitHeader() {
        println("Init process")
        print(" i | j | next |")

        for (i in 1 until pattern.size) {
            val c = pattern[i]
            print(" $c |")
        }
        println()
        repeat(16 + (pattern.size - 1) * 4) {
            print("-")
        }
        println()
    }

    private fun printInit(i: Int, j: Int) {
        print(" $i | $j |   ${next[j]}  |")

        for (i in 1 until tmp.size) {
            val c = tmp[i]
            if (c == null) {
                print("   |")
            } else {
                print(" $c |")
            }
        }

        println()
    }


}