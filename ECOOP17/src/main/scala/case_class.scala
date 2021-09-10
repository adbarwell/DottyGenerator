package output.caseclass
import effpi.channel.{InChannel, OutChannel}
import java.util.Date

case class Mov2AC(z : Boolean)
case class InfoAB(x : String)
case class InfoBC(x : String)
case class InfoCA(x : String)
case class Mov2CA(z : Boolean)
case class Mov1CA(y : Int)
case class Mov2AB(z : Boolean)
case class Mov1AC(y : Int)
case class Mov1AB(y : Int)

// case class SessionA(c2 : InChannel[InfoCA],
//                     c6 : OutChannel[InfoAB],
//                     c7 : OutChannel[Mov2AB|Mov1AB],
//                     c1 : OutChannel[Mov2AC|Mov1AC],
//                     c3 : InChannel[Mov1CA|Mov2CA])
case class SessionB[C4 <: OutChannel[InfoBC],
                    C6 <: InChannel[InfoAB],
                    C7 <: InChannel[Mov2AB|Mov1AB]] (c4 : C4, c6 : C6, c7 : C7)
// case class SessionC(c4 : InChannel[InfoBC],
//                     c2 : OutChannel[InfoCA],
//                     c1 : InChannel[Mov2AC|Mov1AC],
//                     c3 : OutChannel[Mov1CA|Mov2CA])

// case class PlayA(sa : SessionA)
case class PlayB[C4 <: OutChannel[InfoBC],
                 C6 <: InChannel[InfoAB],
                 C7 <: InChannel[Mov2AB|Mov1AB]](sb : SessionB[C4,C6,C7])
// case class PlayC(sc : SessionC)

case class PlayA(x : String)
case class PlayC(x : String)
