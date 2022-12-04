fun main() {

    fun part1(input: List<String>): Int {
        var result = 0

        input.forEach {
            val elves = it.split(",")
            val firstElves = elves[0].split("-")
            val secondElves = elves[1].split("-")

            val firstElvesSections = (firstElves[0].toInt()..firstElves[1].toInt()).toList()
            val secondElvesSections = (secondElves[0].toInt()..secondElves[1].toInt()).toList()

            if (firstElvesSections.containsAll(secondElvesSections)
                || secondElvesSections.containsAll(firstElvesSections)) {
                result++
            }

        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0


        return result
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
//    check(part2(testInput) == 70)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}