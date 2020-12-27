package com.zlrx.algo2.helper

import com.zlrx.algo2.model.Node


fun main() {
   // val expression = "(((((1)5)11(15))25((27)30))50(((55)60)75(80)))"
    val expression = "(((1)2((3)4))5(((6(7))8(9))10((11)12)13(14)))))"
    val root = ExpressionBinTreeBuilder.toBinTree(expression)
    TreePrinter.printTree(root!!, true)
}

object ExpressionBinTreeBuilder {

    fun toBinTree(expression: String): Node? {
        val elements = parseExpression(expression)
        return buildTree(elements, 0, elements.size - 1, 0)
    }

    private fun buildTree(elements: List<String>, from: Int, to: Int, level: Int): Node? {
        if (from >= to) {
            return null
        }
        val nodeIndex = findNextNodeIndex(elements, from + 1, to - 1)
        val parent = Node(elements[nodeIndex].toInt(), level = level)
        val left = buildTree(elements, from + 1, nodeIndex - 1, level + 1)
        val right = buildTree(elements, nodeIndex + 1, to - 1, level + 1)
        parent.left = left
        parent.right = right
        return parent
    }

    private fun findNextNodeIndex(elements: List<String>, from: Int, to: Int): Int {
        if (elements[from] != ")" && elements[from] != "(") {
            return from
        }
        var start = from
        var numberOfParentheses = 0
        while (start < to) {
            if (elements[start] == "(") {
                numberOfParentheses++
            } else if (elements[start] == ")") {
                numberOfParentheses--
            }
            start++
            if (numberOfParentheses == 0) {
                return start
            }

        }
        return -1
    }


    private fun parseExpression(expression: String): List<String> = expression
        .map { it.toString() }
        .fold(mutableListOf()) { list, char ->
            if (char == "(" || char == ")") {
                list.add(char)
            } else {
                val tmp = list.removeLastNonParenthesesOrDefault("") + char
                list.add(tmp)
            }
            list
        }


    private fun MutableList<String>.removeLastNonParenthesesOrDefault(default: String): String {
        val lastValue = this.lastOrNull()
        if (lastValue != null && lastValue != ")" && lastValue != "(") {
            val value = this.removeLastOrNull()
            if (value != null) {
                return value
            }
        }
        return default
    }


}