package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SolveCephalopodMathTest {
    @Test
    fun solveCephalopodMath() {
        assertEquals(3263827L, solveCephalopodMath(readMultiLine("src/test/resources/day06test.txt")))
    }
}
