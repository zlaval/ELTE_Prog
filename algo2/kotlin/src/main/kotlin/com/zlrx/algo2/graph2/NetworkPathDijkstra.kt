package com.zlrx.algo2.graph2

import java.util.*

fun main() {
    val nwd = NetworkPathDijkstra()
    nwd.calculateChances()
    nwd.printResult()
}

class NetworkPathDijkstra {

    data class Edge(
        val weight: Double,
        val source: Vertex,
        val target: Vertex
    )

    data class Vertex(
        val name: String,
        var parent: Vertex? = null,
        val adjacencies: MutableList<Edge> = mutableListOf(),
        var maxChance: Double = 0.0
    ) : Comparable<Vertex> {

        override fun compareTo(other: Vertex): Int = other.maxChance.compareTo(maxChance)
    }

    private val pq = PriorityQueue<Vertex>()

    private val start: Vertex

    init {
        start = Vertex("1")
        val second = Vertex("2")
        val third = Vertex("3")
        val forth = Vertex("4")
        val fifth = Vertex("5")
        val sixth = Vertex("6")
        val seventh = Vertex("7")

        val os = Edge(25.0, start, second)
        val ot = Edge(70.0, start, third)
        start.adjacencies.addAll(arrayOf(os, ot))
        val sesi = Edge(85.0, second, sixth)
        second.adjacencies.add(sesi)

        val tse = Edge(2.0, third, second)
        val tfo = Edge(50.0, third, forth)
        val tfi = Edge(90.0, third, fifth)
        val tsi = Edge(25.0, third, sixth)
        third.adjacencies.addAll(arrayOf(tse, tfo, tfi, tsi))

        val fise = Edge(60.0, fifth, seventh)
        fifth.adjacencies.add(fise)

        val sevsi = Edge(70.0, seventh, sixth)
        seventh.adjacencies.add(sevsi)
    }

    fun calculateChances() {
        start.maxChance = 100.0
        pq.add(start)
        while (pq.isNotEmpty()) {
            val actualVertex = pq.poll()
            actualVertex.adjacencies.forEach {
                val target = it.target
                val actualChance = (it.weight * actualVertex.maxChance) / 100
                println("${actualVertex.name} --> ${target.name} chance was ${target.maxChance} calculated chance is $actualChance")
                if (actualChance > target.maxChance) {
                    println("change best to the calculated")
                    pq.remove(target)
                    target.maxChance = actualChance
                    target.parent = actualVertex
                    pq.add(target)
                }
            }
        }
    }

    fun printResult() {
        println("**********************RESULT***********************")
        fun printAllWeight(vertex: Vertex, parent: Vertex? = null) {
            if (parent == null) {
                println("start from ${vertex.name}, it has ${vertex.maxChance}% chance")
            }else if(parent == start){
                println("${start.name} -> ${vertex.name} has ${vertex.maxChance}% chance")
            } else if (parent == vertex.parent) {
                println("${start.name} -> ... -> ${vertex.parent?.name} -> ${vertex.name} has ${vertex.maxChance}% chance")
            }
            vertex.adjacencies.forEach {
                printAllWeight(it.target, vertex)
            }
        }

        printAllWeight(start)
    }


}