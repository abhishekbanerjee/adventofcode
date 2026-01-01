package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LargestCircuitsTest {
    @Test
    fun largestCircuits() {
        assertEquals(40, largestCircuits(to3dPoints(readMultiLine("src/test/resources/day08test.txt")), 10))
    }
}
