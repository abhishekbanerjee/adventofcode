package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*

class CountDialRotationsResultingInZeroTest {
    @org.junit.jupiter.api.Test
    fun countDialRotationsResultingInZero() {
        assertEquals(3, countDialRotationsResultingInZero(50, readMultiLine("src/test/resources/day01test.txt")))
    }

}