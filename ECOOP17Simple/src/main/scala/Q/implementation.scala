package output.Q.implementation

import output.caseclass._
import output.Q.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{Channel,InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("60 seconds")

   // def q(
   //    c_Q_Pa_1: OutChannel[PlayA],
   //    c_Q_Pb_1: OutChannel[PlayB],
   //    c_Q_Pc_1: OutChannel[PlayC]
   // ):Q[c_Q_Pa_1.type,c_Q_Pb_1.type,c_Q_Pc_1.type] ={
   //    print("Q:Sending PlayA through channel c_Q_Pa_1\n")
   //    send(c_Q_Pa_1,PlayA("REPLACE_ME")) >> {
   //       print("Q:Sending PlayB through channel c_Q_Pb_1\n")
   //       send(c_Q_Pb_1,PlayB("REPLACE_ME")) >> {
   //          print("Q:Sending PlayC through channel c_Q_Pc_1\n")
   //          send(c_Q_Pc_1,PlayC("REPLACE_ME")) >> {
   //             print("Q:Terminating...\n")
   //             nil
   //          }
   //       }
   //    }
   // }

def q(c1_1 : OutChannel[OutChannel[InfoBC]],
      c1_2 : OutChannel[InChannel[InfoAB]],
      c1_3 : OutChannel[InChannel[MovAB]],
      c5_1 : OutChannel[InChannel[InfoBC]],
      c5_2 : OutChannel[OutChannel[InfoCA]],
      c5_3 : OutChannel[InChannel[MovAC]],
      c5_4 : OutChannel[OutChannel[MovCA]],
      c6_1 : OutChannel[InChannel[InfoCA]],
      c6_2 : OutChannel[OutChannel[InfoAB]],
      c6_3 : OutChannel[OutChannel[MovAB]],
      c6_4 : OutChannel[OutChannel[MovAC]],
      c6_5 : OutChannel[InChannel[MovCA]]) : Q[c1_1.type,
                                               c1_2.type,
                                               c1_3.type,
                                               c5_1.type,
                                               c5_2.type,
                                               c5_3.type,
                                               c5_4.type,
                                               c6_1.type,
                                               c6_2.type,
                                               c6_3.type,
                                               c6_4.type,
                                               c6_5.type] = {
  
  val c2 = Channel[InfoBC]()
  val c3 = Channel[InfoAB]()
  val c4 = Channel[MovAB]()
  val c7 = Channel[MovAC]()
  val c8 = Channel[MovCA]()
  val c9 = Channel[InfoCA]()

  send(c1_1, c2) >>
  send(c1_2, c3) >>
  send(c1_3, c4) >>
  send(c5_1, c2) >>
  send(c5_2, c9) >>
  send(c5_3, c7) >>
  send(c5_4, c8) >>
  send(c6_1, c9) >>
  send(c6_2, c3) >>
  send(c6_3, c4) >>
  send(c6_4, c7) >>
  send(c6_5, c8) >>
  nil
}



