package output.Pa.types

import output.caseclass._
import effpi.process._
import effpi.process.dsl._
import effpi.channel.{InChannel, OutChannel}
import effpi.recurse._

type Pa_2[ 
X_2 <: Mov1CA|Mov2CA] <: Process = 
 X_2 match { 
case Mov1CA => Loop[RecPa_1] 
case Mov2CA => Loop[RecPa_1] 
} 

type Pa_3[ 
X_3 <: Mov1CA|Mov2CA] <: Process = 
 X_3 match { 
case Mov1CA => Loop[RecPa_1] 
case Mov2CA => Loop[RecPa_1] 
}

// type Pa0[ 
// // C_Pa_Q_1 <: InChannel[PlayA],
// C_Pa_Pc_1 <: InChannel[InfoCA],
// C_Pa_Pb_1 <: OutChannel[InfoAB],
// C_Pa_Pb_2 <: OutChannel[Mov1AB|Mov2AB],
// C_Pa_Pc_2 <: OutChannel[Mov1AC|Mov2AC],
// C_Pa_Pc_4 <: InChannel[Mov1CA|Mov2CA]] =
// // C_Pa_Pc_3 <: OutChannel[Mov2AC]] = 
// //  In[C_Pa_Q_1, PlayA, (x:PlayA) => 
//   Rec[RecPa_1, In[C_Pa_Pc_1, InfoCA, (x:InfoCA) => Out[C_Pa_Pb_1,InfoAB] >>:
//   ((Out[C_Pa_Pb_2,Mov1AB] >>: Out[C_Pa_Pc_2,Mov1AC] >>: In[C_Pa_Pc_4, Mov1CA|Mov2CA, (X_2:Mov1CA|Mov2CA) => Pa_2[X_2.type]]))]]
//   // |(Out[C_Pa_Pb_2,Mov2AB] >>: Out[C_Pa_Pc_2,Mov2AC] >>: In[C_Pa_Pc_4, Mov1CA|Mov2CA, (X_3:Mov1CA|Mov2CA) => Pa_3[X_3.type]]))]]
// //  ]

type Pa0[C2 <: InChannel[InfoCA],
         C6 <: OutChannel[InfoAB],
         C7 <: OutChannel[Mov1AB|Mov2AB],
         C1 <: OutChannel[Mov1AC],
         C3 <: InChannel[Mov1CA|Mov2CA],
         C11 <: OutChannel[Mov2AC]] =
  Rec[RecPa_1,
    In[C2, InfoCA, (x : InfoCA) =>
    Out[C6, InfoAB] >>:
    ((Out[C7, Mov1AB] >>: PNil ))]
      // Out[C1, Mov1AC] >>: PNil ))]
      // In[C3, Mov1CA|Mov2CA, (y : Mov1CA|Mov2CA) => Loop[RecPa_1]])
    //   |
    //  (Out[C7, Mov2AB] >>: PNil))]
      // Out[C11, Mov2AC] >>: PNil))]
      // In[C3, Mov1CA|Mov2CA, (z : Mov1CA|Mov2CA) => Loop[RecPa_1]]))]
  ]

// c2 c6 c7 c1 c3 c1
type Pa[C8_1 <: InChannel[InChannel[InfoCA]],
        C8_2 <: InChannel[OutChannel[InfoAB]],
        C8_3 <: InChannel[OutChannel[Mov1AB|Mov2AB]],
        C8_4 <: InChannel[OutChannel[Mov1AC]],
        C8_5 <: InChannel[InChannel[Mov1CA|Mov2CA]],
        C8_6 <: InChannel[OutChannel[Mov2AC]]] =
  In[C8_1, InChannel[InfoCA], (c2 : InChannel[InfoCA]) =>
    In[C8_2, OutChannel[InfoAB], (c6 : OutChannel[InfoAB]) =>
      In[C8_3, OutChannel[Mov1AB|Mov2AB], (c7 : OutChannel[Mov1AB|Mov2AB]) =>
        In[C8_4, OutChannel[Mov1AC], (c1 : OutChannel[Mov1AC]) =>
          In[C8_5, InChannel[Mov1CA|Mov2CA], (c3 : InChannel[Mov1CA|Mov2CA]) =>
            In[C8_6, OutChannel[Mov2AC], (c11 : OutChannel[Mov2AC]) =>
              // Pa0[c2.type, c6.type, c7.type, c1.type, c3.type, c11.type]
              Rec[RecPa_1,
                In[c2.type,InfoCA, (x : InfoCA) =>
                Out[c6.type, InfoAB] >>: (
                  (Out[c7.type, Mov1AB] >>: PNil)
                  //  Out[c1.type, Mov1AC] >>: PNil)
                  |
                  (Out[c7.type, Mov2AB] >>: PNil)
                  //  Out[c11.type, Mov2AC] >>: PNil)
                )]
              ]
  ]]]]]]


// type Pa[C1 <: InChannel[OutChannel[Msg]]] =
//   In[C1, OutChannel[Msg], (c : OutChannel[Msg]) =>
//     Out[c.type, Msg]]

// type Pb[C1 <: OutChannel[OutChannel[Msg]], C2 <: OutChannel[Msg]] = Out[C1, C2]

// type Pc[C2 <: InChannel[Msg]] = In[C2, Msg, (x : Msg) => PNil]


