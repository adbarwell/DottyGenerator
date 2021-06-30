package com.P.implementation

import com.caseclass._
import com.P.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   def p(
      c_P_Q_1: OutChannel[Data],
      c_P_R_1: OutChannel[Data]
   ):P[c_P_Q_1.type,c_P_R_1.type] ={
      if(false){throw Exception("Some exception")}
      print("P:Sending Data through channel c_P_Q_1\n")
      send(c_P_Q_1,Data()) >> {
         if(false){throw Exception("Some exception")}
         print("P:Sending Data through channel c_P_R_1\n")
         send(c_P_R_1,Data()) >> {
            print("P:Terminating...\n")
            nil
         }
      }
   }



