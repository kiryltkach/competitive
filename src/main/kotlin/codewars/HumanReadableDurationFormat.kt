// https://www.codewars.com/kata/52742f58faf5485cae000b9a
fun main(args: Array<String>) {
    println(TimeFormatter.formatDuration(934321))
}

object TimeFormatter {
    fun formatDuration(seconds: Int): String {
        var time = seconds
        val units = ArrayList<String>()
        var formatted: String

        fun processTime(modValue: Int, unit: String) {
            val timeValue = time.rem(modValue)
            time = time.div(modValue)
            formatted = formatUnitString(timeValue, unit)
            if (formatted.isNotEmpty()) units.add(formatted)
        }

        if (seconds == 0) return "now"
        else {
            processTime(60, "second")
            processTime(60, "minute")
            processTime(24, "hour")
            processTime(365, "day")
            formatted = formatUnitString(time, "year")
            if (formatted.isNotEmpty()) units.add(formatted)
        }

        val almostAnswer = units.reversed().joinToString(", ")
        val lastCommaIndex = almostAnswer.indexOfLast { it == ',' }
        return if (lastCommaIndex == -1) almostAnswer
        else {
            val builder = StringBuilder(almostAnswer)
            builder.replace(lastCommaIndex, lastCommaIndex + 1, " and")
            builder.toString()
        }
    }
}

fun formatUnitString(value: Int, unit: String): String {
    return if (value == 0) ""
    else {
        var s = "$value $unit"
        if (value > 1) s += 's'
        s
    }
}

