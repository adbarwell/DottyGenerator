package com.Q.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

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

