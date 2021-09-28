package output.Pc.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Pc_2[ 
X_2 <: Mov2AC|Mov1AC,
C_Pc_Pa_3 <: OutChannel[Mov1CA|Mov2CA]] <: Process = 
 X_2 match { 
case Mov2AC => ((Out[C_Pc_Pa_3,Mov1CA] >>: Loop[RecPc_1])|(Out[C_Pc_Pa_3,Mov2CA] >>: Loop[RecPc_1])) 
case Mov1AC => ((Out[C_Pc_Pa_3,Mov1CA] >>: Loop[RecPc_1])|(Out[C_Pc_Pa_3,Mov2CA] >>: Loop[RecPc_1])) 
} 

type Pc[C5_1 <: InChannel[InChannel[InfoBC]],
        C5_2 <: InChannel[OutChannel[InfoCA]],
        C5_3 <: InChannel[InChannel[Mov2AC|Mov1AC]],
        C5_4 <: InChannel[OutChannel[Mov1CA|Mov2CA]]] =
  In[C5_1, InChannel[InfoBC], (c4 : InChannel[InfoBC]) =>
    In[C5_2, OutChannel[InfoCA], (c2 : OutChannel[InfoCA]) =>
      In[C5_3, InChannel[Mov2AC|Mov1AC], (c1 : InChannel[Mov2AC|Mov1AC]) =>
        In[C5_4, OutChannel[Mov1CA|Mov2CA], (c3 : OutChannel[Mov1CA|Mov2CA]) =>
          Rec[RecPc_1,
              In[c4.type, InfoBC, (x : InfoBC) =>
                Out[c2.type, InfoCA] >>:
                In[c1.type, Mov2AC|Mov1AC, (y : Mov2AC|Mov1AC) =>
                  Pc_2[y.type, c3.type]]]]
        ]]]]


// c4 c2 c1 c3
// type Pc[ 
// C_Pc_Q_1 <: InChannel[PlayC],
// C_Pc_Pb_1 <: InChannel[InfoBC],
// C_Pc_Pa_1 <: OutChannel[InfoCA],
// C_Pc_Pa_2 <: InChannel[Mov2AC|Mov1AC],
// C_Pc_Pa_3 <: OutChannel[Mov1CA|Mov2CA]] = 
//  In[C_Pc_Q_1, PlayC, (x:PlayC) => Rec[RecPc_1, In[C_Pc_Pb_1, InfoBC, (x:InfoBC) => Out[C_Pc_Pa_1,InfoCA] >>: In[C_Pc_Pa_2, Mov2AC|Mov1AC, (X_2:Mov2AC|Mov1AC) => Pc_2[X_2.type,C_Pc_Pa_3]]]]] 


