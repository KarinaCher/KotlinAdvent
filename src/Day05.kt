fun main() {


    fun parseData(input: List<String>): Pair<Map<Int, MutableList<Char>>, MutableList<Move>> {
        var stacks: Map<Int, MutableList<Char>> = mutableMapOf()

        var moves = mutableListOf<Move>()
        var boxes = mutableListOf<String>()
        var moveRegex = Regex("move (\\d{1,2}) from (\\d) to (\\d)")

        for (line in input) {
            if (line.contains("[")) {
                boxes.add(line)
            } else if (line.trim().startsWith("1")) {
                stacks = line.trim().split("   ")
                    .associate { it.toInt() to mutableListOf() }
            } else if (line.matches(moveRegex)) {
                val matcher = moveRegex.find(line)!!.groupValues
                moves.add(Move(matcher.get(1).toInt(), matcher.get(2).toInt(), matcher.get(3).toInt()))
            }
        }

        for (line in boxes) {
            val chunked = line.chunked(4)
            var i = 1
            for (chunk in chunked) {
                if (chunk.isNotBlank()) {
                    stacks.get(i)!!.add(0, chunk.get(1))
                }
                i++
            }
        }
        return stacks to moves
    }

    fun part1(input: List<String>): String {

        val parseData = parseData(input)
        var stacks: Map<Int, MutableList<Char>> = parseData.first
        var moves = parseData.second

        moves.forEach {
            val from = stacks.get(it.from)!!

            var start = from.size.minus(it.crateCount)
            val end = from.size

            for (index in end.minus(1) downTo start) {
                val item = from.removeAt(index)
                stacks.get(it.to)?.add(item)
            }
        }

        return stacks.map {
            if (it.value.isEmpty()) " " else it.value.last().toString()
        }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val parseData = parseData(input)
        var stacks: Map<Int, MutableList<Char>> = parseData.first
        var moves = parseData.second

        moves.forEach { move ->
            val from = stacks.get(move.from)!!

            var start = from.size.minus(move.crateCount)
            val end = from.size

            val subList = from.subList(start, end).chunked(3)

            subList.forEach {
                stacks.get(move.to)?.addAll(it)
            }

            for (index in end.minus(1) downTo start) {
                from.removeAt(index)
            }
        }

        return stacks.map {
            if (it.value.isEmpty()) " " else it.value.last().toString()
        }.joinToString("")
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Move(val crateCount: Int, val from: Int, val to: Int)

