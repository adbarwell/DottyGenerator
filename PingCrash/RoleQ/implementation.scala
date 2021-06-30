package com.RoleQ.implementation

import com.caseclass._
import com.RoleQ.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

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



