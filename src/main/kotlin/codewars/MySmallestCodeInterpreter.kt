import java.lang.StringBuilder

// https://www.codewars.com/kata/526156943dfe7ce06200063e
fun main(args: Array<String>) {
    val brainLuck = BrainLuck(",+[-.,+]")
    val res = brainLuck.process("Codewars" + 255.toChar())
    println(res)
}

@OptIn(ExperimentalUnsignedTypes::class)
class BrainLuck(private val code: String) {
    private var codePointer = 0
    private var memoryPointer = 0
    private val memoryTape = UByteArray(30000)

    fun process(input: String): String {
        val inputQueue = ArrayDeque(input.toList())
        val outputBuilder = StringBuilder()

        fun getCurrentInstruction() = code[codePointer]

        fun jump(forward: Boolean) {
            var bracketsCount = 0
            while (true) {
                if (getCurrentInstruction() == '[') bracketsCount++
                else if (getCurrentInstruction() == ']') bracketsCount--
                if (bracketsCount == 0) break
                if (forward) codePointer++ else codePointer--
            }
        }

        fun processInstruction(instruction: Char) {
            when(instruction) {
                '>' -> memoryPointer++
                '<' -> memoryPointer--
                '+' -> memoryTape[memoryPointer]++
                '-' -> memoryTape[memoryPointer]--
                '.' -> outputBuilder.append(memoryTape[memoryPointer].toInt().toChar())
                ',' -> memoryTape[memoryPointer] = inputQueue.removeFirst().code.toUByte()
                '[' -> if (memoryTape[memoryPointer] == 0.toUByte()) jump(true)
                ']' -> if (memoryTape[memoryPointer] != 0.toUByte()) jump(false)
            }
        }

        while (codePointer <= code.lastIndex) {
            processInstruction(getCurrentInstruction())
            codePointer++
        }

        return outputBuilder.toString()
    }
}