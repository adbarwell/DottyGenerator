package output.Pa.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

// type Pa[ 
// C_Pa_Q_1 <: InChannel[PlayA],
// C_Pa_Pc_1 <: InChannel[InfoCA],
// C_Pa_Pb_1 <: OutChannel[InfoAB],
// C_Pa_Pb_2 <: OutChannel[MovAB],
// C_Pa_Pc_2 <: OutChannel[MovAC],
// C_Pa_Pc_3 <: InChannel[MovCA]] = 
//  In[C_Pa_Q_1, PlayA, (x:PlayA) => Rec[RecPa_1, In[C_Pa_Pc_1, InfoCA, (x:InfoCA) => Out[C_Pa_Pb_1,InfoAB] >>: Out[C_Pa_Pb_2,MovAB] >>: Out[C_Pa_Pc_2,MovAC] >>: In[C_Pa_Pc_3, MovCA, (x:MovCA) => Loop[RecPa_1]]]]] 


// pa(c6, c9, c3, c4, c7, c8),

type Pa[C6_1 <: InChannel[InChannel[InfoCA]],
        C6_2 <: InChannel[OutChannel[InfoAB]],
        C6_3 <: InChannel[OutChannel[MovAB]],
        C6_4 <: InChannel[OutChannel[MovAC]],
        C6_5 <: InChannel[InChannel[MovCA]]] =
  In[C6_1, InChannel[InfoCA], (c9 : InChannel[InfoCA]) =>
  In[C6_2, OutChannel[InfoAB], (c3 : OutChannel[InfoAB]) =>
  In[C6_3, OutChannel[MovAB], (c4 : OutChannel[MovAB]) =>
  In[C6_4, OutChannel[MovAC], (c7 : OutChannel[MovAC]) =>
  In[C6_5, InChannel[MovCA], (c8 : OutChannel[MovCA]) =>
    Rec[RecPa_1,
    In[c9.type, InfoCA, (x : InfoCA) =>
      Out[c3.type, InfoAB] >>:
      Out[c4.type, MovAB] >>:
      Out[c7.type, MovAC] >>:
      In[c8.type, MovCA, (y : MovCA) => Loop[RecPa_1]]
    ]]
  ]]]]]

