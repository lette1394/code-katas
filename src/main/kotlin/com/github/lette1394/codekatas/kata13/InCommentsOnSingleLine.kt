package com.github.lette1394.codekatas.kata13

class InCommentsOnSingleLine(
    index: Int,
    javaCode: String,
) : CountingJavaLineStateBase(index, javaCode) {

    override fun appendTo(stringBuilder: StringBuilder) {
        // do nothing
    }

    override fun nextState(): CountingJavaLineState {
        return InExpression(index + getNextOffset(), javaCode)
    }

    private fun getNextOffset(): Int {
        var offset = 0
        while (!isLineBreakAt(offset)) {
            offset++
        }
        return offset
    }
}
