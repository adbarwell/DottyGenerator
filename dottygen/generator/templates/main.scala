// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package com.PROTOCOL

import effpi.process.dsl._
import effpi.channel.Channel

IMPORT_TYPES
IMPORT_IMPLEMENTATIONS
import com.caseclass._

// To run this example, try:
// sbt 'tests/runMain com.PROTOCOL.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    CHANNEL_ASSIGN

    Thread.sleep(1000)
    ps.kill()
  }
}