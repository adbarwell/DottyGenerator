package com.RoleP.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type RoleP[ 
C_RoleP_RoleQ_1 <: OutChannel[Ping]] = 
 Out[C_RoleP_RoleQ_1,Ping] >>: PNil 

