// https://www.codewars.com/kata/51ba717bb08c1cd60f00002f
fun main(args: Array<String>) {
    println(rangeExtraction(intArrayOf(-6, -3, -2, -1, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20)))
}

fun rangeExtraction(arr: IntArray): String {
    var rangeLength = 0
    val builder = StringBuilder()

    fun appendRangeToBuilder(rangeLength: Int, rangeLastNumber: Int) {
        if (builder.isNotEmpty()) builder.append(',')
        if (rangeLength == 2) builder.append(rangeLastNumber - 1).append(',')
        else if (rangeLength > 2) builder.append(rangeLastNumber - rangeLength + 1).append('-')
        builder.append(rangeLastNumber)
    }

    for (i in 0..arr.lastIndex) {
        if (i > 0) {
            if (arr[i] - arr[i - 1] == 1) rangeLength++ // range continues
            else {
                appendRangeToBuilder(rangeLength, arr[i - 1]) // range finished, appending
                rangeLength = 1 // resetting range length
            }
        } else rangeLength++
    }
    appendRangeToBuilder(rangeLength, arr.last())
    return builder.toString()
}
