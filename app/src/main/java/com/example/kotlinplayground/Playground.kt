package com.example.kotlinplayground

import kotlin.system.measureTimeMillis

fun main() {
    measureTime("determine moves") {
    }
}

fun determineMoves(input: String): Int {
    val twoDMap = input.split(',').toTypedArray()
    var copRow = -1
    var thiefRow = -1
    for (i in twoDMap.indices) {
        val row = twoDMap[i]
        if (row.contains("P")) {
            if (row.count { it == 'P' } == 1) {
                if (copRow < 0) {
                    copRow = i
                } else {
                    thiefRow = i
                }
            } else {
                // here cop and thief are on same line in map
                thiefRow = i
                copRow = i
            }
        }
    }
    if (copRow < 0 || thiefRow < 0) {
        return 0
    }
    val copPos = twoDMap[copRow].indexOfFirst { it == 'P' }
    val thiefPos = if (thiefRow == copRow) {
        twoDMap[thiefRow].indexOf('P', copPos)
    } else {
        twoDMap[thiefRow].indexOfFirst { it == 'P' }
    }

    val stepsBeforeDown = if (thiefPos >= copPos) {
        thiefPos - copPos
    } else {
        copPos - thiefPos
    }
    return stepsBeforeDown + thiefRow + 1
}

fun findSuitableExpression(value: Int, expressions: String): String {
    expressions.split(' ')
        .forEachIndexed { index, expression ->
            if (expression.contains("+")) {
                val result = expression.split('+').sumOf { it.toInt() }
                if (result == value) return index.toString()
            } else if (expression.contains("-")) {
                val list = expression.split('-').map { it.toInt() }
                val result = list[0] - list[1]
                if (result == value) return index.toString()
            } else if (expression.contains("*")) {
                val list = expression.split('*').map { it.toInt() }
                val result = list[0] * list[1]
                if (result == value) return index.toString()
            } else {
                val list = expression.split('/').map { it.toInt() }
                val result = list[0] / list[1]
                if (result == value) return index.toString()
            }
        }
    return "none"
}

fun containsPalindrome(boxes: MutableList<String>): String {
    for (i in boxes.indices) {
        val builder = StringBuilder()
        for (j in boxes[i].length - 1 downTo 0) {
            builder.append(boxes[i][j])
        }
        if (boxes[i] == builder.toString()) {
            return "Open"
        }
    }
    return "Trash"
}

// question1
fun solution(numbers: MutableList<Int>): MutableList<Int> {
    val list = mutableListOf<Int>()
    var count = 0
    while (count < numbers.size - 2) {
        val second = numbers[count + 1]
        val third = numbers[count + 2]
        if (numbers[count] < second && second > third || numbers[count] > second && second < third) {
            list.add(1)
        } else {
            list.add(0)
        }
        count++
    }
    return list
}

// question 2
fun solution(a: MutableList<Int>, b: MutableList<Int>, k: Int): Int {
    var tinyCount = 0
    var bIndex = b.size - 1
    a.forEachIndexed { _, i ->
        val concatString = i.toString() + b[bIndex].toString()
        if (concatString.toInt() < k) {
            tinyCount++
        }
        bIndex--
    }
    return tinyCount
}

// question 3
fun solution(arr: MutableList<String>): String {
    val builder = StringBuilder()
    val maxLength = arr.mapIndexed { _, s -> s.length }.max()
    val matrix: Array<CharArray> = Array(arr.size) { charArrayOf() }
    arr.forEachIndexed { index, s ->
        matrix[index] = s.toCharArray()
    }
    println(matrix.maxOfOrNull { it.size })
    for (y in 0 until maxLength) {
        for (x in matrix.indices) {
            val row = matrix[x]
            if (y <= row.lastIndex) {
                val currentLetter = matrix[x][y]
                builder.append(currentLetter)
            }
        }
    }
    matrix.forEach {
        println(it.asList().toString())
    }
    return builder.toString()
}

fun checkRepeatedLetter(input: String): String {
    val hashMath = hashMapOf<Char, Int>()

    for (i in input.indices) {
        if (hashMath.containsKey(input[i])) {
            return "Deja Vu"
        }
        hashMath[input[i]] = i
    }
    return "Unique"
}

fun measureTime(text: String, codeBlock: () -> Unit) {
    val time = measureTimeMillis(codeBlock)
    println("execution time of $text:$time:ms")
}

// question 4
fun solution4(a: MutableList<Int>): Long {
    var count = 0
    var total = 0L
    while (count < a.size) {
        val first = a[count].toString()
        a.forEach {
            val value = it.toString() + first
            total += value.toLong()
        }
        count++
    }
    return total
}

// question 5
fun solution5(a: MutableList<Int>, k: Int): Long {
    return 0
}

// solution 6
fun solution6(a: MutableList<Int>): MutableList<Int> {
    val mutatedArray = arrayListOf<Int>()
    for (i in a.indices) {
        val subValue = if ((i - 1) >= 0) {
            a[i - 1]
        } else {
            0
        }
        val addValue = if ((i + 1) < a.size) {
            a[i + 1]
        } else {
            0
        }
        val mutatedValue = subValue + a[i] + addValue
        mutatedArray.add(mutatedValue)
    }
    return mutatedArray
}

fun solution7(pattern: String, source: String): Int {
    fun Char.isVowel(): Boolean {
        return this == 'a' || this == 'e' || this == 'i' || this == 'o' || this == 'u' || this == '0'
    }

    val limit = pattern.length - 1
    var totalMatchCounts = 0
    for (i in 0..source.length - limit) {
        if (i + limit < source.length) {
            val subString = source.substring(i..i + limit)
            var matchCount = 0
            for (j in pattern.indices) {
                if (pattern[j].isVowel() && subString[j].isVowel()) {
                    matchCount++
                } else if (!pattern[j].isVowel() && !subString[j].isVowel()) {
                    matchCount++
                } else {
                    break
                }
            }
            if (matchCount == subString.length) {
                totalMatchCounts++
            }
        }
    }
    return totalMatchCounts
}
