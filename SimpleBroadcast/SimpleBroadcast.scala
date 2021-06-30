// Effpi - verified message-passing programs in Dotty
// Copyright 2020 Alceste Scalas
// Released under the MIT License: https://opensource.org/licenses/MIT

package com.SimpleBroadcast

import effpi.process._
import effpi.process.dsl._
import effpi.channel.Channel
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration

case class OK()
case class Rec()
case class Data()
case class KO()

type P[ 
C_P_Q_1 <: OutChannel[Data],
C_P_R_1 <: OutChannel[Data]] = 
 Out[C_P_Q_1,Data] >>: Out[C_P_R_1,Data] >>: PNil 

implicit val timeout: Duration = Duration("60 seconds")

def p(
  c_P_Q_1: OutChannel[Data],
  c_P_R_1: OutChannel[Data]
):P[c_P_Q_1.type,c_P_R_1.type] ={
  if(false){throw Exception("Some exception")}
  print("P:Sending Data through channel c_P_Q_1\n")
  send(c_P_Q_1,Data()) >> {
      if(false){throw Exception("Some exception")}
      print("P:Sending Data through channel c_P_R_1\n")
      send(c_P_R_1,Data()) >> {
        print("P:Terminating...\n")
        nil
      }
  }
}

type Q_2[ 
X_2 <: OK|Rec,
C_Q_R_2 <: OutChannel[KO|Data]] <: Process = 
 X_2 match { 
case OK => PNil 
case Rec => ((Out[C_Q_R_2,KO] >>: PNil)|(Out[C_Q_R_2,Data] >>: PNil)) 
} 

type Q_3[ 
X_3 <: Rec|OK,
C_Q_R_2 <: OutChannel[KO|Data]] <: Process = 
 X_3 match { 
case Rec => ((Out[C_Q_R_2,KO] >>: PNil)|(Out[C_Q_R_2,Data] >>: PNil)) 
case OK => PNil 
} 

type Q[ 
C_Q_P_1 <: InChannel[Data],
C_Q_R_1 <: InChannel[OK|Rec],
C_Q_R_2 <: OutChannel[KO|Data]] = 
 InErr[C_Q_P_1, Data, (x:Data) => InErr[C_Q_R_1, OK|Rec, (X_2:OK|Rec) => Q_2[X_2.type,C_Q_R_2], (err:Throwable) => PNil], (err:Throwable) => InErr[C_Q_R_1, Rec|OK, (X_3:Rec|OK) => Q_3[X_3.type,C_Q_R_2], (err:Throwable) => PNil]] 

private def q_2(
  x_2: OK|Rec,
  c_Q_R_2: OutChannel[KO|Data]
):Q_2[x_2.type,c_Q_R_2.type] =
  x_2 match {
      case x_2 : OK => {
        print("Q:Actual type Received from x_2: OK\n")
        print("Q:Terminating...\n")
        nil
      }
      case x_2 : Rec => {
        print("Q:Actual type Received from x_2: Rec\n")
        val r = scala.util.Random(System.currentTimeMillis())
        val decision = r.nextInt(2)
        print("Q:Making selection through channel c_Q_R_2\n")
        if(decision == 0){
            if(false){throw Exception("Some exception")}
            print("Q:Sending KO through channel c_Q_R_2\n")
            send(c_Q_R_2,KO()) >> {
              print("Q:Terminating...\n")
              nil
            }
        }
        else{
            if(false){throw Exception("Some exception")}
            print("Q:Sending Data through channel c_Q_R_2\n")
            send(c_Q_R_2,Data()) >> {
              print("Q:Terminating...\n")
              nil
            }
        }
      }
  }


private def q_3(
  x_3: Rec|OK,
  c_Q_R_2: OutChannel[KO|Data]
):Q_3[x_3.type,c_Q_R_2.type] =
  x_3 match {
      case x_3 : Rec => {
        print("Q:Actual type Received from x_3: Rec\n")
        val r = scala.util.Random(System.currentTimeMillis())
        val decision = r.nextInt(2)
        print("Q:Making selection through channel c_Q_R_2\n")
        if(decision == 0){
            if(false){throw Exception("Some exception")}
            print("Q:Sending KO through channel c_Q_R_2\n")
            send(c_Q_R_2,KO()) >> {
              print("Q:Terminating...\n")
              nil
            }
        }
        else{
            if(false){throw Exception("Some exception")}
            print("Q:Sending Data through channel c_Q_R_2\n")
            send(c_Q_R_2,Data()) >> {
              print("Q:Terminating...\n")
              nil
            }
        }
      }
      case x_3 : OK => {
        print("Q:Actual type Received from x_3: OK\n")
        print("Q:Terminating...\n")
        throw Exception("This branch should not be reached! Please check your communication!")
        nil
      }
  }


def q(
  c_Q_P_1: InChannel[Data],
  c_Q_R_1: InChannel[OK|Rec],
  c_Q_R_2: OutChannel[KO|Data]
):Q[c_Q_P_1.type,c_Q_R_1.type,c_Q_R_2.type] ={
  receiveErr(c_Q_P_1) ({
      (x:Data) =>
      print("Q:Received type Data through channel c_Q_P_1\n")
      receiveErr(c_Q_R_1) ({
        (x_2:OK|Rec) =>
        print("Q:Received type OK|Rec through channel c_Q_R_1\n")
        q_2(x_2,c_Q_R_2)
      },
      {(err : Throwable) =>
        print("Q:Receive OK|Rec through channel c_Q_R_1 TIMEOUT, activating new option\n")
        print("Q:Terminating...\n")
        nil
      }, Duration("5 seconds"))
  },
  {(err : Throwable) =>
      print("Q:Receive Data through channel c_Q_P_1 TIMEOUT, activating new option\n")
      receiveErr(c_Q_R_1) ({
        (x_3:Rec|OK) =>
        print("Q:Received type Rec|OK through channel c_Q_R_1\n")
        q_3(x_3,c_Q_R_2)
      },
      {(err : Throwable) =>
        print("Q:Receive Rec|OK through channel c_Q_R_1 TIMEOUT, activating new option\n")
        print("Q:Terminating...\n")
        nil
      }, Duration("5 seconds"))
  }, Duration("5 seconds"))
}

type R[ 
C_R_P_1 <: InChannel[Data],
C_R_Q_1 <: OutChannel[OK],
C_R_Q_2 <: OutChannel[Rec],
C_R_Q_3 <: InChannel[KO|Data]] = 
 InErr[C_R_P_1, Data, (x:Data) => Out[C_R_Q_1,OK] >>: PNil, (err:Throwable) => Out[C_R_Q_2,Rec] >>: InErr[C_R_Q_3, KO|Data, (x:KO|Data) => PNil, (err:Throwable) => PNil]] 

def r(
  c_R_P_1: InChannel[Data],
  c_R_Q_1: OutChannel[OK],
  c_R_Q_2: OutChannel[Rec],
  c_R_Q_3: InChannel[KO|Data]
):R[c_R_P_1.type,c_R_Q_1.type,c_R_Q_2.type,c_R_Q_3.type] ={
  receiveErr(c_R_P_1) ({
      (x:Data) =>
      print("R:Received type Data through channel c_R_P_1\n")
      if(false){throw Exception("Some exception")}
      print("R:Sending OK through channel c_R_Q_1\n")
      send(c_R_Q_1,OK()) >> {
        print("R:Terminating...\n")
        nil
      }
  },
  {(err : Throwable) =>
      print("R:Receive Data through channel c_R_P_1 TIMEOUT, activating new option\n")
      if(false){throw Exception("Some exception")}
      print("R:Sending Rec through channel c_R_Q_2\n")
      send(c_R_Q_2,Rec()) >> {
        receiveErr(c_R_Q_3) ({
            (x:KO|Data) =>
            print("R:Received type KO|Data through channel c_R_Q_3\n")
            print("R:Terminating...\n")
            nil
        },
        {(err : Throwable) =>
            print("R:Receive KO|Data through channel c_R_Q_3 TIMEOUT, activating new option\n")
            print("R:Terminating...\n")
            nil
        }, Duration("5 seconds"))
      }
  }, Duration("5 seconds"))
}

// To run this example, try:
// sbt 'tests/runMain com.SimpleBroadcast.Main'
object Main {
  def main(): Unit = main(Array())

  def main(args: Array[String]) = {
    println("Successfully compiled! Running now...")
    implicit val ps = effpi.system.ProcessSystemRunnerImproved()

    val(c1, c2, c3, c4) = 
     (Channel.async[Data](),
     Channel.async[Data](),
     Channel.async[OK|Rec](),
     Channel.async[Data|KO]()) 

    eval(par(
     p(c1, c2),
     r(c2, c3, c3, c4),
     q(c1, c3, c4)))


    Thread.sleep(1000)
    ps.kill()
  }
}