package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SolveMathTest {
    @Test
    fun solveMath() {
        assertEquals(4277556L, solveMath(readMultiLine("src/test/resources/day06test.txt")))
    }
}