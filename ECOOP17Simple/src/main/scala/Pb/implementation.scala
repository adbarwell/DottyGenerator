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

  //  def pb(
  //     c_Pb_Q_1: InChannel[PlayB],
  //     c_Pb_Pc_1: OutChannel[InfoBC],
  //     c_Pb_Pa_1: InChannel[InfoAB],
  //     c_Pb_Pa_2: InChannel[MovAB]
  //  ):Pb[c_Pb_Q_1.type,c_Pb_Pc_1.type,c_Pb_Pa_1.type,c_Pb_Pa_2.type] ={
  //     receive(c_Pb_Q_1) {
  //        (x:PlayB) =>
  //        print("Pb:Receive type PlayB through channel c_Pb_Q_1\n")
  //        rec(RecPb_1){
  //           print("Pb:entering loop RecPb_1\n")
  //           print("Pb:Sending InfoBC through channel c_Pb_Pc_1\n")
  //           send(c_Pb_Pc_1,InfoBC("REPLACE_ME")) >> {
  //              receive(c_Pb_Pa_1) {
  //                 (x:InfoAB) =>
  //                 print("Pb:Receive type InfoAB through channel c_Pb_Pa_1\n")
  //                 receive(c_Pb_Pa_2) {
  //                    (x:MovAB) =>
  //                    print("Pb:Receive type MovAB through channel c_Pb_Pa_2\n")
  //                    print("Pb:go to loop RecPb_1\n")
  //                    loop(RecPb_1)
  //                 }
  //              }
  //           }
  //        }
  //     }
  //  }

def pb(c1_1 : InChannel[OutChannel[InfoBC]],
       c1_2 : InChannel[InChannel[InfoAB]],
       c1_3 : InChannel[InChannel[MovAB]]) : Pb[c1_1.type, c1_2.type, c1_3.type] = {
  receive(c1_1) {(c2 : OutChannel[InfoBC]) =>
    println("Pb:Received c2 on c1_1")
    receive(c1_2) {(c3 : InChannel[InfoAB]) =>
      println("Pb:Received c3 on c1_2")
      receive(c1_3) {(c4 : InChannel[MovAB]) =>
        println("Pb:Received c4 on c1_3")
        rec(RecPb_1){
          print("Pb:entering loop RecPb_1\n")
          print("Pb:Sending InfoBC through channel c2\n")
          send(c2,InfoBC("REPLACE_ME")) >> {
              receive(c3) {
                (x:InfoAB) =>
                print("Pb:Receive type InfoAB through channel c3\n")
                receive(c4) {
                    (x:MovAB) =>
                    print("Pb:Receive type MovAB through channel c4\n")
                    print("Pb:go to loop RecPb_1\n")
                    loop(RecPb_1)
                }
              }
          }
        }
      }
    }
  }
}

