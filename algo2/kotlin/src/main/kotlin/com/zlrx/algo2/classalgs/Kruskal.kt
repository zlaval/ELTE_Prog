package com.zlrx.algo2.classalgs

data class Vertex(
    val name: String,
    var node: Node = Node(-1)
)

data class Edge(
    val start: Vertex,
    val target: Vertex,
    val weight: Int
) : Comparable<Edge> {

    override fun compareTo(other: Edge): Int {
        return this.weight.compareTo(other.weight)
    }

}

data class Node(
    val id: Int,
    var rank: Int = 0,
    var parent: Node? = null
)

//val a = Vertex("a") //0
//val b = Vertex("b")
//val c = Vertex("c")//2
//val d = Vertex("d")
//val e = Vertex("e")
//val f = Vertex("f") //5
//val g = Vertex("g")
//val h = Vertex("h")
//val i = Vertex("i")  //8
//
//val e1 = Edge(a, b, 3)
//val e2 = Edge(a, c, 2)
//val e3 = Edge(a, d, 5)
//val e4 = Edge(a, i, 8)
//val e5 = Edge(b, d, 2)
//val e6 = Edge(b, f, 11)
//val e7 = Edge(c, d, 2)
//val e8 = Edge(c, e, 5)
//val e9 = Edge(d, e, 4)
//val e10 = Edge(d, f, 6)
//val e11 = Edge(d, g, 3)
//val e12 = Edge(e, g, 6)
//val e13 = Edge(f, g, 2)
//val e14 = Edge(f, h, 3)
//val e15 = Edge(g, h, 6)
//val e16 = Edge(h, i, 1)
//
//val vertices: List<Vertex> = listOf(a, b, c, d, e, f, g, h, i)
//val edges: List<Edge> = listOf(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16).sorted()

val a = Vertex("1")
val b = Vertex("2")
val c = Vertex("3")
val d = Vertex("4")
val e = Vertex("5")
val f = Vertex("6")
val g = Vertex("7")
val h = Vertex("8")

val e1 = Edge(a, b, 1)
val e2 = Edge(a, c, 4)
val e3 = Edge(b, c, 2)

val e4 = Edge(b, d, 3)
val e5 = Edge(b, e, 4)
val e6 = Edge(c, e, 1)
val e7 = Edge(d, e, 1)
val e8 = Edge(d, f, 6)
val e9 = Edge(d, h, 8)
val e10 = Edge(e, g, 5)
val e11 = Edge(f, g, 4)
val e12 = Edge(g, h, 10)

val vertices: List<Vertex> = listOf(a, b, c, d, e, f, g, h)
val edges: List<Edge> = listOf(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12).sorted()

class DisjointSet(vertices: List<Vertex>) {

    private var nodeCount: Int = 0;
    val representative: List<Node>

    init {
        representative = vertices.map { makeSet(it) }
    }

    private fun makeSet(vertex: Vertex): Node {
        val node = Node(nodeCount++)
        vertex.node = node
        return node
    }

    fun justFindNode(node: Node): Int {
        var rootNode: Node = node
        while (rootNode.parent != null) {
            rootNode = rootNode.parent!!
        }
        return rootNode.id
    }

    fun find(node: Node): Int {
        var rootNode: Node = node
        while (rootNode.parent != null) {
            rootNode = rootNode.parent!!
        }
        var currentNode = node
        while (currentNode != rootNode) {
            val tmp = currentNode.parent
            currentNode.parent = rootNode
            currentNode = tmp!!

        }
        return rootNode.id
    }


    fun union(a: Node, b: Node) {
        val aIndex = find(a)
        val bIndex = find(b)

        val firstRootSet = representative[aIndex]
        val secondRootSet = representative[bIndex]

        when {
            firstRootSet.rank < secondRootSet.rank -> {
                firstRootSet.parent = secondRootSet
            }
            firstRootSet.rank > secondRootSet.rank -> {
                secondRootSet.parent = firstRootSet
            }
            else -> {
                secondRootSet.parent = firstRootSet
                firstRootSet.rank = firstRootSet.rank + 1
            }
        }
    }

}

fun printHeader() {
    print("              Componenets               |")//40+1
    print("   edge   |")//10+1
    print("weight|")//6+1
    print(" safe |")//6+1
    println()
    repeat(66) {
        print("-")
    }
    println()
}

fun printRound(edge: Edge, disjointSet: DisjointSet) {

    val components = findNodeGroups(disjointSet)

    print(components)
    repeat(40 - components.length) {
        print(" ")
    }
    print("|")

    val vx = "(${edge.start.name},${edge.target.name})"
    print(vx)
    repeat(10 - vx.length) {
        print(" ")
    }
    print("|")

    print(edge.weight)
    repeat(6 - edge.weight.toString().length) {
        print(" ")
    }
    print("|")

    val safe = disjointSet.find(edge.start.node) != disjointSet.find(edge.target.node)

    if (safe) {
        print("   +  |")
    } else {
        print("   -  |")
    }


    println()
    repeat(66) {
        print("-")
    }
    println()
}

fun findNodeGroups(disjointSet: DisjointSet): String {
    val nodeGroups = HashMap<Int, ArrayList<Vertex>>()
    vertices.forEach {
        if (it.node == null) {
            nodeGroups.putIfAbsent(it.node.id, arrayListOf(it))
        } else {
            val parent = disjointSet.justFindNode(it.node)
            if (nodeGroups.containsKey(parent)) {
                nodeGroups[parent]?.add(it)
            } else {
                nodeGroups.putIfAbsent(it.node.id, arrayListOf(it))
            }
        }
    }
    var res = ""
    nodeGroups.forEach { (_, v) ->
        v.forEach {
            res += it.name
        }
        res += " "
    }
    return res
}


fun main() {
    printHeader()
    val disjointSet = DisjointSet(vertices)
    val minimumSpanningTree = mutableListOf<Edge>()
    edges.forEach {
        printRound(it, disjointSet)
        val u = it.start
        val v = it.target
        if (disjointSet.find(u.node) != disjointSet.find(v.node)) {
            minimumSpanningTree.add(it)
            disjointSet.union(u.node, v.node)
        }
    }

    println("Route:")
    println()
    minimumSpanningTree.forEach { (start, target, weight) ->
        println("${start.name} ---$weight--> ${target.name}")
    }
    val cost = minimumSpanningTree
        .asSequence()
        .map { it.weight }
        .reduce { acc, w -> acc + w }
    println()
    println("Cost: $cost")


}
