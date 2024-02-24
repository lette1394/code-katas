package com.github.lette1394.codekatas.kata13

class CountingJavaLines(
    private val javaCode: String,
) {
    fun count(): Long {
        val filteredJavaCode = CommentLine().invoke(EmptyLineFilter().invoke(IdentityFilter().invoke(javaCode)))
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
            .filterNot { it.contains("//") }
            .joinToString("\n")
    }
}
