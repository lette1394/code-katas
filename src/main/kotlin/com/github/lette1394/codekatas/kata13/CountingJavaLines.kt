package com.github.lette1394.codekatas.kata13

class CountingJavaLines(
    private val javaCode: String,
) {
    fun count(): Long {
        if (javaCode.lines().any { it.isBlank() }) {
            return javaCode.lines().filter { it.isNotBlank() }.size.toLong()
        } else if (javaCode.contains("\n\n")) {
            return javaCode.lines().size.toLong() - 1
        } else if (javaCode.contains("//")) {
            return javaCode.lines().size.toLong() - javaCode.lines().filter { it.contains("//") }.size.toLong()
        } else if (javaCode.isNotBlank()) {
            return javaCode.lines().size.toLong()
        } else {
            return 0L
        }
    }
}
