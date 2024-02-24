package com.github.lette1394.codekatas.kata13

class CountingJavaLines(
    private val javaCode: String,
) {
    fun count(): Long {
        val stringBuilder = StringBuilder()
        var initial: CountingState = Initial(0, javaCode)
        while (initial.hasNext()) {
            initial.appendTo(stringBuilder)
            initial = initial.nextState()
        }

        return stringBuilder.toString().let {
            println(it)

            if (it.isBlank()) {
                0L
            } else {
                it.lines().filter { it.isNotBlank() }.size.toLong()
            }
        }
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
        return javaCode.getOrNull(index)
    }

    protected fun next(): Char? {
        return next(1)
    }

    protected fun next2(): Char? {
        return next(2)
    }

    protected fun next(offset: Int): Char? {
        return javaCode.getOrNull(index + offset)
    }
}

class InMultiLineComments(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        if (cur() == '\n') {
            stringBuilder.append(cur())
        }
    }

    // TODO: 성능 최적화 가능 "*/" 끝나는곳까지 점프 가능
    //  단, line break는 넣어야함
    override fun nextState(): CountingState {
        return if (cur() == '*' && next() == '/') {
            Initial(index + 2, javaCode)
        } else {
            InMultiLineComments(index + 1, javaCode)
        }
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
        return if (next() == '\n') {
            InCode(index + 1, javaCode)
        } else {
            InSingleLineComments(index + 1, javaCode)
        }
    }
}

class InEscaping(
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

class InCode(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        stringBuilder.append(cur())
    }

    override fun nextState(): CountingState {
        return when {
            next() == '"' -> InString(index + 1, javaCode)
            next() == '/' && next2() == '*' -> InMultiLineComments(index + 2, javaCode)
            next() == '/' && next2() == '/' -> InSingleLineComments(index + 2, javaCode)
            else -> InCode(index + 1, javaCode)
        }
    }
}

class Initial(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {
    override fun appendTo(stringBuilder: StringBuilder) {
        // do nothing
    }

    override fun nextState(): CountingState {
        return when {
            cur() == '/' && next() == '*' -> InMultiLineComments(index, javaCode)
            cur() == '/' && next() == '/' -> InSingleLineComments(index, javaCode)
            else -> InCode(index, javaCode)
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
            next() == '\\' -> InEscaping(index + 1, javaCode)
            next() == '"' -> InCode(index + 1, javaCode)
            else -> InString(index + 1, javaCode)
        }
    }
}
