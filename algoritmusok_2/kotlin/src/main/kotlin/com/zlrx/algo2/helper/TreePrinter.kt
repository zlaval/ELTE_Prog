package com.zlrx.algo2.helper

import com.zlrx.algo2.model.Node
import kotlin.math.max
import kotlin.math.pow

//TODO refactor
//FIXME data with multiple character
object TreePrinter {

    fun printTree(root: Node, printValue: Boolean = false) {
        val maxLevel = maxLevel(root)
        printTreeInternal(listOf(root), 1, maxLevel, printValue)
    }

    private tailrec fun printTreeInternal(nodes: List<Node?>, level: Int, maxLevel: Int, printValue: Boolean) {
        if (nodes.isEmpty() || nodes.isAllElementsNull()) return

        val floor = maxLevel - level
        val edgeLines = (2.0.pow(max(floor - 1, 0))).toInt()
        val firstSpaces = (2.0.pow(floor) - 1).toInt()
        val betweenSpaces = (2.0.pow(floor + 1) - 1).toInt()

        printWhitespaces(firstSpaces)

        val newNodes = mutableListOf<Node?>()

        for (node in nodes) {
            if (node != null) {
                newNodes.add(node.left)
                newNodes.add(node.right)
                var data = if (printValue) "${node.value}" else if (node.data.equals(" ")) "__" else node.data
                if(data!!.length<2){
                    data= "$data "
                }
                print(data)
            } else {
                newNodes.add(null)
                newNodes.add(null)
                print("  ")
            }
            printWhitespaces(betweenSpaces)
        }
        println()

        for (i in 1..edgeLines) {
            for (j in nodes.indices) {
                printWhitespaces(firstSpaces - i)
                if (nodes[j] == null) {
                    printWhitespaces(edgeLines * 2 + i + 1)
                    continue
                }
                if (nodes[j]?.left != null) {
                    print(" /")
                } else {
                    printWhitespaces(1)
                }
                printWhitespaces(i * 2 - 1)

                if (nodes[j]?.right != null) {
                    print("""\ """)
                } else {
                    printWhitespaces(1)
                }
                printWhitespaces(edgeLines * 2 - i)
            }
            println()
        }
        printTreeInternal(newNodes, level + 1, maxLevel, printValue)
    }

    private fun maxLevel(node: Node?): Int {
        if (node == null) {
            return 0
        }
        return max(maxLevel(node.left), maxLevel(node.right)) + 1
    }

    fun List<Node?>.isAllElementsNull() = this.all { it == null }

    private fun printWhitespaces(count: Int) {
        for (i in 0 until count) print("  ")
    }
}