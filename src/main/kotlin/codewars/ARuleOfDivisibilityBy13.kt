// https://www.codewars.com/kata/564057bc348c7200bd0000ff
fun main(args: Array<String>) {
    //println(numToDigitsBackwards(39459857L))
    println(thirt(85299258))
}

fun thirt(n:Long):Long {
    var number = n
    while(true) {
        val processedNumber = process(number)
        if (processedNumber == number) break
        number = processedNumber
    }
    return number
}

val modValues = listOf(1, 10, 9, 12, 3, 4)
fun process(num: Long): Long {
    val digitsBackwards = numToDigitsBackwards(num)
    var sum: Long = 0
    digitsBackwards.forEachIndexed { index, element ->
        sum += element * modValues[index.mod(6)]
    }
    return sum
}

// Returns the array of digits of this number in backwards order
fun numToDigitsBackwards(num: Long): List<Int> {
    val digitsBackwards = ArrayList<Int>()
    var processedNumber = num
    while (processedNumber > 0) {
        val lastDigitResult = getLastDigit(processedNumber)
        digitsBackwards.add(lastDigitResult.first)
        processedNumber = lastDigitResult.second
    }
    return digitsBackwards
}

// Returns last digit of the number and remaining part of the number
fun getLastDigit(num: Long): Pair<Int, Long> = Pair(num.mod(10), num.div(10))