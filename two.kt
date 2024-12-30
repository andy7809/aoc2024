import java.io.File
import kotlin.math.abs

fun isOk(report: List<Int>): Boolean {
    if (report.size == 0) {
        return false
    }
    if (report.size == 1) {
        return true
    }
    val increasing = report[0] < report[1]
    for (i in 1 until report.size) {
        if (report[i] == report[i - 1]) {
            return false
        }
        if (report[i] > report[i - 1] && !increasing) {
            return false
        } else if (report[i] < report[i - 1] && increasing) {
            return false
        }
        else if (abs(report[i] - report[i - 1]) > 3) {
            return false
        }
    }
    return true
}

fun main() {
    val file = File("data/two.txt")
    val col1 = mutableListOf<Int>()
    var safe = 0
    file.forEachLine {
        report -> 
        val levels = report.split(" ").map {
            it.toInt()
        }
        var ok = isOk(levels)
        if (!ok) {
            for (i in 0 until levels.size) {
                val levelsCopy = levels.toMutableList()
                levelsCopy.removeAt(i)
                ok = isOk(levelsCopy)
                if (ok) {
                    safe++
                    break
                }
            }
        } else {
            safe++
        }
    }
    println("all done")
    println(safe)
}