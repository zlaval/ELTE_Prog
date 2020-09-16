package com.zlrx.algo2.model


data class Node(
    val value: Int,
    val data: String? = null,
    var left: Node? = null,
    var right: Node? = null,

    ) : Comparable<Node> {
    override fun compareTo(other: Node): Int = this.value.compareTo(other.value)
}