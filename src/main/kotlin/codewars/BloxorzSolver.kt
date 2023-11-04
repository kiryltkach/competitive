// https://www.codewars.com/kata/5a2a597a8882f392020005e5
fun main(args: Array<String>) {
    val arr = arrayOf(
        "1111000000000000000",
        "1B11111001111111100",
        "1111111111110001110",
        "0000111111000001100",
        "0000111111000001111",
        "00000110000000001X1",
        "0000011000000111111",
        "0000011000001111110",
        "0001111000111110000",
        "0001111100111100000",
        "0000111111111000000",
        "0000011111111100000",
        "0000000000011100000"
    )
    val ans = Blox.bloxSolver(arr)
    println(ans)
    println(ans.length)
}

object Blox {
    sealed interface MapItem {
        abstract class Steppable(var route: String? = null) : MapItem
        open class Tile : Steppable()
        object Hole : MapItem
        class EndHole : Steppable()
        object None : MapItem
    }

    data class RouteCandidate(val row: Int, val column: Int, val direction: Direction) {
        enum class Direction { LEFT, RIGHT, UP, DOWN }
    }

    fun bloxSolver(puzzle: Array<String>): String {
        val initialHeight = puzzle.size
        val initialWidth = puzzle[0].length
        val height = initialHeight * 2 - 1
        val width = initialWidth * 2 - 1

        val map = Array(height) { i ->
            Array(width) { j ->
                if (i.rem(2) == 1 && j.rem(2) == 1) MapItem.None else MapItem.Tile()
            }
        }

        fun addHoleSurroundings(mapRow: Int, mapColumn: Int) {
            if (mapRow > 0) map[mapRow - 1][mapColumn] = MapItem.Hole
            if (mapRow < height - 1) map[mapRow + 1][mapColumn] = MapItem.Hole
            if (mapColumn > 0) map[mapRow][mapColumn - 1] = MapItem.Hole
            if (mapColumn < width - 1) map[mapRow][mapColumn + 1] = MapItem.Hole
        }

        val tileQueue = ArrayDeque<Pair<Int, Int>>()
        var endHole: MapItem.EndHole? = null
        for (i in 0 until initialHeight) {
            for (j in 0 until initialWidth) {
                val char = puzzle[i][j]
                val mapI = i * 2
                val mapJ = j * 2
                when (char) {
                    '0' -> map[mapI][mapJ] = MapItem.Hole
                    'B' -> {
                        (map[mapI][mapJ] as MapItem.Tile).route = ""
                        tileQueue.add(Pair(mapI, mapJ))
                    }
                    'X' -> {
                        endHole = MapItem.EndHole()
                        map[mapI][mapJ] = endHole
                    }
                }
                if (map[mapI][mapJ] is MapItem.Hole) addHoleSurroundings(mapI, mapJ)
            }
        }

        fun checkCoordinates(row: Int, column: Int) = row in 0 until height && column in 0 until width

        fun processTile(row: Int, column: Int) {
            val currentRoute = (map[row][column] as MapItem.Steppable).route
            val candidates = HashSet<RouteCandidate>()

            if (row.rem(2) == 1) {
                candidates.add(RouteCandidate(row, column + 2, RouteCandidate.Direction.RIGHT))
                candidates.add(RouteCandidate(row, column - 2, RouteCandidate.Direction.LEFT))
            } else {
                candidates.add(RouteCandidate(row, column + 3, RouteCandidate.Direction.RIGHT))
                candidates.add(RouteCandidate(row, column - 3, RouteCandidate.Direction.LEFT))
            }

            if (column.rem(2) == 1) {
                candidates.add(RouteCandidate(row + 2, column, RouteCandidate.Direction.DOWN))
                candidates.add(RouteCandidate(row - 2, column, RouteCandidate.Direction.UP))
            } else {
                candidates.add(RouteCandidate(row + 3, column, RouteCandidate.Direction.DOWN))
                candidates.add(RouteCandidate(row - 3, column, RouteCandidate.Direction.UP))
            }

            candidates.removeIf {
                !checkCoordinates(it.row, it.column)
            }

            candidates.forEach {
                val obj = map[it.row][it.column]
                if (obj is MapItem.Steppable && obj.route == null) {
                    obj.route = currentRoute + when (it.direction) {
                        RouteCandidate.Direction.LEFT -> 'L'
                        RouteCandidate.Direction.RIGHT -> 'R'
                        RouteCandidate.Direction.UP -> 'U'
                        RouteCandidate.Direction.DOWN -> 'D'
                    }
                    tileQueue.add(Pair(it.row, it.column))
                }
            }

        }

        do {
            val pair = tileQueue.removeFirst()
            processTile(pair.first, pair.second)
        } while (endHole!!.route == null)

        return endHole.route!!
    }
}