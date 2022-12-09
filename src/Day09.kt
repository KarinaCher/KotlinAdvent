fun main() {

    fun nextPosition(direction: String, position: Pair<Int, Int>): Pair<Int, Int> = when (direction) {
        "R" -> position.first + 1 to position.second
        "L" -> position.first - 1 to position.second
        "U" -> position.first to position.second + 1
        "D" -> position.first to position.second - 1
        else -> throw UnsupportedOperationException("Direction $direction not defined")
    }

    fun tailPosition(head: Pair<Int, Int>, tail: Pair<Int, Int>, direction: String): Pair<Int, Int> {
        // should we move tail
        if ((Math.abs(head.first - tail.first) <= 1) && (Math.abs(head.second - tail.second)) <= 1) {
            return tail
        }

        var tailX = tail.first
        var tailY = tail.second

        // moved by X-axis, Y not changed
        if (head.second == tail.second || arrayOf("R", "L").contains(direction)) {
            tailX = if (head.first > tail.first) tailX + 1 else tailX - 1
            if (head.second != tail.second) {
                tailY = head.second
            }
        }

        // moved by Y-axis, X not changed
        if (head.first == tail.first || arrayOf("U", "D").contains(direction)) {
            tailY = if (head.second > tail.second) tailY + 1 else tailY - 1
            if (head.first != tail.first) {
                tailX = head.first
            }
        }

        return tailX to tailY
    }

    fun part1(input: List<String>): Int {
        val visited = mutableSetOf<Pair<Int, Int>>()

        var rope = arrayOf(0 to 0, 0 to 0)
        visited.add(rope.last())

        for (line in input) {
            val split = line.split(" ")
            val direction = split[0]
            val steps = split[1].toInt()

            for (step in 1..steps) {
                rope[0] = nextPosition(direction, rope.first())

                for (knotNum in 1 until rope.size) {
                    rope[knotNum] = tailPosition(rope[knotNum - 1], rope[knotNum], direction)
                }
                println(rope.joinToString(" "))

                visited.add(rope.last())
            }

        }

        return visited.size
    }

    fun part2(input: List<String>): Int {
        var result = 0;

        return result
    }

    val testInput = readInput("Day09_test")
    val testInput2 = readInput("Day09_2_test")
    check(part1(testInput) == 13)
    check(part1(testInput2) == 9)
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}