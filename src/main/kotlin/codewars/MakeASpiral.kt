// https://www.codewars.com/kata/534e01fbbb17187c7e0000c6
fun main(args: Array<String>) {
    val arr = Spiralizor.spiralize(22)
    arr.print()
}

fun Array<ByteArray>.print() {
    for (i in 0 until size) {
        for (j in 0 until size) print(if (this[i][j] == 1.toByte()) '0' else '.')
        println()
    }
}

object Spiralizor {
    fun spiralize(n: Int): Array<ByteArray> {
        val arr = Array(n) { ByteArray(n) }
        val lastIndex = n - 1

        // Returns last column index
        fun goRight(rowPos: Int, colPos: Int): Int {
            var colIndex = colPos
            while (true) {
                if (colIndex > lastIndex) return lastIndex
                if (colIndex + 1 <= lastIndex && arr[rowPos][colIndex + 1] == 1.toByte()) return colIndex - 1
                arr[rowPos][colIndex] = 1
                colIndex++
            }
        }

        // Returns last row index
        fun goDown(rowPos: Int, colPos: Int): Int {
            var rowIndex = rowPos
            while (true) {
                if (rowIndex > lastIndex) return lastIndex
                if (rowIndex + 1 <= lastIndex && arr[rowIndex + 1][colPos] == 1.toByte()) return rowIndex - 1
                arr[rowIndex][colPos] = 1
                rowIndex++
            }
        }

        // Returns last column index
        fun goLeft(rowPos: Int, colPos: Int): Int {
            var colIndex = colPos
            while (true) {
                if (colIndex < 0) return 0
                if (colIndex - 1 >= 0 && arr[rowPos][colIndex - 1] == 1.toByte()) return colIndex + 1
                arr[rowPos][colIndex] = 1
                colIndex--
            }
        }

        // Returns last row index
        fun goUp(rowPos: Int, colPos: Int): Int {
            var rowIndex = rowPos
            while (true) {
                if (rowIndex - 1 >= 0 && arr[rowIndex - 1][colPos] == 1.toByte()) return rowIndex + 1
                arr[rowIndex][colPos] = 1
                rowIndex--
            }
        }

        var rowPos = 0
        var colPos = 0
        var newPos: Int
        while (true) {
            newPos = goRight(rowPos, colPos)
            if (newPos - colPos <= 1) break
            colPos = newPos

            newPos = goDown(rowPos, colPos)
            if (newPos - rowPos <= 1) break
            rowPos = newPos

            newPos = goLeft(rowPos, colPos)
            if (colPos - newPos <= 1) break
            colPos = newPos

            newPos = goUp(rowPos, colPos)
            if (rowPos - newPos <= 1) break
            rowPos = newPos
        }

        return arr
    }
}