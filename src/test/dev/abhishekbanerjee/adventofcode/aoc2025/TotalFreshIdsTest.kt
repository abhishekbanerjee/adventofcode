package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TotalFreshIdsTest {
    @Test
    fun totalFreshIds() {
        val (freshIdRanges, _) = parseIdRangesAndIds(readMultiLine("src/test/resources/day05test.txt"))
        assertEquals(14L, totalFreshIds(freshIdRanges))
    }
}
