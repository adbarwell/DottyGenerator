// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package com.SimpleConsensus

import effpi.process._
import effpi.process.dsl._
import effpi.channel.Channel
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration

case class Rejected()
case class Propose()
case class Reject()
case class Accepted()
case class Accept()

type P_3[ 
X_3 <: Reject|Accept,
C_P_L_1 <: OutChannel[Rejected],
C_P_L_2 <: OutChannel[Accepted]] <: Process = 
 X_3 match { 
case Reject => Out[C_P_L_1,Rejected] >>: PNil 
case Accept => Out[C_P_L_2,Accepted] >>: PNil 
} 

type P_4[ 
X_4 <: Reject|Accept,
C_P_L_2 <: OutChannel[Accepted]] <: Process = 
 X_4 match { 
case Reject => Out[C_P_L_2,Accepted] >>: PNil 
case Accept => Out[C_P_L_2,Accepted] >>: PNil 
} 

type P_2[ 
X_2 <: Reject|Accept,
C_P_A2_2 <: InChannel[Reject|Accept],
C_P_L_1 <: OutChannel[Rejected],
C_P_L_2 <: OutChannel[Accepted]] <: Process = 
 X_2 match { 
case Reject => InErr[C_P_A2_2, Reject|Accept, (X_3:Reject|Accept) => P_3[X_3.type,C_P_L_1,C_P_L_2], (err:Throwable) => Out[C_P_L_1,Rejected] >>: PNil] 
case Accept => InErr[C_P_A2_2, Reject|Accept, (X_4:Reject|Accept) => P_4[X_4.type,C_P_L_2], (err:Throwable) => Out[C_P_L_2,Accepted] >>: PNil] 
} 

type P_5[ 
X_5 <: Reject|Accept,
C_P_L_1 <: OutChannel[Rejected],
C_P_L_2 <: OutChannel[Accepted]] <: Process = 
 X_5 match { 
case Reject => Out[C_P_L_1,Rejected] >>: PNil 
case Accept => Out[C_P_L_2,Accepted] >>: PNil 
} 

type P[ 
C_P_A1_1 <: OutChannel[Propose],
C_P_A2_1 <: OutChannel[Propose],
C_P_A1_2 <: InChannel[Reject|Accept],
C_P_A2_2 <: InChannel[Reject|Accept],
C_P_L_1 <: OutChannel[Rejected],
C_P_L_2 <: OutChannel[Accepted]] = 
 Out[C_P_A1_1,Propose] >>: Out[C_P_A2_1,Propose] >>: InErr[C_P_A1_2, Reject|Accept, (X_2:Reject|Accept) => P_2[X_2.type,C_P_A2_2,C_P_L_1,C_P_L_2], (err:Throwable) => InErr[C_P_A2_2, Reject|Accept, (X_5:Reject|Accept) => P_5[X_5.type,C_P_L_1,C_P_L_2], (err:Throwable) => Out[C_P_L_1,Rejected] >>: PNil]] 

private def p_3(
  x_3: Reject|Accept,
  c_P_L_1: OutChannel[Rejected],
  c_P_L_2: OutChannel[Accepted]
):P_3[x_3.type,c_P_L_1.type,c_P_L_2.type] =
  x_3 match {
      case x_3 : Reject => {
        print("P:Actual type Received from x_3: Reject\n")
        if(false){throw Exception("Some exception")}
        print("P:Sending Rejected through channel c_P_L_1\n")
        send(c_P_L_1,Rejected()) >> {
            print("P:Terminating...\n")
            nil
        }
      }
      case x_3 : Accept => {
        print("P:Actual type Received from x_3: Accept\n")
        if(false){throw Exception("Some exception")}
        print("P:Sending Accepted through channel c_P_L_2\n")
        send(c_P_L_2,Accepted()) >> {
            print("P:Terminating...\n")
            nil
        }
      }
  }


private def p_4(
  x_4: Reject|Accept,
  c_P_L_2: OutChannel[Accepted]
):P_4[x_4.type,c_P_L_2.type] =
  x_4 match {
      case x_4 : Reject => {
        print("P:Actual type Received from x_4: Reject\n")
        if(false){throw Exception("Some exception")}
        print("P:Sending Accepted through channel c_P_L_2\n")
        send(c_P_L_2,Accepted()) >> {
            print("P:Terminating...\n")
            nil
        }
      }
      case x_4 : Accept => {
        print("P:Actual type Received from x_4: Accept\n")
        if(false){throw Exception("Some exception")}
        print("P:Sending Accepted through channel c_P_L_2\n")
        send(c_P_L_2,Accepted()) >> {
            print("P:Terminating...\n")
            nil
        }
      }
  }


private def p_2(
  x_2: Reject|Accept,
  c_P_A2_2: InChannel[Reject|Accept],
  c_P_L_1: OutChannel[Rejected],
  c_P_L_2: OutChannel[Accepted]
):P_2[x_2.type,c_P_A2_2.type,c_P_L_1.type,c_P_L_2.type] =
  x_2 match {
      case x_2 : Reject => {
        print("P:Actual type Received from x_2: Reject\n")
        receiveErr(c_P_A2_2) ({
            (x_3:Reject|Accept) =>
            print("P:Received type Reject|Accept through channel c_P_A2_2\n")
            p_3(x_3,c_P_L_1,c_P_L_2)
        },
        {(err : Throwable) =>
            print("P:Receive Reject|Accept through channel c_P_A2_2 TIMEOUT, activating new option\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Rejected through channel c_P_L_1\n")
            send(c_P_L_1,Rejected()) >> {
              print("P:Terminating...\n")
              nil
            }
        }, Duration("5 seconds"))
      }
      case x_2 : Accept => {
        print("P:Actual type Received from x_2: Accept\n")
        receiveErr(c_P_A2_2) ({
            (x_4:Reject|Accept) =>
            print("P:Received type Reject|Accept through channel c_P_A2_2\n")
            p_4(x_4,c_P_L_2)
        },
        {(err : Throwable) =>
            print("P:Receive Reject|Accept through channel c_P_A2_2 TIMEOUT, activating new option\n")
            if(false){throw Exception("Some exception")}
            print("P:Sending Accepted through channel c_P_L_2\n")
            send(c_P_L_2,Accepted()) >> {
              print("P:Terminating...\n")
              nil
            }
        }, Duration("5 seconds"))
      }
  }


private def p_5(
  x_5: Reject|Accept,
  c_P_L_1: OutChannel[Rejected],
  c_P_L_2: OutChannel[Accepted]
):P_5[x_5.type,c_P_L_1.type,c_P_L_2.type] =
  x_5 match {
      case x_5 : Reject => {
        print("P:Actual type Received from x_5: Reject\n")
        if(false){throw Exception("Some exception")}
        print("P:Sending Rejected through channel c_P_L_1\n")
        send(c_P_L_1,Rejected()) >> {
            print("P:Terminating...\n")
            nil
        }
      }
      case x_5 : Accept => {
        print("P:Actual type Received from x_5: Accept\n")
        if(false){throw Exception("Some exception")}
        print("P:Sending Accepted through channel c_P_L_2\n")
        send(c_P_L_2,Accepted()) >> {
            print("P:Terminating...\n")
            nil
        }
      }
  }


def p(
  c_P_A1_1: OutChannel[Propose],
  c_P_A2_1: OutChannel[Propose],
  c_P_A1_2: InChannel[Reject|Accept],
  c_P_A2_2: InChannel[Reject|Accept],
  c_P_L_1: OutChannel[Rejected],
  c_P_L_2: OutChannel[Accepted]
):P[c_P_A1_1.type,c_P_A2_1.type,c_P_A1_2.type,c_P_A2_2.type,c_P_L_1.type,c_P_L_2.type] ={
  if(false){throw Exception("Some exception")}
  print("P:Sending Propose through channel c_P_A1_1\n")
  send(c_P_A1_1,Propose()) >> {
      if(false){throw Exception("Some exception")}
      print("P:Sending Propose through channel c_P_A2_1\n")
      send(c_P_A2_1,Propose()) >> {
        receiveErr(c_P_A1_2) ({
            (x_2:Reject|Accept) =>
            print("P:Received type Reject|Accept through channel c_P_A1_2\n")
            p_2(x_2,c_P_A2_2,c_P_L_1,c_P_L_2)
        },
        {(err : Throwable) =>
            print("P:Receive Reject|Accept through channel c_P_A1_2 TIMEOUT, activating new option\n")
            receiveErr(c_P_A2_2) ({
              (x_5:Reject|Accept) =>
              print("P:Received type Reject|Accept through channel c_P_A2_2\n")
              p_5(x_5,c_P_L_1,c_P_L_2)
            },
            {(err : Throwable) =>
              print("P:Receive Reject|Accept through channel c_P_A2_2 TIMEOUT, activating new option\n")
              if(false){throw Exception("Some exception")}
              print("P:Sending Rejected through channel c_P_L_1\n")
              send(c_P_L_1,Rejected()) >> {
                  print("P:Terminating...\n")
                  nil
              }
            }, Duration("5 seconds"))
        }, Duration("5 seconds"))
      }
  }
}

type A1[ 
C_A1_P_1 <: InChannel[Propose],
C_A1_P_2 <: OutChannel[Reject|Accept]] = 
 InErr[C_A1_P_1, Propose, (x:Propose) => ((Out[C_A1_P_2,Reject] >>: PNil)|(Out[C_A1_P_2,Accept] >>: PNil)), (err:Throwable) => PNil] 

def a1(
  c_A1_P_1: InChannel[Propose],
  c_A1_P_2: OutChannel[Reject|Accept]
):A1[c_A1_P_1.type,c_A1_P_2.type] ={
  receiveErr(c_A1_P_1) ({
      (x:Propose) =>
      print("A1:Received type Propose through channel c_A1_P_1\n")
      val r = scala.util.Random(System.currentTimeMillis())
      val decision = r.nextInt(2)
      print("A1:Making selection through channel c_A1_P_2\n")
      if(decision == 0){
        if(false){throw Exception("Some exception")}
        print("A1:Sending Reject through channel c_A1_P_2\n")
        send(c_A1_P_2,Reject()) >> {
            print("A1:Terminating...\n")
            nil
        }
      }
      else{
        if(false){throw Exception("Some exception")}
        print("A1:Sending Accept through channel c_A1_P_2\n")
        send(c_A1_P_2,Accept()) >> {
            print("A1:Terminating...\n")
            nil
        }
      }
  },
  {(err : Throwable) =>
      print("A1:Receive Propose through channel c_A1_P_1 TIMEOUT, activating new option\n")
      print("A1:Terminating...\n")
      nil
  }, Duration("5 seconds"))
}

type A2[ 
C_A2_P_1 <: InChannel[Propose],
C_A2_P_2 <: OutChannel[Reject|Accept]] = 
 InErr[C_A2_P_1, Propose, (x:Propose) => ((Out[C_A2_P_2,Reject] >>: PNil)|(Out[C_A2_P_2,Accept] >>: PNil)), (err:Throwable) => PNil] 

def a2(
  c_A2_P_1: InChannel[Propose],
  c_A2_P_2: OutChannel[Reject|Accept]
):A2[c_A2_P_1.type,c_A2_P_2.type] ={
  receiveErr(c_A2_P_1) ({
      (x:Propose) =>
      print("A2:Received type Propose through channel c_A2_P_1\n")
      val r = scala.util.Random(System.currentTimeMillis())
      val decision = r.nextInt(2)
      print("A2:Making selection through channel c_A2_P_2\n")
      if(decision == 0){
        if(false){throw Exception("Some exception")}
        print("A2:Sending Reject through channel c_A2_P_2\n")
        send(c_A2_P_2,Reject()) >> {
            print("A2:Terminating...\n")
            nil
        }
      }
      else{
        if(false){throw Exception("Some exception")}
        print("A2:Sending Accept through channel c_A2_P_2\n")
        send(c_A2_P_2,Accept()) >> {
            print("A2:Terminating...\n")
            nil
        }
      }
  },
  {(err : Throwable) =>
      print("A2:Receive Propose through channel c_A2_P_1 TIMEOUT, activating new option\n")
      print("A2:Terminating...\n")
      nil
  }, Duration("5 seconds"))
}

type L[ 
C_L_P_1 <: InChannel[Rejected|Accepted]] = 
 InErr[C_L_P_1, Rejected|Accepted, (x:Rejected|Accepted) => PNil, (err:Throwable) => PNil] 

def l(
  c_L_P_1: InChannel[Rejected|Accepted]
):L[c_L_P_1.type] ={
  receiveErr(c_L_P_1) ({
      (x:Rejected|Accepted) =>
      print("L:Received type Rejected|Accepted through channel c_L_P_1\n")
      print("L:Terminating...\n")
      nil
  },
  {(err : Throwable) =>
      print("L:Receive Rejected|Accepted through channel c_L_P_1 TIMEOUT, activating new option\n")
      print("L:Terminating...\n")
      nil
  }, Duration("5 seconds"))
}

// To run this example, try:
// sbt 'tests/runMain com.SimpleConsensus.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    val(c1, c2, c3, c4, c5) = 
     (Channel.async[Propose](),
     Channel.async[Reject|Accept](),
     Channel.async[Propose](),
     Channel.async[Reject|Accept](),
     Channel.async[Rejected|Accepted]()) 

    eval(par(
     p(c1, c3, c2, c4, c5, c5),
     l(c5),
     a1(c1, c2),
     a2(c3, c4)))


    Thread.sleep(1000)
    ps.kill()
  }
}