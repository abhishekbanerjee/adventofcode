package dev.abhishekbanerjee.adventofcode.aoc2025

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver

enum class OptimizerMode {
    TOGGLE,
    JOLTAGE,
}

data class MachineState(
    val target: Long,
    val buttons: List<Long>,
    val joltageRequirements: List<Long>
)

fun readMachineStates(lines: List<String>): List<MachineState> =
    lines.map { line ->
        val components = line.split(' ')
        val target = readTarget(components[0].drop(1).dropLast(1))
        val buttons =
            components.drop(1).dropLast(1).map { readButton(it.drop(1).dropLast(1).split(',')) }.filter { it != 0L }
        val joltageRequirements = components.last().drop(1).dropLast(1).split(',').map { it.toLong() }
        MachineState(target, buttons, joltageRequirements)
    }

fun fewestPresses(machineStates: List<MachineState>, optimizerMode: OptimizerMode): Int =
    machineStates.sumOf { fewestPresses(it, optimizerMode) }

/** A private lazy variable that acts as a one time initialization block */
private val orToolsInitializer by lazy {
    Loader.loadNativeLibraries()
    true
}

private fun fewestPresses(machineState: MachineState, optimizerMode: OptimizerMode): Int {
    when (optimizerMode) {
        OptimizerMode.TOGGLE -> {
            if (machineState.target == 0L) return 0

            val queue = ArrayDeque<Pair<Long, Int>>().apply { add(0L to 0) }
            val visited = mutableSetOf(0L)

            while (queue.isNotEmpty()) {
                val (state, presses) = queue.removeFirst()

                for (button in machineState.buttons) {
                    val next = state xor button
                    if (next == machineState.target) return presses + 1
                    if (visited.add(next)) {
                        queue.add(next to presses + 1)
                    }
                }
            }
            throw IllegalStateException("${machineState.target} unreachable!")
        }
        OptimizerMode.JOLTAGE -> {
            // Mentioning the private value to initialize the native libraries once.
            orToolsInitializer

            val ipSolver = MPSolver.createSolver("SCIP") ?: throw IllegalStateException("Can not create an IP solver!")

            // Set the variables. How many times should we press each button (0 to infinity).
            val presses = machineState.buttons.indices.map {
                ipSolver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "button_$it")
            }

            // Set the constraints. For each bulb index, the sum of the button presses at that index must = the joltage
            // target for that index.
            for (i in 0 until machineState.joltageRequirements.size) {
                val joltage = machineState.joltageRequirements[i].toDouble()
                val constraint = ipSolver.makeConstraint(joltage, joltage, "bulb_$i")
                machineState.buttons.forEachIndexed { idx, button ->
                    // If the button affects this bulb, set the corresponding coefficient to 1
                    if ((button shr i) and 1L == 1L) {
                        constraint.setCoefficient(presses[idx], 1.0)
                    }
                }
            }

            // Set the objective - minimize total presses.
            val objective = ipSolver.objective()
            presses.forEach { objective.setCoefficient(it, 1.0) }
            objective.setMinimization()

            // Run the IP solver and return the results.
            val resultStatus = ipSolver.solve()
            if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
                return presses.sumOf { it.solutionValue().toInt() }
            } else {
                throw IllegalStateException("No solution found!")
            }
        }
    }
}

/**
 * Converts the target string into a number representation, using a binary representation, where switched on bulbs ('#")
 * map to '1'.
 */
private fun readTarget(targetString: String): Long =
    targetString.foldIndexed(0L) { index, acc, ch ->
        if (ch == '#') acc + (1 shl index) else acc
    }

/** Interprets the list of indexed bulbs to be toggled as a binary representation. */
private fun readButton(toggledBulbs: List<String>): Long =
    toggledBulbs.fold(0L) { acc, b -> acc + (1 shl b.toInt()) }
