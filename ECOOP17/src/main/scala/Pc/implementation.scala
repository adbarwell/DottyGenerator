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

   private def pc_2(
      x_2: Mov2AC|Mov1AC,
      c_Pc_Pa_3: OutChannel[Mov1CA|Mov2CA]
   ):Pc_2[x_2.type,c_Pc_Pa_3.type] =
      x_2 match {
         case x_2 : Mov2AC => {
            print("Pc:Actual type Received from x_2: Mov2AC\n")
            val r = scala.util.Random(System.currentTimeMillis())
            val decision = r.nextInt(2)
            print("Pc:Making selection through channel c_Pc_Pa_3\n")
            if(decision == 0){
               print("Pc:Sending Mov1CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov1CA(-1)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
            else{
               print("Pc:Sending Mov2CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov2CA(true)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
         }
         case x_2 : Mov1AC => {
            print("Pc:Actual type Received from x_2: Mov1AC\n")
            val r = scala.util.Random(System.currentTimeMillis())
            val decision = r.nextInt(2)
            print("Pc:Making selection through channel c_Pc_Pa_3\n")
            if(decision == 0){
               print("Pc:Sending Mov1CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov1CA(-1)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
            else{
               print("Pc:Sending Mov2CA through channel c_Pc_Pa_3\n")
               send(c_Pc_Pa_3,Mov2CA(true)) >> {
                  print("Pc:go to loop RecPc_1\n")
                  loop(RecPc_1)
               }
            }
         }
      }


   def pc(
      c_Pc_Q_1: InChannel[PlayC],
      c_Pc_Pb_1: InChannel[InfoBC],
      c_Pc_Pa_1: OutChannel[InfoCA],
      c_Pc_Pa_2: InChannel[Mov2AC|Mov1AC],
      c_Pc_Pa_3: OutChannel[Mov1CA|Mov2CA]
   ):Pc[c_Pc_Q_1.type,c_Pc_Pb_1.type,c_Pc_Pa_1.type,c_Pc_Pa_2.type,c_Pc_Pa_3.type] ={
      receive(c_Pc_Q_1) {
         (x:PlayC) =>
         print("Pc:Receive type PlayC through channel c_Pc_Q_1\n")
         rec(RecPc_1){
            print("Pc:entering loop RecPc_1\n")
            receive(c_Pc_Pb_1) {
               (x:InfoBC) =>
               print("Pc:Receive type InfoBC through channel c_Pc_Pb_1\n")
               print("Pc:Sending InfoCA through channel c_Pc_Pa_1\n")
               send(c_Pc_Pa_1,InfoCA("REPLACE_ME")) >> {
                  receive(c_Pc_Pa_2) {
                     (x_2:Mov2AC|Mov1AC) =>
                     print("Pc:Receive type Mov2AC|Mov1AC through channel c_Pc_Pa_2\n")
                     pc_2(x_2,c_Pc_Pa_3)
                  }
               }
            }
         }
      }
   }



