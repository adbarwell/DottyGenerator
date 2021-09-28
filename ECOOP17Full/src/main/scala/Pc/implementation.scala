package output.Pc.implementation

import output.caseclass._
import output.Pc.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   private def pc_2(
      x_2: Mov2AC|Mov1AC,
      c_Pc_Pa_3: OutChannel[Mov1CA|Mov2CA]
   ):Pc_2[x_2.type,c_Pc_Pa_3.type] =
      x_2 match {
         case x_2 : Mov2AC => {
            print("Pc:Actual type Received from x_2: Mov2AC\n")
            val r = scala.util.Random(System.currentTimeMillis())
            val decision = r.nextInt(2)
            print("Pc:Making selection through channel c_Pc_Pa_3\n")
            if(decision == 0){
               print("Pc:Sending Mov1CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov1CA(-1)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
            else{
               print("Pc:Sending Mov2CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov2CA(true)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
         }
         case x_2 : Mov1AC => {
            print("Pc:Actual type Received from x_2: Mov1AC\n")
            val r = scala.util.Random(System.currentTimeMillis())
            val decision = r.nextInt(2)
            print("Pc:Making selection through channel c_Pc_Pa_3\n")
            if(decision == 0){
               print("Pc:Sending Mov1CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov1CA(-1)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
            else{
               print("Pc:Sending Mov2CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov2CA(true)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
         }
      }


   def pc(c5_1 : InChannel[InChannel[InfoBC]],
          c5_2 : InChannel[OutChannel[InfoCA]],
          c5_3 : InChannel[InChannel[Mov2AC|Mov1AC]],
          c5_4 : InChannel[OutChannel[Mov1CA|Mov2CA]]): Pc[c5_1.type,
                                                           c5_2.type,
                                                           c5_3.type,
                                                           c5_4.type] ={
      receive(c5_1) {(c4 : InChannel[InfoBC]) =>
         println("Pc:Received type InChannel[InfoBC] through channel c5_1")
         receive(c5_2) {(c2 : OutChannel[InfoCA]) =>
            println("Pc:Received type OutChannel[InfroCAO) through channel c5_2")
            receive(c5_3) {(c1 : InChannel[Mov2AC|Mov1AC]) =>
               println("Pc:Received type InChannel[Mov2AC|Mov1AC] through channel c5_3")
               receive(c5_4) {(c3 : OutChannel[Mov1CA|Mov2CA]) =>
                  println("Pc:Received type OutChannel[Mov1CA|Mov2CA] through channel c5_4")
                  rec(RecPc_1){
                     print("Pc:entering loop RecPc_1\n")
                     receive(c4) {
                        (x:InfoBC) =>
                        print("Pc:Receive type InfoBC through channel c4\n")
                        print("Pc:Sending InfoCA through channel c2\n")
                        send(c2,InfoCA("REPLACE_ME")) >> {
                           receive(c1) {
                              (x_2:Mov2AC|Mov1AC) =>
                              print("Pc:Receive type Mov2AC|Mov1AC through channel c1\n")
                              pc_2(x_2,c3)
                           }
                        }
                     }
                  }
               }

            }
         }
      }
   }



