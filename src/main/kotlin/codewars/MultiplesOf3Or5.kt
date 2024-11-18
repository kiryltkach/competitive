package codewars

// https://www.codewars.com/kata/514b92a657cdc65150000006/kotlin
fun main() {
    println(solution(10))
}

fun solution(number: Int): Int {
    fun sumOfProgression(first: Int, last: Int, num: Int): Long {
        return (first + last) * num.toLong() / 2
    }

    fun sum(number: Int, divisible: Int): Long {
        val numMinus1 = number - 1
        val last = numMinus1 - numMinus1 % divisible
        val first = divisible
        val num = last / divisible
        if (last <= 0) return 0
        return sumOfProgression(first, last, num)
    }

    fun sum(divisible: Int): Long = sum(number, divisible)

    return (sum(3) + sum(5) - sum(15)).toInt()
}