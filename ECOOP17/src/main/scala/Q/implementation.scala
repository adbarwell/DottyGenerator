package output.Q.implementation

import output.caseclass._
import output.Q.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   def q(
      c_Q_Pa_1: OutChannel[PlayA],
      c_Q_Pb_1: OutChannel[PlayB],
      c_Q_Pc_1: OutChannel[PlayC]
   ):Q[c_Q_Pa_1.type,c_Q_Pb_1.type,c_Q_Pc_1.type] ={
      print("Q:Sending PlayA through channel c_Q_Pa_1\n")
      send(c_Q_Pa_1,PlayA("REPLACE_ME")) >> {
         print("Q:Sending PlayB through channel c_Q_Pb_1\n")
         send(c_Q_Pb_1,PlayB("REPLACE_ME")) >> {
            print("Q:Sending PlayC through channel c_Q_Pc_1\n")
            send(c_Q_Pc_1,PlayC("REPLACE_ME")) >> {
               print("Q:Terminating...\n")
               nil
            }
         }
      }
   }



