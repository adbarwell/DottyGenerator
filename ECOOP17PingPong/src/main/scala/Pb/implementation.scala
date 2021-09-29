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

   def pb(
      c_Pb_Q_1: InChannel[PlayB],
      c_Pb_Pa_1: InChannel[InfoAB],
      c_Pb_Pa_2: OutChannel[InfoBA],
      c_Pb_Pa_3: InChannel[MovAB],
      c_Pb_Pa_4: OutChannel[MovBA]
   ):Pb[c_Pb_Q_1.type,c_Pb_Pa_1.type,c_Pb_Pa_2.type,c_Pb_Pa_3.type,c_Pb_Pa_4.type] ={
      receive(c_Pb_Q_1) {
         (x:PlayB) =>
         print("Pb:Receive type PlayB through channel c_Pb_Q_1\n")
         receive(c_Pb_Pa_1) {
            (x:InfoAB) =>
            print("Pb:Receive type InfoAB through channel c_Pb_Pa_1\n")
            print("Pb:Sending InfoBA through channel c_Pb_Pa_2\n")
            send(c_Pb_Pa_2,InfoBA("REPLACE_ME")) >> {
               rec(RecPb_3){
                  print("Pb:entering loop RecPb_3\n")
                  receive(c_Pb_Pa_3) {
                     (x:MovAB) =>
                     print("Pb:Receive type MovAB through channel c_Pb_Pa_3\n")
                     print("Pb:Sending MovBA through channel c_Pb_Pa_4\n")
                     send(c_Pb_Pa_4,MovBA()) >> {
                        print("Pb:go to loop RecPb_3\n")
                        loop(RecPb_3)
                     }
                  }
               }
            }
         }
      }
   }



