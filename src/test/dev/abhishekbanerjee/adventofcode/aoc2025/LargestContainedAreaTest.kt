package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LargestContainedAreaTest {
    @Test
    fun largestContainedArea() {
        assertEquals(24L, largestContainedArea(to2dPoints(readMultiLine("src/test/resources/day09test.txt"))))
    }
}
