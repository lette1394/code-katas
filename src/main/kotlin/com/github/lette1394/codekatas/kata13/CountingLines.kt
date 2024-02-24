package com.github.lette1394.codekatas.kata13

class CountingLines(
    private val javaCode: String,
) {
    fun count(): Long {
        if (javaCode.contains("\n\n")) {
            return javaCode.lines().size.toLong() - 1
        } else if (javaCode.contains("//")) {
            return javaCode.lines().size.toLong() - 1
        } else if (javaCode.isNotBlank()) {
            return javaCode.lines().size.toLong()
        } else {
            return 0L
        }
    }
}
