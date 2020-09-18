package com.zlrx.algo2

import com.zlrx.algo2.helper.TreePrinter
import com.zlrx.algo2.model.Node
import kotlin.math.max

fun main() {
    AVL()
        .add(47)
        .add(70)
        .add(92)
        .add(12)
        .add(81)
        .add(87)
        .add(110)
        .add(85)
        .add(5)
        .add(41)
}


class AVL {

    private var root: Node? = null

    fun add(value: Int): AVL {
        val node = Node(value)
        if (root == null) {
            root = node
        } else {
            var parent = root!!;
            var found = false
            while (!found) {
                val nextParent = if (node.value < parent.value) {
                    parent.left
                } else {
                    parent.right
                }
                if (nextParent != null) {
                    parent = nextParent
                } else {
                    found = true
                }
            }

            if (node.value < parent.value) {
                parent.left = node
            } else {
                parent.right = node
            }
            node.parent = parent
            reBalance(node)
        }
        println()
        TreePrinter.printTree(root!!, true)
        println("*****************************************************************************************************")
        return this
    }

    fun delete(node: Node) {
        //TODO
    }

    private fun reBalance(node: Node) {
        //TODO
        var actualNode: Node? = node
        var shouldBalance = false
        while (!shouldBalance || actualNode == null) {
            val leftHeight = height(node.left) + 1
            val rightHeight = height(node.right) + 1
            val diff = leftHeight - rightHeight
            node.balance = diff
            if (diff < -1 || diff > 1) {
                shouldBalance = true
            } else {
                actualNode = node.parent
            }
        }
        if (shouldBalance) {
            //TODO balance the tree
        }

    }

    private fun height(node: Node?): Int {
        return if (node == null) {
            -1
        } else {
            val left = height(node.left)
            val right = height(node.right)
            max(left, right) + 1
        }
    }

}