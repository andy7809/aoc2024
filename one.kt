import java.io.File
import kotlin.math.abs

fun main() {
    val file = File("data/one.txt")
    val col1 = mutableListOf<Int>()
    val col2 = mutableListOf<Int>()
    file.forEachLine {
        line -> 
        val (a, b) = line.split("   ")
        col1.add(a.toInt())
        col2.add(b.toInt())
    }

    val counts1 = mutableMapOf<Int, Int>()
    col1.forEach { x ->
        counts1[x] = counts1.getOrDefault(x, 0) + 1
    }

    val counts2 = mutableMapOf<Int, Int>()
    col2.forEach { x ->
        counts2[x] = counts2.getOrDefault(x, 0) + 1
    }

    var simscore = 0
    col1.forEach { value ->
        if (counts2.containsKey(value)) {
            simscore += value * counts2.getOrDefault(value, 0)
        } 
    }
    // println(counts1)

    // val sortedCol1 = col1.sorted()
    // val sortedCol2 = col2.sorted()
    // var distance = 0
    // sortedCol1.forEachIndexed {
    //     index, value ->
    //     distance += abs(value - sortedCol2[index])
    // }
    println(simscore)
    println("all done")
}