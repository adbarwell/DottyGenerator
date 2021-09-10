package output.Pa.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Pa_2[ 
X_2 <: Mov1CA|Mov2CA] <: Process = 
 X_2 match { 
case Mov1CA => Loop[RecPa_1] 
case Mov2CA => Loop[RecPa_1] 
} 

type Pa_3[ 
X_3 <: Mov1CA|Mov2CA] <: Process = 
 X_3 match { 
case Mov1CA => Loop[RecPa_1] 
case Mov2CA => Loop[RecPa_1] 
} 

type Pa[ 
C_Pa_Q_1 <: InChannel[PlayA],
C_Pa_Pc_1 <: InChannel[InfoCA],
C_Pa_Pb_1 <: OutChannel[InfoAB],
C_Pa_Pb_2 <: OutChannel[Mov1AB|Mov2AB],
C_Pa_Pc_2 <: OutChannel[Mov1AC],
C_Pa_Pc_4 <: InChannel[Mov1CA|Mov2CA],
C_Pa_Pc_3 <: OutChannel[Mov2AC]] = 
 In[C_Pa_Q_1, PlayA, (x:PlayA) => Rec[RecPa_1, In[C_Pa_Pc_1, InfoCA, (x:InfoCA) => Out[C_Pa_Pb_1,InfoAB] >>: ((Out[C_Pa_Pb_2,Mov1AB] >>: Out[C_Pa_Pc_2,Mov1AC] >>: In[C_Pa_Pc_4, Mov1CA|Mov2CA, (X_2:Mov1CA|Mov2CA) => Pa_2[X_2.type]])|(Out[C_Pa_Pb_2,Mov2AB] >>: Out[C_Pa_Pc_3,Mov2AC] >>: In[C_Pa_Pc_4, Mov1CA|Mov2CA, (X_3:Mov1CA|Mov2CA) => Pa_3[X_3.type]]))]]] 


