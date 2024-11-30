package leetcode

// https://leetcode.com/problems/n-queens
fun main() {
    println(
        Solution().solveNQueens(9)
    )
}

class Solution {

    class Board(
        val cells: List<MutableList<Boolean>>
    ) {
        fun copy(): Board {
            return Board(
                cells = cells.map { rowList ->
                    rowList.map { it }.toMutableList()
                }
            )
        }

        fun isCellAttackedByQueenInUpperRows(row: Int, column: Int): Boolean {
            for (i in row - 1 downTo 0) {
                if (cells[i][column]) return true
                val ortogonalDistanceFromCell = row - i

                fun isThereQueen(column: Int): Boolean {
                    if (column < 0 || column > cells.lastIndex) return false
                    return cells[i][column]
                }

                if (
                    isThereQueen(column - ortogonalDistanceFromCell)
                    || isThereQueen(column + ortogonalDistanceFromCell)
                ) return true
            }
            return false
        }

        fun print() = cells.map { array ->
            buildString {
                array.forEach { cellValue ->
                    append(
                        when (cellValue) {
                            true -> 'Q'
                            false -> '.'
                        }
                    )
                }
            }
        }


        companion object {
            fun create(size: Int) = Board(
                cells = buildList {
                    repeat(size) {
                        add(
                            buildList {
                                repeat(size) {
                                    add(false)
                                }
                            }.toMutableList()
                        )
                    }
                }
            )
        }
    }

    fun solveNQueens(n: Int): List<List<String>> {
        val board = Board.create(size = n)
        val result = ArrayList<List<String>>()

        fun processRow(board: Board, row: Int) {
            for (i in 0 until n) {
                val cellAttacked = board.isCellAttackedByQueenInUpperRows(row = row, column = i)
                if (!cellAttacked) {
                    val boardCopy = board.copy()
                    boardCopy.cells[row][i] = true
                    if (row == boardCopy.cells.lastIndex) result.add(boardCopy.print())
                    else processRow(board = boardCopy, row = row + 1)
                }
            }
        }

        processRow(board = board, row = 0)
        return result
    }
}