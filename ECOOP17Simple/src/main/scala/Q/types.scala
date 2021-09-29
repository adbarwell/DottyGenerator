package output.Q.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

// type Q[ 
// C_Q_Pa_1 <: OutChannel[PlayA],
// C_Q_Pb_1 <: OutChannel[PlayB],
// C_Q_Pc_1 <: OutChannel[PlayC]] = 
//  Out[C_Q_Pa_1,PlayA] >>: Out[C_Q_Pb_1,PlayB] >>: Out[C_Q_Pc_1,PlayC] >>: PNil 


type Q[C1_1 <: OutChannel[OutChannel[InfoBC]],
       C1_2 <: OutChannel[InChannel[InfoAB]],
       C1_3 <: OutChannel[InChannel[MovAB]],
       C5_1 <: OutChannel[InChannel[InfoBC]],
       C5_2 <: OutChannel[OutChannel[InfoCA]],
       C5_3 <: OutChannel[InChannel[MovAC]],
       C5_4 <: OutChannel[OutChannel[MovCA]],
       C6_1 <: OutChannel[InChannel[InfoCA]],
       C6_2 <: OutChannel[OutChannel[InfoAB]],
       C6_3 <: OutChannel[OutChannel[MovAB]],
       C6_4 <: OutChannel[OutChannel[MovAC]],
       C6_5 <: OutChannel[InChannel[MovCA]]] =
  Out[C1_1, OutChannel[InfoBC]] >>:
  Out[C1_2, InChannel[InfoAB]] >>:
  Out[C1_3, InChannel[MovAB]] >>:
  Out[C5_1, InChannel[InfoBC]] >>:
  Out[C5_2, OutChannel[InfoCA]] >>:
  Out[C5_3, InChannel[MovAC]] >>:
  Out[C5_4, OutChannel[MovCA]] >>:
  Out[C6_1, InChannel[InfoCA]] >>:
  Out[C6_2, OutChannel[InfoAB]] >>:
  Out[C6_3, OutChannel[MovAB]] >>:
  Out[C6_4, OutChannel[MovAC]] >>:
  Out[C6_5, InChannel[MovCA]]  >>:
  PNil

