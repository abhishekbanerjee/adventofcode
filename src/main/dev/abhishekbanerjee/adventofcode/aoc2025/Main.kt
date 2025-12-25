package dev.abhishekbanerjee.adventofcode.aoc2025

fun main() {
    // day01()
    // day02()
    // day03()
    // day04()
    // day05()
    // day06()
    day07()
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

fun day04() {
    // Day 4: Printing Department
    val inputFilePathDay4 = "src/main/resources/day04/input.txt"
    val grid = readMultiLine(inputFilePathDay4)
    println("Day 4 accessible rolls of paper = " + numAccessibleRolls(grid))
    // Part Two
    println("Day 4 removable rolls of paper (part two) = " + numRemovableRolls(grid))
}

fun day05() {
    // Day 5: Cafeteria
    val inputFilePathDay5 = "src/main/resources/day05/input.txt"
    val (freshIds, ids) = parseIdRangesAndIds(readMultiLine(inputFilePathDay5))
    println("Day 5 fresh ids = " + countFreshIds(freshIds, ids))
    // Part Two
    println("Day 5 total fresh ids (part two) = " + totalFreshIds(freshIds))
}

fun day06() {
    // Day 6: Trash Compactor
    val inputFilePathDay6 = "src/main/resources/day06/input.txt"
    val worksheet = readMultiLine(inputFilePathDay6)
    println("Day 6 grand total = " + solveMath(worksheet))
    // Part Two
    println("Day 6 grand total (part two) = " + solveCephalopodMath(worksheet))
}

fun day07() {
    // Day 7: Laboratories
    val inputFilePathDay7 = "src/main/resources/day07/input.txt"
    val diagram = readMultiLine(inputFilePathDay7)
    println("Day 7 number of splits = " + countTachyons(diagram, CountMode.SPLITS))
    // Part Two
    println("Day 7 number of timelines (part two) = " + countTachyons(diagram, CountMode.TIMELINES))
}
