package output.Pc.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

// type Pc[ 
// C_Pc_Q_1 <: InChannel[PlayC],
// C_Pc_Pb_1 <: InChannel[InfoBC],
// C_Pc_Pa_1 <: OutChannel[InfoCA],
// C_Pc_Pa_2 <: InChannel[MovAC],
// C_Pc_Pa_3 <: OutChannel[MovCA]] = 
//  In[C_Pc_Q_1, PlayC, (x:PlayC) => Rec[RecPc_1, In[C_Pc_Pb_1, InfoBC, (x:InfoBC) => Out[C_Pc_Pa_1,InfoCA] >>: In[C_Pc_Pa_2, MovAC, (x:MovAC) => Out[C_Pc_Pa_3,MovCA] >>: Loop[RecPc_1]]]]] 


// pc(c5, c2, c9, c7, c8)))

type Pc[C5_1 <: InChannel[InChannel[InfoBC]],
        C5_2 <: InChannel[OutChannel[InfoCA]],
        C5_3 <: InChannel[InChannel[MovAC]],
        C5_4 <: InChannel[OutChannel[MovCA]]] =
  In[C5_1, InChannel[InfoBC], (c2 : InChannel[InfoBC]) =>
  In[C5_2, OutChannel[InfoCA], (c9 : OutChannel[InfoCA]) =>
  In[C5_3, InChannel[MovAC], (c7 : InChannel[MovAC]) =>
  In[C5_4, OutChannel[MovCA], (c8 : OutChannel[MovCA]) =>
    Rec[RecPc_1,
    In[c2.type, InfoBC, (x : InfoBC) =>
      Out[c9.type, InfoCA] >>:
      In[c7.type, MovAC, (y : MovAC) =>
        Out[c8.type, MovCA] >>:
        Loop[RecPc_1]]]]
  ]]]]