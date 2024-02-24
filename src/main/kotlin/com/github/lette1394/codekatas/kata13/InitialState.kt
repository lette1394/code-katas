package com.github.lette1394.codekatas.kata13

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
