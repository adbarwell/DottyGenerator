package output.Q.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

// type Q[C1i <: InChannel[Mov2AC|Mov1AC],
//        C1o <: OutChannel[Mov2AC|Mov1AC],
//        C2i <: InChannel[InfoCA],
//        C2o <: OutChannel[InfoCA],
//        C3i <: InChannel[Mov1CA|Mov2CA],
//        C3o <: OutChannel[Mov1CA|Mov2CA],
//        C4i <: InChannel[InfoBC],
//        C4o <: OutChannel[InfoBC],
//        C6i <: InChannel[InfoAB],
//        C6o <: OutChannel[InfoAB],
//        C7i <: InChannel[Mov2AB|Mov1AB],
//        C7o <: OutChannel[Mov2AB|Mov1AB],
//        C_Q_Pa_1 <: OutChannel[PlayA],
//        C_Q_Pb_1 <: OutChannel[PlayB[C4o,C6i,C7i]],
//        C_Q_Pc_1 <: OutChannel[PlayC]] = 
//   Out[C_Q_Pa_1,PlayA] >>: Out[C_Q_Pb_1,PlayB] >>: Out[C_Q_Pc_1,PlayC] >>: PNil 

type Q[C4o <: OutChannel[InfoBC],
       C6i <: InChannel[InfoAB],
       C7i <: InChannel[Mov2AB|Mov1AB],
       C5 <: OutChannel[PlayB[C4o,C6i,C7i]]] =
  Out[C5, PlayB[C4o,C6i,C7i]] >>: PNil

