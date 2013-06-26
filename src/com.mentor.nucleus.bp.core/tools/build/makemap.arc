.//========================================================================
.//
.//File:      $RCSfile$
.//Version:   $Revision$
.//Modified:  $Date$
.//
.//(c) Copyright 2006-2012 by Mentor Graphics Corp. All rights reserved.
.//
.//========================================================================
.//This document contains information proprietary and confidential to
.//Mentor Graphics Corp., and is not for external distribution.
.//========================================================================
.//
.// This archetype file searches a loaded model and emits a name UUID tuple for
.// each model element that has a name and can be referred to from another part
.// of a model.
.// To use it, a model must first have its UUID's converted to strings, using
.// the Perl script; 'id2str.pl' (checked into the same folder as this file).
.//
.select any dom from instances of S_DOM
INSERT INTO SRC_DM VALUES ('${dom.Name}','${dom.Dom_ID}');
.select many dts from instances of S_DT
.for each dt in dts
INSERT INTO SRC_DT VALUES ('${dt.Name}','${dt.DT_ID}');
  .select one edt related by dt->S_EDT[R17]
  .if (not_empty edt)
    .select many enums related by edt->S_ENUM[R27]
    .for each enum in enums
INSERT INTO SRC_EN VALUES ('${dt.Name}','${enum.Name}','${enum.Enum_ID}');
    .end for
  .end if
.end for
.select many fns from instances of S_SYNC
.for each fn in fns
INSERT INTO SRC_FN VALUES ('${fn.Name}','${fn.Sync_ID}');
  .select many fnParms related by fn->S_SPARM[R24]
  .for each fnParm in fnParms
INSERT INTO SRC_FPRM VALUES ('${fn.Name}','${fnParm.Name}','${fnParm.SParm_ID}');
  .end for
.end for
.select many ees from instances of S_EE
.for each ee in ees
INSERT INTO SRC_EE VALUES ('${ee.Name}','${ee.EE_ID}');
  .select many brgs related by ee->S_BRG[R19]
  .for each brg in brgs
INSERT INTO SRC_BRG VALUES ('${ee.Name}','${brg.Name}','${brg.Brg_ID}');
    .select many brParms related by brg->S_BPARM[R21]
    .for each brParm in brParms
INSERT INTO SRC_BPRM VALUES ('${ee.Name}','${brg.Name}','${brParm.Name}','${brParm.BParm_ID}');
    .end for
  .end for
.end for
.select many sss from instances of S_SS
.for each ss in sss
INSERT INTO SRC_SS VALUES ('${ss.Name}','${ss.SS_ID}');
  .select many rels related by ss->R_REL[R4]
  .for each rel in rels
INSERT INTO SRC_REL VALUES ('${ss.Name}','${rel.Numb}','${rel.Rel_ID}');
    .select many rtos related by rel->R_OIR[R201]->R_RTO[R203];
    .for each rto in rtos
      .select one class related by rto->R_OIR[R203]->O_OBJ[R201];
INSERT INTO SRC_RTO VALUES ('${ss.Name}','${rel.Numb}','${class.Name}','${rto.OIR_ID}');
    .end for
    .select many rgos related by rel->R_OIR[R201]->R_RGO[R203];
    .for each rgo in rgos
      .select one class related by rgo->R_OIR[R203]->O_OBJ[R201];
INSERT INTO SRC_RGO VALUES ('${ss.Name}','${rel.Numb}','${class.Name}','${rgo.OIR_ID}');
    .end for
  .end for
.end for
.select many classes from instances of O_OBJ
.for each class in classes
INSERT INTO SRC_CL VALUES ('${class.Name}','${class.Obj_ID}');
  .select many attributes related by class->O_ATTR[R102]
  .for each attr in attributes
INSERT INTO SRC_AT VALUES ('${class.Name}','${attr.Name}','${attr.Attr_ID}');
  .end for
  .select many ops related by class->O_TFR[R115]
  .for each op in ops
INSERT INTO SRC_OP VALUES ('${class.Name}','${op.Name}','${op.Tfr_ID}');
    .select many opParms related by op->O_TPARM[R117]
    .for each opParm in opParms
INSERT INTO SRC_OPRM VALUES ('${class.Name}','${op.Name}','${opParm.Name}','${opParm.TParm_ID}');
    .end for
  .end for
  .select many events related by class->SM_ISM[R518]->SM_SM[R517]->SM_EVT[R502]
  .for each event in events
INSERT INTO SRC_EVT VALUES ('${class.Name}','${event.Mning}','${event.SMevt_ID}');
    .select many evtDtIs related by event->SM_SDI[R522]->SM_EVTDI[R522]
    .for each evtDtI in evtDtIs
INSERT INTO SRC_EVTDI VALUES ('${class.Name}','${event.Mning}','${evtDtI.Name}','${event.SMedi_ID}');
    .end for
  .end for
  .select many events related by class->SM_ASM[R519]->SM_SM[R517]->SM_EVT[R502]
  .for each event in events
INSERT INTO SRC_EVT VALUES ('${class.Name}','${event.Mning}','${event.SMEvt_ID}');
    .select many evtDtIs related by event->SM_EVTD[R522?]->SM_EVTDI[R522]
    .for each evtDtI in evtDtIs
INSERT INTO SRC_EVTDI VALUES ('${class.Name}','${event.Mning}','${evtDtI.Name}','${event.SMedi_ID}');
    .end for
  .end for
.end for
.emit to file "../../sql/map.sql"