package com.zlrx.algo2

import com.zlrx.algo2.helper.TreePrinter
import com.zlrx.algo2.model.Node
import java.util.*
import kotlin.collections.HashMap

fun main() {
    println("Give me a sentence: ")
    val str = readLine()
    if (str != null) {
        val huffmanEncode = HuffmanEncode(str)
        val code = huffmanEncode.calculateCode()
        println(code.first)

        val huffmanDecode = HuffmanDecode(code.first, code.second)
        val decoded = huffmanDecode.decode()
        println(decoded)
    } else {
        println(":(")
    }
}

abstract class HuffMan {

    protected val codeTable = HashMap<String, String>()

    protected fun buildCodeTable(parent: Node) {
        fun traverse(accumulator: String, node: Node?) {
            if (node == null) {
                return
            }
            if (node.left == null && node.right == null) {
                codeTable[node.data!!] = accumulator
            }
            traverse(accumulator + "0", node.left)
            traverse(accumulator + "1", node.right)
        }
        traverse("", parent);
    }
}

class HuffmanDecode(private val encoded: String, private val root: Node) : HuffMan() {

    private lateinit var reversedCodeTable: Map<String, String>

    fun decode(): String {
        TreePrinter.printTree(root)
        buildCodeTable(root)
        reversedCodeTable = codeTable.entries.associate { (k, v) -> v to k }
        return buildString()
    }

    private fun buildString(): String {
        val codes = encoded.split(" ")
        return codes
            .map { reversedCodeTable[it] }
            .joinToString("")
    }
}


class HuffmanEncode(private val originalStr: String) : HuffMan() {

    private val charMap = LinkedHashMap<String, Int>()
    private lateinit var pq: PriorQueue

    fun calculateCode(): Pair<String, Node> {
        sliceToChars()
        val nodes = buildNodes()
        pq = PriorQueue(nodes)
        val root = buildTree()
        TreePrinter.printTree(root)
        buildCodeTable(root);
        printCodeTable()
        val result = calculateFinalCode()
        return Pair(result, root)
    }

    private fun printCodeTable() {
        codeTable.forEach {
            val (letter, code) = it
            println("$letter: $code")
        }
    }

    private fun sliceToChars() {
        originalStr.forEach { c ->
            charMap.computeIfPresent(c.toString()) { _: String, b: Int -> b + 1 }
            charMap.putIfAbsent(c.toString(), 1)
        }
    }

    private fun buildNodes(): List<Node> =
        charMap.map {
            val (letter, value) = it
            Node(value, letter)
        }

    private tailrec fun buildTree(): Node {
        if (pq.size() < 2) {
            return pq.poll()
        }
        val left = pq.poll()
        val right = pq.poll()
        val value = left.value + right.value
        val parent = Node(value, left = left, right = right, data = value.toString())
        pq.add(parent)
        return buildTree()
    }

    private fun calculateFinalCode(): String =
        originalStr
            .map { it.toString() }
            .map { codeTable[it] }
            .joinToString(" ")

}

//FIXME optimize
class PriorQueue(nodes: List<Node>) {

    private val list = mutableListOf<Node>()

    init {
        list.addAll(nodes)
        list.sort()
    }

    fun add(node: Node) {
        list.add(0, node)
    }

    fun size() = list.size

    fun poll(): Node {
        var minNode = list[0]
        var minPrio = minNode.value
        for (i in 1 until size()) {
            val node = list[i]
            if (node.value < minPrio) {
                minNode = node
                minPrio = node.value
            }
        }
        list.remove(minNode)
        return minNode
    }

}



