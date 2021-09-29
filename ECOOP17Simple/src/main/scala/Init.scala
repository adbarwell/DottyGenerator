// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package output.Init

import effpi.process.dsl._
import effpi.channel.{Channel, InChannel, OutChannel}

import output.Pb.types._
import output.Q.types._
import output.Pc.types._
import output.Pa.types._

import output.Pb.implementation._
import output.Q.implementation._
import output.Pc.implementation._
import output.Pa.implementation._

import output.caseclass._

// To run this example, try:
// sbt 'tests/runMain output.Init.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    val c1_1 = Channel[OutChannel[InfoBC]]()
    val c1_2 = Channel[InChannel[InfoAB]]()
    val c1_3 = Channel[InChannel[MovAB]]()
    // val c2 = Channel[InfoBC]()
    // val c3 = Channel[InfoAB]()
    // val c4 = Channel[MovAB]()
    // val c5 = Channel[PlayC]()
    val c5_1 = Channel[InChannel[InfoBC]]()
    val c5_2 = Channel[OutChannel[InfoCA]]()
    val c5_3 = Channel[InChannel[MovAC]]()
    val c5_4 = Channel[OutChannel[MovCA]]()
    val c6_1 = Channel[InChannel[InfoCA]]()
    val c6_2 = Channel[OutChannel[InfoAB]]()
    val c6_3 = Channel[OutChannel[MovAB]]()
    val c6_4 = Channel[OutChannel[MovAC]]()
    val c6_5 = Channel[InChannel[MovCA]]()
    // val c7 = Channel[MovAC]()
    // val c8 = Channel[MovCA]()
    // val c9 = Channel[InfoCA]()

    eval(par(
     q(c1_1, c1_2, c1_3, c5_1, c5_2, c5_3, c5_4, c6_1, c6_2, c6_3, c6_4, c6_5),
    //  pa(c6, c9, c3, c4, c7, c8),
     pa(c6_1, c6_2, c6_3, c6_4, c6_5),
    //  pb(c1, c2, c3, c4),
     pb(c1_1, c1_2, c1_3),
    //  pc(c5, c2, c9, c7, c8)))
     pc(c5_1, c5_2, c5_3, c5_4)))


    Thread.sleep(1000)
    ps.kill()
  }
}
