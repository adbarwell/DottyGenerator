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

// def pc(
//    c_Pc_Q_1: InChannel[PlayC],
//    c_Pc_Pb_1: InChannel[InfoBC],
//    c_Pc_Pa_1: OutChannel[InfoCA],
//    c_Pc_Pa_2: InChannel[MovAC],
//    c_Pc_Pa_3: OutChannel[MovCA]
// ):Pc[c_Pc_Q_1.type,c_Pc_Pb_1.type,c_Pc_Pa_1.type,c_Pc_Pa_2.type,c_Pc_Pa_3.type] ={
//    receive(c_Pc_Q_1) {
//       (x:PlayC) =>
//       print("Pc:Receive type PlayC through channel c_Pc_Q_1\n")

//    }
// }

def pc(c5_1 : InChannel[InChannel[InfoBC]],
       c5_2 : InChannel[OutChannel[InfoCA]],
       c5_3 : InChannel[InChannel[MovAC]],
       c5_4 : InChannel[OutChannel[MovCA]]) : Pc[c5_1.type, c5_2.type, c5_3.type, c5_4.type] = {
  receive(c5_1) {(c2 : InChannel[InfoBC]) =>
    println("Pc:Received c2 on c5_1")
    receive(c5_2) {(c9 : OutChannel[InfoCA]) =>
      println("Pc:Received c9 on c5_2")
      receive(c5_3) {(c7 : InChannel[MovAC]) =>
        println("Pc:Received c7 on c5_3")
        receive(c5_4) {(c8 : OutChannel[MovCA]) =>
          println("Pc:Received c8 on c5_4")
          rec(RecPc_1){
            print("Pc:entering loop RecPc_1\n")
            receive(c2) {
                (x:InfoBC) =>
                print("Pc:Receive type InfoBC through channel c2\n")
                print("Pc:Sending InfoCA through channel c9\n")
                send(c9,InfoCA("REPLACE_ME")) >> {
                  receive(c7) {
                      (x:MovAC) =>
                      print("Pc:Receive type MovAC through channel c7\n")
                      print("Pc:Sending MovCA through channel c8\n")
                      send(c8,MovCA(-1)) >> {
                        print("Pc:go to loop RecPc_1\n")
                        loop(RecPc_1)
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



