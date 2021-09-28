package output.Pb.implementation

import output.caseclass._
import output.Pb.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   private def pb_2(
      x_2: Mov1AB|Mov2AB
   ):Pb_2[x_2.type] =
      x_2 match {
         case x_2 : Mov1AB => {
            print("Pb:Actual type Received from x_2: Mov1AB\n")
            print("Pb:go to loop RecPb_1\n")
            loop(RecPb_1)
         }
         case x_2 : Mov2AB => {
            print("Pb:Actual type Received from x_2: Mov2AB\n")
            print("Pb:go to loop RecPb_1\n")
            loop(RecPb_1)
         }
      }


   // def pb(c_Pb_Q_1: InChannel[PlayB[OutChannel[InfoBC], InChannel[InfoAB], InChannel[Mov1AB|Mov2AB]]]):Pb[c_Pb_Q_1.type] ={
   def pb(c9_1 : InChannel[OutChannel[InfoBC]],
          c9_2 : InChannel[InChannel[InfoAB]],
          c9_3 : InChannel[InChannel[Mov2AB|Mov1AB]]) : Pb[c9_1.type, c9_2.type, c9_3.type] ={
      receive(c9_1) {(c4 : OutChannel[InfoBC]) =>
         println("Pb:Receive type OutChannel[InfoBC] through channel c9_1")
         receive(c9_2) {(c6 : InChannel[InfoAB]) =>
            println("Pb:Received type InChannel[InfoAB] through channel c9_2")
            receive(c9_3) {(c7 : InChannel[Mov2AB|Mov1AB]) =>
               println("Pb:Received type InChannel[Mov2AB|Mov1AB] through channel c9_3")
               rec(RecPb_1){
                  print("Pb:entering loop RecPb_1\n")
                  print("Pb:Sending InfoBC through channel c4\n")
                  send(c4,InfoBC("REPLACE_ME")) >> {
                     receive(c6) {
                        (x:InfoAB) =>
                        print("Pb:Receive type InfoAB through channel c6\n")
                        receive(c7) {
                           (x_2:Mov1AB|Mov2AB) =>
                           print("Pb:Receive type Mov1AB|Mov2AB through channel c7\n")
                           pb_2(x_2)
                        }
                     }
                  }
               }
            }
         }

      }
   }



