package dev.abhishekbanerjee.adventofcode.aoc2025

import java.io.File

fun readMultiLine(path: String): List<String> {
    return File(path).readLines()
}