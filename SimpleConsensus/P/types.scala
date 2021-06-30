package com.P.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

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

