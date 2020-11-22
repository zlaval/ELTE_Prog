package com.zlrx.algo2.graph1

import java.util.*

fun main() {
    val pq=PriorityQueue<Int>()
    pq.add(3)
    pq.add(5)
    pq.add(1)
    pq.add(9)
    pq.add(4)

    while (pq.isNotEmpty()){
        println(pq.poll())
    }

}