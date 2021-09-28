package output.Q.implementation

import output.caseclass._
import output.Q.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{Channel, InChannel, OutChannel}
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


// def q(c4i : InChannel[InfoBC],
//       c6i : InChannel[InfoAB],
//       c7i : InChannel[Mov2AB|Mov1AB],
//       c_Q_Pb_1: OutChannel[PlayB[InChannel[InfoBC], InChannel[InfoAB], InChannel[Mov2AB|Mov1AB]]]) : Q[c4i.type, c6i.type, c7i.type, c_Q_Pb_1.type] = {
//    print("Q:Sending PlayB through channel c_Q_Pb_1\n")
//    send(c_Q_Pb_1,PlayB(SessionB(c4o,c6i,c7i))) >> {
//       nil
//    }
// }

def q(c5_1 : OutChannel[InChannel[InfoBC]],
      c5_2 : OutChannel[OutChannel[InfoCA]],
      c5_3 : OutChannel[InChannel[Mov2AC|Mov1AC]],
      c5_4 : OutChannel[OutChannel[Mov1CA|Mov2CA]],
      c8_1 : OutChannel[InChannel[InfoCA]],
      c8_2 : OutChannel[OutChannel[InfoAB]],
      c8_3 : OutChannel[OutChannel[Mov1AB|Mov2AB]],
      c8_4 : OutChannel[OutChannel[Mov1AC]],
      c8_5 : OutChannel[InChannel[Mov1CA|Mov2CA]],
      c8_6 : OutChannel[OutChannel[Mov2AC]],
      c9_1 : OutChannel[OutChannel[InfoBC]],
      c9_2 : OutChannel[InChannel[InfoAB]],
      c9_3 : OutChannel[InChannel[Mov2AB|Mov1AB]])
  : Q[c5_1.type, c5_2.type, c5_3.type, c5_4.type,
      c8_1.type, c8_2.type, c8_3.type, c8_4.type, c8_5.type, c8_6.type,
      c9_1.type, c9_2.type, c9_3.type] = {

  val c1 = Channel[Mov1AC]()
  val c11 = Channel[Mov2AC]()
  val c2 = Channel[InfoCA]()
  val c3 = Channel[Mov1CA|Mov2CA]()
  val c4 = Channel[InfoBC]()
  val c6 = Channel[InfoAB]()
  val c7 = Channel[Mov2AB|Mov1AB]()
    
  println("Q:Sending c4 through channel c5_1")
  send(c5_1, c4) >> {
    println("Q:Sending c2 through channel c5_2")
    send(c5_2, c2) >> {
      println("Q:Sending c1 through channel c5_3")
      send(c5_3, c1) >> {
        println("Q:Sending c3 through channel c5_4")
        send(c5_4, c3) >> {
          println("Q:Sending c2 through channel c8_1")
          send(c8_1, c2) >> {
            println("Q:Sending c6 through channel c8_2")
            send(c8_2, c6) >> {
              println("Q:Sending c7 through channel c8_3")
              send(c8_3, c7) >> {
                println("Q:Sending c1 through channel c8_4")
                send(c8_4, c1) >> {
                  println("Q:Sending c3 through channel c8_5")
                  send(c8_5, c3) >> {
                    println("Q:Sending c11 through channel c8_6")
                    send(c8_6, c11) >> {
                      println("Q:Sending c4 through channel c9_1")
                      send(c9_1, c4) >> {
                        println("Q:Sending c6 through channel c9_2")
                        send(c9_2, c6) >> {
                          println("Q:Sending c7 through channel c9_3")
                          send(c9_3, c7) >> nil
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

