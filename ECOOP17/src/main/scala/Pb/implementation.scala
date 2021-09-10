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


   def pb(
      c_Pb_Q_1: InChannel[PlayB],
      c_Pb_Pc_1: OutChannel[InfoBC],
      c_Pb_Pc_2: OutChannel[InfoAB],
      c_Pb_Pa_1: InChannel[Mov1AB|Mov2AB]
   ):Pb[c_Pb_Q_1.type,c_Pb_Pc_1.type,c_Pb_Pc_2.type,c_Pb_Pa_1.type] ={
      receive(c_Pb_Q_1) {
         (x:PlayB) =>
         print("Pb:Receive type PlayB through channel c_Pb_Q_1\n")
         rec(RecPb_1){
            print("Pb:entering loop RecPb_1\n")
            print("Pb:Sending InfoBC through channel c_Pb_Pc_1\n")
            send(c_Pb_Pc_1,InfoBC("REPLACE_ME")) >> {
               print("Pb:Sending InfoAB through channel c_Pb_Pc_2\n")
               send(c_Pb_Pc_2,InfoAB("REPLACE_ME")) >> {
                  receive(c_Pb_Pa_1) {
                     (x_2:Mov1AB|Mov2AB) =>
                     print("Pb:Receive type Mov1AB|Mov2AB through channel c_Pb_Pa_1\n")
                     pb_2(x_2)
                  }
               }
            }
         }
      }
   }



