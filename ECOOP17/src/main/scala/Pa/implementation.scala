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


   def pa(
      c_Pa_Q_1: InChannel[PlayA],
      c_Pa_Pc_1: InChannel[InfoCA],
      c_Pa_Pb_1: OutChannel[InfoAB],
      c_Pa_Pb_2: OutChannel[Mov1AB|Mov2AB],
      c_Pa_Pc_2: OutChannel[Mov1AC],
      c_Pa_Pc_4: InChannel[Mov1CA|Mov2CA],
      c_Pa_Pc_3: OutChannel[Mov2AC]
   ):Pa[c_Pa_Q_1.type,c_Pa_Pc_1.type,c_Pa_Pb_1.type,c_Pa_Pb_2.type,c_Pa_Pc_2.type,c_Pa_Pc_4.type,c_Pa_Pc_3.type] ={
      receive(c_Pa_Q_1) {
         (x:PlayA) =>
         print("Pa:Receive type PlayA through channel c_Pa_Q_1\n")
         rec(RecPa_1){
            print("Pa:entering loop RecPa_1\n")
            receive(c_Pa_Pc_1) {
               (x:InfoCA) =>
               print("Pa:Receive type InfoCA through channel c_Pa_Pc_1\n")
               print("Pa:Sending InfoAB through channel c_Pa_Pb_1\n")
               send(c_Pa_Pb_1,InfoAB("REPLACE_ME")) >> {
                  val r = scala.util.Random(System.currentTimeMillis())
                  val decision = r.nextInt(2)
                  print("Pa:Making selection through channel c_Pa_Pb_2\n")
                  if(decision == 0){
                     print("Pa:Sending Mov1AB through channel c_Pa_Pb_2\n")
                     send(c_Pa_Pb_2,Mov1AB(-1)) >> {
                        print("Pa:Sending Mov1AC through channel c_Pa_Pc_2\n")
                        send(c_Pa_Pc_2,Mov1AC(-1)) >> {
                           receive(c_Pa_Pc_4) {
                              (x_2:Mov1CA|Mov2CA) =>
                              print("Pa:Receive type Mov1CA|Mov2CA through channel c_Pa_Pc_4\n")
                              pa_2(x_2)
                           }
                        }
                     }
                  }
                  else{
                     print("Pa:Sending Mov2AB through channel c_Pa_Pb_2\n")
                     send(c_Pa_Pb_2,Mov2AB(true)) >> {
                        print("Pa:Sending Mov2AC through channel c_Pa_Pc_3\n")
                        send(c_Pa_Pc_3,Mov2AC(true)) >> {
                           receive(c_Pa_Pc_4) {
                              (x_3:Mov1CA|Mov2CA) =>
                              print("Pa:Receive type Mov1CA|Mov2CA through channel c_Pa_Pc_4\n")
                              pa_3(x_3)
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }



