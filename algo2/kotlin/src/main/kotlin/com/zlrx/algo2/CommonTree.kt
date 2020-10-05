package com.zlrx.algo2

data class Node(
    val value: Int,
    val firstChild: Node? = null,
    val nextSibling: Node? = null
)

fun main() {
    val root = createCommonTree()
    printLargestKeyOnEverySiblingsGroup(root)
}

fun printLargestKeyOnEverySiblingsGroup(node: Node?) {
    if (node == null) {
        return
    }
    var next = node
    var max = next.value
    while (next != null) {
        printLargestKeyOnEverySiblingsGroup(next.firstChild)
        if (next.value > max) {
            max = next.value
        }
        next = next.nextSibling
    }
    println(max)
}

fun createCommonTree(): Node {
    return Node(
        value = 7,
        firstChild = Node(
            value = 15,
            firstChild = Node(
                value = 3,
                firstChild = Node(
                    value = 16,
                    nextSibling = Node(
                        value = 36,
                        nextSibling = Node(value = 31)
                    )
                ),
                nextSibling = Node(value = 11)
            ),
            nextSibling = Node(
                value = 8,
                firstChild = Node(
                    value = 10,
                    firstChild = Node(
                        value = 9,
                        nextSibling = Node(value = 50)
                    ),
                ),
                nextSibling = Node(
                    value = 14,
                    firstChild = Node(
                        value = 19,
                        nextSibling = Node(
                            value = 35,
                            firstChild = Node(
                                value = 17,
                                nextSibling = Node(
                                    value = 49,
                                    nextSibling = Node(
                                        value = 21,
                                        nextSibling = Node(value = 34)
                                    )
                                )
                            ),
                        )
                    ),
                )
            )
        ),
    )


}