package com.github.lette1394.codekatas.kata13

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

// see http://codekata.com/kata/kata13-counting-code-lines/

class CountingJavaLinesTest : StringSpec({
    "빈 문자열의 lines() 메서드는 1을 반환한다" {
        "".lines().size shouldBe 1
    }

    "비어있는 코드는 라인 수가 없다" {
        // language=java
        val javaCode = ""
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 0
    }

    "빈 줄이 없는 코드는 곧 코드 만큼 라인 수를 갖는다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  public static void main(String[] args) {
            |    System.out.println("Hello, World!");
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "주석은 라인 수로 세지 않는다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  public static void main(String[] args) {
            |    // System.out.println("Hello, World!");
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 4
    }

    "여러 개의 단일 주석은 라인 수로 세지 않는다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  public static void main(String[] args) {
            |    // System.out.println("Hello, World!");
            |    // comment 0
            |  }
            |  
            |  // comment 1
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 4
    }

    "빈 줄은 라인 수로 세지 않는다" {
        // language=java
        val javaCode = """
            |class Simple {
            |
            |  public static void main(String[] args) {
            |    System.out.println("Hello, World!");
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "여러 개의 빈 줄은 라인 수로 세지 않는다" {
        // language=java
        val javaCode = """
            |class Simple {
            |
            |  public static void main(String[] args) {
            |    System.out.println("Hello, World!");
            |    
            |  }
            |  
            |  
            |  
            |  
            |  
            |}
            |
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "공백을 포함한 빈 줄은 라인 수로 세지 않는다" {
        // language=java
        val javaCode = """
            |class Simple {
            |    
            |  public static void main(String[] args) {
            |    System.out.println("Hello, World!");
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "공백을 포함한 여러 빈 줄은 라인 수로 세지 않는다" {
        // language=java
        val javaCode = """
            |    
            |class Simple {
            |
            |  public static void main(String[] args) {
            |    System.out.println("Hello, World!");
            |
            |            
            |  }
            |  
            |            
            |}
            |
            |
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }
})
