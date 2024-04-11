fun main(args: Array<String>) {
    println(getPINs("76239487"))
}

// https://www.codewars.com/kata/5263c6999e0f40dee200059d
fun getPINs(observed: String): List<String> {
    fun possibleVariants(digit: Int): Set<Int> {
        val resultSet = mutableSetOf(digit)
        resultSet.addAll(
            when (digit) {
                1 -> setOf(2, 4)
                2 -> setOf(1, 3, 5)
                3 -> setOf(2, 6)
                4 -> setOf(1, 5, 7)
                5 -> setOf(2, 4, 6, 8)
                6 -> setOf(3, 5, 9)
                7 -> setOf(4, 8)
                8 -> setOf(5, 7, 9, 0)
                9 -> setOf(6, 8)
                0 -> setOf(8)
                else -> emptySet() // should never happen
            }
        )
        return resultSet
    }

    val observedDigits: List<Int> = observed.toList().map { it.digitToInt() }
    val possibleVariants: List<List<Int>> = observedDigits.map { possibleVariants(it).toList() }
    val indexList = MutableList(possibleVariants.size) { 0 }
    val result = ArrayList<String>()

    val builder = StringBuilder()
    while (true) {
        builder.clear()
        for (i in 0..possibleVariants.lastIndex) {
            builder.append(possibleVariants[i][indexList[i]])
        }
        result.add(builder.toString())

        var indexIncreased = false
        for (i in possibleVariants.lastIndex downTo 0) {
            if (indexList[i] < possibleVariants[i].lastIndex) {
                indexList[i] = indexList[i] + 1
                indexIncreased = true
                for (j in i + 1 .. possibleVariants.lastIndex) {
                    indexList[j] = 0
                }
                break
            }
        }
        if (!indexIncreased) return result
    }

}