package output.Pb.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Pb[ 
C_Pb_Q_1 <: InChannel[PlayB],
C_Pb_Pa_1 <: InChannel[InfoAB],
C_Pb_Pa_2 <: OutChannel[InfoBA],
C_Pb_Pa_3 <: InChannel[MovAB],
C_Pb_Pa_4 <: OutChannel[MovBA]] = 
 In[C_Pb_Q_1, PlayB, (x:PlayB) => In[C_Pb_Pa_1, InfoAB, (x:InfoAB) => Out[C_Pb_Pa_2,InfoBA] >>: Rec[RecPb_3, In[C_Pb_Pa_3, MovAB, (x:MovAB) => Out[C_Pb_Pa_4,MovBA] >>: Loop[RecPb_3]]]]] 


