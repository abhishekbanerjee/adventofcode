package dev.abhishekbanerjee.adventofcode.aoc2025

import java.util.PriorityQueue

data class Point(val x: Int, val y: Int, val z: Int)

data class WeightedEdge(val weight: Long, val u: Int, val v: Int)

class GraphComponents(numVertices: Int) {
    val parents = MutableList(numVertices) { it }
    val componentSizes = MutableList(numVertices) { 1 }
    var numComponents = numVertices

    fun find(vertex: Int): Int {
        if (parents[vertex] == vertex) return vertex
        parents[vertex] = find(parents[vertex])
        return parents[vertex]
    }

    fun union(v1: Int, v2: Int): Boolean {
        val u1 = find(v1)
        val u2 = find(v2)
        if (u1 == u2) return false

        val (parent, child) = if (componentSizes[u1] > componentSizes[u2]) u1 to u2 else u2 to u1
        parents[child] = parent
        componentSizes[parent] += componentSizes[child]
        numComponents--
        return true
    }

    fun nLargestComponentSizes(n: Int): List<Int> {
        val distinctCompoentSizes = componentSizes
            .zip(parents)
            .filterIndexed { vertex, (_, parent) -> vertex == parent }
            .map { it.first }
            .sorted()
        if (n > distinctCompoentSizes.size)
            throw IllegalArgumentException("We have less than $n distinct components (${distinctCompoentSizes.size} actual components)")
        return distinctCompoentSizes.takeLast(n)
    }
}

fun toPoints(lines: List<String>): List<Point> {
    return lines.map { line -> line.trim().split(",").let { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) } }
}

fun largestCircuits(points: List<Point>, numConnections: Int): Int {
    val edgesHeap = buildEdgesHeap(points)
    val graphComponents = GraphComponents(points.size)
    repeat(numConnections) {
        val edge = edgesHeap.poll()
        graphComponents.union(edge.u, edge.v)
    }
    return graphComponents.nLargestComponentSizes(3).fold(1) { acc, i -> acc * i }
}

fun combineTillOneComponent(points: List<Point>): Long {
    val edgesHeap = buildEdgesHeap(points)
    val graphComponents = GraphComponents(points.size)
    var xProduct = 1L
    while (graphComponents.numComponents > 1) {
        val edge = edgesHeap.poll()
        graphComponents.union(edge.u, edge.v)
        xProduct = points[edge.u].x.toLong() * points[edge.v].x.toLong()
    }
    return xProduct
}

private fun buildEdgesHeap(points: List<Point>): PriorityQueue<WeightedEdge> {
    val pointHeap = PriorityQueue<WeightedEdge>(compareBy { it.weight })
    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            pointHeap.add(WeightedEdge(distanceSq(points[i], points[j]), i, j))
        }
    }
    return pointHeap
}

private fun distanceSq(point1: Point, point2: Point): Long {
    val dx = point1.x.toLong() - point2.x.toLong()
    val dy = point1.y.toLong() - point2.y.toLong()
    val dz = point1.z.toLong() - point2.z.toLong()
    return dx * dx + dy * dy + dz * dz
}
