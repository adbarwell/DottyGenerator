package com.A2.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type A2[ 
C_A2_P_1 <: InChannel[Propose],
C_A2_P_2 <: OutChannel[Reject|Accept]] = 
 InErr[C_A2_P_1, Propose, (x:Propose) => ((Out[C_A2_P_2,Reject] >>: PNil)|(Out[C_A2_P_2,Accept] >>: PNil)), (err:Throwable) => PNil] 

