package com.github.lette1394.codekatas.kata13

abstract class BaseCountingState(
    protected val index: Int,
    protected val javaCode: String,
) : CountingState {

    override fun hasNext(): Boolean {
        return index < javaCode.length
    }

    protected fun cur(): Char? {
        return nextAt(0)
    }

    protected fun next(): Char? {
        return nextAt(1)
    }

    protected fun nextAt(offset: Int): Char? {
        return javaCode.getOrNull(index + offset)
    }

    protected fun multiLineCommentStartedAt(offset: Int): Boolean {
        return nextAt(offset) == '/' && nextAt(offset + 1) == '*'
    }

    protected fun singleLineCommentStartedAt(offset: Int): Boolean {
        return nextAt(offset) == '/' && nextAt(offset + 1) == '/'
    }

    protected fun isDoubleColonAt(offset: Int): Boolean {
        return nextAt(offset) == '"'
    }

    protected fun isLineBreakAt(offset: Int): Boolean {
        return nextAt(offset) == '\n'
    }
}
