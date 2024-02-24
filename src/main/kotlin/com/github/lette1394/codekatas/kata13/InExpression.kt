package com.github.lette1394.codekatas.kata13

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
