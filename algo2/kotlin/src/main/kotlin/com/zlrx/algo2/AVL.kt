package com.zlrx.algo2

import com.zlrx.algo2.helper.TreePrinter
import com.zlrx.algo2.model.Node
import kotlin.math.max

fun main() {
    AVL()
        .add(51)
        .add(35)
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
        var rebalanced = false
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
            println("********************** $value inserted - Tree before rebalancing *******************************************")
            TreePrinter.printTree(root!!, true)
            val aVLTree=isAVLTree(root)
            println("Tree is AVL now: $aVLTree")
            rebalanced = reBalance(node)
        }
        println()
        if (rebalanced) {
            println("------------------------------------ Tree after rebalanced ---------------------------------------------")
            TreePrinter.printTree(root!!, true)
            val aVLTree=isAVLTree(root)
            println("Tree is AVL now: $aVLTree")
        }
        return this
    }

    fun delete(node: Node) {
        //TODO
    }

    private fun reBalance(node: Node): Boolean {
        var actualNode: Node? = node
        var shouldBalance = false
        while (!shouldBalance && actualNode != null) {
            val leftHeight = height(actualNode.left) + 1
            val rightHeight = height(actualNode.right) + 1
            val diff = rightHeight - leftHeight
            actualNode.balance = diff
            if (diff < -1 || diff > 1) {
                shouldBalance = true
            } else {
                actualNode = actualNode.parent
            }
        }
        if (shouldBalance) {
            balance(actualNode!!)
        }
        return shouldBalance
    }

    private fun balance(node: Node) {
        if (node.balance == 2) {
            if (node.right?.balance == 1) {
                balancePPP(node)
            } else if (node.right?.balance == -1) {
                balancePPM(node)
            }
        } else if (node.balance == -2) {
            if (node.left?.balance == 1) {
                balanceMMP(node)
            } else if (node.left?.balance == -1) {
                balanceMMM(node)
            }
        }
    }

    private fun balancePPM(node: Node) {
        val newRoot = node.right!!.left!!
        val parent = node.parent
        node.right = newRoot.left
        val newParent = newRoot.parent
        newRoot.parent?.left = newRoot.right
        newRoot.right = newParent
        newRoot.left = node
        handleRoot(node, parent, newRoot)
    }

    private fun balanceMMP(node: Node) {
        val newRoot = node.left!!.right!!
        val parent = node.parent
        node.left = newRoot.right
        val newParent = newRoot.parent
        newRoot.parent?.right = newRoot.left
        newRoot.left = newParent
        newRoot.right = node
        handleRoot(node, parent, newRoot)
    }

    private fun balancePPP(node: Node) {
        val newRoot = node.right!!
        val parent = node.parent
        node.right = newRoot.left
        newRoot.left = node
        handleRoot(node, parent, newRoot)
    }

    private fun balanceMMM(node: Node) {
        val newRoot = node.left!!
        val parent = node.parent
        node.left = newRoot.right
        newRoot.right = node
        handleRoot(node, parent, newRoot)
    }

    private fun handleRoot(originalChild: Node, parent: Node?, newNode: Node) {
        if (parent == null) {
            root = newNode
            newNode.parent = null
        } else {
            if (parent.left === originalChild) {
                parent.left = newNode
            } else {
                parent.right = newNode
            }
        }
    }


    companion object {

        fun height(node: Node?): Int {
            return if (node == null) {
                -1
            } else {
                val left = height(node.left)
                val right = height(node.right)
                max(left, right) + 1
            }
        }


        fun isAVLTree(node: Node?): Boolean {
            if (node == null) {
                return true
            }
            val left = node.left
            val right = node.right
            var leftHeight = 0
            var rightHeight = 0

            var thisIsAvl = true
            if (left != null) {
                thisIsAvl = thisIsAvl && left.value < node.value
                leftHeight = height(left)+1
            }
            if (right != null) {
                thisIsAvl = thisIsAvl && right.value > node.value
                rightHeight = height(right)+1
            }

            val levelDiff = leftHeight - rightHeight

            if (levelDiff > 1 || levelDiff < -1) {
                return false
            }

            if (thisIsAvl) {
                val leftIsAvl = isAVLTree(left)
                val rightIsAvl = isAVLTree(right)
                return leftIsAvl && rightIsAvl
            }
            return false
        }

    }

}