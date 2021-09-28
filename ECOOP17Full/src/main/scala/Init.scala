// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package output.Init

import effpi.process.dsl._
import effpi.channel.{Channel, InChannel, OutChannel}

import output.Pc.types._
import output.Pa.types._
import output.Pb.types._
import output.Q.types._

import output.Pc.implementation._
import output.Pa.implementation._
import output.Pb.implementation._
import output.Q.implementation._

import output.caseclass._

// To run this example, try:
// sbt 'tests/runMain output.Init.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    // val c1 = Channel[Mov2AC|Mov1AC]()
    // val c2 = Channel[InfoCA]()
    // val c3 = Channel[Mov1CA|Mov2CA]()
    // val c4 = Channel[InfoBC]()
    // val c6 = Channel[InfoAB]()
    // val c7 = Channel[Mov2AB|Mov1AB]())

    // c
    val c5_1 = Channel[InChannel[InfoBC]]()
    val c5_2 = Channel[OutChannel[InfoCA]]()
    val c5_3 = Channel[InChannel[Mov2AC|Mov1AC]]()
    val c5_4 = Channel[OutChannel[Mov1CA|Mov2CA]]()

    // a
    val c8_1 = Channel[InChannel[InfoCA]]()
    val c8_2 = Channel[OutChannel[InfoAB]]()
    val c8_3 = Channel[OutChannel[Mov1AB|Mov2AB]]()
    val c8_4 = Channel[OutChannel[Mov1AC]]()
    val c8_5 = Channel[InChannel[Mov1CA|Mov2CA]]()
    val c8_6 = Channel[OutChannel[Mov2AC]]()

    // b
    val c9_1 = Channel[OutChannel[InfoBC]]()
    val c9_2 = Channel[InChannel[InfoAB]]()
    val c9_3 = Channel[InChannel[Mov2AB|Mov1AB]]()

    // eval(par(
    // //  pc(c5, c4, c2, c1, c3),
    // //  pa(c8, c2, c6, c7, c1, c3, c1),
    //  pb(c9),
    //  q(c4, c6, c7, c8, c9, c5)))

    eval(
      par(
        q(c5_1, c5_2, c5_3, c5_4, c8_1, c8_2, c8_3, c8_4, c8_5, c8_6, c9_1, c9_2, c9_3),
        pa(c8_1, c8_2, c8_3, c8_4, c8_5, c8_6),
        pb(c9_1, c9_2, c9_3),
        pc(c5_1, c5_2, c5_3, c5_4)
      )
    )

    Thread.sleep(1000)
    ps.kill()
  }
}
