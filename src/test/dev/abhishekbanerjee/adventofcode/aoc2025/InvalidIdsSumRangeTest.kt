package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InvalidIdsSumRangeTest {
    @Test
    fun invalidIdsSumRangeTwice() {
        assertEquals(
            1227775554L,
            invalidIdsSumRange(readSingleLineSplit("src/test/resources/day02test.txt"), ::isRepeatingPatternTwice)
        )
    }

    @Test
    fun invalidIdsSumRangeGeneric() {
        assertEquals(
            4174379265L,
            invalidIdsSumRange(readSingleLineSplit("src/test/resources/day02test.txt"), ::isRepeatingPatternGeneric)
        )
    }
}
