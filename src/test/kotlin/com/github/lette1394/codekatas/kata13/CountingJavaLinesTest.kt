package com.github.lette1394.codekatas.kata13

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.Instant

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

    "단일 주석이 코드 뒤에 있으면 라인 수로 센다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  public static void main(String[] args) {
            |    System.out.println("Hello, World!"); // comment after code
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "단일 주석을 표현하는 기호가 제 기능을 못하는 경우 라인 수로 센다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  public static void main(String[] args) {
            |    System.out.println("Hello, // World!"); 
            |    System.out.println("Hello, // World!"); 
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 6
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

    "여러 개의 주석과 빈 줄이 섞여 있을 때" {
        // language=java
        val javaCode = """
            |class Simple {
            |  // comment 0
            |  public static void main(String[] args) {
            |    // comment 1
            |    System.out.println("Hello, World!");
            |    // comment 2
            |  }
            |  // comment 3
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "멀티 라인 주석은 라인 수로 세지 않는다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  /*
            |   * comment 0
            |   */
            |  public static void main(String[] args) {
            |    System.out.println("Hello, World!");
            |  }
            |  /*
            |   * comment 1
            |   */
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "멀티 라인 주석의 시작이 코드 뒤에 있으면 라인 수로 센다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  public static void main(String[] args) { /*
            |    System.out.println("Hello, World!");
            |  }
            |  a
            |  b 123
            |  cdefg
            |  */   System.out.println("Hello World 2");
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 5
    }

    "멀티라인 주석이 끝나자 마자 문자열이 존재하는 경우 코드로 남긴다" {
        // language=java
        val javaCode = """
            |class Simple {
            |  public static void main(String[] args) { /*
            |    this line should be removed
            |  */"/*wow*/"
            |  System.out.println("Hello Wor/*wow*/ld 2");
            |  }
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        // language=java
        sut.commentsRemoved() shouldBe """
            |class Simple {
            |  public static void main(String[] args) { 
            |"/*wow*/"
            |  System.out.println("Hello Wor/*wow*/ld 2");
            |  }
            |}
            """.trimMargin()
    }

    "Dave.java" {
        // language=java
        val javaCode = """
            |// This file contains 3 lines of code
            |public interface Dave {
            |  /**
            |   * count the number of lines in a file
            |   */
            |  int countLines(File inFile); // not the real signature!
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 3
    }

    "Hello.java" {
        // language=java
        val javaCode = """
            |/*****
            | * This is a test program with 5 lines of code
            | *  \/* no nesting allowed!
            | //*****/
            |
            |/***/// Slightly pathological comment ending...
            |
            |public class Hello {
            |  public static final void main(String[] args) { // gotta love Java
            |    // Say hello
            |    System./*wait*/out./*for*/println/*it*/("Hello/*");
            |    /*wait*//*for*//*it*/
            |    /*wait*//*for*//*it*/
            |    /*wait*//*for*//*it*/
            |    /*wait*//*for*//*it*/
            |    /*wait*//*for*//*it*/
            |    
            |    /* wow
            |    gogo
            |    */ System.out./*wow*/println/*gogo*/("Hel/*lo"); /* 34 */
            |    System.out./*wow*/println/*gogo*/("*/H/*ello");
            |   /*2142*/ System.out.println/*gogo*/("He*/llo");
            |    System.out.println/*gogo*/("He//ll//o"); System.out.println/*gogo*/("He//ll//o"); 
            |    System.out.println/*gogo*/("He//ll//o"); // wow 
            |  }
            |
            |}
            """.trimMargin()
        val sut = CountingJavaLines(javaCode)

        sut.count() shouldBe 10
    }
})
