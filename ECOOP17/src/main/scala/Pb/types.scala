package output.Pb.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Pb_2[X_2 <: Mov1AB|Mov2AB] <: Process = X_2 match { 
  case Mov1AB => Loop[RecPb_1] 
  case Mov2AB => Loop[RecPb_1] 
}

type Pb[C4 <: OutChannel[InfoBC],
        C6 <: InChannel[InfoAB],
        C7 <: InChannel[Mov2AB|Mov1AB],
        C_Pb_Q_1 <: InChannel[PlayB[C4,C6,C7]]] =
  In[C_Pb_Q_1, PlayB, (x:PlayB[C4,C6,C7]) =>
    Rec[RecPb_1,
      Out[C4,InfoBC] >>:
      In[C6, InfoAB, (x:InfoAB) =>
          In[C7, Mov1AB|Mov2AB, (X_2:Mov1AB|Mov2AB) => Pb_2[X_2.type]]]]] 


