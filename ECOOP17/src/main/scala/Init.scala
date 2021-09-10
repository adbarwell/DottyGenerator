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

    val(c1, c2, c3, c4, c6, c7) = 
     (Channel[Mov2AC|Mov1AC](), // c1
     Channel[InfoCA](),         // c2
     Channel[Mov1CA|Mov2CA](),  // c3
     Channel[InfoBC](),         // c4
    //  Channel[PlayC](),          // c5 // Send session
     Channel[InfoAB](),         // c6
     Channel[Mov2AB|Mov1AB]()) //,  // c7
    //  Channel[PlayA](),          // c8 // Send session
    //  Channel[PlayB[c4.type,c6.type,c7.type]]())          // c9 // Send session

    val (c5, c8, c9) = (Channel[PlayA](),
                        Channel[PlayB[c4.type,c6.type,c7.type]](),
                        Channel[PlayC]())

    eval(par(
    //  pc(c5, c4, c2, c1, c3),
    //  pa(c8, c2, c6, c7, c1, c3, c1),
     pb(c9),
     q(c4, c6, c7, c8, c9, c5)))


    Thread.sleep(1000)
    ps.kill()
  }
}
