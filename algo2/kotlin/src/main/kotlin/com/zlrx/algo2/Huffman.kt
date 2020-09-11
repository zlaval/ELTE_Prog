package com.zlrx.algo2

import java.util.*
import kotlin.collections.HashMap

fun main() {
    println("Give me a sentence: ")
    val str = readLine()
    if (str != null) {
        val huffman = Huffman(str)
        val code = huffman.calculateCode()
        println(code)
    } else {
        println(":(")
    }
}


class Huffman(private val originalStr: String) {

    private val charMap = LinkedHashMap<String, Int>()
    private lateinit var pq: PriorQueue
    private val codeTable = HashMap<String, String>()

    fun calculateCode(): String {
        sliceToChars()
        val nodes = buildNodes()
        pq = PriorQueue(nodes)
        val root = buildTree()
        buildCodeTable(root);
        printCodeTable()
        return calculateFinalCode()
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
        val parent = Node(left.value + right.value, left = left, right = right)
        pq.add(parent)
        return buildTree()
    }

    private fun buildCodeTable(parent: Node) {
        fun traverse(accumulator: String, node: Node?) {
            if (node == null) {
                return
            }
            if (node.left == null && node.right == null) {
                codeTable[node.letter!!] = accumulator
            }
            traverse(accumulator + "0", node.left)
            traverse(accumulator + "1", node.right)
        }
        traverse("", parent);
    }

    private fun calculateFinalCode(): String =
        originalStr
            .map { it.toString() }
            .map { codeTable[it] }
            .joinToString(" ")

}

data class Node(
    val value: Int,
    val letter: String? = null,
    var left: Node? = null,
    var right: Node? = null,

    ) : Comparable<Node> {
    override fun compareTo(other: Node): Int = this.value.compareTo(other.value)
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



