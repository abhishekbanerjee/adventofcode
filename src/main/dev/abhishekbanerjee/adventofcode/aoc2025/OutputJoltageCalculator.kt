package dev.abhishekbanerjee.adventofcode.aoc2025

fun calculateTotalJoltage(banks: List<String>, numBatteries: Int): Long {
    return banks.sumOf { calculateJoltage(it, numBatteries) }
}

fun calculateJoltage(bank: String, numBatteries: Int): Long {
    val length = bank.length
    if (length < numBatteries) {
        throw IllegalArgumentException("Bank has less batteries ($length) than needed ($numBatteries)")
    }
    val chosenBatteries: MutableList<Int> = emptyList<Int>().toMutableList()
    var prevIdx = -1
    for (pos in 1..numBatteries) {
        var currIdx = prevIdx+1
        for (idx in prevIdx+2 until length - (numBatteries - pos)) {
            if (bank[idx] > bank[currIdx]) currIdx = idx
        }
        chosenBatteries += currIdx
        prevIdx = currIdx
    }
    return chosenBatteries.map { bank[it] }.joinToString(separator = "").toLong()
}
