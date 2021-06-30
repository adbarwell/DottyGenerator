package com.R.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type R[ 
C_R_P_1 <: InChannel[Data],
C_R_Q_1 <: OutChannel[OK],
C_R_Q_2 <: OutChannel[Rec],
C_R_Q_3 <: InChannel[KO|Data]] = 
 InErr[C_R_P_1, Data, (x:Data) => Out[C_R_Q_1,OK] >>: PNil, (err:Throwable) => Out[C_R_Q_2,Rec] >>: InErr[C_R_Q_3, KO|Data, (x:KO|Data) => PNil, (err:Throwable) => PNil]] 

