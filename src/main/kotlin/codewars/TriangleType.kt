package codewars

import kotlin.math.pow

/* Should return ᐃ type:
    0 : if ᐃ cannot be made with given sides
    1 : acute ᐃ
    2 : right ᐃ
    3 : obtuse ᐃ
*/
fun triangleType(a: Double, b: Double, c: Double): Int {
    fun calculateType(biggestSide: Double, notBiggestSide: Double, notBiggestSide2: Double): Int {
        val biggestSideSquared = biggestSide.pow(2)
        val sumOfSquaresOfSmallerSides = notBiggestSide.pow(2) + notBiggestSide2.pow(2)
        return when {
            biggestSide >= notBiggestSide + notBiggestSide2 -> 0
            biggestSideSquared < sumOfSquaresOfSmallerSides -> 1
            biggestSideSquared == sumOfSquaresOfSmallerSides -> 2
            else -> 3
        }
    }

    return when {
        a >= b && a >= c -> calculateType(a, b, c)
        b >= a && b >= c -> calculateType(b, a, c)
        else -> calculateType(c, a, b)
    }
}