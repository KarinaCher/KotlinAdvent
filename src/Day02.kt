val LOST = 0
val DRAW = 3
val WON = 6

val ROCK = "Rock"
val PAPER = "Paper"
val SCISSORS = "Scissors"

val STRATEGY_LOST = "Lost"
val STRATEGY_DRAW = "Draw"
val STRATEGY_WON = "Won"

fun main() {

    fun part1(input: List<String>): Int {
        var round = 1
        var reward = 0;
        for (line in input) {
            val part = line.split(" ")
            val me = Me.valueOf(part[1])
            val opponent = Opponent.valueOf(part[0])
            reward += play(opponent.value, me.value)
                .plus(me.reward)
            round++
        }
        return reward
    }

    fun part2(input: List<String>): Int {
        var round = 1
        var reward = 0;
        for (line in input) {
            val part = line.split(" ")

            val myStrategy = MyStrategy.valueOf(part[1])
            val opponent = Opponent.valueOf(part[0])

            val me = choice(myStrategy, opponent)

            reward += play(opponent.value, me.value)
                .plus(me.reward)

            round++
        }
        return reward
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

/**
 * Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
 */
fun play(opponent: String, me: String): Int {
    if (opponent.equals(me)) {
        return DRAW
    }
    if (opponent.equals(Opponent.A.value) && me.equals(Me.Y.value)) {
        return WON
    }
    if (opponent.equals(Opponent.C.value) && me.equals(Me.X.value)) {
        return WON
    }
    if (opponent.equals(Opponent.B.value) && me.equals(Me.Z.value)) {
        return WON
    }
    return LOST
}

fun choice(myStrategy: MyStrategy, opponent: Opponent): Me {
    if (myStrategy.value.equals(STRATEGY_DRAW)) {
        return when (opponent.value) {
            ROCK -> Me.X
            PAPER -> Me.Y
            SCISSORS -> Me.Z
            else -> throw IllegalArgumentException()
        }
    }
    if (myStrategy.value.equals(STRATEGY_WON)) {
        return when (opponent.value) {
            ROCK -> Me.Y
            PAPER -> Me.Z
            SCISSORS -> Me.X
            else -> throw IllegalArgumentException()
        }
    } else {
        return when (opponent.value) {
            ROCK -> Me.Z
            PAPER -> Me.X
            SCISSORS -> Me.Y
            else -> throw IllegalArgumentException()
        }
    }
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

enum class MyStrategy(val value: String, val reward: Int) {
    X(STRATEGY_LOST, LOST),
    Y(STRATEGY_DRAW, DRAW),
    Z(STRATEGY_WON, WON);
}