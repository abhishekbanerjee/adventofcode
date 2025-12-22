package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CountFreshIdsTest {
    @Test
    fun countFreshIds() {
        val (freshIdRanges, ids) = parseIdRangesAndIds(readMultiLine("src/test/resources/day05test.txt"))
        assertEquals(3, countFreshIds(freshIdRanges, ids))
    }
}
