package com.zlrx.algo2

fun main() {
    val plainText = "ABABCABABA"
    val codeTable = mutableMapOf("A" to 1, "B" to 2, "C" to 3)
    val lzwCoder = LZWCoder(plainText, codeTable)
    println(lzwCoder.code())

}

class LZWCoder(private val plainText: String, private val codeTable: MutableMap<String, Int>) {

    private val charList = mutableListOf<String>()

    fun code(): String {
        var result = ""
        var nextIndexValue = codeTable.maxOf { it.value }

        plainText.forEach { charList.add(it.toString()) }

        var actualIndex = 0

        while (actualIndex < charList.size - 1) {
            var nextIndex = actualIndex
            var nextString = charList[actualIndex]
            while (codeTable.contains(nextString) && charList.size > nextIndex +1) {
                nextString += charList[++nextIndex]
            }
            if(!codeTable.contains(nextString)) {
                nextIndexValue++
                codeTable[nextString] = nextIndexValue
            }else{
                ++nextIndex
            }
            val codeableStringFragment = plainText.substring(actualIndex, nextIndex)
            actualIndex = nextIndex
            result += codeTable[codeableStringFragment]
            result += " "
        }

        return result;
    }


}

class LZWDecoder(
    private val coded: String,
    private val codeTable: MutableMap<String, Int>
) {

    fun decode(): String {
        return ""
    }


}