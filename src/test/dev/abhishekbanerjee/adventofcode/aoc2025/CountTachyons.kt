package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CountTachyons {
    @Test
    fun countTachyonSplits() {
        assertEquals(21L, countTachyons(readMultiLine("src/test/resources/day07test.txt"), CountMode.SPLITS))
    }

    @Test
    fun countTachyonTimelines() {
        assertEquals(40L, countTachyons(readMultiLine("src/test/resources/day07test.txt"), CountMode.TIMELINES))
    }
}
