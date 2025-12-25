package dev.abhishekbanerjee.adventofcode.aoc2025

enum class CountMode {
    SPLITS,
    TIMELINES
}

fun countTachyons(lines: List<String>, countMode: CountMode): Long {
    // Defensive check 1: return early if empty.
    if (lines.isEmpty()) return 0

    val length = lines[0].length
    val startCol = lines[0].indexOf('S')

    // Defensive check 2: return early if start not found.
    if (startCol == -1) return 0

    // An inner function to handle the iteration through the lines and the bookkeeping of state within.
    fun <R> runIterations(initialState: R, transitionFunction: (R, String) -> R): R {
        return lines.drop(1).fold(initialState, transitionFunction)
    }

    return when (countMode) {
        CountMode.SPLITS -> {
            // The state tracks two things: the column indexes the beam is at in the current line and the number of
            // splits encountered so far.
            val initialState = Pair(setOf(startCol), 0L)
            val finalState = runIterations(initialState) { (beamCols, currentCount), line ->
                var newCount = currentCount
                val newBeamCols = beamCols.flatMap { idx ->
                    if (line[idx] == '^') {
                        newCount++
                        setOf(idx-1, idx+1).filter { it != -1 && it != length }
                    } else setOf(idx)
                }.toSet()
                Pair(newBeamCols, newCount)
            }
            finalState.second
        }

        CountMode.TIMELINES -> {
            val initialState = LongArray(length).apply { this[startCol] = 1L }
            val finalState = runIterations(initialState) { currentTimelines, line ->
                val nextTimelines = LongArray(length)
                currentTimelines.forEachIndexed { idx, timelines ->
                    if (timelines != 0L) {
                        val idxsToUpdate = if (line[idx] == '^') {
                            setOf(idx - 1, idx + 1).filter { it != -1 && it != length }
                        } else setOf(idx)
                        idxsToUpdate.forEach { nextTimelines[it] += timelines }
                    }
                }
                nextTimelines
            }
            finalState.sum()
        }
    }
}
