package com.zlrx.algo2.graph1.model

enum class State {
    NOT_VISITED, VISITING, VISITED
}

data class Vertex<T>(
    val value: T,
    var distance: Int = Integer.MAX_VALUE,
    var state: State = State.NOT_VISITED,
    val neighbour: MutableList<Vertex<T>> = mutableListOf()
) {

    fun addNeighbour(vertex: Vertex<T>) = neighbour.add(vertex)

    override fun toString(): String {
        return "Vertex(value=$value, distance=$distance, state=$state)"
    }

}