package output.Q.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Q[ 
C_Q_Pa_1 <: OutChannel[PlayA],
C_Q_Pb_1 <: OutChannel[PlayB]] = 
 Out[C_Q_Pa_1,PlayA] >>: Out[C_Q_Pb_1,PlayB] >>: PNil 


