import java.math.BigInteger

// https://www.codewars.com/kata/559a28007caad2ac4e000083
fun main(args: Array<String>) {
    println(SumFct.perimeter(7))
}

object SumFct {
    fun perimeter(n: Int): BigInteger {
        return fibonacciSum(n + 1) * BigInteger.valueOf(4)
    }
}

fun fibonacciSum(n: Int): BigInteger {
    var sum = BigInteger.ZERO
    var prevPrevFibonacciNumber = BigInteger.ZERO
    var prevFibonacciNumber = BigInteger.ZERO
    var currentFibonacciNumber: BigInteger
    for (i in 1 .. n) {
        currentFibonacciNumber = if (prevFibonacciNumber == BigInteger.ZERO) BigInteger.ONE else prevFibonacciNumber + prevPrevFibonacciNumber
        sum += currentFibonacciNumber
        prevPrevFibonacciNumber = prevFibonacciNumber
        prevFibonacciNumber = currentFibonacciNumber
    }
    return sum
}

