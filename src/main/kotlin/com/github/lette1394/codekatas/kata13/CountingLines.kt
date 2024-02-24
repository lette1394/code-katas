package com.github.lette1394.codekatas.kata13

class CountingLines(
    private val javaCode: String,
) {
    fun count(): Long {
        if (javaCode.isNotBlank()) {
            return javaCode.lines().size.toLong()
        } else {
            return 0L
        }
    }
}
