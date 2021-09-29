package output.Pa.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Pa[ 
C_Pa_Q_1 <: InChannel[PlayA],
C_Pa_Pb_1 <: OutChannel[InfoAB],
C_Pa_Pb_2 <: InChannel[InfoBA],
C_Pa_Pb_3 <: OutChannel[MovAB],
C_Pa_Pb_4 <: InChannel[MovBA]] = 
 In[C_Pa_Q_1, PlayA, (x:PlayA) => Out[C_Pa_Pb_1,InfoAB] >>: In[C_Pa_Pb_2, InfoBA, (x:InfoBA) => Rec[RecPa_3, Out[C_Pa_Pb_3,MovAB] >>: In[C_Pa_Pb_4, MovBA, (x:MovBA) => Loop[RecPa_3]]]]] 


