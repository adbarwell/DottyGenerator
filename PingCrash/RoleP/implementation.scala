package com.RoleP.implementation

import com.caseclass._
import com.RoleP.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

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



