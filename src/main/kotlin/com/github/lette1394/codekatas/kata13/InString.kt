package com.github.lette1394.codekatas.kata13

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
