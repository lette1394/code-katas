package com.github.lette1394.codekatas.kata13

class InitialJavaLineState(
    index: Int,
    javaCode: String,
) : CountingJavaLineStateBase(index, javaCode) {

    override fun appendTo(stringBuilder: StringBuilder) {
        // do nothing
    }

    override fun nextState(): CountingJavaLineState {
        return when {
            multiLineCommentStartedAt(0) -> InCommentsOnMultiLine(index, javaCode)
            singleLineCommentStartedAt(0) -> InCommentsOnSingleLine(index, javaCode)
            isDoubleColonAt(0) -> InString(index, javaCode)
            else -> InExpression(index, javaCode)
        }
    }
}
