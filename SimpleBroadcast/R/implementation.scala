package com.R.implementation

import com.caseclass._
import com.R.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   def r(
      c_R_P_1: InChannel[Data],
      c_R_Q_1: OutChannel[OK],
      c_R_Q_2: OutChannel[Rec],
      c_R_Q_3: InChannel[KO|Data]
   ):R[c_R_P_1.type,c_R_Q_1.type,c_R_Q_2.type,c_R_Q_3.type] ={
      receiveErr(c_R_P_1) ({
         (x:Data) =>
         print("R:Received type Data through channel c_R_P_1\n")
         if(false){throw Exception("Some exception")}
         print("R:Sending OK through channel c_R_Q_1\n")
         send(c_R_Q_1,OK()) >> {
            print("R:Terminating...\n")
            nil
         }
      },
      {(err : Throwable) =>
         print("R:Receive Data through channel c_R_P_1 TIMEOUT, activating new option\n")
         if(false){throw Exception("Some exception")}
         print("R:Sending Rec through channel c_R_Q_2\n")
         send(c_R_Q_2,Rec()) >> {
            receiveErr(c_R_Q_3) ({
               (x:KO|Data) =>
               print("R:Received type KO|Data through channel c_R_Q_3\n")
               print("R:Terminating...\n")
               nil
            },
            {(err : Throwable) =>
               print("R:Receive KO|Data through channel c_R_Q_3 TIMEOUT, activating new option\n")
               print("R:Terminating...\n")
               nil
            }, Duration("5 seconds"))
         }
      }, Duration("5 seconds"))
   }



