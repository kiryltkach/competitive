import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sqrt

// https://www.codewars.com/kata/5886e082a836a691340000c3
fun main(args: Array<String>) {
    println(rectangleRotation(6,4))
}

fun rectangleRotation(a: Int, b: Int): Int {
    val sqrt2 = sqrt(2.0)
    val diagonalSideA = (floor(a.div(sqrt2)) * 2 + 1).toInt()
    val diagonalSideB = (floor(b.div(sqrt2)) * 2 + 1).toInt()

    var dots = diagonalSideA * diagonalSideB / 2

    val diff = abs(diagonalSideA - diagonalSideB)
    if (diff.rem(4) == 0) dots++
    return dots
}