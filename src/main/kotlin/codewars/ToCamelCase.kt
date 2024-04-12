package codewars

fun main() {
    println(toCamelCase("the-stealth-warrior"))
}

// https://www.codewars.com/kata/517abf86da9663f1d2000003
fun toCamelCase(str: String): String = str.split('-', '_').mapIndexed { index, word ->
        if (index != 0) word.replaceFirstChar{ it.titlecase() } else word
    }.joinToString(separator = "")