val LOST = 0
val DRAW = 3
val WON = 6

val ROCK = "Rock"
val PAPER = "Paper"
val SCISSORS = "Scissors"

fun main() {

    fun part1(input: List<String>): Int {
        var round = 1
        var reward = 0;
        for (line in input) {
            val part = line.split(" ")
            val me = Me.valueOf(part[1])
            val opponent = Opponent.valueOf(part[0])
            reward += play(opponent, me)
                .plus(me.reward)
            round++
        }
        return reward
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

/**
 * Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
 */
fun play(opponent: Opponent, me: Me): Int {
    if (opponent.value.equals(me.value)) {
        return DRAW
    }
    if (opponent == Opponent.A && me == Me.Y) {
        return WON
    }
    if (opponent == Opponent.C && me == Me.X) {
        return WON
    }
    if (opponent == Opponent.B && me == Me.Z) {
        return WON
    }
    return LOST
}

enum class Opponent(val value: String) {
    A(ROCK),
    B(PAPER),
    C(SCISSORS);
}

enum class Me(val value: String, val reward: Int) {
    X(ROCK, 1),
    Y(PAPER, 2),
    Z(SCISSORS, 3);
}