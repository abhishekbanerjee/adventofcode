package dev.abhishekbanerjee.adventofcode.aoc2025

import java.lang.Math.floorDiv
import kotlin.math.abs

fun countDialRotationsResultingInZero(startPosition: Int, rotations: List<String>): Int {
    var position = startPosition
    var zeroCount = 0
    for (rotation in rotations) {
        val rotationValue = rotation.substring(startIndex = 1).toInt()
        when (rotation[0]) {
            'L' -> position -= rotationValue
            'R' -> position += rotationValue
            else -> throw IllegalStateException("Unexpected rotation value: ${rotation[0]}")
        }
        position %= 100
        if (position == 0) zeroCount++
    }
    return zeroCount
}

fun countDialRotationsPassingThroughZero(startPosition: Int, rotations: List<String>): Int {
    var currentPosition = startPosition
    var zeroCount = 0
    for (rotation in rotations) {
        val rotationValue = rotation.substring(startIndex = 1).toInt()
        var nextPosition: Int
        when (rotation[0]) {
            'L' -> {
                nextPosition = currentPosition - rotationValue
                // We use floorDiv, because we want to round down, and division by 100 to faithfully count all the times
                // we pass 0. When we're turning the dial left ("decreasing" the position), we subtract one in addition,
                // to account for the case where the end of the dial turn results in landing at an exact multiple of 100.
                zeroCount += abs(floorDiv(nextPosition - 1, 100) - floorDiv(currentPosition - 1, 100))
            }

            'R' -> {
                nextPosition = currentPosition + rotationValue
                // See the previous comment for why we use floordiv and division by 100. For turning the dial right
                // ("increasing" the position), landing on a multiple of 100 would mean landing on the "next" multiple,
                // so regular floordiv works out fine.
                zeroCount += abs(floorDiv(nextPosition, 100) - floorDiv(currentPosition, 100))
            }

            else -> throw IllegalStateException("Unexpected rotation value: ${rotation[0]}")
        }
        // The remainder by 100 is not really necessary, but we do it to keep the currentPosition manageable (if the
        // input file keeps turning the dial in one direction).
        currentPosition = nextPosition % 100
    }
    return zeroCount
}