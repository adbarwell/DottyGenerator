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

   private def p_3(
      x_3: Reject|Accept,
      c_P_L_1: OutChannel[Rejected],
      c_P_L_2: OutChannel[Accepted]
   ):P_3[x_3.type,c_P_L_1.type,c_P_L_2.type] =
      x_3 match {
         case x_3 : Reject => {
            print("P:Actual type Received from x_3: Reject\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Rejected through channel c_P_L_1\n")
            send(c_P_L_1,Rejected()) >> {
               print("P:Terminating...\n")
               nil
            }
         }
         case x_3 : Accept => {
            print("P:Actual type Received from x_3: Accept\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Accepted through channel c_P_L_2\n")
            send(c_P_L_2,Accepted()) >> {
               print("P:Terminating...\n")
               nil
            }
         }
      }


   private def p_4(
      x_4: Reject|Accept,
      c_P_L_2: OutChannel[Accepted]
   ):P_4[x_4.type,c_P_L_2.type] =
      x_4 match {
         case x_4 : Reject => {
            print("P:Actual type Received from x_4: Reject\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Accepted through channel c_P_L_2\n")
            send(c_P_L_2,Accepted()) >> {
               print("P:Terminating...\n")
               nil
            }
         }
         case x_4 : Accept => {
            print("P:Actual type Received from x_4: Accept\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Accepted through channel c_P_L_2\n")
            send(c_P_L_2,Accepted()) >> {
               print("P:Terminating...\n")
               nil
            }
         }
      }


   private def p_2(
      x_2: Reject|Accept,
      c_P_A2_2: InChannel[Reject|Accept],
      c_P_L_1: OutChannel[Rejected],
      c_P_L_2: OutChannel[Accepted]
   ):P_2[x_2.type,c_P_A2_2.type,c_P_L_1.type,c_P_L_2.type] =
      x_2 match {
         case x_2 : Reject => {
            print("P:Actual type Received from x_2: Reject\n")
            receiveErr(c_P_A2_2) ({
               (x_3:Reject|Accept) =>
               print("P:Received type Reject|Accept through channel c_P_A2_2\n")
               p_3(x_3,c_P_L_1,c_P_L_2)
            },
            {(err : Throwable) =>
               print("P:Receive Reject|Accept through channel c_P_A2_2 TIMEOUT, activating new option\n")
               if(false){throw Exception("Some exception")}
               print("P:Sending Rejected through channel c_P_L_1\n")
               send(c_P_L_1,Rejected()) >> {
                  print("P:Terminating...\n")
                  nil
               }
            }, Duration("5 seconds"))
         }
         case x_2 : Accept => {
            print("P:Actual type Received from x_2: Accept\n")
            receiveErr(c_P_A2_2) ({
               (x_4:Reject|Accept) =>
               print("P:Received type Reject|Accept through channel c_P_A2_2\n")
               p_4(x_4,c_P_L_2)
            },
            {(err : Throwable) =>
               print("P:Receive Reject|Accept through channel c_P_A2_2 TIMEOUT, activating new option\n")
               if(false){throw Exception("Some exception")}
               print("P:Sending Accepted through channel c_P_L_2\n")
               send(c_P_L_2,Accepted()) >> {
                  print("P:Terminating...\n")
                  nil
               }
            }, Duration("5 seconds"))
         }
      }


   private def p_5(
      x_5: Reject|Accept,
      c_P_L_1: OutChannel[Rejected],
      c_P_L_2: OutChannel[Accepted]
   ):P_5[x_5.type,c_P_L_1.type,c_P_L_2.type] =
      x_5 match {
         case x_5 : Reject => {
            print("P:Actual type Received from x_5: Reject\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Rejected through channel c_P_L_1\n")
            send(c_P_L_1,Rejected()) >> {
               print("P:Terminating...\n")
               nil
            }
         }
         case x_5 : Accept => {
            print("P:Actual type Received from x_5: Accept\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Accepted through channel c_P_L_2\n")
            send(c_P_L_2,Accepted()) >> {
               print("P:Terminating...\n")
               nil
            }
         }
      }


   def p(
      c_P_A1_1: OutChannel[Propose],
      c_P_A2_1: OutChannel[Propose],
      c_P_A1_2: InChannel[Reject|Accept],
      c_P_A2_2: InChannel[Reject|Accept],
      c_P_L_1: OutChannel[Rejected],
      c_P_L_2: OutChannel[Accepted]
   ):P[c_P_A1_1.type,c_P_A2_1.type,c_P_A1_2.type,c_P_A2_2.type,c_P_L_1.type,c_P_L_2.type] ={
      if(false){throw Exception("Some exception")}
      print("P:Sending Propose through channel c_P_A1_1\n")
      send(c_P_A1_1,Propose()) >> {
         if(false){throw Exception("Some exception")}
         print("P:Sending Propose through channel c_P_A2_1\n")
         send(c_P_A2_1,Propose()) >> {
            receiveErr(c_P_A1_2) ({
               (x_2:Reject|Accept) =>
               print("P:Received type Reject|Accept through channel c_P_A1_2\n")
               p_2(x_2,c_P_A2_2,c_P_L_1,c_P_L_2)
            },
            {(err : Throwable) =>
               print("P:Receive Reject|Accept through channel c_P_A1_2 TIMEOUT, activating new option\n")
               receiveErr(c_P_A2_2) ({
                  (x_5:Reject|Accept) =>
                  print("P:Received type Reject|Accept through channel c_P_A2_2\n")
                  p_5(x_5,c_P_L_1,c_P_L_2)
               },
               {(err : Throwable) =>
                  print("P:Receive Reject|Accept through channel c_P_A2_2 TIMEOUT, activating new option\n")
                  if(false){throw Exception("Some exception")}
                  print("P:Sending Rejected through channel c_P_L_1\n")
                  send(c_P_L_1,Rejected()) >> {
                     print("P:Terminating...\n")
                     nil
                  }
               }, Duration("5 seconds"))
            }, Duration("5 seconds"))
         }
      }
   }



