package dev.abhishekbanerjee.adventofcode.aoc2025

/**
 * Counts how many gift-packing queries in the puzzle input are geometrically feasible.
 *
 * The input is split into two sections by blank lines:
 *   - Several shape blocks (one per gift type), each consisting of a label line ("0:", "1:", …)
 *     followed by rows of '#' and '.' characters describing the shape on a grid.
 *   - A final block of query lines, each in the form "WxH: c0 c1 c2 …", asking whether
 *     c_i copies of gift shape i can all be packed into a W×H box without overlapping.
 *
 * A query is counted as feasible if such a packing actually exists.
 */
fun possiblePackingCounter(text: String): Int {
    // Blank-line-separated blocks: all but the last are shape definitions;
    // the last block holds the query lines.
    val parts = text.trim().split("\n\n")
    val shapes = parts.dropLast(1).map { block ->
        // The first line of each block is just the shape index label ("0:", etc.); skip it.
        parseShape(block.lines().drop(1))
    }

    // Pre-compute every distinct orientation (rotation + flip) for each shape so we
    // don't regenerate them for every query.
    val allOrientations = shapes.map { generateOrientations(it) }

    return parts.last().lines().count { line ->
        if (line.isBlank()) return@count false

        // Parse "WxH: c0 c1 c2 …" into box dimensions and per-shape copy counts.
        val (dimensions, counts) = line.split(": ")
        val (width, height) = dimensions.split('x').map { it.toInt() }
        val presentCounts = counts.trim().split(' ').map { it.toInt() }

        // Fast area check: if the total cell-count of all requested pieces exceeds the box
        // area, no packing can possibly exist — skip the expensive backtracking entirely.
        val totalArea = presentCounts.zip(shapes).sumOf { (cnt, s) -> cnt * s.size }
        if (totalArea > width * height) return@count false

        // Full geometric feasibility check via backtracking.
        canPack(width, height, presentCounts, allOrientations)
    }
}

/**
 * A shape is stored as a sorted list of (row, col) offsets relative to its top-left corner.
 * Keeping it sorted makes equality comparisons between orientations reliable.
 */
private typealias Shape = List<Pair<Int, Int>>

/** Reads '#' cells from the ASCII grid lines and returns them as a normalized shape. */
private fun parseShape(lines: List<String>): Shape =
    buildList {
        lines.forEachIndexed { r, line ->
            line.forEachIndexed { c, ch -> if (ch == '#') add(r to c) }
        }
    }.let(::normalize)

/**
 * Shifts a set of cells so the minimum row and column are both 0, then sorts them.
 * This canonical form lets us detect when two orientations produce the same shape.
 */
private fun normalize(cells: List<Pair<Int, Int>>): Shape {
    val minR = cells.minOf { it.first }
    val minC = cells.minOf { it.second }
    return cells.map { (r, c) -> (r - minR) to (c - minC) }
        .sortedWith(compareBy({ it.first }, { it.second }))
}

/**
 * Rotates a shape 90° clockwise: (r, c) → (c, maxR - r).
 * The result is re-normalized so the top-left offset is always (0, 0).
 */
private fun rotate(shape: Shape): Shape {
    val maxR = shape.maxOf { it.first }
    return normalize(shape.map { (r, c) -> c to (maxR - r) })
}

/**
 * Mirrors a shape horizontally (flips left↔right): (r, c) → (r, maxC - c).
 * Combined with rotation this gives all 8 possible orientations (dihedral group D4).
 */
private fun flip(shape: Shape): Shape {
    val maxC = shape.maxOf { it.second }
    return normalize(shape.map { (r, c) -> r to (maxC - c) })
}

/**
 * Produces every geometrically distinct orientation of a shape.
 *
 * We generate 4 rotations and, for each, its mirror image — up to 8 candidates in total.
 * Storing them in a Set automatically deduplicates symmetric shapes (e.g. a square or a
 * shape with rotational symmetry) so we never try redundant placements.
 */
private fun generateOrientations(shape: Shape): List<Shape> {
    val seen = mutableSetOf<Shape>()
    var cur = shape
    repeat(4) {
        seen.add(cur)          // one rotation
        seen.add(flip(cur))    // its mirror
        cur = rotate(cur)      // advance to next 90° rotation
    }
    return seen.toList()
}

/**
 * Returns true if all requested gift copies can be placed inside a W×H box without overlap.
 *
 * Pieces are grouped by shape type and the groups are sorted largest-first so that the
 * most constrained placements are attempted early, letting area pruning cut the search
 * tree as soon as possible.
 */
private fun canPack(w: Int, h: Int, counts: List<Int>, allOrientations: List<List<Shape>>): Boolean {
    val groups = counts.zip(allOrientations)
        .filter { (cnt, _) -> cnt > 0 }
        .sortedByDescending { (_, orients) -> orients[0].size }   // largest pieces first

    val grid = Array(h) { BooleanArray(w) }   // false = empty cell
    return packGroups(grid, w, h, groups, 0, 0, 0)
}

/**
 * Core backtracking routine.
 *
 * Works through [groups] one shape type at a time. Within each group it places the
 * required number of copies one at a time, always trying positions in reading order
 * (left→right, top→bottom) and backtracking whenever a branch leads to a dead end.
 *
 * @param gi       index of the shape group currently being placed
 * @param placed   how many copies of groups[gi] have already been placed this call
 * @param minPos   smallest flat index (row * w + col) the next copy may start at;
 *                 enforces a non-decreasing placement order among identical copies
 *                 so we never count the same physical arrangement twice
 */
private fun packGroups(
    grid: Array<BooleanArray>,
    w: Int, h: Int,
    groups: List<Pair<Int, List<Shape>>>,
    gi: Int, placed: Int, minPos: Int
): Boolean {
    // All groups fully placed — success.
    if (gi == groups.size) return true

    val (count, orientations) = groups[gi]

    // Current group is done; move on to the next shape type.
    if (placed == count) return packGroups(grid, w, h, groups, gi + 1, 0, 0)

    // Area pruning: total cells still needed vs. free cells remaining.
    // If there isn't enough room even in theory, abandon this branch immediately.
    val remPieceArea = groups.drop(gi).withIndex().sumOf { (i, grp) ->
        val (cnt, orients) = grp
        val rem = if (i == 0) cnt - placed else cnt   // subtract already-placed copies of current group
        rem * orients[0].size
    }
    val freeArea = grid.sumOf { row -> row.count { !it } }
    if (remPieceArea > freeArea) return false

    // Try every starting position at or after minPos, in reading order.
    for (pos in minPos until h * w) {
        val r = pos / w
        val c = pos % w
        for (orient in orientations) {
            if (canPlace(grid, w, h, r, c, orient)) {
                place(grid, r, c, orient, true)
                // The next copy of the same shape must start at pos or later,
                // which prevents counting permutations of identical placements.
                if (packGroups(grid, w, h, groups, gi, placed + 1, pos)) {
                    place(grid, r, c, orient, false)   // undo before returning (clean state)
                    return true
                }
                place(grid, r, c, orient, false)       // backtrack
            }
        }
    }
    return false
}

/**
 * Checks whether [shape] (given as offsets from origin) can be placed with its origin at
 * (r, c) without going out of bounds or overlapping any already-occupied cell.
 */
private fun canPlace(grid: Array<BooleanArray>, w: Int, h: Int, r: Int, c: Int, shape: Shape): Boolean =
    shape.all { (dr, dc) ->
        val nr = r + dr; val nc = c + dc
        nr in 0 until h && nc in 0 until w && !grid[nr][nc]
    }

/** Marks or unmarks all cells of [shape] (offset from origin at (r, c)) in the grid. */
private fun place(grid: Array<BooleanArray>, r: Int, c: Int, shape: Shape, v: Boolean) {
    shape.forEach { (dr, dc) -> grid[r + dr][c + dc] = v }
}
