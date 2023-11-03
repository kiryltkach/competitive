import java.lang.Exception
import kotlin.IllegalArgumentException

// https://www.codewars.com/kata/5abab55b20746bc32e000008
fun main(args: Array<String>) {
    val generation = arrayOf(
        Blob(0,4,3),
        Blob(0,7,5),
        Blob(2,0,2),
        Blob(3,7,2),
        Blob(4,3,4),
        Blob(5,6,2),
        Blob(6,7,1),
        Blob(7,0,3),
        Blob(7,2,1)
    )
    val generation2 = arrayOf(
        Blob(0,4,3),
        Blob(0,7,5),
        Blob(2,0,2),
        Blob(3,7,2),
        Blob(4,3,4),
        Blob(5,6,2),
        Blob(6,7,1),
        Blob(7,0,3),
        Blob(7,2,0)
    )
    val blobservation = Blobservation(10, 8)
    blobservation.populate(generation)
    blobservation.printMap()
    val newMap = blobservation.deepCopyMap()
    for (i in 0 until blobservation.h) {
        for (j in 0 until blobservation.w) {
            newMap[i][j] = 0
        }
    }
    blobservation.printMap()

}

data class Blob(var x: Int, var y: Int, var size: Int)

private fun Blobservation.printMap() {
    println()
    for (i in 0 until h) {
        for (j in 0 until w) {
            val blobSize = map[i][j]
            print(
                if (blobSize > 10) blobSize
                else if (blobSize > 0) "0$blobSize"
                else "--"
            )
            print(' ')
        }
        println()
    }
}

private class Blobservation(height: Int, width: Int = height) {
    val w = width
    val h = height

    private fun createMap() = Array(h) { IntArray(w) }

    var map = createMap()

    fun deepCopyMap(): Array<IntArray> {
        val newMap = createMap()
        for (i in 0 until h) {
            for (j in 0 until w) {
                newMap[i][j] = map[i][j]
            }
        }
        return newMap
    }

    // if invalid arguments are given to either `populate` or `move` methods, throw an IllegalArgumentException
    fun populate(blobs: Array<Blob>) {
        val newMap = deepCopyMap()
        try {
            blobs.forEach { blob ->
                val blobSize = blob.size
                if (blobSize < 1 || blobSize > 20) throw IllegalArgumentException()
                newMap[blob.x][blob.y] += blobSize
            }
            map = newMap
        } catch (e: Exception) {
            throw IllegalArgumentException()
        }
    }

    private fun findTargetForBlob(row: Int, column: Int, selfSize: Int): Pair<Int, Int>? {
        var lookupDistance = 1
        var foundTargetSize = 0
        var foundTargetCoordinates: Pair<Int, Int>? = null

        fun checkCell(row: Int, column: Int) {
            if (row in 0 until h && column in 0 until w) {
                val currentCellSize = map[row][column]
                if (currentCellSize in (foundTargetSize + 1) until selfSize) {
                    foundTargetSize = currentCellSize
                    foundTargetCoordinates = Pair(row, column)
                }
            }
        }

        fun makeProgression(from: Int, to: Int): IntProgression = if (from <= to) from .. to else from downTo to

        fun lookupRow(row: Int, columnFrom: Int, columnTo: Int) {
            for (j in makeProgression(columnFrom, columnTo)) checkCell(row, j)
        }

        fun lookupColumn(column: Int, rowFrom: Int, rowTo: Int) {
            for (i in makeProgression(rowFrom, rowTo)) checkCell(i, column)
        }

        val maxLookupDistance = maxOf(maxOf(column, row), maxOf(w - column - 1, h - row - 1))

        while (true) {
            if (lookupDistance > maxLookupDistance) return null

            lookupRow(row - lookupDistance, column, column + lookupDistance)
            lookupColumn(column + lookupDistance, row - lookupDistance + 1, row + lookupDistance)
            lookupRow(row + lookupDistance, column + lookupDistance - 1, column - lookupDistance)
            lookupColumn(column - lookupDistance, row + lookupDistance - 1, row - lookupDistance)
            lookupRow(row - lookupDistance, column - lookupDistance + 1, column - 1)

            if (foundTargetSize > 0) return foundTargetCoordinates
            lookupDistance++
        }
    }

    fun move(n: Int = 1) {
        if (n <= 0) throw IllegalArgumentException()
        repeat(n) {

            val newMap = createMap()

            fun findBlobNewCoordinates(row: Int, column: Int, targetForBlob: Pair<Int, Int>?): Pair<Int, Int> {
                if (targetForBlob == null) return Pair(row, column)
                val targetRow = targetForBlob.first
                val targetColumn = targetForBlob.second
                var newRow = row
                var newColumn = column
                if (targetRow > row) newRow++ else if (targetRow < row) newRow--
                if (targetColumn > column) newColumn++ else if (targetColumn < column) newColumn--
                return Pair(newRow, newColumn)
            }

            fun addBlobToNewMap(row: Int, column: Int, size: Int) {
                newMap[row][column] += size
            }

            for (i in 0 until h) {
                for (j in 0 until w) {
                    val blobSize = map[i][j]
                    if (blobSize > 0) {
                        val targetForBlob = findTargetForBlob(i, j, blobSize)
                        val newBlobCoordinates = findBlobNewCoordinates(i, j, targetForBlob)
                        addBlobToNewMap(newBlobCoordinates.first, newBlobCoordinates.second, blobSize)
                    }
                }
            }

            map = newMap
        }
    }

    fun printState(): List<IntArray> {
        val result = ArrayList<IntArray>()
        for (i in 0 until h) {
            for (j in 0 until w) {
                val blobSize = map[i][j]
                if (blobSize > 0) {
                    result.add(intArrayOf(i, j, blobSize))
                }
            }
        }
        return result
    }
}