// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package com.PingCrash

import effpi.process._
import effpi.process.dsl._
import effpi.channel.Channel
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration

case class Ping()

type RoleP[ 
C_RoleP_RoleQ_1 <: OutChannel[Ping]] = 
 Out[C_RoleP_RoleQ_1,Ping] >>: PNil 

type RoleQ[ 
C_RoleQ_RoleP_1 <: InChannel[Ping]] = 
 InErr[C_RoleQ_RoleP_1, Ping, (x:Ping) => PNil, (err:Throwable) => PNil] 

implicit val timeout: Duration = Duration("60 seconds")

def roleP(
  c_RoleP_RoleQ_1: OutChannel[Ping]
):RoleP[c_RoleP_RoleQ_1.type] ={
  if(false){throw Exception("Some exception")}
  print("RoleP:Sending Ping through channel c_RoleP_RoleQ_1\n")
  send(c_RoleP_RoleQ_1,Ping()) >> {
      print("RoleP:Terminating...\n")
      nil
  }
}

def roleQ(
  c_RoleQ_RoleP_1: InChannel[Ping]
):RoleQ[c_RoleQ_RoleP_1.type] ={
  receiveErr(c_RoleQ_RoleP_1) ({
      (x:Ping) =>
      print("RoleQ:Received type Ping through channel c_RoleQ_RoleP_1\n")
      print("RoleQ:Terminating...\n")
      nil
  },
  {(err : Throwable) =>
      print("RoleQ:Receive Ping through channel c_RoleQ_RoleP_1 TIMEOUT, activating new option\n")
      print("RoleQ:Terminating...\n")
      nil
  }, Duration("5 seconds"))
}

// To run this example, try:
// sbt 'tests/runMain com.PingCrash.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    val(c1) = 
     (Channel[Ping]()) 

    eval(par(
     roleQ(c1),
     roleP(c1)))


    Thread.sleep(1000)
    ps.kill()
  }
}