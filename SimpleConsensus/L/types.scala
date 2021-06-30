package com.L.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type L[ 
C_L_P_1 <: InChannel[Rejected|Accepted]] = 
 InErr[C_L_P_1, Rejected|Accepted, (x:Rejected|Accepted) => PNil, (err:Throwable) => PNil] 

