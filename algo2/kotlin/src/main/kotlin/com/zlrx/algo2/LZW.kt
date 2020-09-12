package com.zlrx.algo2

fun main() {
    val plainText = "ABABABAACAACCBBAAAAAAAAA"
    val codeTable = mapOf("A" to 1, "B" to 2, "C" to 3)
    val lzwCoder = LZWCoder(plainText, codeTable.toMutableMap())
    val code = lzwCoder.code()
    println(code)
    val revertedCodeTable = codeTable.entries.associate { (k, v) -> v to k }
    val lzwDecoder = LZWDecoder(code, revertedCodeTable.toMutableMap())
    println(lzwDecoder.decode())

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
            while (codeTable.contains(nextString) && charList.size > nextIndex + 1) {
                nextString += charList[++nextIndex]
            }
            if (!codeTable.contains(nextString)) {
                nextIndexValue++
                codeTable[nextString] = nextIndexValue
            } else {
                ++nextIndex
            }
            val codableStringFragment = plainText.substring(actualIndex, nextIndex)
            actualIndex = nextIndex
            result += codeTable[codableStringFragment]
            result += " "
        }

        return result;
    }


}

class LZWDecoder(
    private val coded: String,
    private val codeTable: MutableMap<Int, String>
) {


    fun decode(): String {
        val codes = coded.split(" ")
            .filter { it.isNotBlank() }
            .map { it -> Integer.parseInt(it) }
        var result = codeTable[codes[0]]!!
        var elementIndex = 1
        var nextIndexValue = codeTable.maxOf { it.key }
        while (elementIndex < codes.size) {
            val searchIndex = result.length - codeTable[codes[elementIndex - 1]]!!.length
            var notFound = false
            result += if (codeTable.containsKey(codes[elementIndex])) {
                codeTable[codes[elementIndex]]
            } else {
                notFound = true
                codeTable[codes[elementIndex - 1]]
            }
            var newCode = result[searchIndex].toString()
            var nextSearchIndex = searchIndex
            while (inCodeTable(newCode) && nextSearchIndex - 1 < result.length) {
                newCode += result[++nextSearchIndex].toString()
            }
            codeTable[++nextIndexValue] = newCode

            if (notFound) {
                result = result.substring(0, result.length - codeTable[codes[elementIndex - 1]]!!.length)
                result += codeTable[codes[elementIndex]]
            }

            elementIndex++
        }


        return result
    }

    private fun inCodeTable(str: String) = codeTable.values.contains(str)


}