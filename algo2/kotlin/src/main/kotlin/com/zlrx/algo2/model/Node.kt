package com.zlrx.algo2.model


class Node : Comparable<Node> {

    val value: Int
    var data: String? = null

    var left: Node? = null
        set(value) {
            field = value
            field?.parent = this
        }

    var right: Node? = null
        set(value) {
            field = value
            field?.parent = this
        }

    var parent: Node? = null
    var balance: Int = 0
    var level: Int? = null

    constructor(
        value: Int,
        data: String? = null,
        left: Node? = null,
        right: Node? = null,
        parent: Node? = null,
        balance: Int = 0,
        level:Int?=null
    ) {
        this.value = value
        this.data = data
        this.left = left
        this.right = right
        this.parent = parent
        this.balance = balance
        this.level=level
    }


    override fun compareTo(other: Node): Int = this.value.compareTo(other.value)
}