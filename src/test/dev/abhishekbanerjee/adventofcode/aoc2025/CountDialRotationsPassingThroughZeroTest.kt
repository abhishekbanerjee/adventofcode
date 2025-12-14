package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CountDialRotationsPassingThroughZeroTest {
    @Test
    fun countDialRotationsPassingThroughZero() {
        assertEquals(6, countDialRotationsPassingThroughZero(50, readMultiLine("src/test/resources/day01test.txt")))
    }

}