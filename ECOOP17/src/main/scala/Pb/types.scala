package output.Pb.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Pb_2[ 
X_2 <: Mov1AB|Mov2AB] <: Process = 
 X_2 match { 
case Mov1AB => Loop[RecPb_1] 
case Mov2AB => Loop[RecPb_1] 
} 

type Pb[ 
C_Pb_Q_1 <: InChannel[PlayB],
C_Pb_Pc_1 <: OutChannel[InfoBC],
C_Pb_Pa_1 <: InChannel[InfoAB],
C_Pb_Pa_2 <: InChannel[Mov1AB|Mov2AB]] = 
 In[C_Pb_Q_1, PlayB, (x:PlayB) => Rec[RecPb_1, Out[C_Pb_Pc_1,InfoBC] >>: In[C_Pb_Pa_1, InfoAB, (x:InfoAB) => In[C_Pb_Pa_2, Mov1AB|Mov2AB, (X_2:Mov1AB|Mov2AB) => Pb_2[X_2.type]]]]] 


