package dev.abhishekbanerjee.adventofcode.aoc2025

class Point2(val x: Int, val y: Int) {
    companion object {
        fun toPoint(values: List<String>): Point2 {
            return Point2(values[0].toInt(), values[1].toInt())
        }
    }
}

class Point3(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun toPoint(values: List<String>): Point3 {
            return Point3(values[0].toInt(), values[1].toInt(), values[2].toInt())
        }
    }
}

fun to2dPoints(lines: List<String>): List<Point2> {
    return toPoints(lines, Point2::toPoint)
}

fun to3dPoints(lines: List<String>): List<Point3> {
    return toPoints(lines, Point3::toPoint)
}

private fun <R> toPoints(lines: List<String>, listToPoint: (List<String>) -> R): List<R> {
    return lines.map { line -> line.trim().split(",").let { listToPoint(it) } }
}
