package dev.abhishekbanerjee.adventofcode.aoc2025

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun largestArea(points: List<Point2>): Long {
    return points.indices.flatMap { i ->
        (i + 1 until points.size).map { j ->
            rectangleArea(points[i], points[j])
        }
    }.maxOrNull() ?: 0L
}

fun largestContainedArea(points: List<Point2>): Long {
    return points.indices.flatMap { i ->
        (i + 1 until points.size).mapNotNull { j ->
            if (isValidRectangle(points[i], points[j], points)) rectangleArea(points[i], points[j]) else null
        }
    }.maxOrNull() ?: 0L
}

/**
 * Checks if the rectangle with the given points as corners is fully inside the polygon denoted by the list of points.
 * Two checks are necessary to determine this:
 * 1. The center point of the rectangle is inside the polygon.
 * 2. No edge of the polygon crosses the boundaries of the rectangle.
 *
 * Note: this check assumes that the polygon is simple (does not cross over itself) and rectilinear (all edges parallel
 * to the axes).
 */
private fun isValidRectangle(point1: Point2, point2: Point2, points: List<Point2>): Boolean {
    val x1 = min(point1.x, point2.x)
    val x2 = max(point1.x, point2.x)
    val y1 = min(point1.y, point2.y)
    val y2 = max(point1.y, point2.y)

    val midX = (x1 + x2) / 2.0
    val midY = (y1 + y2) / 2.0

    return isPointInside(midX, midY, points) && noEdgesIntersectRectangle(x1, x2, y1, y2, points)
}

/**
 * We will use the ray tracing algorithm to determine if the given point with the input co-ordinates x and y is inside
 * the polygon denoted by the list of points. The way the algorithm works is by casting a ray in an arbitrary direction
 * from the point (in our case, this would be to the right, so at the same "y" but with increasing "x") and checking how
 * many of the line segment boundaries this ray intersects. If this number is odd, the point is in the interior of the
 * polygon.
 *
 * Note: this check assumes that the polygon is simple and rectilinear.
 */
private fun isPointInside(x: Double, y: Double, vertices: List<Point2>): Boolean {
    return vertices.indices.filter { i ->
        val u = vertices[i]
        val v = vertices[(i + 1) % vertices.size]

        // There are three checks that happen here:
        // 1. Is the polygon edge vertical (same x co-ordinate)? Only vertical edge intersections count.
        // 2. Are the two ends of the polygon edge on opposite sides of the point (one y co-ordinate is greater and one
        //    is lesser)?
        // 3. Is the polygon edge to the right (greater x co-ordinate) of the point.
        ((u.x == v.x) && (u.y > y != v.y > y) && u.x > x)
    }.size % 2 != 0
}

/**
 * For every edge of the polygon, we will check if said edge overlaps the rectangle denoted by the given co-ordinates.
 * x1, x2 (where x1 <= x2) are the x boundaries and y1, y2 (where y1 <= y2) are the y boundaries.
 *
 * Note: this check assumes that the polygon is simple and rectilinear.
 */
private fun noEdgesIntersectRectangle(x1: Int, x2: Int, y1: Int, y2: Int, vertices: List<Point2>): Boolean {
    return vertices.indices.none { i ->
        val u = vertices[i]
        val v = vertices[(i + 1) % vertices.size]

        // To check overlap, we check if the line segment's x-interval overlaps the rectangle's, and same with the y.
        val xOverlaps = max(u.x, v.x) > x1 && min(u.x, v.x) < x2
        val yOverlaps = max(u.y, v.y) > y1 && min(u.y, v.y) < y2

        // If there's overlap in both dimensions, this line segment crosses the rectangle's boundaries.
        xOverlaps && yOverlaps
    }
}

private fun rectangleArea(p1: Point2, p2: Point2): Long {
    return (abs(p1.x - p2.x) + 1).toLong() * (abs(p1.y - p2.y) + 1).toLong()
}
