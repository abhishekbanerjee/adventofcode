package dev.abhishekbanerjee.adventofcode.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FewestPressesTest {
    @Test
    fun fewestPressesToggle() {
        assertEquals(
            7,
            fewestPresses(readMachineStates(readMultiLine("src/test/resources/day10test.txt")), OptimizerMode.TOGGLE)
        )
    }

    @Test
    fun fewestPressesJoltage() {
        assertEquals(
            33,
            fewestPresses(readMachineStates(readMultiLine("src/test/resources/day10test.txt")), OptimizerMode.JOLTAGE)
        )
    }

}
