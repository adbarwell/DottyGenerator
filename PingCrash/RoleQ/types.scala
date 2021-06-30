package com.RoleQ.types

import com.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type RoleQ[ 
C_RoleQ_RoleP_1 <: InChannel[Ping]] = 
 InErr[C_RoleQ_RoleP_1, Ping, (x:Ping) => PNil, (err:Throwable) => PNil] 

