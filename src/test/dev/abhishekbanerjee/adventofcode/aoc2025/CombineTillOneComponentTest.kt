package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CombineTillOneComponentTest {
    @Test
    fun combineTillOneComponent() {
        assertEquals(25272L, combineTillOneComponent(toPoints(readMultiLine("src/test/resources/day08test.txt"))))
    }
}
