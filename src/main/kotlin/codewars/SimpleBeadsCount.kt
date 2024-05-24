package codewars

fun main() {
    countRedBeads(3)
}

fun countRedBeads(nBlue: Int): Int {
    if (nBlue < 2) return 0
    return (nBlue - 1) * 2
}