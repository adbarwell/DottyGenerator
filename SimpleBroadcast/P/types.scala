package com.P.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type P[ 
C_P_Q_1 <: OutChannel[Data],
C_P_R_1 <: OutChannel[Data]] = 
 Out[C_P_Q_1,Data] >>: Out[C_P_R_1,Data] >>: PNil 

