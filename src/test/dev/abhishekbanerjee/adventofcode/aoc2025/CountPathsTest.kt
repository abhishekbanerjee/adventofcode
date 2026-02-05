package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CountPathsTest {
    @Test
    fun countPaths() {
        assertEquals(5L, countPaths(readEdges(readMultiLine("src/test/resources/day11test.txt"))))
    }

    @Test
    fun countPathsWithRequirements() {
        assertEquals(2L, countPaths(readEdges(readMultiLine("src/test/resources/day11part2test.txt")), source = "svr", required = listOf("fft", "dac")))
    }
}
