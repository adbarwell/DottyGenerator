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

// type Q[C4o <: OutChannel[InfoBC],
//        C6i <: InChannel[InfoAB],
//        C7i <: InChannel[Mov2AB|Mov1AB],
//        C5 <: OutChannel[PlayB[C4o,C6i,C7i]]] =
//   Out[C5, PlayB[C4o,C6i,C7i]] >>: PNil

type Q[C5_1 <: OutChannel[InChannel[InfoBC]],
       C5_2 <: OutChannel[OutChannel[InfoCA]],
       C5_3 <: OutChannel[InChannel[Mov2AC|Mov1AC]],
       C5_4 <: OutChannel[OutChannel[Mov1CA|Mov2CA]],
       C8_1 <: OutChannel[InChannel[InfoCA]],
       C8_2 <: OutChannel[OutChannel[InfoAB]],
       C8_3 <: OutChannel[OutChannel[Mov1AB|Mov2AB]],
       C8_4 <: OutChannel[OutChannel[Mov1AC]],
       C8_5 <: OutChannel[InChannel[Mov1CA|Mov2CA]],
       C8_6 <: OutChannel[OutChannel[Mov2AC]],
       C9_1 <: OutChannel[OutChannel[InfoBC]],
       C9_2 <: OutChannel[InChannel[InfoAB]],
       C9_3 <: OutChannel[InChannel[Mov2AB|Mov1AB]]] =
  Out[C5_1, InChannel[InfoBC]] >>:
  Out[C5_2, OutChannel[InfoCA]] >>:
  Out[C5_3, InChannel[Mov2AC|Mov1AC]] >>:
  Out[C5_4, OutChannel[Mov1CA|Mov2CA]] >>:
  Out[C8_1, InChannel[InfoCA]] >>:
  Out[C8_2, OutChannel[InfoAB]] >>:
  Out[C8_3, OutChannel[Mov1AB|Mov2AB]] >>:
  Out[C8_4, OutChannel[Mov1AC]] >>:
  Out[C8_5, InChannel[Mov1CA|Mov2CA]] >>:
  Out[C8_6, OutChannel[Mov2AC]] >>:
  Out[C9_1, OutChannel[InfoBC]] >>:
  Out[C9_2, InChannel[InfoAB]] >>:
  Out[C9_3, InChannel[Mov2AB|Mov1AB]] >>:
  PNil


