# Advent of Code 2025

Solutions to [Advent of Code 2025](https://adventofcode.com/2025) written in Kotlin.

## Structure

```
src/
├── main/
│   ├── dev/abhishekbanerjee/adventofcode/aoc2025/   # Solution source files
│   └── resources/day{01-12}/input.txt               # Puzzle inputs
└── test/
    ├── dev/abhishekbanerjee/adventofcode/aoc2025/   # Unit tests per day
    └── resources/day{01-12}test.txt                 # Sample inputs from problem statements
```

Each day has a dedicated solver class and a corresponding test file that validates against the sample input from the problem statement.

## Days

| Day | Title              | Part 1                                      | Part 2                                        |
|-----|--------------------|---------------------------------------------|-----------------------------------------------|
| 1   | Secret Entrance    | Dial rotations resulting in zero            | Dial rotations passing through zero           |
| 2   | ID Validation      | Invalid IDs (repeating pattern ×2)          | Invalid IDs (generic repeating pattern)       |
| 3   | Lobby              | Total joltage (factor 2)                    | Total joltage (factor 12)                     |
| 4   | Printing Dept.     | Accessible rolls of paper                   | Removable rolls of paper                      |
| 5   | Cafeteria          | Count fresh IDs                             | Total fresh IDs                               |
| 6   | Trash Compactor    | Solve math worksheet                        | Solve cephalopod math                         |
| 7   | Laboratories       | Count tachyon splits                        | Count tachyon timelines                       |
| 8   | Playground         | Three largest circuit sizes                 | Cross-product of last connection              |
| 9   | Movie Theater      | Largest area                                | Largest contained area                        |
| 10  | Factory            | Fewest button presses (toggle mode)         | Fewest button presses (joltage mode)          |
| 11  | Reactor            | Count paths                                 | Count paths via required nodes                |
| 12  | Christmas Tree Farm| Possible packing count                      | —                                             |

## Running

**Prerequisites:** Java 17+, Maven 3.x

Build and test:

```bash
mvn test
```

Run all solutions (outputs answers to stdout):

```bash
mvn compile exec:java -Dexec.mainClass="dev.abhishekbanerjee.adventofcode.aoc2025.MainKt"
```

Or run from your IDE — `Main.kt` is the entry point. Uncomment the day you want to run.

## Tech

- **Language:** Kotlin 2.3
- **Build:** Maven
- **Testing:** JUnit Jupiter 5
- **Optimization:** Google OR-Tools (Day 12 only — all other days use vanilla Kotlin with no external dependencies)
- **AI assistance:** Day 12 solution written with [Claude](https://claude.ai) — all other days hand-solved
