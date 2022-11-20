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
            if (copRow < 0) {
                copRow = i
            } else {
                thiefRow = i
            }
        }
    }
    if (copRow < 0 || thiefRow < 0) {
        return 0
    }
    val thiefPos = twoDMap[thiefRow].indexOfLast { it == 'P' }
    val copPos = twoDMap[copRow].indexOfFirst { it == 'P' }
    val stepsBeforeDown = if (thiefPos >= copPos) {
        thiefPos - copPos
    } else {
        copPos - thiefPos
    }
    return stepsBeforeDown + thiefRow
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
