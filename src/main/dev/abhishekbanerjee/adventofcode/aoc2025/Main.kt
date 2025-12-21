package dev.abhishekbanerjee.adventofcode.aoc2025

fun main() {
    // day01()
    // day02()
    day03()
}

fun day01() {
    // Day 1: Secret Entrance
    val inputFilePathDay1 = "src/main/resources/day01/input.txt"
    val rotations = readMultiLine(inputFilePathDay1)
    println("Day 1 password = " + countDialRotationsResultingInZero(50, rotations))
    // Part Two
    println("Day 1 password (part two) = " + countDialRotationsPassingThroughZero(50, rotations))
}

fun day02() {
    // Day 1: Secret Entrance
    val inputFilePathDay2 = "src/main/resources/day02/input.txt"
    val idRanges = readSingleLineSplit(inputFilePathDay2)
    println("Day 2 answer = " + invalidIdsSumRange(idRanges, ::isRepeatingPatternTwice))
    // Part Two
    println("Day 2 answer (part two) = " + invalidIdsSumRange(idRanges, ::isRepeatingPatternGeneric))
}

fun day03() {
    // Day 3: Lobby
    val inputFilePathDay3 = "src/main/resources/day03/input.txt"
    val banks = readMultiLine(inputFilePathDay3)
    println("Day 3 total joltage = " + calculateTotalJoltage(banks, 2))
    // Part Two
    println("Day 3 total joltage (part two) = " + calculateTotalJoltage(banks, 12))
}
