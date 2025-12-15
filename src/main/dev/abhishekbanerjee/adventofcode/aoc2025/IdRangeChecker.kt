package dev.abhishekbanerjee.adventofcode.aoc2025

fun invalidIdsSumRange(idRanges: List<String>, isInvalid: (Long) -> Boolean): Long {
    return idRanges.sumOf { idRange ->
        val (start, end) = idRange.split('-').map { it.toLong() }
        invalidIdsSum(start, end, isInvalid)
    }
}

fun invalidIdsSum(start: Long, end: Long, isInvalid: (Long) -> Boolean): Long {
    val invalids = (start..end).filter { num -> isInvalid(num) }
    return invalids.sum()
}

fun isRepeatingPatternTwice(num: Long): Boolean {
    // Convert to list of digits for easy indexing.
    val digits = num.toString().map { it.digitToInt() }
    val length = digits.size

    // If the length is odd, this cannot be a twice-repeated pattern.
    if (length % 2 != 0) return false

    // Check if every digit in the first half is repeated in its corresponding position after the halfway line.
    return (0 until length/2).all { digits[it] == digits[length/2 + it] }
}

fun isRepeatingPatternGeneric(num: Long): Boolean {
    // Convert to list of digits for easy indexing.
    val digits = num.toString().map { it.digitToInt() }
    val length = digits.size

    // For all possible prefix lengths:
    // 1. If a candidate prefix length does not divide the length of the number, then we eliminate this as a candidate.
    // 2. Of the remaining, we check if all the digits of the number after the prefix match the appropriate location
    //    inside the prefix.
    return (1..length/2)
        .filter { prefixLen -> length % prefixLen == 0 }
        .any { prefixLen ->
            (prefixLen until length).all { idx -> digits[idx] == digits[idx % prefixLen] }
        }
}
