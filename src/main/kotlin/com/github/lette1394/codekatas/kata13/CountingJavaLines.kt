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
        var state: CountingState = InitialState(0, javaCode)
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

/**
 * # Summary
 * 자바 코드 문자열을 character 단위로 방문하면서 발생할 수 있는 상태를 나타낸다.
 * 이 인터페이스를 구현하는 클래스는 자체적인 상태를 갖지 않으며, 항상 불변 상태여야 한다.
 *
 *
 */
interface CountingState {
    /**
     * 이 메서드를 통해 현재 상태에서 추가할 문자열을 출력한다.
     *
     * @param stringBuilder 출력할 문자열을 기록할 객체
     */
    fun appendTo(stringBuilder: StringBuilder)

    /**
     * 다음 상태로 이동한다.
     */
    fun nextState(): CountingState

    fun hasNext(): Boolean
}

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

class InMultiLineComments(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        if (isLineBreakAt(0)) {
            stringBuilder.append(cur())
        }
    }

    // TODO: 성능 최적화 가능 "*/" 끝나는곳까지 점프 가능
    //  단, line break는 넣어야함
    override fun nextState(): CountingState {
        return if (multiLineCommentEnded()) {
            InitialState(index + 2, javaCode)
        } else {
            InMultiLineComments(index + 1, javaCode)
        }
    }

    private fun multiLineCommentEnded(): Boolean {
        return cur() == '*' && next() == '/'
    }
}

class InSingleLineComments(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        // do nothing
    }

    // TODO: 성능 최적화 가능 "\n" 만나기전까지 점프 가능
    override fun nextState(): CountingState {
        return if (singleLineCommentEnded()) {
            InExpression(index + 1, javaCode)
        } else {
            InSingleLineComments(index + 1, javaCode)
        }
    }

    private fun singleLineCommentEnded(): Boolean {
        return isLineBreakAt(1)
    }
}

class InEscapeString(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        stringBuilder.append(cur())
        stringBuilder.append(next())
    }

    override fun nextState(): CountingState {
        return InString(index + 2, javaCode)
    }
}

class InExpression(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        stringBuilder.append(cur())
    }

    override fun nextState(): CountingState {
        return when {
            multiLineCommentStartedAt(1) -> InMultiLineComments(index + 3, javaCode)
            singleLineCommentStartedAt(1) -> InSingleLineComments(index + 3, javaCode)
            isDoubleColonAt(1) -> InString(index + 1, javaCode)
            else -> InExpression(index + 1, javaCode)
        }
    }
}

class InitialState(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        // do nothing
    }

    override fun nextState(): CountingState {
        return when {
            multiLineCommentStartedAt(0) -> InMultiLineComments(index, javaCode)
            singleLineCommentStartedAt(0) -> InSingleLineComments(index, javaCode)
            isDoubleColonAt(0) -> InString(index, javaCode)
            else -> InExpression(index, javaCode)
        }
    }
}


class InString(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        stringBuilder.append(cur())
    }

    override fun nextState(): CountingState {
        return when {
            next() == '\\' -> InEscapeString(index + 1, javaCode)
            next() == '"' -> InExpression(index + 1, javaCode)
            else -> InString(index + 1, javaCode)
        }
    }
}
