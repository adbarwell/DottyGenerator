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

   // def q(
   //    c1i : InChannel[Mov2AC|Mov1AC],
   //    c1o : OutChannel[Mov2AC|Mov1AC],
   //    c2i : InChannel[InfoCA],
   //    c2o : OutChannel[InfoCA],
   //    c3i : InChannel[Mov1CA|Mov2CA],
   //    c3o : OutChannel[Mov1CA|Mov2CA],
   //    c4i : InChannel[InfoBC],
   //    c4o : OutChannel[InfoBC],
   //    c6i : InChannel[InfoAB],
   //    c6o : OutChannel[InfoAB],
   //    c7i : InChannel[Mov2AB|Mov1AB],
   //    c7o : OutChannel[Mov2AB|Mov1AB],
   //    c_Q_Pa_1: OutChannel[PlayA[InChannel[InfoBC], InChannel[InfoAB], InChannel[Mov2AB|Mov1AB]]],
   //    c_Q_Pb_1: OutChannel[PlayB],
   //    c_Q_Pc_1: OutChannel[PlayC],
   // ):Q[c1i.type, c1o.type, c2i.type, c2o.type, c3i.type, c3o.type, c4i.type, c4o.type, c6i.type, c6o.type, c7i.type, c7o.type, c_Q_Pa_1.type,c_Q_Pb_1.type,c_Q_Pc_1.type] ={
   //    print("Q:Sending PlayA through channel c_Q_Pa_1\n")
   //    send(c_Q_Pa_1,PlayA(SessionA(c2i,c6o,c7o,c1o,c3i))) >> {
   //       print("Q:Sending PlayB through channel c_Q_Pb_1\n")
   //       send(c_Q_Pb_1,PlayB(SessionB(c4o,c6i,c7i))) >> {
   //          print("Q:Sending PlayC through channel c_Q_Pc_1\n")
   //          send(c_Q_Pc_1,PlayC(SessionC(c4i,c2o,c1i,c3o))) >> {
   //             print("Q:Terminating...\n")
   //             nil
   //          }
   //       }
   //    }
   // }


def q(c4i : InChannel[InfoBC],
      c6i : InChannel[InfoAB],
      c7i : InChannel[Mov2AB|Mov1AB],
      c_Q_Pb_1: OutChannel[PlayB[InChannel[InfoBC], InChannel[InfoAB], InChannel[Mov2AB|Mov1AB]]]) : Q[c4i.type, c6i.type, c7i.type, c_Q_Pb_1.type] = {
   print("Q:Sending PlayB through channel c_Q_Pb_1\n")
   send(c_Q_Pb_1,PlayB(SessionB(c4o,c6i,c7i))) >> {
      nil
   }
}


