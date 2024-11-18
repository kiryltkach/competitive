package codewars

// https://www.codewars.com/kata/51b6249c4612257ac0000005
fun main() {
    println(decode("MCMXC"))
}

fun decode(str: String): Int {
    val map = mapOf(
        'I' to 1,
        'V' to 5,
        'X' to 10,
        'L' to 50,
        'C' to 100,
        'D' to 500,
        'M' to 1000
    )

    var value = 0

    str.forEachIndexed { index, c ->
        val currentSymbolValue = map[c]!!
        val nextSymbolIndex = index + 1
        if (nextSymbolIndex <= str.lastIndex) {
            val nextSymbol = str[nextSymbolIndex]
            val nextSymbolValue = map[nextSymbol]!!
            if (currentSymbolValue < nextSymbolValue) {
                value -= currentSymbolValue
                return@forEachIndexed
            }
        }
        value += currentSymbolValue
    }
    return value
}