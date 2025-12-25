package dev.abhishekbanerjee.adventofcode.aoc2025

val WHITESPACE_REGEX = "\\s+".toRegex()

fun solveMath(lines: List<String>): Long {
    val numbers = lines.dropLast(1).map {
        it.trim().split(WHITESPACE_REGEX).map(String::toLong)
    }
    return lines.last().trim().split(WHITESPACE_REGEX).mapIndexed { i, op ->
        val operands = numbers.map { it[i] }
        when (op) {
            "+" -> operands.sum()
            "*" -> operands.fold(1L) { accumulator, element -> accumulator * element }
            else -> throw IllegalStateException("Unrecognized operation $op")
        }
    }.sum()
}

fun solveCephalopodMath(lines: List<String>): Long {
    var total = 0L
    
    val numbers = lines.dropLast(1)
    val operators = lines.last().trim().split(WHITESPACE_REGEX)
    
    // idx keeps state of the index within each numeric line, character by character.
    var idx = 0
    
    for (op in operators) {
        // We initialize the state differently when the operator is addition or multiplication.
        var result = when (op) {
            "+" -> 0L
            "*" -> 1L
            else -> throw IllegalStateException("Unrecognized operation $op")
        }
        
        // This loop moves forward in the numeric lines until we exhaust the numbers for the current operator.
        while (idx < numbers[0].length) {
            // We filter out the blank characters because the numbers on each line have different lengths.
            val numString = numbers.map { it[idx] }.filter { it != ' ' }.joinToString("")
            idx++
            
            // If we only encountered blank characters, then we're at the dividing column between succesive operations.
            if (numString.isEmpty()) break
            
            when (op) {
                "+" -> result += numString.toLong()
                "*" -> result *= numString.toLong()
                else -> throw IllegalStateException("Unrecognized operation $op")
            }
        }
        total += result
    }
    return total
}
