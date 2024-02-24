package com.github.lette1394.codekatas.kata13

class CountingJavaLines(
    private val javaCode: String,
) {
    private val filters = listOf(
        Log(),
        MultiCommentLine(),
        Log(),
        EmptyLineFilter(),
        Log(),
    )

    fun count(): Long {
        val filteredJavaCode = filters.fold(javaCode) { acc, filter -> filter.invoke(acc) }
        if (filteredJavaCode.isBlank()) {
            return 0L
        } else {
            return filteredJavaCode.lines().size.toLong()
        }
    }
}

interface Filter {
    fun invoke(javaCode: String): String
}

class IdentityFilter : Filter {
    override fun invoke(javaCode: String): String {
        return javaCode
    }
}

class Log : Filter {
    override fun invoke(javaCode: String): String {
        println(javaCode)
        return javaCode
    }
}

class EmptyLineFilter : Filter {
    override fun invoke(javaCode: String): String {
        return javaCode
            .lines()
            .filter { it.isNotBlank() }
            .joinToString("\n")
    }
}

class MultiCommentLine : Filter {
    override fun invoke(javaCode: String): String {
        var index = 0
        val newText = mutableListOf<Char>()
        var multiOpened = false
        var inString = false

        while (index < javaCode.length) {
            val cur = javaCode[index]
            val next = javaCode.getOrNull(index + 1)
            when {
                cur == '\\' && next == '"' && multiOpened.not() -> {
                    newText.add(cur)
                    newText.add(next)
                    index += 2
                }

                cur == '"' && multiOpened.not() -> {
                    inString = inString.not()
                    newText.add(cur)
                    index++
                }

                inString && multiOpened.not() -> {
                    newText.add(cur)
                    index++
                }
                // string end

                cur == '\n' -> {
                    newText.add(cur)
                    index++
                }

                // empty line end

                cur == '/' && next == '*' && multiOpened.not() -> {
                    multiOpened = true
                    index += 2
                }

                cur == '*' && next == '/' && multiOpened -> {
                    multiOpened = false
                    index += 2
                }

                multiOpened -> {
                    index++
                }

                // multi comment end

                cur == '/' && next == '/' -> {
                    val newLineIndex = javaCode.indexOf(System.lineSeparator(), index)
                    index = if (newLineIndex == -1) {
                        javaCode.length
                    } else {
                        newLineIndex
                    }
                }

                else -> {
                    newText.add(cur)
                    index++
                }
            }

            println(newText.joinToString(""))
        }
        return newText.joinToString("")
    }
}
