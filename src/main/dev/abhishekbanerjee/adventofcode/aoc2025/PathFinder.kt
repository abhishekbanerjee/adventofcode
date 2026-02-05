package dev.abhishekbanerjee.adventofcode.aoc2025

fun readEdges(lines: List<String>): Map<String, List<String>> =
    lines.map { it.split(':') }.associateBy({ it[0] }, { it[1].trim().split(' ') })

fun countPaths(
    adjacencies: Map<String, List<String>>,
    source: String = "you",
    destination: String = "out",
    required: List<String> = listOf()
): Long {
    val requiredIndexed = required.indices.associateBy({ required[it] }, { it })
    val numRequired = required.size
    val targetMask = (1 shl numRequired) - 1

    val inDegrees = mutableMapOf<String, Int>().withDefault { 0 }
    val allNodes = adjacencies.keys + adjacencies.values.flatten()
    for (u in allNodes) {
        for (v in adjacencies[u].orEmpty()) {
            inDegrees[v] = inDegrees.getValue(v) + 1
        }
    }

    val state = mutableMapOf<String, LongArray>()
    for (node in allNodes) {
        state[node] = LongArray(targetMask + 1)
    }

    val sourceMask = if (source in requiredIndexed) 1 shl requiredIndexed.getValue(source) else 0
    state.getValue(source)[sourceMask] = 1L

    val queue = ArrayDeque<String>()
    for (node in allNodes) {
        if (inDegrees.getValue(node) == 0) queue.add(node)
    }

    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()

        for (v in adjacencies[u].orEmpty()) {
            for (mask in 0..targetMask) {
                if (state.getValue(u)[mask] > 0) {
                    val nextMask = if (v in requiredIndexed) mask or (1 shl requiredIndexed.getValue(v)) else mask
                    state.getValue(v)[nextMask] += state.getValue(u)[mask]
                }
            }

            inDegrees[v] = inDegrees.getValue(v) - 1
            if (inDegrees[v] == 0) queue.add(v)
        }
    }

    return state[destination]?.get(targetMask) ?: 0L
}