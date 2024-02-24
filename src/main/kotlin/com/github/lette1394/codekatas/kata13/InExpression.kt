package com.github.lette1394.codekatas.kata13

class InExpression(
    index: Int,
    javaCode: String,
) : CountingJavaLineStateBase(index, javaCode) {

    override fun appendTo(stringBuilder: StringBuilder) {
        stringBuilder.append(cur())
    }

    override fun nextState(): CountingJavaLineState {
        return when {
            multiLineCommentStartedAt(1) -> InCommentsOnMultiLine(index + 3, javaCode)
            singleLineCommentStartedAt(1) -> InCommentsOnSingleLine(index + 3, javaCode)
            isDoubleColonAt(1) -> InString(index + 1, javaCode)
            else -> InExpression(index + 1, javaCode)
        }
    }
}
