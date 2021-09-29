// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package output.Init

import effpi.process.dsl._
import effpi.channel.Channel

import output.Pa.types._
import output.Pb.types._
import output.Q.types._

import output.Pa.implementation._
import output.Pb.implementation._
import output.Q.implementation._

import output.caseclass._

// To run this example, try:
// sbt 'tests/runMain effpi_sandbox.Init.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    val(c1, c2, c3, c4, c5, c6) = 
     (Channel[InfoAB](),
     Channel[InfoBA](),
     Channel[MovAB](),
     Channel[MovBA](),
     Channel[PlayA](),
     Channel[PlayB]()) 

    eval(par(
     pa(c5, c1, c2, c3, c4),
     pb(c6, c1, c2, c3, c4),
     q(c5, c6)))


    Thread.sleep(1000)
    ps.kill()
  }
}
