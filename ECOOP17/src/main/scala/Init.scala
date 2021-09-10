// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package output.Init

import effpi.process.dsl._
import effpi.channel.Channel

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

    val(c1, c2, c3, c4, c5, c6, c7, c8, c9) = 
     (Channel[Mov2AC|Mov1AC](),
     Channel[InfoCA](),
     Channel[Mov1CA|Mov2CA](),
     Channel[InfoBC](),
     Channel[PlayC](),
     Channel[InfoAB](),
     Channel[Mov2AB|Mov1AB](),
     Channel[PlayA](),
     Channel[PlayB]()) 

    eval(par(
     pc(c5, c4, c2, c1, c3),
     pa(c8, c2, c6, c7, c1, c3, c1),
     pb(c9, c4, c6, c7),
     q(c8, c9, c5)))


    Thread.sleep(1000)
    ps.kill()
  }
}
