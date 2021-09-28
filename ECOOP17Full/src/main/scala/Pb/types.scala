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


// c4 c6 c7
type Pb[C9_1 <: InChannel[OutChannel[InfoBC]],
        C9_2 <: InChannel[InChannel[InfoAB]],
        C9_3 <: InChannel[InChannel[Mov2AB|Mov1AB]]] =
  In[C9_1, OutChannel[InfoBC], (c4 : OutChannel[InfoBC]) =>
    In[C9_2, InChannel[InfoAB], (c6 : InChannel[InfoAB]) =>
      In[C9_3, InChannel[Mov2AB|Mov1AB], (c7 : InChannel[Mov2AB|Mov1AB]) => 
        Rec[RecPb_1,
            Out[c4.type, InfoBC] >>:
            In[c6.type, InfoAB, (x : InfoAB) =>
              In[c7.type, Mov1AB|Mov2AB, (y : Mov1AB|Mov2AB) => Pb_2[y.type]]]]]]]

