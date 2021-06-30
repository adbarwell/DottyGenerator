package com.A1.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type A1[ 
C_A1_P_1 <: InChannel[Propose],
C_A1_P_2 <: OutChannel[Reject|Accept]] = 
 InErr[C_A1_P_1, Propose, (x:Propose) => ((Out[C_A1_P_2,Reject] >>: PNil)|(Out[C_A1_P_2,Accept] >>: PNil)), (err:Throwable) => PNil] 

