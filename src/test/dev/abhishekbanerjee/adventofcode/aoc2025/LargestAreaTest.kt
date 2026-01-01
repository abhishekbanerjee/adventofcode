package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LargestAreaTest {
    @Test
    fun largestArea() {
        assertEquals(50L, largestArea(to2dPoints(readMultiLine("src/test/resources/day09test.txt"))))
    }
}
