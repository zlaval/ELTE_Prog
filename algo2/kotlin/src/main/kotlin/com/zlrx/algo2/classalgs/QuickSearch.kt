package com.zlrx.algo2.classalgs


fun main() {
    val text = "-ABDAEBBCBBCABCBABCA".toCharArray().map { it.toString() }
    val pattern = "-ABCA".toCharArray().map { it.toString() }
    val quickSearch = QuickSearch(text, pattern)
    quickSearch.run()
}


class QuickSearch constructor(
    private val text: List<String>, private val pattern: List<String>
) {
    private val m: Int = pattern.size - 1
    private val n: Int = text.size - 1

    private val dictionary: LinkedHashMap<String, Int> = LinkedHashMap()
    private val sh = mutableListOf<Int>()

    fun run() {
        buildDictionary()
        search()
        printResult()
    }

    private fun search() {
        printHeader()
        var s = 0
        while (s + m <= n) {
            printProcessRound(s)
            if (match(s)) {
                sh.add(s)
            }
            if (s + m < n) {
                val shift = dictionary[text[s + m + 1]]!!
                s += shift
            } else {
                return
            }
        }
    }

    private fun printProcessRound(s:Int){
        var k=1
        for(i in 1 ..n){
            if(i > (s+m)||i<=s ) {
                print("    |")
            }else{
                print(" ${pattern[k++]}  |")
            }
        }
        println()
    }

    private fun printHeader(){
        println()
        println("Search table:")
        for(i in 1 .. n){
            print(" ${text[i]}  |")
        }
        println()
        for(i in 1 .. n){
            if(i<10){
                print(" $i  |")
            }else{
                print(" $i |")
            }
        }
        println()
        repeat( (text.size-1) * 5) {
            print("-")
        }


        println()
    }

    private fun printResult(){
        println()
        println("Result:")
        print("{ ")
        sh.forEach {
            print("$it, ")
        }
        print(" } ")
        println()
    }

    private fun match(s: Int): Boolean {
        var result = true
        var k = 1
        for (i in s + 1..s + m) {
            if (text[i] != pattern[k]) {
                result = false
            }
            k++
        }
        return result
    }

    private fun buildDictionary() {
        val letters = text.distinct().sorted()
        for (i in 1 until letters.size) {
            val letter = letters[i]
            dictionary[letter] = pattern.size
        }
        printDictionaryHeader()
        for (i in 1 until pattern.size) {
            val letter = pattern[i]
            dictionary[letter] = pattern.size - i
            printDictionaryProcess(i, letter)
        }
        printDictionaryProcessResult()
    }

    private fun printDictionaryProcess(row: Int, pletter: String) {
        var changeIndex = 0
        dictionary.onEachIndexed { index, (key, _) ->
            if (key == pletter) {
                changeIndex = index + 1
            }
        }
        print("$pletter  $row ||")
        for (i in 1..dictionary.size) {
            var e = " "
            if (i == changeIndex) {
                e = (pattern.size - row).toString()
            }
            print(" $e |")
        }
        println()
    }

    private fun printDictionaryProcessResult() {
        repeat(7 + dictionary.size * 4) {
            print("-")
        }
        println()
        print("shift||")
        for ((_, v) in dictionary) {
            print(" $v |")
        }
        println()
    }


    private fun printDictionaryHeader() {
        println("Dictionary:")
        println()
        print(" o   ||")
        for ((k, _) in dictionary) {
            print(" $k |")
        }
        println()
        print("     ||")
        for ((_, v) in dictionary) {
            print(" $v |")
        }
        println()
        repeat(7 + dictionary.size * 4) {
            print("-")
        }
        println()
    }


}