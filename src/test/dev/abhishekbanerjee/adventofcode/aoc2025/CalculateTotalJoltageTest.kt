package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CalculateTotalJoltageTest {
    @Test
    fun calculateTotalJoltageTwo() {
        assertEquals(357L, calculateTotalJoltage(readMultiLine("src/test/resources/day03test.txt"), 2))
    }

    @Test
    fun calculateTotalJoltageTwelve() {
        assertEquals(3121910778619L, calculateTotalJoltage(readMultiLine("src/test/resources/day03test.txt"), 12))
    }
}
