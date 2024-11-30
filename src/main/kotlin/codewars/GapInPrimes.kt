// https://www.codewars.com/kata/561e9c843a2ef5a40c0000a4
fun main(args: Array<String>) {
    println(gap(138, 2, 100000000).contentToString())
}

//if these g-gap prime numbers don't exist return []
fun gap(g:Int, m:Long, n:Long):LongArray {

    var prevPrime: Long = 0
    for (i in m..n) {
        if (isPrime(i)) {
            if (prevPrime != 0L && i - prevPrime == g.toLong()) return longArrayOf(prevPrime, i)
            prevPrime = i
        }
    }
    return longArrayOf()
}

fun isPrime(num: Long): Boolean {
    if (num.mod(2) == 0 && num != 2L) return false
    var divisor = 3
    while (divisor * divisor <= num) {
        if (num.mod(divisor) == 0) return false
        divisor += 2
    }
    return true
}
