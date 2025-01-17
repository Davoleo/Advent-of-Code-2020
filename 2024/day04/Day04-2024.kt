@file:Suppress("t")

package day04

import java.nio.file.Files
import java.nio.file.Path

val text = Files.readString(Path.of("2024", "day04", "input.txt"))
val width = text.indexOf('\n')+1
val height = text.length / width

const val pattern = "XMAS"

fun main() {

    println("--- Part 1 ---")
    println("matrix: $height x $width")

    println(text.length)

    var occurrences = 0

    for (i in 0 until height) {
        for (j in 0 until width) {
            val index = i*width+j
            if (text[index] == 'X') {
                //println("i=${index / width} j=${index % width}")
                occurrences += starPatternMatching(index)
            }
        }
    }

    println("The number of XMAS occurrences in the text is: $occurrences")

    println("--- Part 2 ---")

    var xmases = 0
    for (i in 1 until height-1) {
        for (j in 1 until width-1) {
            val index = i*width+j
            if (text[index] == 'A') {
                if (matchXMASes(index)) {
                    xmases++
                }
            }
        }
    }

    println("The number of X-MASes in the text is: $xmases")
}

fun starPatternMatching(index: Int): Int {
    var occurrences = 0

    occurrences += cardinalPatternMatching(index, CardinalAxis.VERTICAL)

    occurrences += cardinalPatternMatching(index, CardinalAxis.HORIZONTAL)

    occurrences += diagonalPatternMatching(index, DiagonalAxis.DESCENDING)

    occurrences += diagonalPatternMatching(index, DiagonalAxis.ASCENDING)

    return occurrences
}

enum class CardinalAxis {
    VERTICAL,
    HORIZONTAL
}

fun cardinalPatternMatching(index: Int, type: CardinalAxis): Int {
    var forward = true
    var reverse = true
    for (i in 1..3) {
        val offset = if (type == CardinalAxis.VERTICAL) i*width else i

        // Out of bounds
        if (index+offset >= text.length) forward = false
        if (index-offset < 0) reverse = false

        if (forward && text[index+offset] != pattern[i]) {
            forward = false
        }

        if (reverse && text[index-offset] != pattern[i]) {
            reverse = false
        }
    }
    return when {
        forward.and(reverse) -> 2
        forward.xor(reverse) -> 1
        else -> 0
    }
}

enum class DiagonalAxis {
    DESCENDING,
    ASCENDING
}

fun diagonalPatternMatching(index: Int, type: DiagonalAxis): Int {
    var forward = true
    var reverse = true
    for (i in 1..3) {
        val offset = if (type == DiagonalAxis.DESCENDING) i*width+i else i*width-i

        // Out of bounds
        if (index+offset >= text.length) forward = false
        if (index-offset < 0) reverse = false


        if (forward && text[index+offset] != pattern[i]) {
            forward = false
        }

        if (reverse && text[index-offset] != pattern[i]) {
            reverse = false
        }
    }
    return when {
        forward.and(reverse) -> 2
        forward.xor(reverse) -> 1
        else -> 0
    }
}

fun matchXMASes(index: Int): Boolean {
    val offsetAsc = 1*width-1
    val offsetDesc = 1*width+1

    val descReverse = text[index+offsetDesc] == 'M' && text[index-offsetDesc] == 'S'
    val descForward = text[index-offsetDesc] == 'M' && text[index+offsetDesc] == 'S'

    val ascReverse = text[index+offsetAsc] == 'M' && text[index-offsetAsc] == 'S'
    val ascForward = text[index-offsetAsc] == 'M' && text[index+offsetAsc] == 'S'

    return (descForward || descReverse) && (ascReverse || ascForward)
}

