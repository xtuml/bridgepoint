.select any dommap from instances of SRC_DM
.select any dom from instances of S_DOM
${dommap.Id}:${dom.Dom_ID}
.select many dtmaps from instances of SRC_DT
.for each dtmap in dtmaps
  .select any dt from instances of S_DT where (selected.Name == dtmap.Name)
  .if (not_empty dt)
${dtmap.Id}:${dt.DT_ID}
    .select many enmaps related by dtmap->SRC_EN[R10008]
    .for each enmap in enmaps
      .select any enum related by dt->S_EDT[R17]->S_ENUM[R27] where (selected.Name == enmap.Name)
      .if (not_empty enum)
${enmap.Id}:${enum.Enum_ID}
      .end if
    .end for
  .end if
.end for
.select many fnmaps from instances of SRC_FN
.for each fnmap in fnmaps
  .select any sync from instances of S_SYNC where (selected.Name == fnmap.Name)
  .if (not_empty sync)
${fnmap.Id}:${sync.Sync_ID}
    .select many parmmaps related by fnmap->SRC_FPRM[R10001]
    .for each parmmap in parmmaps
      .select any sparm related by sync->S_SPARM[R24] where (selected.Name == parmmap.Name)
      .if (not_empty sparm)
${parmmap.Id}:${sparm.SParm_ID}
      .end if
    .end for
  .end if
.end for
.select many eemaps from instances of SRC_EE
.for each eemap in eemaps
  .select any ee from instances of S_EE where (selected.Name == eemap.Name)
  .if (not_empty ee)
${eemap.Id}:${ee.EE_ID}
    .select many brgmaps related by eemap->SRC_BRG[R10002]
    .for each brgmap in brgmaps
      .select any brg related by ee->S_BRG[R19] where (selected.Name == brgmap.Name)
      .if (not_empty brg)
${brgmap.Id}:${brg.Brg_ID}
        .select many bparmmaps related by brgmap->SRC_BPRM[R10003]
        .for each bparmmap in bparmmaps
          .select any bparm related by brg->S_BPARM[R21] where (selected.Name == bparmmap.Name)
          .if (not_empty bparm)
${bparmmap.Id}:${bparm.BParm_ID}
          .end if
        .end for
      .end if
    .end for
  .end if
.end for
.select many ssmaps from instances of SRC_SS
.for each ssmap in ssmaps
  .select any ss from instances of S_SS where (selected.Name == ssmap.Name)
  .if (not_empty ss)
${ssmap.Id}:${ss.SS_ID}
    .select many relmaps related by ssmap->SRC_REL[R10009]
    .for each relmap in relmaps
      .select any rel related by ss->R_REL[R4] where ("${selected.Numb}" == relmap.Name)
      .if (not_empty rel)
${relmap.Id}:${rel.Rel_ID}
        .select many rgomaps related by relmap->SRC_RGO[R10010]
        .for each rgomap in rgomaps
          .select any class related by rel->R_OIR[R201]->O_OBJ[R201] where (selected.Name == rgomap.ClassName)
          .if (not_empty class)
            .select any rgo related by rel->R_OIR[R201]->R_RGO[R203] where (selected.Obj_ID == class.Obj_ID)
            .if (not_empty rgo)
${rgomap.Id}:${rgo.OIR_ID}
            .end if
          .end if
        .end for
        .select many rtomaps related by relmap->SRC_RTO[R10011]
        .for each rtomap in rtomaps
          .select any class related by rel->R_OIR[R201]->O_OBJ[R201] where (selected.Name == rtomap.ClassName)
          .if (not_empty class)
            .select any rto related by rel->R_OIR[R201]->R_RTO[R203] where (selected.Obj_ID == class.Obj_ID)
            .if (not_empty rto)
${rtomap.Id}:${rto.OIR_ID}
            .end if
          .end if
        .end for
      .end if
    .end for
  .end if
.end for
.select many classmaps from instances of SRC_CL
.for each classmap in classmaps
  .select any class from instances of O_OBJ where (selected.Name == classmap.Name)
  .if (not_empty class)
${classmap.Id}:${class.Obj_ID}
    .select many attrmaps related by classmap->SRC_AT[R10000]
    .for each attrmap in attrmaps
      .select any attr related by class->O_ATTR[R102] where (selected.Name == attrmap.Name)
      .if (not_empty attr)
${attrmap.Id}:${attr.Attr_ID}
      .end if
    .end for
    .select many opmaps related by classmap->SRC_OP[R10004]
    .for each opmap in opmaps
      .select any op related by class->O_TFR[R115] where (selected.Name == opmap.Name)
      .if (not_empty op)
${opmap.Id}:${op.Tfr_ID}
        .select many parmmaps related by opmap->SRC_OPRM[R10005]
        .for each parmmap in parmmaps
          .select any oparm related by op->O_TPARM[R117] where (selected.Name == parmmap.Name)
          .if (not_empty oparm)
${parmmap.Id}:${oparm.TParm_ID}
          .end if
        .end for
      .end if
    .end for
    .select many evtmaps related by classmap->SRC_EVT[R10006]
    .for each evtmap in evtmaps
      .select any evt related by class->SM_ISM[R518]->SM_SM[R517]->SM_EVT[R502] where (selected.Mning == evtmap.Name)
      .if (empty evt)
        .select any evt related by class->SM_ASM[R519]->SM_SM[R517]->SM_EVT[R502] where (selected.Mning == evtmap.Name)
      .end if
      .if (not_empty evt)
${evtmap.Id}:${evt.SMevt_ID}
        .select many edimaps related by evtmap->SRC_EVTDI[R10007]
        .for each edimap in edimaps
          .select any edi related by evt->SM_SDI[R522]->SM_EVTDI[R522] where (selected.Name == edimap.Name)
          .if (not_empty edi)
${edimap.Id}:${edi.Evt_ID}
          .end if
        .end for
      .end if
    .end for
  .end if
.end for
.emit to file "map.txt"