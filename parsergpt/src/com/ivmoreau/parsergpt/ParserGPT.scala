package com.ivmoreau.parsergpt

import cats.parse.Parser
import cats.instances.char
import fabric._
import fabric.rw._
import fabric.io._

object ParserGPT:
  private def read(acc: Int): Parser[String] =
    var accu = acc
    Parser.charsWhile(_ => {
      accu = accu - 1
      accu > 0
    })

  private case class Out[A](val parsed: String, output: A)

  /**
    * This function does magic.
    *
    * @param expected describes what you are trying to parse, be specific.
    * @param output describres how your output json should be structurates, a schema.
    * @param maxRead the maximum number of characters to processby this parser, should
    * be lower than 500.
    * @param rw instance defined for fabric.
    * @return
    */
  def apply[A](expected: String, output: String, maxRead: Int = 125)(implicit rw: RW[A], apiKey: String): Parser[A] =
    implicit val rw: RW[Out[A]] = RW.gen[Out[A]]
    var parseVar = ""
    assert(maxRead < 500, "Oh no, too big! Try a lower boundary for maxRead")
    val input: String => String = promp => s"""
      |/*Given $expected, generate a JSON structure such that is defined as follows:*/
      |$output
      |/*You should also specify the part of the input that you are actually parsing
      |from; left the remaining text intact. Specify it like the folowing:
      |RESULT:%%{"parsed": <the parsed text>, "output": <the output structure as
      |previously specified.>}%%*/
      |
      |INPUT:$promp
      |RESULT%%{"parsed": "
      """.stripMargin('|')
    val parsedActual = read(maxRead).flatMap(a => {
      parseVar = a
      Parser.fail
    })
    def result(a: => String): Out[A] = 
      val r = input(a)
      val o: String = CallGPT(r)
      val json = JsonParser(o, Format.Json)
      json.as[Out[A]]
    parsedActual.backtrack | Parser.unit.with1.flatMap {_ =>
      val r = result(parseVar)
      Parser.string(r.parsed).map(_ => r.output)  
    }
