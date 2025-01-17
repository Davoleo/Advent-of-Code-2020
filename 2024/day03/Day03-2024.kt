package day03

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    println("--- Part 1 ---")

    val code = Files.readString(Path.of("2024", "day03", "input.txt"))
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")

    val matches = regex.findAll(code)
    var sum = 0
    matches.forEach { matchResult ->
        val match = matchResult.value
        val factors = match
            .slice(4 until match.length-1)
            .split(",")
            .map(String::toInt)
        sum += (factors[0] * factors[1])
    }

    println("The sum of all products in the code is: $sum")
    println("--- Part 2 ---")

    val regex2 = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)")
    var conditionedSum = 0
    var enabled = true

    for (matchResult in regex2.findAll(code)) {
        val match = matchResult.value
        if (match == "do()") {
            enabled = true
            continue
        }
        if (match == "don't()") {
            enabled = false
            continue
        }

        if (enabled) {
            val factors = match
                .slice(4 until match.length-1)
                .split(",")
                .map(String::toInt)
            conditionedSum += factors[0] * factors[1]
        }
    }

    println("The sum of all products in the code with conditions is: $conditionedSum")

}