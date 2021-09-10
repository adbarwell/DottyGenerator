// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package output.Init

import effpi.process.dsl._
import effpi.channel.Channel

import output.Q.types._
import output.Pb.types._
import output.Pa.types._
import output.Pc.types._

import output.Q.implementation._
import output.Pb.implementation._
import output.Pa.implementation._
import output.Pc.implementation._

import output.caseclass._

// To run this example, try:
// sbt 'tests/runMain effpi_sandbox.Init.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    val(c1, c2, c3, c4, c5, c6, c7, c8, c9) = 
     (Channel[PlayB](),
     Channel[PlayA](),
     Channel[PlayC](),
     Channel[Mov2AB|Mov1AB](),
     Channel[InfoAB](),
     Channel[InfoBC](),
     Channel[Mov2AC|Mov1BC](),
     Channel[InfoCA](),
     Channel[Mov1CA|Mov2CA]()) 

    eval(par(
     q(c2, c1, c3),
     pb(c1, c6, c5, c4),
     pa(c2, c8, c4, c7, c9, c7),
     pc(c3, c6, c8, c5, c7, c9)))


    Thread.sleep(1000)
    ps.kill()
  }
}
