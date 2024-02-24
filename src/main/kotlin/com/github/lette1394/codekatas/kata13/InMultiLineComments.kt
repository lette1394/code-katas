package com.github.lette1394.codekatas.kata13

class InMultiLineComments(
    index: Int,
    javaCode: String,
) : BaseCountingState(index, javaCode) {

    override fun appendTo(stringBuilder: StringBuilder) {
        runEveryBlockUntilEndedThenGetOffset { offset ->
            stringBuilder.append(nextAt(offset))
        }
    }

    override fun nextState(): CountingState {
        val offset = runEveryBlockUntilEndedThenGetOffset {
            // do nothing
        }
        return InitialState(index + offset + 2, javaCode)
    }

    // TODO: 뭔가... 상위 클래스로 올려서 더 범용적으로 쓸 수 있을거같음
    //  searchUntil(predicate) 이런식으로...?
    private fun runEveryBlockUntilEndedThenGetOffset(block: (offset: Int) -> Unit): Int {
        var offset = 0
        while (!multiLineCommentEndedAt(offset)) {
            if (isLineBreakAt(offset)) {
                block(offset)
                offset++
            } else {
                offset++
            }
        }
        return offset
    }

    private fun multiLineCommentEndedAt(offset: Int): Boolean {
        return nextAt(offset) == '*' && nextAt(offset + 1) == '/'
    }
}
