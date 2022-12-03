fun main() {

    fun part1(input: List<String>): Int {
        var result = 0
        for (rucksack in input) {

            val rp = rucksack.toList()
            val chunked = rp.chunked(rp.size / 2)
            val ic = findIncorrect(chunked)
            result += getPriority(ic)
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for (rucksack in input) {


        }

        return result
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
//    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun findIncorrect(chunked: List<List<Char>>): Char {
    for (it in chunked[0]) {
        if (chunked[1].contains(it)) {
            return it
        }
    }
    return '-'
}

fun getPriority(ch: Char): Int = if (ch in 'a'..'z') ch.minus(96).code else ch.minus(38).code
