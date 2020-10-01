package com.zlrx.algo2

import com.zlrx.algo2.helper.ExpressionBinTreeBuilder
import com.zlrx.algo2.helper.TreePrinter
import com.zlrx.algo2.model.Node
import kotlin.math.max

fun main() {
    val avl = AVL()
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


    avl.minRemove()
    avl.minRemove()
    avl.minRemove()
    avl.minRemove()
    avl.minRemove()

    println("#############################################################################")
    val avl2 = AVL.fromTree(ExpressionBinTreeBuilder.toBinTree("(((((1)5)11(15))25((27)30))50(((55)60)75(80)))"))
    avl2.maxRemove()
}

@Deprecated("new class version available. @see com.zlrx.algo2.AvlTree ")
class AVL(private var root: Node?) {

    constructor() : this(null)

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
            val aVLTree = isAVLTree(root)
            println("Tree is AVL now: $aVLTree")
            rebalanced = reBalance(node)
        }
        println()
        if (rebalanced) {
            println("------------------------------------ Tree after rebalanced ---------------------------------------------")
            TreePrinter.printTree(root!!, true)
            val aVLTree = isAVLTree(root)
            println("Tree is AVL now: $aVLTree")
        }
        return this
    }

    fun minRemove(): Node? {
        if (root == null) {
            return null
        }
        var node = root!!
        while (node.left != null) {
            node = node.left!!
        }
        delete(node)
        println("------------------------------------ After min remove ---------------------------------------------")
        TreePrinter.printTree(root!!, true)
        return node
    }

    private fun findMinInSubTree(parent: Node): Node {
        var node = parent.right
        while (node?.left != null) {
            node = node.left!!
        }
        return node!!//TODO
    }

    fun maxRemove(): Node? {
        if (root == null) {
            return null
        }
        var node = root!!
        while (node.right != null) {
            node = node.right!!
        }
        delete(node)
        return node
    }

    //TODO refactor
    fun delete(node: Node) {
        when {
            node.left == null -> {
                when {
                    node.parent?.left == node -> {
                        node.parent?.left = node.right
                    }
                    node.parent?.right == node -> {
                        node.parent?.right = node.right
                    }
                    else -> {
                        root = node.right
                    }
                }
            }
            node.right == null -> {
                when {
                    node.parent?.left == node -> {
                        node.parent?.left = node.left
                    }
                    node.parent?.right == node -> {
                        node.parent?.right = node.left
                    }
                    else -> {
                        root = node.left
                    }
                }
            }
            else -> {
                val minNode = findMinInSubTree(node)
                if (node.parent?.left == node) {
                    node.parent?.left = minNode
                } else {
                    node.parent?.right = minNode
                }
                minNode.left = node.left
                minNode.parent?.left = minNode.right
                minNode.right = node.right
            }
        }

        node.parent = null
        node.left = null
        node.right = null
        reBalanceAfterDelete(root)

    }

    //TODO naiv rebalance
    private fun reBalanceAfterDelete(node: Node?) {
        if (node == null) {
            return
        }
        reBalanceAfterDelete(node.left)
        reBalance(node, true)
        println("-------------------balancing after delete----------------------")
        TreePrinter.printTree(root!!, true)
        reBalanceAfterDelete(node.right)
    }

    private fun reBalance(node: Node, afterDelete: Boolean = false): Boolean {
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
            balance(actualNode!!, afterDelete)
        }
        return shouldBalance
    }

    private fun balance(node: Node, afterDelete: Boolean = false) {
        if (node.balance == 2) {
            if (node.right?.balance == 1) {
                balancePPP(node)
            } else if (node.right?.balance == -1) {
                balancePPM(node)
            } else if (node.right?.balance == 0 && afterDelete) {
                balancePP0(node)
            }
        } else if (node.balance == -2) {
            if (node.left?.balance == 1) {
                balanceMMP(node)
            } else if (node.left?.balance == -1) {
                balanceMMM(node)
            } else if (node.left?.balance == 0 && afterDelete) {
                balanceMM0(node)
            }
        }
    }

    private fun balancePP0(node: Node) {
        val newRoot = node.right!!
        val parent = node.parent
        node.right = newRoot.left
        newRoot.left = node
        handleRoot(node, parent, newRoot)
    }

    private fun balanceMM0(node: Node) {
        val newRoot = node.left!!
        val parent = node.parent
        node.left = newRoot.right
        newRoot.right = node
        handleRoot(node, parent, newRoot)
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

        fun fromTree(root: Node?): AVL {
            if (root == null) {
                return AVL()
            }
            if (!isAVLTree(root)) {
                throw IllegalArgumentException("Not an avl tree")
            }

            return AVL(root)
        }

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
            fun isAvlTreeInner(node: Node?): Pair<Boolean, Int> {
                if (node == null) {
                    return Pair(true, 0)
                }
                val left = node.left
                val right = node.right

                var thisIsAvl = true

                if (left != null) {
                    thisIsAvl = thisIsAvl && left.value < node.value
                }
                if (right != null) {
                    thisIsAvl = thisIsAvl && right.value > node.value
                }

                if (thisIsAvl) {
                    val leftAvl = isAvlTreeInner(left)
                    val rightAvl = isAvlTreeInner(right)
                    val leftHeight = leftAvl.second + 1
                    val rightHeight = rightAvl.second + 1

                    val levelDiff = leftHeight - rightHeight
                    if (levelDiff > 1 || levelDiff < -1) {
                        return Pair(false, 0)
                    }
                    val isChildrenAvl = leftAvl.first && rightAvl.first
                    return Pair(isChildrenAvl, max(leftHeight, rightHeight))
                }
                return Pair(false, 0)
            }

            return isAvlTreeInner(node).first
        }


    }


}