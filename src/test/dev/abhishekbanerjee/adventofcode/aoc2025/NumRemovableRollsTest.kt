package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NumRemovableRollsTest {
    @Test
    fun numRemovableRolls() {
        assertEquals(43, numRemovableRolls(readMultiLine("src/test/resources/day04test.txt")))
    }
}
