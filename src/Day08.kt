fun main() {
    /*
    A tree is visible if all of the other trees between it and an edge of the grid are shorter than it
     */
    fun isVisibleTree(x: Int, y: Int, forest: List<String>): Boolean {
        val line = forest.get(x).toCharArray().toMutableList()
        val column = forest.map { it.get(y) }.toMutableList()

        val treeHeight = line.get(y).digitToInt()
        // remove tree from calculation
        line.removeAt(y)
        column.removeAt(x)

        val fromTop = column.subList(0, x)
        val fromBottom = column.subList(x, column.size)
        val fromLeft = line.subList(0, y)
        val fromRight = line.subList(y, line.size)

//println("Tree $x,$y height $treeHeight $fromTop,$fromBottom $fromLeft,$fromRight]")
        val top = fromTop.stream().mapToInt { it.digitToInt() }.max()
        if (!top.isPresent || top.asInt < treeHeight) {
            return true
        }
        val bottom = fromBottom.stream().mapToInt { it.digitToInt() }.max()
        if (!bottom.isPresent || bottom.asInt < treeHeight) {
            return true
        }
        val left = fromLeft.stream().mapToInt { it.digitToInt() }.max()
        if (!left.isPresent || left.asInt < treeHeight) {
            return true
        }
        val right = fromRight.stream().mapToInt { it.digitToInt() }.max()
        if (!right.isPresent || right.asInt < treeHeight) {
            return true
        }
        return false
    }

    fun getVisibleTrees(input: List<String>): List<Pair<Int, Int>> {
        var result = mutableListOf<Pair<Int, Int>>()
        for (x in 0 until input.size) {
            for (y in 0 until input.get(0).length) {
                if (isVisibleTree(x, y, input)) {
                    result.add(x to y)
                }
            }
        }

        return result
    }

    fun getScenicScore(x: Int, y: Int, forest: List<String>): Int {
        val line = forest.get(x).toCharArray().toMutableList()
        val column = forest.map { it.get(y) }.toMutableList()

        val treeHeight = line.get(y).digitToInt()
        // remove tree from calculation
        line.removeAt(y)
        column.removeAt(x)

        val fromTop = column.subList(0, x)
        val fromBottom = column.subList(x, column.size)
        val fromLeft = line.subList(0, y)
        val fromRight = line.subList(y, line.size)

        var topSize = 0
        var bottomSize = 0
        var leftSize = 0
        var rightSize = 0
        val top = fromTop.reversed().takeWhile {
            topSize++
            it.digitToInt() < treeHeight
        }
        val bottom = fromBottom.takeWhile {
            bottomSize++
            it.digitToInt() < treeHeight
        }
        val left = fromLeft.reversed().takeWhile {
            leftSize++
            it.digitToInt() < treeHeight
        }
        val right = fromRight.takeWhile {
            rightSize++
            it.digitToInt() < treeHeight
        }

//        println("Tree $x,$y height $treeHeight $top,$bottom $left,$right")

        return topSize * bottomSize * leftSize * rightSize
    }


    fun part1(input: List<String>): Int {
        return getVisibleTrees(input).size
    }

    fun part2(input: List<String>): Int {
        var result = 0;
        for (x in 0 until input.size) {
            for (y in 0 until input.get(0).length) {
                result = Math.max(result, getScenicScore(y, x, input))
            }
        }

        return result
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
