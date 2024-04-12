package codewars

fun main() {
    println(longToIP(2154959208u))
}

fun longToIP(ip: UInt): String {
    var tempIp = ip

    fun processGroup(): UInt {
        val result = tempIp % 256u
        tempIp /= 256u
        return result
    }

    return buildList {
        repeat(4) {
            add(processGroup())
        }
    }.reversed().joinToString(".")
}