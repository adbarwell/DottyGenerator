package com.L.implementation

import com.caseclass._
import com.L.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   def l(
      c_L_P_1: InChannel[Rejected|Accepted]
   ):L[c_L_P_1.type] ={
      receiveErr(c_L_P_1) ({
         (x:Rejected|Accepted) =>
         print("L:Received type Rejected|Accepted through channel c_L_P_1\n")
         print("L:Terminating...\n")
         nil
      },
      {(err : Throwable) =>
         print("L:Receive Rejected|Accepted through channel c_L_P_1 TIMEOUT, activating new option\n")
         print("L:Terminating...\n")
         nil
      }, Duration("5 seconds"))
   }



