// https://www.codewars.com/kata/5861487fdb20cff3ab000030
fun main(args: Array<String>) {
    val result = interpret(">,>,>,>,>,>,>,>,<<<<<<<[>]+<[+<]>>>>>>>>>[+]+<<<<<<<<+[>+]<[<]>>>>>>>>>[+<<<<<<<<[>]+<[+<]>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+]<<<<<<<<;>;>;>;>;>;>;>;<<<<<<<,>,>,>,>,>,>,>,<<<<<<<[>]+<[+<]>>>>>>>>>[+]+<<<<<<<<+[>+]<[<]>>>>>>>>>]<[+<]", "Codewars\u00ff")
    println(result)
}

fun interpret(code: String, input: String): String {
    val memoryTape = IntArray(30000)
    var memoryPointer = 15000

    var codePointer = 0

    fun charToBinaryLittleEndian(c: Char): List<Int> {
        val list = Integer.toBinaryString(c.code).reversed().toList().map { it.digitToInt() }.toMutableList()
        repeat(8 - list.size) { list.add(0) }
        return list
    }

    val inputDeque = ArrayDeque(
        input.toList().map { charToBinaryLittleEndian(it) }.flatten()
    )

    fun getInput(): Int = if (inputDeque.isNotEmpty()) inputDeque.removeFirst() else 0

    val output = ArrayList<Int>()

    fun goToBracket(forward: Boolean) {
        var brackets = 0
        while (true) {
            val currentVal = code[codePointer]
            if (currentVal == '[') brackets++ else if (currentVal == ']') brackets--
            if (brackets == 0) break
            if (forward) codePointer++ else codePointer--
        }
    }

    fun processInstruction(instruction: Char) {
        when (instruction) {
            '+' -> memoryTape[memoryPointer] = if (memoryTape[memoryPointer] == 1) 0 else 1
            ',' -> memoryTape[memoryPointer] = getInput()
            ';' -> output.add(memoryTape[memoryPointer])
            '>' -> memoryPointer++
            '<' -> memoryPointer--
            '[' -> if (memoryTape[memoryPointer] == 0) goToBracket(true)
            ']' -> if (memoryTape[memoryPointer] == 1) goToBracket(false)
        }
    }

    while (codePointer <= code.lastIndex) {
        val currentInstruction = code[codePointer]
        processInstruction(currentInstruction)
        codePointer++
    }

    val notFilledBits = output.size.rem(8)
    var bitsToAdd = 0
    if (notFilledBits > 0) bitsToAdd = 8 - notFilledBits
    repeat(bitsToAdd) { output.add(0) }

    return output.chunked(8).map { it.reversed().joinToString("").toInt(2).toChar() }.joinToString("")
}

