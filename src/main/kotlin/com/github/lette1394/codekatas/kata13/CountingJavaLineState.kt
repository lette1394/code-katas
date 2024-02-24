package com.github.lette1394.codekatas.kata13

/**
 * # Summary
 * 자바 코드 문자열을 character 단위로 방문하면서 발생할 수 있는 상태를 나타낸다.
 * 이 인터페이스를 구현하는 클래스는 자체적인 상태를 갖지 않으며, 항상 불변 상태여야 한다.
 *
 *
 */
interface CountingJavaLineState {
    /**
     * 이 메서드를 통해 현재 상태에서 추가할 문자열을 출력한다.
     *
     * @param stringBuilder 출력할 문자열을 기록할 객체
     */
    fun appendTo(stringBuilder: StringBuilder)

    /**
     * 다음 상태로 이동한다.
     */
    fun nextState(): CountingJavaLineState

    fun hasNext(): Boolean
}
