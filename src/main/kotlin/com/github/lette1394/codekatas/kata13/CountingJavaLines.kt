package com.github.lette1394.codekatas.kata13

class CountingJavaLines(
    private val javaCode: String,
) {
    fun count(): Long {
        val commentsRemoved = commentsRemoved()
        return if (commentsRemoved.isBlank()) {
            0L
        } else {
            commentsRemoved
                .let {
                    println(it)
                    it
                }
                .lines()
                .filter { it.isNotBlank() }
                .size.toLong()
        }
    }

    fun commentsRemoved(): String {
        val builder = StringBuilder()
        var state: CountingJavaLineState = InitialJavaLineState(0, javaCode)

        while (state.hasNext()) {
            state.appendTo(builder)
            state = state.nextState()
        }

        return builder.toString()
            .split("\n")
            .filter { it.isNotBlank() }
            .joinToString("\n")
    }
}
