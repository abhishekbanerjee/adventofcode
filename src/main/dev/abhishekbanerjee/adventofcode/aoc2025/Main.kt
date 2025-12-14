package dev.abhishekbanerjee.adventofcode.aoc2025

fun main() {
    // Day 1: Secret Entrance
    val inputFilePathDay1 = "src/main/resources/day01/input.txt"
    println("Day 1 password = " + countDialRotationsResultingInZero(50,readMultiLine(inputFilePathDay1)))
    // Part Two
    println("Day 1 password (part two) = " + countDialRotationsPassingThroughZero(50, readMultiLine(inputFilePathDay1)))
}