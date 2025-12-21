package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NumAccessibleRollsTest {
    @Test
    fun numAccessibleRolls() {
        assertEquals(13, numAccessibleRolls(readMultiLine("src/test/resources/day04test.txt")))
    }
}
