package com.example.kotlinplayground

import com.example.kotlinplayground.graphs.Edge
import com.example.kotlinplayground.graphs.EdgeType
import com.example.kotlinplayground.graphs.Graph
import com.example.kotlinplayground.graphs.Vertex

class AdjacencyList<T> : Graph<T> {

    // holds the pathCost for each tsp path
    private val tspPathCosts = arrayListOf<Double>()

    // holds key pair for all vertices
    private val adjacency: HashMap<Vertex<T>, ArrayList<Edge<T>>> = HashMap()

    // used to determine if an adjacency list contains at least one cycle
    private var isCyclic = false

    // used to store the cycle
    private var cycle = ArrayList<Vertex<T>>()

    // holds reference to visited vertex
    private val visited = arrayListOf<Vertex<T>>()

    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacency.count(), data)
        adjacency[vertex] = ArrayList()
        return vertex
    }

    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        val edge = Edge(source, destination, weight)
        adjacency[source]?.add(edge)
    }

    override fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    override fun add(edge: EdgeType, source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        when (edge) {
            EdgeType.DIRECTED -> addDirectedEdge(
                source,
                destination,
                weight
            )
            EdgeType.UNDIRECTED -> addUndirectedEdge(
                source,
                destination,
                weight
            )
        }
    }

    override fun edges(source: Vertex<T>) = adjacency[source] ?: arrayListOf()

    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return edges(source).firstOrNull {
            it.destination == destination
        }?.weight
    }

    override fun toString(): String {
        return buildString {
            adjacency.forEach { (vertex, edges) ->
                val edgeString = edges.joinToString("-->") {
                    it.destination.data.toString()
                }
                append("${vertex.data}-->$edgeString \n")
            }
        }
    }

    // method to initiate the search of the Hamiltonian cycle
    fun findTspRecursive(start: Vertex<T>): Double {
        // add starting vertex to the hamiltonian cycle
        cycle.add(start)

        // start searching the path
        solveHamiltonianPath(start)

        // minimum cost
        return tspPathCosts.min()
    }

    private fun calculatePathCost(cycle: ArrayList<Vertex<T>>) {
        var totalPathCost = 0.0
        for (i in cycle.indices) {
            val nextIndex = i + 1
            if (nextIndex < cycle.size) {
                totalPathCost += weight(cycle[i], cycle[nextIndex])!!
            }
        }
        val style = cycle.joinToString("-->") {
            it.data.toString()
        }
        println("$style cost: $totalPathCost")
        tspPathCosts.add(totalPathCost)
    }

    private fun solveHamiltonianPath(source: Vertex<T>) {
        // Base condition: if the vertex is the start vertex
        // and all nodes have been visited (our start vertex twice)
        val start = adjacency.keys.first()
        if (source === start && cycle.size == adjacency.size + 1) {
            isCyclic = true
            calculatePathCost(cycle)
            // return to continue solving other hamiltonian paths
            return
        }
        // iterate through the neighbor vertices
        val neighbors = edges(source)
        for (nbr in neighbors) {
            if (nbr.destination !in visited) {
                // visit and add vertex to the cycle
                visited.add(nbr.destination)
                cycle.add(nbr.destination)

                // Go to the neighbor vertex to find the cycle
                solveHamiltonianPath(nbr.destination)

                // Backtrack
                visited.removeLast()
                cycle.removeLast()
            }
        }
    }
}

fun main() {
    val graph = AdjacencyList<String>()
    val a = graph.createVertex("A")
    val b = graph.createVertex("B")
    val c = graph.createVertex("C")
    val d = graph.createVertex("D")
    val e = graph.createVertex("E")

    graph.add(EdgeType.UNDIRECTED, a, b, 20.0)
    graph.add(EdgeType.UNDIRECTED, a, e, 35.0)
    graph.add(EdgeType.UNDIRECTED, a, c, 42.0)
    graph.add(EdgeType.UNDIRECTED, b, d, 34.0)
    graph.add(EdgeType.UNDIRECTED, b, e, 30.0)
    graph.add(EdgeType.UNDIRECTED, c, d, 12.0)
    graph.add(EdgeType.UNDIRECTED, c, e, 30.0)
    graph.add(EdgeType.UNDIRECTED, d, e, 31.0)

    measureTime("minimum cost") {
        println("minimum path cost: ${graph.findTspRecursive(a)}")
    }
}
