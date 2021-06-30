package com.A2.implementation

import com.caseclass._
import com.A2.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   def a2(
      c_A2_P_1: InChannel[Propose],
      c_A2_P_2: OutChannel[Reject|Accept]
   ):A2[c_A2_P_1.type,c_A2_P_2.type] ={
      receiveErr(c_A2_P_1) ({
         (x:Propose) =>
         print("A2:Received type Propose through channel c_A2_P_1\n")
         val r = scala.util.Random(System.currentTimeMillis())
         val decision = r.nextInt(2)
         print("A2:Making selection through channel c_A2_P_2\n")
         if(decision == 0){
            if(false){throw Exception("Some exception")}
            print("A2:Sending Reject through channel c_A2_P_2\n")
            send(c_A2_P_2,Reject()) >> {
               print("A2:Terminating...\n")
               nil
            }
         }
         else{
            if(false){throw Exception("Some exception")}
            print("A2:Sending Accept through channel c_A2_P_2\n")
            send(c_A2_P_2,Accept()) >> {
               print("A2:Terminating...\n")
               nil
            }
         }
      },
      {(err : Throwable) =>
         print("A2:Receive Propose through channel c_A2_P_1 TIMEOUT, activating new option\n")
         print("A2:Terminating...\n")
         nil
      }, Duration("5 seconds"))
   }



