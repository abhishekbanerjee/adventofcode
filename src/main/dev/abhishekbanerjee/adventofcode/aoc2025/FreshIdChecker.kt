package dev.abhishekbanerjee.adventofcode.aoc2025

/**
 * Parses the input with the following formatting:
 * 1. A list of ranges, which are two integers separated by a hyphen, one per line
 * 2. A blank line
 * 3. A list of integers, one per line
 *
 * Returns a pair. The first member of the pair is a list of the ranges, with the start and end represented as a pair.
 * The ranges are sorted and consolidated (so no overlaps among ranges). The second member is a list of the integers
 * that follow after the blank line. These integers are also sorted.
 */
fun parseIdRangesAndIds(lines: List<String>): Pair<List<Pair<Long, Long>>, List<Long>> {
    val ranges = mutableListOf<Pair<Long, Long>>()
    val ints = mutableListOf<Long>()
    var isBlankEncountered = false
    for (line in lines) {
        if (line.isBlank()) {
            isBlankEncountered = true
            continue
        }
        when (isBlankEncountered) {
            true -> ints += line.toLong()
            false -> {
                val rangeBoundaries = line.split('-')
                ranges += Pair(rangeBoundaries[0].toLong(), rangeBoundaries[1].toLong())
            }
        }
    }
    ranges.sortWith(compareBy({ it.first }, { it.second }))
    val sortedRanges = mutableListOf<Pair<Long, Long>>()
    for (range in ranges) {
        if (sortedRanges.isEmpty() || sortedRanges.last().second < range.first) {
            sortedRanges += range
        } else {
            val (lastStart, lastEnd) = sortedRanges.removeLast()
            val newLastEnd = maxOf(lastEnd, range.second)
            sortedRanges += Pair(lastStart, newLastEnd)
        }
    }
    return Pair(sortedRanges, ints.sorted())
}

fun countFreshIds(freshRanges: List<Pair<Long, Long>>, ids: List<Long>): Int {
    var count = 0
    var currentRangeIdx = 0
    for (id in ids) {
        while (currentRangeIdx < freshRanges.size && id > freshRanges[currentRangeIdx].second) {
            currentRangeIdx++
        }
        // We've exhausted the list of fresh ranges, so all other ids are spoiled.
        if (currentRangeIdx == freshRanges.size) break
        if (id >= freshRanges[currentRangeIdx].first && id <= freshRanges[currentRangeIdx].second) {
            count++
        }
    }
    return count
}

fun totalFreshIds(freshRanges: List<Pair<Long, Long>>): Long {
    return freshRanges.map { it.second - it.first + 1 }.sum()
}
