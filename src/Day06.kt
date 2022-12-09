fun main() {

    fun findMarker(windowSize: Int, input: List<String>): Int {
        var index = windowSize
        for (line in input) {
            val windowed = line.toCharArray().toList().windowed(windowSize)
            for (window in windowed) {
                if (window.toSet().size == windowSize) {
                    return index
                }
                index++
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val windowSize = 4
        return findMarker(windowSize, input)
    }

    fun part2(input: List<String>): Int {
        val windowSize = 14
        return findMarker(windowSize, input)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
