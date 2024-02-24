# Kata13: Counting Code Lines

My Implementation on http://codekata.com/kata/kata13-counting-code-lines/

## Implementation Summary
- [`com.github.lette1394.codekatas.kata13.CountingJavaLinesTest`](https://github.com/lette1394/code-katas/blob/main/src/test/kotlin/com/github/lette1394/codekatas/kata13/CountingJavaLinesTest.kt) 에서 전체 테스트 목록을 확인할 수 있음
- [`com.github.lette1394.codekatas.kata13.CountingJavaLineState`](https://github.com/lette1394/code-katas/blob/main/src/main/kotlin/com/github/lette1394/codekatas/kata13/CountingJavaLineState.kt) 인터페이스가 구현의 핵심

## Implementation Detail
3번의 접근 방법 변화가 있었음

- 원래 코드에서 모든 주석을 제거한 코드를 만든 후에 모든 코드 줄 수를 세기로 함
- 처음에는 줄 단위로 접근. 이 줄을 삭제해야할지 남겨둬야 할지 선택하는 식
- 문자열 안에 들어있는 주석 같은 엣지 케이스를 고려해야 한다는 사실을 깨닿고는 줄 단위가 아닌 문자 하나씩 접근하기로 변경
- 일단 구현하고 보니 파싱하는 메서드가 너무 복잡해졌음
- Boolean 변수를 없애고 전체 복잡도를 줄이기 위해 State 기반으로 [구현을 변경함](https://github.com/lette1394/code-katas/commit/a5ed91a7037d154cd0900dda45512f5889b9bd31) 
- 공통 메서드를 더 빼내고 성능 최적화를 진행 
- 동일한 상태로 전이하는 코드를 없애면 더 성능을 끌어올릴 수 있음
  - `CountingJavaLineState` interface 하위 구현체에 모두 적용할 수 있는 좋은 방법을 찾지 못했음 (뭔가 인터페이스 설계가 잘못된 것 같기도 함)
    - 현재 구현상으로 다음 상태 전이를 위한 탐색을 두 번 해야함 (`appendTo()`에서 다음 index까지 char를 입력하고, 실제 상태 전이는 `nextState()`가 호출될 때 일어남)
    - 아니면 무조건 `appendTo()`를 호출한 뒤에 `nextState()`를 호출하는 제약을 추가해야 함, 게다가 이러면 각 클래스가 불변하지 않게 됨
    - 계산을 한 번만 하면서 불변하게 할 수 있는 방법이 없을까?
    - 생성자에 로직을 넣고 싶지는 않음
  - 현재 구현에서는 멀티라인/싱글라인 주석 각각에만 적용되어있음
