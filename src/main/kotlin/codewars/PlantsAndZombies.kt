// https://www.codewars.com/kata/5a5db0f580eba84589000979
fun main(args: Array<String>) {
    val res = PNZ.plantsAndZombies(
        arrayOf(
            "1         ",
            "SS        ",
            "SSS       ",
            "SSS       ",
            "SS        ",
            "1         "),
        arrayOf(
            intArrayOf(0,2,16),
            intArrayOf(1,3,19),
            intArrayOf(2,0,18),
            intArrayOf(4,2,21),
            intArrayOf(6,3,20),
            intArrayOf(7,5,17),
            intArrayOf(8,1,21),
            intArrayOf(8,2,11),
            intArrayOf(9,0,10),
            intArrayOf(11,4,23),
            intArrayOf(12,1,15),
            intArrayOf(13,3,22))
    )
    println(res)
}

object PNZ {
    abstract class GameObject
    abstract class Shooter(val fireRate: Int = 1) : GameObject()
    class NumberedShooter(fireRate: Int) : Shooter(fireRate)
    class SShooter : Shooter()
    class Zombie(var health: Int) : GameObject()

    fun plantsAndZombies(lawn:Array<String>,zombies:Array<IntArray>): Int? {
        val mapHeight = lawn.size
        val mapWidth = lawn.first().length
        val map = Array(mapHeight) { i ->
            Array<GameObject?>(mapWidth) { j ->
                val char = lawn[i][j]
                when {
                    char.isDigit() -> NumberedShooter(char.digitToInt())
                    char == 'S' -> SShooter()
                    else -> null
                }
            }
        }

        data class ZombieSpawnInfo(val row: Int, val health: Int)
        val zombiesSchedule: MutableMap<Int, Set<ZombieSpawnInfo>> = HashMap()
        var maxSpawnMove = 0
        zombies.forEach {
            val move = it[0]
            val row = it[1]
            val health = it[2]
            val set = zombiesSchedule[move] ?: HashSet()
            zombiesSchedule[move] = set + ZombieSpawnInfo(row, health)
            if (move > maxSpawnMove) maxSpawnMove = move
        }

        fun simulateShot(power: Int, initialRow: Int, initialColumn: Int, deltaY: Int = 0) {
            var i = initialRow
            var j = initialColumn
            var remainingPower = power

            while (remainingPower > 0) {
                i += deltaY
                j++
                if (j >= mapWidth || i < 0 || i >= mapHeight) break
                val gameObject = map[i][j]
                if (gameObject is Zombie) {
                    if (remainingPower >= gameObject.health) { // killing the zombie
                        remainingPower -= gameObject.health
                        map[i][j] = null
                    } else {
                        gameObject.health -= remainingPower
                        remainingPower = 0
                    }
                }
            }
        }

        fun simulateLineShot(power: Int, rowNumber: Int) {
            simulateShot(power, rowNumber, 0)
        }

        fun simulateSShooterShot(initialRow: Int, initialColumn: Int) {
            simulateShot(1, initialRow, initialColumn, -1)
            simulateShot(1, initialRow, initialColumn, 0)
            simulateShot(1, initialRow, initialColumn, 1)
        }

        // Simulation
        var moveNumber = 0

        fun printMap(comment: String = "") {
            println("Move number $moveNumber. $comment")
            println()
            for (i in 0 until mapHeight) {
                for (j in 0 until mapWidth) {
                    val gameObject = map[i][j]
                    print(
                        when (gameObject) {
                            is Zombie -> "${if (gameObject.health < 10) "z" else ""}${gameObject.health}"
                            is SShooter -> "#S"
                            is NumberedShooter -> "#${gameObject.fireRate}"
                            else -> "__"
                        }
                    )
                    print(" ")
                }
                println()
                println()
            }
        }
        while (true) {
            //printMap("Beginning of the move.")
            // Zombies move left
            for (i in 0 until mapHeight) {
                for (j in 0 until mapWidth) {
                    val gameObject = map[i][j]
                    if (gameObject is Zombie) {
                        if (j == 0) return moveNumber // zombie penetrated defences
                        else { // moving zombie to the left
                            map[i][j] = null
                            map[i][j - 1] = gameObject
                        }
                    }
                }
            }

            // Zombies appear
            zombiesSchedule[moveNumber]?.forEach {
                map[it.row][mapWidth - 1] = Zombie(it.health)
            }

            //printMap("After zombies move and appearing.")

            // Numbered shooters shoot
            for (i in 0 until mapHeight) {
                val line = map[i]
                val shotPower = line.filterIsInstance<NumberedShooter>().sumOf { it.fireRate }
                simulateLineShot(shotPower, i)
            }

            //printMap("After number shooters shot.")

            // S shooters shoot. We should iterate from right to left, then from top to bottom
            for (j in mapWidth - 1  downTo  0) {
                for (i in 0 until mapHeight) {
                    val gameObject = map[i][j]
                    if (gameObject is SShooter) simulateSShooterShot(i, j)
                }
            }

            //printMap("After S shooters shot.")

            // Check the game for finish
            if (moveNumber >= maxSpawnMove) {
                var haveZombies = false
                for (i in 0 until mapHeight) {
                    for (j in 0 until mapWidth) {
                        val gameObject = map[i][j]
                        if (gameObject is Zombie) {
                            haveZombies = true
                            break
                        }
                    }
                    if (haveZombies) break
                }
                if (!haveZombies) return null // All zombies killed
            }

            moveNumber++
        }

    }
}