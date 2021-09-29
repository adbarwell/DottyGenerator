package output.Pa.implementation

import output.caseclass._
import output.Pa.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   def pa(
      c_Pa_Q_1: InChannel[PlayA],
      c_Pa_Pb_1: OutChannel[InfoAB],
      c_Pa_Pb_2: InChannel[InfoBA],
      c_Pa_Pb_3: OutChannel[MovAB],
      c_Pa_Pb_4: InChannel[MovBA]
   ):Pa[c_Pa_Q_1.type,c_Pa_Pb_1.type,c_Pa_Pb_2.type,c_Pa_Pb_3.type,c_Pa_Pb_4.type] ={
      receive(c_Pa_Q_1) {
         (x:PlayA) =>
         print("Pa:Receive type PlayA through channel c_Pa_Q_1\n")
         print("Pa:Sending InfoAB through channel c_Pa_Pb_1\n")
         send(c_Pa_Pb_1,InfoAB("REPLACE_ME")) >> {
            receive(c_Pa_Pb_2) {
               (x:InfoBA) =>
               print("Pa:Receive type InfoBA through channel c_Pa_Pb_2\n")
               rec(RecPa_3){
                  print("Pa:entering loop RecPa_3\n")
                  print("Pa:Sending MovAB through channel c_Pa_Pb_3\n")
                  send(c_Pa_Pb_3,MovAB()) >> {
                     receive(c_Pa_Pb_4) {
                        (x:MovBA) =>
                        print("Pa:Receive type MovBA through channel c_Pa_Pb_4\n")
                        print("Pa:go to loop RecPa_3\n")
                        loop(RecPa_3)
                     }
                  }
               }
            }
         }
      }
   }



