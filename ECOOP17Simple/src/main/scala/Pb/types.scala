package output.Pb.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

// type Pb[ 
// C_Pb_Q_1 <: InChannel[PlayB],
// C_Pb_Pc_1 <: OutChannel[InfoBC],
// C_Pb_Pa_1 <: InChannel[InfoAB],
// C_Pb_Pa_2 <: InChannel[MovAB]] = 
//  In[C_Pb_Q_1, PlayB, (x:PlayB) => Rec[RecPb_1, Out[C_Pb_Pc_1,InfoBC] >>: In[C_Pb_Pa_1, InfoAB, (x:InfoAB) => In[C_Pb_Pa_2, MovAB, (x:MovAB) => Loop[RecPb_1]]]]] 

// pb(c1, c2, c3, c4),

type Pb[C1_1 <: InChannel[OutChannel[InfoBC]],
        C1_2 <: InChannel[InChannel[InfoAB]],
        C1_3 <: InChannel[InChannel[MovAB]]] =
  In[C1_1, OutChannel[InfoBC], (c2 : OutChannel[InfoBC]) =>
  In[C1_2, InChannel[InfoAB], (c3 : InChannel[InfoAB]) =>
  In[C1_3, InChannel[MovAB], (c4 : InChannel[MovAB]) =>
    Rec[RecPb_1,
    Out[c2.type, InfoBC] >>:
    In[c3.type, InfoAB, (x : InfoAB) =>
      In[c4.type, MovAB, (y : MovAB) => Loop[RecPb_1]]]]
  ]]]
