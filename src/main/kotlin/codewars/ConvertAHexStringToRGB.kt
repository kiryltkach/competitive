fun main(args: Array<String>) {
    println(hexStringToRGB("#ffbcff"))
}

// https://www.codewars.com/kata/5282b48bb70058e4c4000fa7
data class RGB(val r: Int, val g: Int, val b: Int)
fun hexStringToRGB(hexString: String): RGB {
    // hex is lowercase char
    fun hexToInt(hex: Char) = if (hex.isDigit()) hex.digitToInt() else hex.code - 87
    // hex is 2 symbol lowercase string
    fun twoDigitHexToInt(hex: String) = hexToInt(hex[0]) * 16 + hexToInt(hex[1])
    val s = hexString.toLowerCase()
    return RGB(
        twoDigitHexToInt(s.substring(1,3)),
        twoDigitHexToInt(s.substring(3,5)),
        twoDigitHexToInt(s.substring(5,7))
    )
}
