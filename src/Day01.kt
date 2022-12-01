import java.util.Comparator

fun main() {
    fun mapElvesCalories(input: List<String>): MutableMap<Int, Int> {
        var elvesCalories = mutableMapOf<Int, Int>()
        var index = 1
        for (line in input) {
            if (line.isEmpty()) {
                index = index.plus(1)
                elvesCalories.put(index.inc(), 0)
            } else {
                elvesCalories[index] = elvesCalories[index]?.plus(line.toInt()) ?: 0
            }
        }
        return elvesCalories
    }

    fun part1(input: List<String>): Int {
        var elvesCalories = mapElvesCalories(input)
        return elvesCalories.maxOf {e -> e.value}
    }

    fun part2(input: List<String>): Int {
        var elvesCalories = mapElvesCalories(input)
        return elvesCalories.toList()
            .sortedBy { it.second }
            .reversed()
            .stream()
            .limit(3)
            .mapToInt { it.second }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 70613)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
