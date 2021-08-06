package output.ROLE.implementation

import output.caseclass._
import output.ROLE.types._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import scala.concurrent.duration.Duration
import effpi.recurse._
import java.util.Date

implicit val timeout: Duration = Duration("TIMEOUT seconds")

IMPLEMENTATIONS
