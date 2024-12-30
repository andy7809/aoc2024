import java.io.File
import java.io.FileReader

fun main() {
    val file = File("data/three.txt")
    var sum = 0
    val mul_seq = charArrayOf('m', 'u', 'l', '(')
    val do_seq = charArrayOf('d', 'o', '(', ')')
    val dont_seq = charArrayOf('d', 'o', 'n', '\'', 't', '(', ')')
    FileReader(file).use { reader ->
            var charCode: Int
            var enabled = true
            var mul_seq_idx = 0
            var do_seq_idx = 0
            var dont_seq_idx = 0
            var need_a = false
            var need_b = false
            var a = ""
            var b = ""
            while (reader.read().also { charCode = it } != -1) {
                val char = charCode.toChar()
                if (char == do_seq[do_seq_idx] && !enabled) {
                    do_seq_idx++
                } else if (char != do_seq[do_seq_idx] && !enabled) {
                    do_seq_idx = 0
                    continue
                }

                if (do_seq_idx >= do_seq.size && !enabled) {
                    do_seq_idx = 0
                    println("enabling")
                    enabled = true
                    continue
                }

                if (!need_a && !need_b && char != mul_seq[mul_seq_idx] && char != dont_seq[dont_seq_idx]) {
                    dont_seq_idx = 0
                    mul_seq_idx = 0
                    continue
                }

                if (char == mul_seq[mul_seq_idx] && enabled) {
                    mul_seq_idx++
                } else if (char == dont_seq[dont_seq_idx] && enabled) {
                    dont_seq_idx++
                } else if (mul_seq_idx > 0 && dont_seq_idx > 0 && enabled) {
                    mul_seq_idx = 0
                    dont_seq_idx = 0
                    continue
                } else if (char != mul_seq[mul_seq_idx] && enabled && mul_seq_idx > 0) {
                    mul_seq_idx = 0
                    continue
                } else if (char != dont_seq[dont_seq_idx] && enabled && dont_seq_idx > 0) {
                    dont_seq_idx = 0
                    continue
                }
                
                if (dont_seq_idx >= dont_seq.size && enabled) {
                    println("disabling")
                    enabled = false
                    dont_seq_idx = 0
                    continue
                }

                // We found the opening parenthesis, look for digits next
                if (mul_seq_idx >= mul_seq.size && enabled) {
                    mul_seq_idx = 0
                    need_a = true
                    continue
                }

                // Case mul(,123
                if (need_a && char == ',' && a == "") {
                    mul_seq_idx = 0
                    need_a = false
                    a = ""
                    b = ""
                }

                if (need_a && char.isDigit()) {
                    a += char
                } else if (need_a && char == ',') {
                    need_a = false
                    need_b = true
                    continue
                } else if (need_a && !char.isDigit()) {
                    mul_seq_idx = 0
                    a = ""
                    need_a = false
                    continue
                }

                // Case mul(123,)
                if (need_b && char == ')' && b == "") {
                    mul_seq_idx = 0
                    a = ""
                    b = ""
                    need_a = false
                    need_b = false
                }

                if (need_b && char.isDigit()) {
                    b += char
                } else if (need_b && char == ')') {
                    if (a != "" && b != "") {
                        sum += a.toInt() * b.toInt()
                    }
                    a = ""
                    b = ""
                    mul_seq_idx = 0
                    need_a = false
                    need_b = false
                    continue
                } else if (need_b && !char.isDigit()) {
                    mul_seq_idx = 0
                    a = ""
                    b = ""
                    need_a = false
                    need_b = false
                    continue
                }
            }
        }
    println("all done")
    println(sum)
}