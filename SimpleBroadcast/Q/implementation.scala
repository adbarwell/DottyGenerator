package com.Q.implementation

import com.caseclass._
import com.Q.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   private def q_2(
      x_2: OK|Rec,
      c_Q_R_2: OutChannel[KO|Data]
   ):Q_2[x_2.type,c_Q_R_2.type] =
      x_2 match {
         case x_2 : OK => {
            print("Q:Actual type Received from x_2: OK\n")
            print("Q:Terminating...\n")
            nil
         }
         case x_2 : Rec => {
            print("Q:Actual type Received from x_2: Rec\n")
            val r = scala.util.Random(System.currentTimeMillis())
            val decision = r.nextInt(2)
            print("Q:Making selection through channel c_Q_R_2\n")
            if(decision == 0){
               if(false){throw Exception("Some exception")}
               print("Q:Sending KO through channel c_Q_R_2\n")
               send(c_Q_R_2,KO()) >> {
                  print("Q:Terminating...\n")
                  nil
               }
            }
            else{
               if(false){throw Exception("Some exception")}
               print("Q:Sending Data through channel c_Q_R_2\n")
               send(c_Q_R_2,Data()) >> {
                  print("Q:Terminating...\n")
                  nil
               }
            }
         }
      }


   private def q_3(
      x_3: Rec|OK,
      c_Q_R_2: OutChannel[KO|Data]
   ):Q_3[x_3.type,c_Q_R_2.type] =
      x_3 match {
         case x_3 : Rec => {
            print("Q:Actual type Received from x_3: Rec\n")
            val r = scala.util.Random(System.currentTimeMillis())
            val decision = r.nextInt(2)
            print("Q:Making selection through channel c_Q_R_2\n")
            if(decision == 0){
               if(false){throw Exception("Some exception")}
               print("Q:Sending KO through channel c_Q_R_2\n")
               send(c_Q_R_2,KO()) >> {
                  print("Q:Terminating...\n")
                  nil
               }
            }
            else{
               if(false){throw Exception("Some exception")}
               print("Q:Sending Data through channel c_Q_R_2\n")
               send(c_Q_R_2,Data()) >> {
                  print("Q:Terminating...\n")
                  nil
               }
            }
         }
         case x_3 : OK => {
            print("Q:Actual type Received from x_3: OK\n")
            print("Q:Terminating...\n")
            throw Exception("This branch should not be reached! Please check your communication!")
            nil
         }
      }


   def q(
      c_Q_P_1: InChannel[Data],
      c_Q_R_1: InChannel[OK|Rec],
      c_Q_R_2: OutChannel[KO|Data]
   ):Q[c_Q_P_1.type,c_Q_R_1.type,c_Q_R_2.type] ={
      receiveErr(c_Q_P_1) ({
         (x:Data) =>
         print("Q:Received type Data through channel c_Q_P_1\n")
         receiveErr(c_Q_R_1) ({
            (x_2:OK|Rec) =>
            print("Q:Received type OK|Rec through channel c_Q_R_1\n")
            q_2(x_2,c_Q_R_2)
         },
         {(err : Throwable) =>
            print("Q:Receive OK|Rec through channel c_Q_R_1 TIMEOUT, activating new option\n")
            print("Q:Terminating...\n")
            nil
         }, Duration("5 seconds"))
      },
      {(err : Throwable) =>
         print("Q:Receive Data through channel c_Q_P_1 TIMEOUT, activating new option\n")
         receiveErr(c_Q_R_1) ({
            (x_3:Rec|OK) =>
            print("Q:Received type Rec|OK through channel c_Q_R_1\n")
            q_3(x_3,c_Q_R_2)
         },
         {(err : Throwable) =>
            print("Q:Receive Rec|OK through channel c_Q_R_1 TIMEOUT, activating new option\n")
            print("Q:Terminating...\n")
            nil
         }, Duration("5 seconds"))
      }, Duration("5 seconds"))
   }



