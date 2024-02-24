package com.github.lette1394.codekatas.kata13

class CountingJavaLines(
    private val javaCode: String,
) {
    private val filters = listOf(
        EmptyLineFilter(),
        CommentLine(),
        MultiCommentLine()
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

class EmptyLineFilter : Filter {
    override fun invoke(javaCode: String): String {
        return javaCode
            .lines()
            .filter { it.isNotBlank() }
            .joinToString("\n")
    }
}

class CommentLine : Filter {
    override fun invoke(javaCode: String): String {
        return javaCode
            .lines()
            .filterNot { it.trimStart().startsWith("//") }
            .joinToString("\n")
    }
}

class MultiCommentLine : Filter {
    override fun invoke(javaCode: String): String {
        return javaCode
            .lines()
            .filterNot { it.contains("/*") || it.contains("*/") || it.contains("*") }
            .joinToString("\n")
    }
}
