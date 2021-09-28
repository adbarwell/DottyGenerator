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

private def pa_2(
      x_2: Mov1CA|Mov2CA
   ):Pa_2[x_2.type] =
      x_2 match {
         case x_2 : Mov1CA => {
            print("Pa:Actual type Received from x_2: Mov1CA\n")
            print("Pa:go to loop RecPa_1\n")
            loop(RecPa_1)
         }
         case x_2 : Mov2CA => {
            print("Pa:Actual type Received from x_2: Mov2CA\n")
            print("Pa:go to loop RecPa_1\n")
            loop(RecPa_1)
         }
      }


private def pa_3(
   x_3: Mov1CA|Mov2CA
):Pa_3[x_3.type] =
   x_3 match {
      case x_3 : Mov1CA => {
         print("Pa:Actual type Received from x_3: Mov1CA\n")
         print("Pa:go to loop RecPa_1\n")
         loop(RecPa_1)
      }
      case x_3 : Mov2CA => {
         print("Pa:Actual type Received from x_3: Mov2CA\n")
         print("Pa:go to loop RecPa_1\n")
         loop(RecPa_1)
      }
   }


// def pa0(
//    c_Pa_Pc_1: InChannel[InfoCA],
//    c_Pa_Pb_1: OutChannel[InfoAB],
//    c_Pa_Pb_2: OutChannel[Mov1AB|Mov2AB],
//    c_Pa_Pc_2: OutChannel[Mov1AC|Mov2AC],
//    c_Pa_Pc_4: InChannel[Mov1CA|Mov2CA] //,
//    // c_Pa_Pc_3: OutChannel[Mov2AC]
// ):Pa0[c_Pa_Pc_1.type,c_Pa_Pb_1.type,c_Pa_Pb_2.type,c_Pa_Pc_2.type,c_Pa_Pc_4.type] ={
//    rec(RecPa_1){
//       print("Pa:entering loop RecPa_1\n")
//       receive(c_Pa_Pc_1) {
//          (x:InfoCA) =>
//          print("Pa:Receive type InfoCA through channel c_Pa_Pc_1\n")
//          print("Pa:Sending InfoAB through channel c_Pa_Pb_1\n")
//          send(c_Pa_Pb_1,InfoAB("REPLACE_ME")) >> {
//             val r = scala.util.Random(System.currentTimeMillis())
//             val decision = r.nextInt(2)
//             print("Pa:Making selection through channel c_Pa_Pb_2\n")
//             if(decision == 0){
//                print("Pa:Sending Mov1AB through channel c_Pa_Pb_2\n")
//                send(c_Pa_Pb_2,Mov1AB(-1)) >> {
//                   print("Pa:Sending Mov1AC through channel c_Pa_Pc_2\n")
//                   send(c_Pa_Pc_2,Mov1AC(-1)) >> {
//                      receive(c_Pa_Pc_4) {
//                         (x_2:Mov1CA|Mov2CA) =>
//                         print("Pa:Receive type Mov1CA|Mov2CA through channel c_Pa_Pc_4\n")
//                         pa_2(x_2)
//                      }
//                   }
//                }
//             }
//             else{
//                print("Pa:Sending Mov2AB through channel c_Pa_Pb_2\n")
//                send(c_Pa_Pb_2,Mov2AB(true)) >> {
//                   print("Pa:Sending Mov2AC through channel c_Pa_Pc_2\n")
//                   send(c_Pa_Pc_2,Mov2AC(true)) >> {
//                      receive(c_Pa_Pc_4) {
//                         (x_3:Mov1CA|Mov2CA) =>
//                         print("Pa:Receive type Mov1CA|Mov2CA through channel c_Pa_Pc_4\n")
//                         pa_2(x_3)
//                      }
//                   }
//                }
//             }
//          }
//       }
//    }
// }

def pa0(c2 : InChannel[InfoCA],
        c6 : OutChannel[InfoAB],
        c7 : OutChannel[Mov1AB|Mov2AB],
        c1 : OutChannel[Mov1AC],
        c3 : InChannel[Mov1CA|Mov2CA],
        c11 : OutChannel[Mov2AC]
) : Pa0[c2.type,c6.type,c7.type,c1.type,c3.type,c11.type] = {
  rec(RecPa_1) {
    println("Pa:Entered loop RecPa_1")
    receive(c2) {(x : InfoCA) =>
      println("Pa:Received type InfoCA through channel c2")
      send(c6, InfoAB("Replace Me")) >> {
        println("Pa:Sent InfoAB through channel c6")
          // val r = scala.util.Random(System.currentTimeMillis())
          // val decision = r.nextInt(2)
          // print("Pa:Making selection through channel c_Pa_Pb_2\n")
          // if(decision == 0){
            send(c7, Mov1AB(42)) >> nil // this, by itself, is okay.
            // send(c1, Mov1AC(42)) >> nil
            // receive(c3) {(y : Mov1CA|Mov2CA) =>
            //   loop(RecPa_1)
            // }
          // } else {
          //   send(c7, Mov2AB(true)) >> nil
          //   // send(c11, Mov2AC(true)) >> nil
          //   // receive(c3) {(z : Mov1CA|Mov2CA) =>
          //   //   loop(RecPa_1)
          //   // }
          // }
      }
    }
  }
}

def pa(c8_1 : InChannel[InChannel[InfoCA]],
       c8_2 : InChannel[OutChannel[InfoAB]],
       c8_3 : InChannel[OutChannel[Mov1AB|Mov2AB]],
       c8_4 : InChannel[OutChannel[Mov1AC]],
       c8_5 : InChannel[InChannel[Mov1CA|Mov2CA]],
       c8_6 : InChannel[OutChannel[Mov2AC]]) : Pa[c8_1.type,
                                                  c8_2.type,
                                                  c8_3.type,
                                                  c8_4.type,
                                                  c8_5.type,
                                                  c8_6.type] = {
   receive(c8_1) {(c2 : InChannel[InfoCA]) =>
      println("Pa:Received type InChannel[InfoCA] through channel c8_1")
      receive(c8_2) {(c6 : OutChannel[InfoAB]) =>
         println("Pa:Received type OutChannel[InfoAB] through channel c8_2")
         receive(c8_3) {(c7 : OutChannel[Mov1AB|Mov2AB]) =>
            println("Pa:Received type OutChannel[Mov1AB|Mov2AB] through channel 8_3")
            receive(c8_4) {(c1 : OutChannel[Mov1AC]) =>
               println("Pa:Received type OutChannel[Mov1AC|Mov2AC] through channel 8_4")
               receive(c8_5) {(c3 : InChannel[Mov1CA|Mov2CA]) =>
                  println("Pa:Received type InChannel[Mov1CA|Mov2CA] through channel 8_5")
                  receive(c8_6) {(c11 : OutChannel[Mov2AC]) =>
                    println("Pa:Received type OutChannel[Mov2AC] through channel 8_6")
                    // pa0(c2, c6, c7, c1, c3, c11)
                    rec(RecPa_1) {
                      println("Pa:Entered loop RecPa_1")
                      receive(c2) {(x : InfoCA) =>
                        println("Pa:Received type InfoCA through channel c2")
                        send(c6, InfoAB("Replace Me")) >> {
                          println("Pa:Sent InfoAB through channel c6")
                          // send(c7, Mov1AB(42)) >> nil // this, by itself, is okay.
                          val r = scala.util.Random(System.currentTimeMillis())
                          val decision = r.nextInt(2)
                          print("Pa:Making selection through channel c_Pa_Pb_2\n")
                          if(decision == 0){
                            send(c7, Mov1AB(42)) >> // nil
                            // send(c1, Mov1AC(42)) >> nil
                          } else {
                            send(c7, Mov2AB(true)) >> nil
                            // send(c11, Mov2AC(true)) >> nil
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

