package dev.abhishekbanerjee.adventofcode.aoc2025

import java.io.File

fun readMultiLine(path: String): List<String> {
    return File(path).readLines()
}

fun readSingleLineSplit(path: String, splitChar: Char = ','): List<String> {
    val lines = readMultiLine(path)
    if (lines.size != 1) throw IllegalArgumentException("The file should only have one line!")
    return lines[0].split(splitChar)
}
