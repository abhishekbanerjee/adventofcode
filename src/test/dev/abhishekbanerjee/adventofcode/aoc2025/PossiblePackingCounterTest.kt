package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PossiblePackingCounterTest {
    @Test
    fun possiblePackingCounter() {
        assertEquals(2, possiblePackingCounter(readAll("src/test/resources/day12test.txt")))
    }

}
