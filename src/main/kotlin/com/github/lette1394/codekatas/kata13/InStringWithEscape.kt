package com.github.lette1394.codekatas.kata13

class InStringWithEscape(
    index: Int,
    javaCode: String,
) : CountingJavaLineStateBase(index, javaCode) {

    override fun appendTo(stringBuilder: StringBuilder) {
        stringBuilder.append(cur())
        stringBuilder.append(next())
    }

    override fun nextState(): CountingJavaLineState {
        return InString(index + 2, javaCode)
    }
}
