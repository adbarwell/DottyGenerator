// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package output.PROTOCOL

import scala.concurrent.duration.Duration

import effpi.recurse._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{Channel, InChannel, OutChannel}
import java.util.Date

package types {

CASE_CLASSES
EFFPI_TYPES
}

package implementation {
  import types._
  implicit val timeout: Duration = Duration("60 seconds")
  import effpi.process.dsl._

  FUNCTIONS
}

// To run this example, try:
// sbt 'tests/runMain output.PROTOCOL.Main'
object Main {
  import types._
  import implementation._

  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    CHANNEL_ASSIGN

    Thread.sleep(1000)
    ps.kill()
  }
}
