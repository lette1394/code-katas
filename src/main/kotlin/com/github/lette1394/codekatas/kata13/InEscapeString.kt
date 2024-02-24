package com.github.lette1394.codekatas.kata13

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
