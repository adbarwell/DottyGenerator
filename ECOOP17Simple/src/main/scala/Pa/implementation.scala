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

  //  def pa(
  //     c_Pa_Q_1: InChannel[PlayA],
  //     c_Pa_Pc_1: InChannel[InfoCA],
  //     c_Pa_Pb_1: OutChannel[InfoAB],
  //     c_Pa_Pb_2: OutChannel[MovAB],
  //     c_Pa_Pc_2: OutChannel[MovAC],
  //     c_Pa_Pc_3: InChannel[MovCA]
  //  ):Pa[c_Pa_Q_1.type,c_Pa_Pc_1.type,c_Pa_Pb_1.type,c_Pa_Pb_2.type,c_Pa_Pc_2.type,c_Pa_Pc_3.type] ={
  //     receive(c_Pa_Q_1) {
  //        (x:PlayA) =>
  //        print("Pa:Receive type PlayA through channel c_Pa_Q_1\n")
  //        rec(RecPa_1){
  //           print("Pa:entering loop RecPa_1\n")
  //           receive(c_Pa_Pc_1) {
  //              (x:InfoCA) =>
  //              print("Pa:Receive type InfoCA through channel c_Pa_Pc_1\n")
  //              print("Pa:Sending InfoAB through channel c_Pa_Pb_1\n")
  //              send(c_Pa_Pb_1,InfoAB("REPLACE_ME")) >> {
  //                 print("Pa:Sending MovAB through channel c_Pa_Pb_2\n")
  //                 send(c_Pa_Pb_2,MovAB(-1)) >> {
  //                    print("Pa:Sending MovAC through channel c_Pa_Pc_2\n")
  //                    send(c_Pa_Pc_2,MovAC(-1)) >> {
  //                       receive(c_Pa_Pc_3) {
  //                          (x:MovCA) =>
  //                          print("Pa:Receive type MovCA through channel c_Pa_Pc_3\n")
  //                          print("Pa:go to loop RecPa_1\n")
  //                          loop(RecPa_1)
  //                       }
  //                    }
  //                 }
  //              }
  //           }
  //        }
  //     }
  //  }

def pa(c6_1 : InChannel[InChannel[InfoCA]],
       c6_2 : InChannel[OutChannel[InfoAB]],
       c6_3 : InChannel[OutChannel[MovAB]],
       c6_4 : InChannel[OutChannel[MovAC]],
       c6_5 : InChannel[InChannel[MovCA]]) : Pa[c6_1.type, c6_2.type, c6_3.type, c6_4.type, c6_5.type] = {
  receive(c6_1) {(c9 : InChannel[InfoCA]) =>
    println("Pa:Received c9 on c6_1")
    receive(c6_2) {(c3 : OutChannel[InfoAB]) =>
      println("Pa:Received c3 on c6_2")
      receive(c6_3) {(c4 : OutChannel[MovAB]) =>
        println("Pa:Received c4 on c6_3")
        receive(c6_4) {(c7 : OutChannel[MovAC]) =>
          println("Pa:received c7 on c6_4")
          receive(c6_5) {(c8 : InChannel[MovCA]) =>
            println("Pa:received c8 on c6_5")
            rec(RecPa_1){
                print("Pa:entering loop RecPa_1\n")
                receive(c9) {
                  (x:InfoCA) =>
                  print("Pa:Receive type InfoCA through channel c9\n")
                  print("Pa:Sending InfoAB through channel c3\n")
                  send(c3,InfoAB("REPLACE_ME")) >> {
                      print("Pa:Sending MovAB through channel c4\n")
                      send(c4,MovAB(-1)) >> {
                        print("Pa:Sending MovAC through channel c7\n")
                        send(c7,MovAC(-1)) >> {
                            receive(c8) {
                              (x:MovCA) =>
                              print("Pa:Receive type MovCA through channel c8\n")
                              print("Pa:go to loop RecPa_1\n")
                              loop(RecPa_1)
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

