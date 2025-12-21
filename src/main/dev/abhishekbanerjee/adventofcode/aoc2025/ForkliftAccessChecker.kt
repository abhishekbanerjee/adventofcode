package dev.abhishekbanerjee.adventofcode.aoc2025

fun numAccessibleRolls(grid: List<String>): Int {
    var numAccessible = 0
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (isAccessible(i, j, { a, b -> grid[a][b] }, grid.size, grid[i].length)) {
                numAccessible++
            }
        }
    }
    return numAccessible
}

fun numRemovableRolls(grid: List<String>): Int {
    val charGrid = grid.map { it.toMutableList() }
    var numRemovable = 0
    while (true) {
        var accessibleFound = false
        for (i in charGrid.indices) {
            for (j in charGrid[i].indices) {
                if (isAccessible(i, j, { a, b -> charGrid[a][b] }, charGrid.size, charGrid[i].size)) {
                    charGrid[i][j] = '.'
                    numRemovable += 1
                    accessibleFound = true
                }
            }
        }
        if (!accessibleFound) break
    }
    return numRemovable
}

fun isAccessible(i: Int, j: Int, gridAccessor: (Int, Int) -> Char, numRows: Int, numCols: Int): Boolean {
    if (gridAccessor(i, j) == '.') return false
    var numSurroundingRolls = 0
    for (di in -1..1) {
        for (dj in -1..1) {
            if (di == 0 && dj == 0) continue
            if (i + di < 0 || i + di > numRows - 1 || j + dj < 0 || j + dj > numCols - 1) continue
            if (gridAccessor(i + di, j + dj) == '.') continue
            numSurroundingRolls += 1
        }
    }
    return (numSurroundingRolls < 4)
}
