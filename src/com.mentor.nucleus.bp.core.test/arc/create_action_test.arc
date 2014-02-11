.//=====================================================================
.//
.// File:      $RCSfile: create_action_test.arc,v $
.// Version:   $Revision: 1.73 $
.// Modified:  $Date: 2013/05/10 04:34:14 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//=====================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not
.// use this file except in compliance with the License.  You may obtain a copy
.// of the License at
.//
.//      http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
.// License for the specific language governing permissions and limitations under
.// the License.
.//=====================================================================
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "../com.mentor.nucleus.bp.core/arc/cme_names.inc"
.include "../com.mentor.nucleus.bp.core/arc/parse_chain.inc"
.//
.//=====================================================================
.//
.function create_new_action_test
  .param inst_ref action   .// CME
    public void test${action.Specialism}${action.Key_Lett}$r{action.Label}() throws Exception
    {
  .select any owner from instances of O_OBJ where (selected.Key_Lett == action.Key_Lett)
  .invoke ocn = get_class_name(owner)
  .if ( ((owner.Key_Lett == "S_DOM") or (owner.Key_Lett == "S_DPK")) or ((owner.Key_Lett == "S_FPK") or (owner.Key_Lett == "S_EEPK")) )
    .invoke oca = get_instance_accessor(owner)
        ${ocn.body} t1 = ${ocn.body}.${oca.body}(modelRoot);
  .else
        ${ocn.body} t1 = new ${ocn.body}(modelRoot);
  .end if
  .if ( (action.Key_Lett == "S_SS") and (action.Label == "Subsystem") )
        Domain_c dom = Domain_c.DomainInstance(modelRoot);
        dom.relateAcrossR1To(t1);
  .elif(((action.Key_Lett == "MSG_R") or (action.Key_Lett == "MSG_SM")) or (action.Key_Lett == "MSG_AM"))
        Message_c message = new Message_c(modelRoot);
        t1.relateAcrossR1018To(message);
  .elif ( (action.Label == "Event") or (action.Label == "State") )
    .select any sm from instances of O_OBJ where (selected.Key_Lett == "SM_SM")
    .invoke smcn = get_class_name(sm)
        ${smcn.body} sm = new ${smcn.body}(modelRoot);
        t1.relateAcrossR517To(sm);
    .if ( action.Label == "State" )
      .select any msm from instances of O_OBJ where (selected.Key_Lett == "SM_MOORE")
      .invoke msmcn = get_class_name(msm)
        ${msmcn.body} msm = new ${msmcn.body}(modelRoot);
        sm.relateAcrossR510To(msm);
    .end if
  .elif((action.Key_Lett == "C_IO") or (action.Key_Lett == "C_AS"))
        SystemModel_c systemModel = new SystemModel_c(modelRoot);
        Interface_c iface = new Interface_c(modelRoot);
		iface.Newexecutableproperty(false);
		 .if (action.Key_Lett == "C_IO")
		 t1.relateAcrossR4008To(DataType_c.DataTypeInstances(modelRoot)[0]);
	     .end if		
	 	t1.relateAcrossR4004To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
	 	t1.Initialize();
   .elif(action.Key_Lett == "O_TFR")
        ModelClass_c clazz = new ModelClass_c(modelRoot);
        t1.relateAcrossR115To(clazz);
        DataType_c type = new DataType_c(modelRoot);
        t1.relateAcrossR116To(type);
   .elif ( ( (action.Key_Lett == "S_SYNC") or (action.Key_Lett == "S_BRG") ) or  (action.Key_Lett == "O_TFR") )      
		 t1.Initialize();       
  .end if
.//checking filter
   .invoke filter_res = call_filter_func(action, "True")
   .if (filter_res.body != "")
        ${filter_res.body}
   .end if
        StructuredSelection sel = new StructuredSelection(t1);
        Selection.getInstance().setSelection(sel, true);
  .invoke result = get_action_class_name(action)
        ${result.body} t2 = new ${result.body}();
        Action a = new Action() {};
        t2.run(a);

  .select any new_thing from instances of O_OBJ where (selected.Key_Lett == action.Resultant_Class)
  .invoke ntcn = get_class_name(new_thing)
  .invoke nta = get_instance_accessor(new_thing)
  .invoke filter_res = call_filter_func(action, "False")

  .if ( (action.Label == "Datatype Package") or ((action.Label == "Function Package") or (action.Label == "External Entity Package")) )
        ${ntcn.body} [] t3 = ${ntcn.body}.$cr{new_thing.Name}Instances(modelRoot);
        assertEquals(2, t3.length);
        t3[1].checkConsistency();
        .if (filter_res.body != "")
        ${filter_res.body}
        .end if
        t3[1].delete_unchecked();
  .elif ( action.Label == "Datatype" )
        ${ntcn.body} [] t3 = ${ntcn.body}.$cr{new_thing.Name}Instances(modelRoot);
        assertEquals(3, t3.length);
        t3[2].checkConsistency();
        .if (filter_res.body != "")
        ${filter_res.body}
        .end if
        t3[2].delete_unchecked();
  .else
        ${ntcn.body} t3 = ${ntcn.body}.${nta.body}(modelRoot);
        assertNotNull(t3);
        t3.checkConsistency();
        .if (filter_res.body != "")
        ${filter_res.body}
        .end if
        t3.delete_unchecked();
  .end if
  .if ( ((owner.Key_Lett != "S_DOM") and (owner.Key_Lett != "S_DPK")) and ((owner.Key_Lett != "S_FPK") and (owner.Key_Lett != "S_EEPK")) )
        t1.delete_unchecked();
  .end if
    }
.end function
.//
.//====================================================================
.//
.function call_filter_func
  .param inst_ref action  .//CME
  .param string assertValue
  .select many filter_set related by action->MEF[R2013]
  .if (not_empty filter_set)
    .for each filter in filter_set
      .if(action.Key_Lett == "MSG_SM")
        .if(assertValue == "False")
    Operation_c operation = new Operation_c(modelRoot);
        OperationMessage_c om = new OperationMessage_c(modelRoot);
        operation.relateAcrossR1011To(om);
        t1.relateAcrossR1020To(om);
        t1.unrelateAcrossR1020From(im);
            .else
        InformalSynchronousMessage_c im = new InformalSynchronousMessage_c(modelRoot);
        t1.relateAcrossR1020To(im);
            .end if
        assert${assertValue}(t1.Actionfilter("${filter.name}", "${filter.value}"));
      .elif(action.Key_Lett == "MSG_AM")
        .if(assertValue == "False")
    StateMachineEvent_c event = new StateMachineEvent_c(modelRoot);
        EventMessage_c em = new EventMessage_c(modelRoot);
        event.relateAcrossR1009To(em);
        t1.relateAcrossR1019To(em);
        t1.unrelateAcrossR1019From(im);
            .else
        InformalAsynchronousMessage_c im = new InformalAsynchronousMessage_c(modelRoot);
        t1.relateAcrossR1019To(im);
            .end if
        assert${assertValue}(t1.Actionfilter("${filter.name}", "${filter.value}"));
          .elif(action.Key_Lett == "SQ_CP")
        .if(assertValue == "False")
    ModelClass_c mclass = new ModelClass_c(modelRoot);
        t1.relateAcrossR939To(mclass);
            .end if
        assert${assertValue}(t1.Actionfilter("${filter.name}", "${filter.value}"));
          .elif(action.Key_Lett == "SQ_CIP")
        .if(assertValue == "False")
    ModelClass_c mclass = new ModelClass_c(modelRoot);
        t1.relateAcrossR934To(mclass);
            .end if
        assert${assertValue}(t1.Actionfilter("${filter.name}", "${filter.value}"));
      .else
    //Checking for filter actions
        assert${assertValue}(t1.Actionfilter("${filter.name}", "${filter.value}"));
      .end if
    .end for
  .end if
.end function
.//
.//=====================================================================
.//
.// These are excluded from the rename test as they are
.// not shown in model explorer.  At some point the
.// canvas rename code shall be exercised to test these
.// elements.
.function isExcluded
  .param String kl
  .assign attr_result = false
  .if(kl == "MSG_R")
    .assign attr_result = true
  .elif(kl == "SQ_TM")
    .assign attr_result = true
  .elif(kl == "SQ_TS")
    .assign attr_result = true
  .elif(kl == "A_FJ")
    .assign attr_result = true
  .elif(kl == "A_E")
    .assign attr_result = true
  .elif(kl == "A_AP")
    .assign attr_result = true
  .elif(kl == "A_DM")
    .assign attr_result = true
  .// these tests are ignored for release 1.4.1
  .elif(kl == "CP_CP")
    .assign attr_result = true
  .elif(kl == "IP_IP")
    .assign attr_result = true
  .elif(kl == "C_C")
    .assign attr_result = true
  .elif(kl == "C_PO")
    .assign attr_result = true
  .elif(kl == "C_I")
    .assign attr_result = true
  .elif(kl == "C_P")
    .assign attr_result = true
  .elif(kl == "EP_PKG")
    .assign attr_result = true
  .elif(kl == "C_R")
    .assign attr_result = true
  .elif(kl == "C_AS")
    .assign attr_result = true
  .elif(kl == "C_IO")
    .assign attr_result = true
  .elif(kl == "C_PP")
    .assign attr_result = true
  .elif(kl == "PA_DIC")
    .assign attr_result = true
  .elif(kl == "PA_SIC")
    .assign attr_result = true
  .elif(kl == "C_PA_SICP")
    .assign attr_result = true
   .elif(kl == "SQ_PP") 
     .assign attr_result = true
  .end if
.end function
.//
.//=====================================================================
.//
.function create_rename_action_test
  .param inst_ref action   .// CME
  .param string useFocus
  .//
  .assign testModifier = ""
  .if (useFocus == "true")
    .assign testModifier = "usingFocus"
  .end if
    public void testRename${action.Key_Lett}${testModifier}() throws Exception
    {
  .select any owner from instances of O_OBJ where (selected.Key_Lett == action.Key_Lett)
  .invoke ocn = get_class_name(owner)
  .invoke oa = get_instance_accessor(owner)
  .assign root = "modelRoot"
  .if (action.Key_Lett == "S_SYS")
      .assign root = "Ooaofooa.getDefaultInstance()"
  .end if
        ${ocn.body} t1 = ${ocn.body}.${oa.body}(${root});
        assertNotNull(t1);
  .assign oldName = "Old_Name";
  .assign newName = "New_Name"+"$r{action.Key_Lett}";
  .if (((action.Key_Lett == "S_UDT") or (action.Key_Lett == "S_EDT")) or (action.Key_Lett == "S_SDT"))
        DataType_c t2 = DataType_c.getOneS_DTOnR17(t1);
        t2.setName("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t2.getName() );
  .elif ( action.Key_Lett == "SM_EVT" )
        t1.setMning("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t1.getMning() );
  .elif ( action.Key_Lett == "O_ATTR" )
        t1.setRoot_nam("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t1.getName() );
  .elif ((action.Key_Lett == "S_SYS") or (action.Key_Lett == "S_DOM"))
        String oldName = t1.getName();
        t1.setName("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t1.getName() );

        // rename the element back to its original name
        // so that succeeding tests that depend
        // on the model-root's ID being unchanged won't fail
        t1.setName(oldName);
        updateTreeItem( t1, oldName, false );
  .// special case for sequence elements which
  .// do not have an attribute called name
  .elif(((((((action.Key_Lett == "SQ_CP") or (action.Key_Lett == "SQ_EEP")) or (action.Key_Lett == "SQ_FPP")) or (action.Key_Lett == "SQ_AV")) or (action.Key_Lett == "MSG_A")) or (action.Key_Lett == "C_P")) or (action.Key_Lett == "C_R"))
        t1.setInformalname("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t1.getInformalname());
  .elif((action.Key_Lett == "MSG_AM") or (action.Key_Lett == "MSG_SM"))
        t1.setInformalname("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t1.getInformalname());
  .elif ( action.Key_Lett == "A_FJ" )
        t1.setGuardcondition("${oldName}");
        updateTreeItem(t1, "${newName}", ${useFocus});
        assertEquals("${newName}", t1.getGuardcondition());
  .elif ( action.Key_Lett == "A_DM" )
        t1.setDecisioninput("${oldName}");
        updateTreeItem(t1, "${newName}", ${useFocus});
        assertEquals("${newName}", t1.getDecisioninput());
  .elif ( action.Key_Lett == "A_E" )
        t1.setGuard("${oldName}");
        updateTreeItem(t1, "${newName}", ${useFocus});
        assertEquals("${newName}", t1.getGuard());
  .elif(action.Key_Lett == "CNST_LSC")
        SymbolicConstant_c t2 = SymbolicConstant_c.getOneCNST_SYCOnR1502(
            LeafSymbolicConstant_c.getOneCNST_LFSCOnR1503(t1));
        t2.setName("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t2.getName() );
  .else
        t1.setName("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t1.getName() );
  .end if
    }
.end function
.//
.//=====================================================================
.//
.// These are included in the rename with space test as they
.// are shown in model explorer.  At some point the canvas
.// rename code shall be exercised to test these elements.
.function isIncludedForSpace
  .param String kl
  .assign attr_result = false
  .if(kl == "MSG_A")
    .assign attr_result = true
  .elif(kl == "S_ENUM")
    .assign attr_result = true
  .elif(kl == "CNST_LSC")
    .assign attr_result = true
  .elif(kl == "S_SYNC")
    .assign attr_result = true
  .elif(kl == "S_SPARM")
    .assign attr_result = true
  .elif(kl == "S_BRG")
    .assign attr_result = true
  .elif(kl == "S_BPARM")
    .assign attr_result = true
  .elif(kl == "O_ATTR")
    .assign attr_result = true
  .elif(kl == "O_TFR")
    .assign attr_result = true
  .elif(kl == "O_TPARM")
    .assign attr_result = true
  .elif(kl == "SM_EVTDI")
    .assign attr_result = true
  .elif(kl == "S_MBR")
    .assign attr_result = true
  .elif(kl == "S_UDT")
    .assign attr_result = true
  .elif(kl == "S_EDT")
    .assign attr_result = true
  .elif(kl == "S_SDT")
    .assign attr_result = true
  .elif(kl == "CNST_SYC")
    .assign attr_result = true
  .elif(kl == "SM_SGEVT")
    .assign attr_result = true
  .end if
.end function
.//
.//=====================================================================
.//
.function create_rename_with_space_action_test
  .param inst_ref action   .// CME
  .param string useFocus
  .//
  .assign testModifier = ""
  .if (useFocus == "true")
    .assign testModifier = "usingFocus"
  .end if
    public void testRenameWithSpace${action.Key_Lett}${testModifier}() throws Exception
    {
  .select any owner from instances of O_OBJ where (selected.Key_Lett == action.Key_Lett)
  .invoke ocn = get_class_name(owner)
  .invoke oa = get_instance_accessor(owner)
  .assign root = "modelRoot"
  .if (action.Key_Lett == "S_SYS")
      .assign root = "Ooaofooa.getDefaultInstance()"
  .end if
        ${ocn.body} t1 = ${ocn.body}.${oa.body}(${root});
        assertNotNull(t1);
  .assign oldName = "Old_Name";
  .assign newName = "New Name";
  .if (((action.Key_Lett == "S_UDT") or (action.Key_Lett == "S_EDT")) or (action.Key_Lett == "S_SDT"))
        DataType_c t2 = DataType_c.getOneS_DTOnR17(t1);
        t2.setName("${oldName}");
        updateTreeItem( t1, "${newName}", ${useFocus} );
        assertEquals("${newName}", t2.getName() );
  .elif((action.Key_Lett == "CNST_LSC"))
        SymbolicConstant_c t2 = SymbolicConstant_c.getOneCNST_SYCOnR1502(
            LeafSymbolicConstant_c.getOneCNST_LFSCOnR1503(t1));
        t2.setName("${oldName}");

        TestUtil.dismissDialog(500);

        updateTreeItem( t1, "${newName}", ${useFocus} );
        
        assertEquals("${oldName}", t2.getName() );
  .elif ( action.Key_Lett == "O_ATTR" )
        t1.setRoot_nam("${oldName}");

        TestUtil.dismissDialog(500);

        updateTreeItem( t1, "${newName}", ${useFocus} );

        assertEquals("${oldName}", t1.getName() );
  .elif((action.Key_Lett == "MSG_A") or (action.Key_Lett == "SM_SGEVT"))
        t1.setInformalname("${oldName}");

        TestUtil.dismissDialog(500);

        updateTreeItem( t1, "${newName}", ${useFocus} );

        assertEquals("${oldName}", t1.getInformalname());
  .else
        t1.setName("${oldName}");

        TestUtil.dismissDialog(500);

        updateTreeItem( t1, "${newName}", ${useFocus} );
        
        assertEquals("${oldName}", t1.getName() );
  .end if
    }
.end function
.//
.//=====================================================================
.//
.function create_delete_action_test
  .param inst_ref action   .// CME
  .//
    public void testDelete${action.Key_Lett}() throws Exception
    {
  .select any owner from instances of O_OBJ where (selected.Key_Lett == action.Key_Lett)
  .invoke ocn = get_class_name(owner)
  .if ( action.Key_Lett == "S_SYS" )
        Ooaofooa mr = Ooaofooa.getDefaultInstance();
        ${ocn.body} [] before = ${ocn.body}.$cr{owner.Name}Instances(mr);
        ${ocn.body} t1 = new ${ocn.body}(mr);
        final String nameOfSystemToDelete = "toDelete";
        t1.setName(nameOfSystemToDelete);
        TestingUtilities.createProject(nameOfSystemToDelete);
  .elif(action.Key_Lett == "R_SUB")
        ${ocn.body} [] before = ${ocn.body}.$cr{owner.Name}Instances(modelRoot);
        DisposeTestGenerics dt = new DisposeTestGenerics();
        ${ocn.body} t1 = dt.createClassAsSubtype_c(null);
  .else
        ${ocn.body} [] before = ${ocn.body}.$cr{owner.Name}Instances(modelRoot);
        ${ocn.body} t1 = new ${ocn.body}(modelRoot);
  .end if
  .if ( (action.Key_Lett == "SM_ISM") or (action.Key_Lett == "SM_ASM" ) )
    .select any sm from instances of O_OBJ where ( selected.Key_Lett == "SM_SM" )
    .invoke smcn = get_class_name(sm)
        ${smcn.body} sm = new ${smcn.body}(modelRoot);
        sm.relateAcrossR517To(t1);
  .elif (( (action.Key_Lett == "S_EDT") or (action.Key_Lett == "S_UDT" ) ) or (action.Key_Lett == "S_SDT"))
    .select any dt from instances of O_OBJ where ( selected.Key_Lett == "S_DT" )
    .invoke dtcn = get_class_name(dt)
        ${dtcn.body} [] before_dt = ${dtcn.body}.$cr{dt.Name}Instances(modelRoot);
        ${dtcn.body} dt = new ${dtcn.body}(modelRoot);
        dt.relateAcrossR17To(t1);
  .elif ( action.Key_Lett == "S_SYNC" )
        t1.Initialize();
    .select any fp from instances of O_OBJ where ( selected.Key_Lett == "S_FPK" )
    .if ( not_empty fp )
      .invoke fpcn = get_class_name(fp)
        ${fpcn.body} fp = new ${fpcn.body}(modelRoot);
      .select any fip from instances of O_OBJ where ( selected.Key_Lett == "S_FIP" )
      .invoke fipcn = get_class_name(fip)
        ${fipcn.body} fip = new ${fipcn.body}(modelRoot);
        fip.relateAcrossR31To(t1);
        fip.relateAcrossR31To(fp);
    .end if
  .elif(action.Key_Lett == "SM_CRTXN")
        Transition_c transition = new Transition_c(modelRoot);
        transition.relateAcrossR507To(t1);
  .elif(action.Key_Lett == "SM_NLEVT")
    .select any evt from instances of O_OBJ where (selected.Key_Lett == "SM_EVT")
    .select any sevt from instances of O_OBJ where (selected.Key_Lett == "SM_SEVT")
    .invoke evtcn = get_class_name(evt)
    .invoke sevtcn = get_class_name(sevt)
    ${evtcn.body} evt = new ${evtcn.body}(modelRoot);
    ${sevtcn.body} sevt = new ${sevtcn.body}(modelRoot);
    evt.relateAcrossR525To(sevt);
    sevt.relateAcrossR526To(t1);
  .elif(action.Key_Lett == "CNST_LSC")
    SymbolicConstant_c syc = new SymbolicConstant_c(modelRoot);
    LeafSymbolicConstant_c lfsc = new LeafSymbolicConstant_c(modelRoot);
    syc.relateAcrossR1502To(lfsc);
    lfsc.relateAcrossR1503To(t1);
  .elif ( action.Key_Lett == "S_EE" )
    .select any eep from instances of O_OBJ where ( selected.Key_Lett == "S_EEPK" )
    .invoke eepcn = get_class_name(eep)
        ${eepcn.body} eep = new ${eepcn.body}(modelRoot);
    .select any eeip from instances of O_OBJ where ( selected.Key_Lett == "S_EEIP" )
    .invoke eeipcn = get_class_name(eeip)
        ${eeipcn.body} eeip = new ${eeipcn.body}(modelRoot);
        eeip.relateAcrossR33To(t1);
        eeip.relateAcrossR33To(eep);
  .elif ( action.Key_Lett == "SM_STATE" )
    .select any sm from instances of O_OBJ where (selected.Key_Lett == "SM_SM")
    .invoke smcn = get_class_name(sm)
        ${smcn.body} sm = new ${smcn.body}(modelRoot);
        t1.relateAcrossR501To(sm);
  .elif ( action.Key_Lett == "O_ATTR" )
     t1.Initialize();
    .select any battr from instances of O_OBJ where (selected.Key_Lett == "O_BATTR")
    .invoke bacn = get_class_name(battr)
        ${bacn.body} battr = new ${bacn.body}(modelRoot);
        t1.relateAcrossR106To(battr);
    .select any nbattr from instances of O_OBJ where (selected.Key_Lett == "O_NBATTR")
    .invoke nbacn = get_class_name(nbattr)
        ${nbacn.body} nbattr = new ${nbacn.body}(modelRoot);
        battr.relateAcrossR107To(nbattr);
  .elif (action.Key_Lett == "R_ASSR")
    .select any rgo from instances of O_OBJ where (selected.Key_Lett == "R_RGO")
    .invoke rgocn = get_class_name(rgo)
        ${rgocn.body} rgo = new ${rgocn.body}(modelRoot);
        t1.relateAcrossR205To(rgo);
    .select any oir from instances of O_OBJ where (selected.Key_Lett == "R_OIR")
    .invoke oircn = get_class_name(oir)
        ${oircn.body} oir = new ${oircn.body}(modelRoot);
        rgo.relateAcrossR203To(oir);
  .elif   (((action.Key_Lett == "C_AS")  or (action.Key_Lett == "S_BRG") )or((action.Key_Lett == "O_TFR")   or(action.Key_Lett == "COMM_LNK")  ))
    .if(action.Key_Lett == "C_AS")
        Interface_c iface = new Interface_c(modelRoot);
        iface.Newexecutableproperty(false);
        t1.relateAcrossR4004To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
    .end if
    .if(action.Key_Lett == "O_TFR")
        ModelClass_c clazz = new ModelClass_c(modelRoot);
        clazz.relateAcrossR115To(t1);
    .end if
  t1.Initialize();
  .elif ( (action.Key_Lett == "C_IO")or(action.Key_Lett == "C_PP"))  
  		SystemModel_c systemModel = new SystemModel_c(modelRoot);
		Interface_c iface = new Interface_c(modelRoot);
		iface.Newexecutableproperty(true);
		    .if(action.Key_Lett == "C_IO")
		    t1.relateAcrossR4008To(DataType_c.DataTypeInstances(modelRoot)[0]);
		    t1.relateAcrossR4004To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
		    t1.Initialize();
           .elif (action.Key_Lett == "C_PP")     
       		t1.relateAcrossR4006To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
       	    .end if          
  .end if
        StructuredSelection sel = new StructuredSelection(t1);
        Selection.getInstance().setSelection(sel, true);
        DeleteAction t2 = new DeleteAction(null);
  .if ( action.Key_Lett == "S_SYS" )
        TestUtil.dismissDialog(2000);
  .end if
        t2.run();

  .if ( action.Key_Lett == "S_SYS" )
        while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        TestUtil.sleep(500);
        ${ocn.body} [] after = ${ocn.body}.$cr{owner.Name}Instances(mr);
  .else
        ${ocn.body} [] after = ${ocn.body}.$cr{owner.Name}Instances(modelRoot);
  .end if
        assertEquals(before.length, after.length);
  .if (( (action.Key_Lett == "S_EDT") or (action.Key_Lett == "S_UDT" ) ) or (action.Key_Lett == "S_SDT"))
    .select any dt from instances of O_OBJ where ( selected.Key_Lett == "S_DT" )
    .invoke dtcn = get_class_name(dt)
        ${dtcn.body} [] after_dt = ${dtcn.body}.$cr{dt.Name}Instances(modelRoot);
        assertEquals(before_dt.length, after_dt.length);
  .end if
    }
.end function
.//
.//=====================================================================
.// Main code
.//
.assign path = "com/mentor/nucleus/bp/core/test"
.assign classname = "ActionTest"
.assign filename = "${path}/${classname}.java"
.//
package com.mentor.nucleus.bp.core.test;
//======================================================================
//
// File: ${filename}
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.73 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.test.CoreTest;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.ui.*;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;

public class ${classname} extends CoreTest
{
    private static boolean initialized = false;
    private Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
    protected void setUp() throws Exception
    {
        super.setUp();
        Ooaofooa.setPersistEnabled(false);
        Ooaofooa.setConsistencyEnabled(false);
        if ( !initialized )
        {
            modelRoot.clearDatabase(new NullProgressMonitor());
            Domain_c d2 = new Domain_c(modelRoot);
            DataType_c dt1 = new DataType_c(modelRoot);
            dt1.setName("void");
            dt1.relateAcrossR14To(d2);
            CoreDataType_c cdt1 = new CoreDataType_c(modelRoot);
            dt1.relateAcrossR17To(cdt1);

            DataType_c dt2 = new DataType_c(modelRoot);
            dt2.setName("integer");
            dt2.relateAcrossR14To(d2);
            CoreDataType_c cdt2 = new CoreDataType_c(modelRoot);
            dt2.relateAcrossR17To(cdt2);

.select any dpkg from instances of O_OBJ where (selected.Key_Lett == "S_DPK" )
.if ( not_empty dpkg )
            DataTypePackage_c dpk = new DataTypePackage_c(modelRoot);
            dpk.relateAcrossR40To(d2);
            dpk.setName(Ooaofooa.Getcoredatatypespackagename(modelRoot));
            DataTypeInPackage_c dip1 = new DataTypeInPackage_c(modelRoot);
            dpk.relateAcrossR39To(dip1);
            dip1.relateAcrossR39To(dt1);
            DataTypeInPackage_c dip2 = new DataTypeInPackage_c(modelRoot);
            dpk.relateAcrossR39To(dip2);
            dip2.relateAcrossR39To(dt2);

.end if
.select any eepkg from instances of O_OBJ where (selected.Key_Lett == "S_EEPK" )
.if ( not_empty eepkg )
            ExternalEntityPackage_c epk = new ExternalEntityPackage_c(modelRoot);
            epk.relateAcrossR36To(d2);
            epk.setName("External Entities");

.end if
.select any fpkg from instances of O_OBJ where (selected.Key_Lett == "S_FPK" )
.if ( not_empty fpkg )
            FunctionPackage_c fpk = new FunctionPackage_c(modelRoot);
            fpk.relateAcrossR29To(d2);
            fpk.setName("Functions");
.end if
            initialized = true;
        }
		UITestingUtilities.hidePropertyView();
    }

.select many new_action_set from instances of CME where ( selected.Specialism == "New" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Classes" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Components" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "External" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Interaction" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Activity" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Types" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Usecase" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if ((new_action.Key_Lett != "CP_CP") and (new_action.Key_Lett != "C_C"))
  .invoke cnat = create_new_action_test( new_action )
${cnat.body}\
.end if
.end for


.//
.select many delete_action_set from instances of CME where ( selected.Specialism == "Delete" )
.for each delete_action in delete_action_set
  .if (delete_action.Key_Lett == "S_SYS")

    // testDeleteS_SYS was removed, as it was redundant;
    // the functionality tested by it is also tested
    // as part of bp.ui.explorer.test.ExplorerTest.
    // testProjectDeleteFromModelExplorer();

  .elif (delete_action.Key_Lett == "S_DOM")

    // testDeleteS_DOM was removed, as it was redundant;
    // the functionality tested by it is also tested
    // as part of bp.ui.explorer.test.ExplorerTest.
    // testDomainDeleteFromModelExplorer();
  .elif ((((delete_action.Key_Lett == "SPR_PO") or (delete_action.Key_Lett == "SPR_PS")) or (delete_action.Key_Lett == "SPR_RO")) or (delete_action.Key_Lett == "SPR_RS"))
    // handle in /com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/DeleteProvidedAndRequiredSignalsAndOperations.java
  .else
  .invoke cdat = create_delete_action_test( delete_action )
${cdat.body}\
  .end if
.end for
.//
}
.emit to file "src/${filename}"
.//
.//====================================================================
.//
.//=====================================================================
.//
.function create_delete_action_test_generics
  .param inst_ref action   .// CME
  .//
    public void testDelete${action.Key_Lett}() throws Exception
    {
  .select any owner from instances of O_OBJ where (selected.Key_Lett == action.Key_Lett)
  .invoke ocn = get_class_name(owner)
  .if ( action.Key_Lett == "S_SYS" )
        Ooaofooa mr = Ooaofooa.getDefaultInstance();
        ${ocn.body} [] before = ${ocn.body}.$cr{owner.Name}Instances(mr);
        ${ocn.body} t1 = new ${ocn.body}(mr);
        final String nameOfSystemToDelete = "toDelete";
        t1.setName(nameOfSystemToDelete);
        TestingUtilities.createProject(nameOfSystemToDelete);
  .elif(action.Key_Lett == "R_SUB")
        ${ocn.body} [] before = ${ocn.body}.$cr{owner.Name}Instances(modelRoot);
        DisposeTestGenerics dt = new DisposeTestGenerics();
        ${ocn.body} t1 = dt.createClassAsSubtype_c(null);
  .else
        ${ocn.body} [] before = ${ocn.body}.$cr{owner.Name}Instances(modelRoot);
        ${ocn.body} t1 = new ${ocn.body}(modelRoot);
  .end if
  .if ( (action.Key_Lett == "SM_ISM") or (action.Key_Lett == "SM_ASM" ) )
    .select any sm from instances of O_OBJ where ( selected.Key_Lett == "SM_SM" )
    .invoke smcn = get_class_name(sm)
        ${smcn.body} sm = new ${smcn.body}(modelRoot);
        sm.relateAcrossR517To(t1);
  .elif (( (action.Key_Lett == "S_EDT") or (action.Key_Lett == "S_UDT" ) ) or (action.Key_Lett == "S_SDT"))
    .select any dt from instances of O_OBJ where ( selected.Key_Lett == "S_DT" )
    .invoke dtcn = get_class_name(dt)
        ${dtcn.body} [] before_dt = ${dtcn.body}.$cr{dt.Name}Instances(modelRoot);
        ${dtcn.body} dt = new ${dtcn.body}(modelRoot);
        dt.relateAcrossR17To(t1);
  .elif ( action.Key_Lett == "S_SYNC" )
 
		Package_c pkg = new Package_c(modelRoot);
		pkg.relateAcrossR1405To(m_sys);

		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8000To(pkg);

		t1.relateAcrossR8001To(pe);
        t1.Initialize();

  .elif(action.Key_Lett == "SM_CRTXN")
        Transition_c transition = new Transition_c(modelRoot);
        transition.relateAcrossR507To(t1);
  .elif(action.Key_Lett == "SM_NLEVT")
    .select any evt from instances of O_OBJ where (selected.Key_Lett == "SM_EVT")
    .select any sevt from instances of O_OBJ where (selected.Key_Lett == "SM_SEVT")
    .invoke evtcn = get_class_name(evt)
    .invoke sevtcn = get_class_name(sevt)
    ${evtcn.body} evt = new ${evtcn.body}(modelRoot);
    ${sevtcn.body} sevt = new ${sevtcn.body}(modelRoot);
    evt.relateAcrossR525To(sevt);
    sevt.relateAcrossR526To(t1);
  .elif(action.Key_Lett == "CNST_LSC")
    SymbolicConstant_c syc = new SymbolicConstant_c(modelRoot);
    LeafSymbolicConstant_c lfsc = new LeafSymbolicConstant_c(modelRoot);
    syc.relateAcrossR1502To(lfsc);
    lfsc.relateAcrossR1503To(t1);
  .elif ( action.Key_Lett == "S_EE" )
    .select any eep from instances of O_OBJ where ( selected.Key_Lett == "S_EEPK" )
    .invoke eepcn = get_class_name(eep)
        ${eepcn.body} eep = new ${eepcn.body}(modelRoot);
    .select any eeip from instances of O_OBJ where ( selected.Key_Lett == "S_EEIP" )
    .invoke eeipcn = get_class_name(eeip)
        ${eeipcn.body} eeip = new ${eeipcn.body}(modelRoot);
        eeip.relateAcrossR33To(t1);
        eeip.relateAcrossR33To(eep);
  .elif ( action.Key_Lett == "SM_STATE" )
    .select any sm from instances of O_OBJ where (selected.Key_Lett == "SM_SM")
    .invoke smcn = get_class_name(sm)
        ${smcn.body} sm = new ${smcn.body}(modelRoot);
        t1.relateAcrossR501To(sm);
  .elif ( action.Key_Lett == "O_ATTR" )
       Package_c pkg = new Package_c(modelRoot);
		pkg.relateAcrossR1405To(m_sys);
		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8000To(pkg);
		ModelClass_c mc = new ModelClass_c(modelRoot);
		pe.relateAcrossR8001To(mc);
		t1.relateAcrossR102To(mc);
		t1.Initialize();;
    .select any battr from instances of O_OBJ where (selected.Key_Lett == "O_BATTR")
    .invoke bacn = get_class_name(battr)
        ${bacn.body} battr = new ${bacn.body}(modelRoot);
        t1.relateAcrossR106To(battr);
    .select any nbattr from instances of O_OBJ where (selected.Key_Lett == "O_NBATTR")
    .invoke nbacn = get_class_name(nbattr)
        ${nbacn.body} nbattr = new ${nbacn.body}(modelRoot);
        battr.relateAcrossR107To(nbattr);
  .elif (action.Key_Lett == "R_ASSR")
    .select any rgo from instances of O_OBJ where (selected.Key_Lett == "R_RGO")
    .invoke rgocn = get_class_name(rgo)
        ${rgocn.body} rgo = new ${rgocn.body}(modelRoot);
        t1.relateAcrossR205To(rgo);
    .select any oir from instances of O_OBJ where (selected.Key_Lett == "R_OIR")
    .invoke oircn = get_class_name(oir)
        ${oircn.body} oir = new ${oircn.body}(modelRoot);
        rgo.relateAcrossR203To(oir);
        
  .elif (action.Key_Lett == "S_BRG")
  		ExternalEntity_c ee = new ExternalEntity_c(modelRoot);
		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(ee);
		Package_c pkg = new Package_c(modelRoot);
		pe.relateAcrossR8000To(pkg);
		pkg.relateAcrossR1405To(m_sys);
		t1.relateAcrossR19To(ee);
	    t1.Initialize();
	   
  .elif  (action.Key_Lett == "O_TFR")	 
        Package_c pkg = new Package_c(modelRoot);
		pkg.relateAcrossR1405To(m_sys);

		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8000To(pkg);

		ModelClass_c mc = new ModelClass_c(modelRoot);
		pe.relateAcrossR8001To(mc);

		t1.relateAcrossR115To(mc);
		t1.Initialize();
  .elif   ((action.Key_Lett == "C_AS")  or (action.Key_Lett == "COMM_LNK")  )
    .if(action.Key_Lett == "C_AS")
  Interface_c iface = new Interface_c(modelRoot);
  PackageableElement_c pe = new PackageableElement_c(modelRoot);
  iface.relateAcrossR8001To(pe);
  iface.Newexecutableproperty(false);
  t1.relateAcrossR4004To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
    .end if   
  t1.Initialize();
  .elif ( (action.Key_Lett == "C_IO")or(action.Key_Lett == "C_PP"))  
		SystemModel_c systemModel = new SystemModel_c(modelRoot);
		Package_c ifacepkg = Package_c.PackageInstance(modelRoot);
		ifacepkg.relateAcrossR1405To(systemModel);
		Interface_c iface = new Interface_c(modelRoot);
		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		iface.relateAcrossR8001To(pe);
		pe.relateAcrossR8000To(ifacepkg);
		iface.Newexecutableproperty(true);
		    .if(action.Key_Lett == "C_IO")
		      t1.relateAcrossR4008To(DataType_c.DataTypeInstances(SystemDatatypePackage_c.getOneSLD_SDPOnR4400(m_sys).getModelRoot())[0]);
	          t1.relateAcrossR4004To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
	          t1.Initialize();
           .elif (action.Key_Lett == "C_PP")     
       		t1.relateAcrossR4006To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
       	    .end if          
  .end if
        StructuredSelection sel = new StructuredSelection(t1);
        Selection.getInstance().setSelection(sel, true);
        DeleteAction t2 = new DeleteAction(null);
  .if ( action.Key_Lett == "S_SYS" )
        TestUtil.dismissDialog(2000);
  .end if
        t2.run();

  .if ( action.Key_Lett == "S_SYS" )
        while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        TestUtil.sleep(500);
        ${ocn.body} [] after = ${ocn.body}.$cr{owner.Name}Instances(mr);
  .else
        ${ocn.body} [] after = ${ocn.body}.$cr{owner.Name}Instances(modelRoot);
  .end if
        assertEquals(before.length, after.length);
  .if (( (action.Key_Lett == "S_EDT") or (action.Key_Lett == "S_UDT" ) ) or (action.Key_Lett == "S_SDT"))
    .select any dt from instances of O_OBJ where ( selected.Key_Lett == "S_DT" )
    .invoke dtcn = get_class_name(dt)
        ${dtcn.body} [] after_dt = ${dtcn.body}.$cr{dt.Name}Instances(modelRoot);
        assertEquals(before_dt.length, after_dt.length);
  .end if
    }
.end function
.//
.//=====================================================================
.//=====================================================================
.//
.function create_new_action_test_generics
  .param inst_ref action   .// CME
    public void test${action.Specialism}${action.Key_Lett}$r{action.Label}() throws Exception
    {
  .select any owner from instances of O_OBJ where (selected.Key_Lett == action.Key_Lett)
  .invoke ocn = get_class_name(owner)
  .if ( ((owner.Key_Lett == "S_DOM") or (owner.Key_Lett == "S_DPK")) or ((owner.Key_Lett == "S_FPK") or (owner.Key_Lett == "S_EEPK")) )
    .invoke oca = get_instance_accessor(owner)
        ${ocn.body} t1 = ${ocn.body}.${oca.body}(modelRoot);
  .elif ((owner.Key_Lett =="S_BRG")   or  (action.Key_Lett == "O_TFR"))   
  .elif (action.Key_Lett == "SM_EVT")
  .else
        ${ocn.body} t1 = new ${ocn.body}(modelRoot);
  .end if

  .if(((action.Key_Lett == "MSG_R") or (action.Key_Lett == "MSG_SM")) or (action.Key_Lett == "MSG_AM"))
        Message_c message = new Message_c(modelRoot);
        t1.relateAcrossR1018To(message);
  .elif ( (action.Label == "Event") or (action.Label == "State") )
    .select any sm from instances of O_OBJ where (selected.Key_Lett == "SM_SM")
    .invoke smcn = get_class_name(sm)
        ${smcn.body} sm = new ${smcn.body}(modelRoot);
        t1.relateAcrossR517To(sm);
    .if ( action.Label == "State" )
      .select any msm from instances of O_OBJ where (selected.Key_Lett == "SM_MOORE")
      .invoke msmcn = get_class_name(msm)
        ${msmcn.body} msm = new ${msmcn.body}(modelRoot);
        sm.relateAcrossR510To(msm);
    .end if
  .elif((action.Key_Lett == "C_IO") or (action.Key_Lett == "C_AS"))
        SystemModel_c systemModel = new SystemModel_c(modelRoot);
	    Package_c ifacepkg = Package_c.PackageInstance(modelRoot); 
       	ifacepkg.relateAcrossR1405To(systemModel);
        Interface_c iface = new Interface_c(modelRoot);
		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		iface.relateAcrossR8001To(pe);
		pe.relateAcrossR8000To(ifacepkg);
		iface.Newexecutableproperty(false);
		 .if (action.Key_Lett == "C_IO")
		 t1.relateAcrossR4008To(DataType_c.DataTypeInstances(SystemDatatypePackage_c.getOneSLD_SDPOnR4400(m_sys).getModelRoot())[0]);
	     .end if		
	 	t1.relateAcrossR4004To(ExecutableProperty_c.getOneC_EPOnR4003(iface));
	 	t1.Initialize();
	.elif (action.Key_Lett == "CNST_CSP")
	 	PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(t1);
		Package_c pkg = new Package_c(modelRoot);
		pe.relateAcrossR8000To(pkg);
		pkg.relateAcrossR1405To(m_sys);
   
   .elif (action.Key_Lett == "S_SYNC")
  		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(t1);
		Package_c pkg = new Package_c(modelRoot);
		pe.relateAcrossR8000To(pkg);
		pkg.relateAcrossR1405To(m_sys);
		t1.Initialize();	
		
	.elif (action.Key_Lett == "S_BRG")	
	    ExternalEntity_c ee = new ExternalEntity_c(modelRoot);
		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(ee);
		Package_c pkg = new Package_c(modelRoot);
		pe.relateAcrossR8000To(pkg);
		pkg.relateAcrossR1405To(m_sys);
		StructuredSelection selee = new StructuredSelection(ee);
		Selection.getInstance().setSelection(selee, true);
		NewBridgeOperationOnS_EEAction newBridgeAction = new NewBridgeOperationOnS_EEAction();
		Action aa = new Action() {
		};
		newBridgeAction.run(aa);
		Bridge_c t1= Bridge_c.BridgeInstance(modelRoot);
		assertNotNull(t1);
	    t1.Initialize();   
   .elif  (action.Key_Lett == "O_TFR") .//((action.Key_Lett == "S_BRG") or  (action.Key_Lett == "O_TFR") )      
		modelRoot.clearDatabase(new NullProgressMonitor());
		Package_c pkg = new Package_c(modelRoot);
		pkg.relateAcrossR1405To(m_sys);

		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8000To(pkg);

		ModelClass_c mc = new ModelClass_c(modelRoot);
		pe.relateAcrossR8001To(mc);

		StructuredSelection selmc = new StructuredSelection(mc);
		Selection.getInstance().setSelection(selmc, true);
		NewOperationOnO_OBJAction newOp = new NewOperationOnO_OBJAction();
		Action aa = new Action() {
		};
		newOp.run(aa);

		Operation_c t1 = Operation_c.OperationInstance(modelRoot);
		assertNotNull(t1);
	
		t1.Initialize();
		
  .elif (action.Key_Lett == "SM_EVT")
   		modelRoot.clearDatabase(new NullProgressMonitor());
		Package_c pkg = new Package_c(modelRoot);
		pkg.relateAcrossR1405To(m_sys);

		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8000To(pkg);

		ModelClass_c mc = new ModelClass_c(modelRoot);
		pe.relateAcrossR8001To(mc);
		ClassStateMachine_c csm = new ClassStateMachine_c(modelRoot);
		mc.relateAcrossR519To(csm);
		StateMachine_c sm = new StateMachine_c(modelRoot);
		csm.relateAcrossR517To(sm);
		StructuredSelection selcsm = new StructuredSelection(csm);
		Selection.getInstance().setSelection(selcsm, true);
		NewEventOnSM_ASMAction newEvt = new NewEventOnSM_ASMAction();
		Action aa = new Action() {
		};
		newEvt.run(aa);

		StateMachineEvent_c t1 = StateMachineEvent_c.StateMachineEventInstance(modelRoot);
		
  .end if
.//checking filter
   .invoke filter_res = call_filter_func(action, "True")
   .if (filter_res.body != "")
        ${filter_res.body}
   .end if
        StructuredSelection sel = new StructuredSelection(t1);
        Selection.getInstance().setSelection(sel, true);
  .invoke result = get_action_class_name(action)
        ${result.body} t2 = new ${result.body}();
        Action a = new Action() {};
        t2.run(a);

  .select any new_thing from instances of O_OBJ where (selected.Key_Lett == action.Resultant_Class)
  .invoke ntcn = get_class_name(new_thing)
  .invoke nta = get_instance_accessor(new_thing)
  .invoke filter_res = call_filter_func(action, "False")

  .if ( (action.Label == "Datatype Package") or ((action.Label == "Function Package") or (action.Label == "External Entity Package")) )
        ${ntcn.body} [] t3 = ${ntcn.body}.$cr{new_thing.Name}Instances(modelRoot);
        assertEquals(2, t3.length);
        t3[1].checkConsistency();
        .if (filter_res.body != "")
        ${filter_res.body}
        .end if
        t3[1].delete_unchecked();
  .elif ( action.Label == "Datatype" )
        ${ntcn.body} [] t3 = ${ntcn.body}.$cr{new_thing.Name}Instances(modelRoot);
        assertEquals(3, t3.length);
        t3[2].checkConsistency();
        .if (filter_res.body != "")
        ${filter_res.body}
        .end if
        t3[2].delete_unchecked();
  .else
        ${ntcn.body} t3 = ${ntcn.body}.${nta.body}(modelRoot);
        assertNotNull(t3);
        t3.checkConsistency();
        .if (filter_res.body != "")
        ${filter_res.body}
        .end if
        t3.delete_unchecked();
  .end if
  .if ( ((owner.Key_Lett != "S_DOM") and (owner.Key_Lett != "S_DPK")) and ((owner.Key_Lett != "S_FPK") and (owner.Key_Lett != "S_EEPK")) )
        t1.delete_unchecked();
  .end if
    }
.end function
.//
.//====================================================================
.//=====================================================================
.// Main code
.//
.assign path = "com/mentor/nucleus/bp/core/test"
.assign classname = "ActionTestGenerics"
.assign filename = "${path}/${classname}.java"
.//
package com.mentor.nucleus.bp.core.test;
//======================================================================
//
// File: ${filename}
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.73 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.test.CoreTest;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.ui.*;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;

public class ${classname} extends CoreTest
{
    private static boolean initialized = false;
    private Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
    protected void setUp() throws Exception
    {
        super.setUp();
        Ooaofooa.setPersistEnabled(false);
        Ooaofooa.setConsistencyEnabled(false);
		if (!initialized) {
			modelRoot.clearDatabase(new NullProgressMonitor());
			m_sys.Newsystemdtpkg();
			Package_c epk = new Package_c(modelRoot);
			initialized = true;
		}
    }

.select many new_action_set from instances of CME where ( selected.Specialism == "New" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")
.elif (new_action.Key_Lett ==  "S_DPK")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Classes" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Components" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "External" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Interaction" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Activity" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Types" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for

.select many new_action_set from instances of CME where ( selected.Specialism == "Usecase" )
.for each new_action in new_action_set
.// TODO: FIXME dts0100656073
.if   (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "C_C")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A")
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif (new_action.Key_Lett ==  "UC_UCC")

.else 
  .invoke cnat = create_new_action_test_generics( new_action )
${cnat.body}\
.end if
.end for


.//
.select many delete_action_set from instances of CME where ( selected.Specialism == "Delete" )
.for each delete_action in delete_action_set
  .if (delete_action.Key_Lett == "S_SYS")

    // testDeleteS_SYS was removed, as it was redundant;
    // the functionality tested by it is also tested
    // as part of bp.ui.explorer.test.ExplorerTest.
    // testProjectDeleteFromModelExplorer();

  .elif (delete_action.Key_Lett == "S_DOM")

    // testDeleteS_DOM was removed, as it was redundant;
    // the functionality tested by it is also tested
    // as part of bp.ui.explorer.test.ExplorerTest.
    // testDomainDeleteFromModelExplorer();

.elif (new_action.Key_Lett ==  "CP_CP")
.elif (new_action.Key_Lett ==  "S_DOM")
.elif (new_action.Key_Lett ==  "IP_IP")
.elif (new_action.Key_Lett ==  "S_FPK")
.elif (new_action.Key_Lett ==  "S_EEPK")
.elif (new_action.Key_Lett ==  "S_SS") 
.elif (new_action.Key_Lett ==  "SQ_S")
.elif (new_action.Key_Lett ==  "A_A") 
.elif (new_action.Key_Lett ==  "COMM_COMM")
.elif ((((delete_action.Key_Lett == "SPR_PO") or (delete_action.Key_Lett == "SPR_PS")) or (delete_action.Key_Lett == "SPR_RO")) or (delete_action.Key_Lett == "SPR_RS"))
.elif (new_action.Key_Lett ==  "UC_UCC")
  .else
  .invoke cdat = create_delete_action_test_generics( delete_action )
${cdat.body}\
  .end if
.end for
.//
}
.emit to file "src/${filename}"
.//======================================================================
.//======================================================================
.assign classname = "RenameTest"
.assign filename = "${path}/${classname}.java"
.//
package com.mentor.nucleus.bp.core.test;
//======================================================================
//
// File: ${filename}
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.73 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//

import java.io.FileInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.test.CoreTest;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.ui.*;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.io.mdl.ImportModel;
import com.mentor.nucleus.bp.ui.explorer.ExplorerTreeViewer;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;

public class ${classname} extends CoreTest
{
    private static boolean firstTime = true;
    private static PersistableModelComponent m_pmc = null;

    /**
     * The name of the main system and project manipulated during these tests.
     */
    private static final String mainSystemName = "testRename1";

    /**
     * Used by the ModelChangeListenersBatchingTest to reset the
     * project and modelRoot variables
     *
     * @throws CoreException
     */
    public void specialSetUp() throws CoreException {
        setupProject(mainSystemName);
        PersistableModelComponent pmc = ensureAvailableAndLoaded(
            mainSystemName, false, true);
        m_bp_tree.refresh();
        m_bp_tree.expandToLevel(pmc.getRootModelElement(),
            AbstractTreeViewer.ALL_LEVELS);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Ooaofooa.setPersistEnabled(false);
        Ooaofooa.setConsistencyEnabled(true);
        if (firstTime) {
            initialized = false;
            m_pmc = initialize(mainSystemName, false);
            firstTime = false;
            Display d = Display.getDefault();
            while (d.readAndDispatch());
        }
        m_bp_tree.refresh();
        m_bp_tree.expandToLevel(m_pmc.getRootModelElement(),
                AbstractTreeViewer.ALL_LEVELS);
    }

    private void updateTreeItem( Object t1, String newValue ) throws Exception
    {
      updateTreeItem(t1, newValue, false);
    }
    private void updateTreeItem( Object t1, String newValue, boolean useFocusChange ) throws Exception
    {
        StructuredSelection sel = new StructuredSelection(t1);
        Selection.getInstance().setSelection(sel, false);
        m_bp_tree.getTree().selectAll();
        TreeItem x [] = m_bp_tree.getTree().getSelection();
        assertNotNull( "Tree is empty", x );
        for ( int i = 0; i < x.length; ++i )
        {
            if ( x[i].getData() == t1 )
            {
                TreeItem [] x_set = { x[i] };
                m_bp_tree.getTree().setSelection(x_set);
                RenameAction t2 = (RenameAction)CorePlugin.getRenameAction(m_bp_tree);
                t2.run();
                t2.getTextEditor().setText(newValue);
                Event e = new Event();
                Display d = Display.getDefault();
                if (useFocusChange) {
                    if (i > 0) {
                      String oldName = x[i-1].getText();
                      TreeItem [] y_set = { x[i-1] };
                      m_bp_tree.getTree().setSelection(y_set);
                      e.type = SWT.FocusOut;
                      e.widget = t2.getTextEditor();
                      t2.getTextEditor().notifyListeners(e.type, e);
                      assertTrue( "Focus target name was corrupted", oldName.equals(x[i-1].getText()));
                    }
                  }
                  else {
                    e.type = SWT.Traverse;
                    e.detail = SWT.TRAVERSE_RETURN;
                    e.widget = t2.getTextEditor();
                    t2.getTextEditor().notifyListeners(e.type, e);
                  }
                while ( d.readAndDispatch() ) ;
                return;
            }
        }
        fail( "Tree item not found" );
    }

    public void testDatatypesPackageReadonly() throws Exception {
        DataTypePackage_c t1 = DataTypePackage_c.DataTypePackageInstance(modelRoot);
        assertNotNull(t1);
        assertEquals(t1.getName(), Ooaofooa.Getcoredatatypespackagename(modelRoot));
        StructuredSelection sel = new StructuredSelection(t1);
        Selection.getInstance().setSelection(sel, false);
        assertFalse(RenameAction.canRenameAction());
    }


.//
.select many rename_action_set from instances of CME where ( selected.Specialism == "Rename" )
.for each rename_action in rename_action_set
  .invoke isExcluded = isExcluded(rename_action.Key_Lett)
  .if(isExcluded.result == false)
    .invoke crat = create_rename_action_test( rename_action, "false" )
${crat.body}\
  .end if
  .// SYS rename by lost focus fails with a race condition,
  .// special casing this test out until we find the problem.
  .if (rename_action.Key_Lett != "S_SYS")
    .if(isExcluded.result == false)
      .invoke crat = create_rename_action_test( rename_action, "true" )
${crat.body}\
    .end if
  .end if
.end for

.for each rename_action in rename_action_set
  .invoke isIncluded = isIncludedForSpace(rename_action.Key_Lett)
  .if(isIncluded.result == true)
    .invoke crat = create_rename_with_space_action_test( rename_action, "false" )
${crat.body}\
  .end if
  .// SYS rename by lost focus fails with a race condition,
  .// special casing this test out until we find the problem.
  .if (rename_action.Key_Lett != "S_SYS")
    .if(isIncluded.result == true)
      .invoke crat = create_rename_with_space_action_test( rename_action, "true" )
${crat.body}\
    .end if
  .end if
.end for

    /**
     * Is created by testRenameS_SYSWithConflict() below and then also used later
     * by testRenameS_DOMWithInterProjectConflict().
     */
    private static final String nameOfExtraSystem = "extra";

    public void testRenameS_SYSWithConflict() throws Exception
    {
        // create an extra system besides the one created in setUp()
        // to ensure we have two present
        TestingUtilities.createProject(nameOfExtraSystem);

        SystemModel_c[] t1 = SystemModel_c.SystemModelInstances(Ooaofooa.getDefaultInstance());
        assertNotNull(t1);
        assertTrue(t1.length >= 2);
        String oldName = t1[0].getName();
        String newName = t1[1].getName();
        TestUtil.dismissDialog(2000);
        updateTreeItem( t1[0], newName );
        assertEquals(oldName, t1[0].getName() );
    }
    public void testRenameS_DOMWithConflict() throws Exception
    {
        // copy a second model into the main system, to ensure we have two
        // present; also, get it noticed by Eclipse
        Object[] dom_set = getMainSystem().getChildren();

        if (dom_set.length < 2) {
            IProject mainProject = CorePlugin.getWorkspace().getRoot().getProject(mainSystemName);
            ensureAvailableAndLoaded("Models", "small", mainProject, false, true);
            mainProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
        }

        dom_set = getDomainSet();
        Domain_c dom1 = (Domain_c)dom_set[0];
        Domain_c dom2 = (Domain_c)dom_set[1];
        String oldName = dom1.getName();
        String newName = dom2.getName();
        TestUtil.dismissDialog(2000);
        updateTreeItem( dom1, newName );
        assertEquals(oldName, dom1.getName() );
    }
    public void testRenameS_DOMWithInterProjectConflict() throws Exception
    {
        // copy a model into the extra system created previously so that
        // we can detm whether renaming a model in the main system to this copied
        // model's name is allowed; also, get the new model noticed by Eclipse
        String extraSystemModelName = "odms1";
        //recreate if not present.
        IProject extraProject = TestingUtilities.createProject(nameOfExtraSystem);
        ensureAvailableAndLoaded("Models", extraSystemModelName, extraProject, false, true);
        extraProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

        Object[] dom_set = getMainSystem().getChildren();
        Domain_c dom1 = (Domain_c)dom_set[0];
        String newName = extraSystemModelName;
        updateTreeItem( dom1, newName );
        assertEquals(newName, dom1.getName() );
    }

    private Object[] getDomainSet() {
        SystemModel_c sysMod = getMainSystem();
        assertNotNull(sysMod);
        Object [] dom_set = sysMod.getChildren();
        assertTrue(dom_set.length >= 2);

        // refresh the explorer tree in case any domains were loaded
        // from their proxies, above
        m_bp_tree.refresh();

        return dom_set;
    }

    /**
     * Returns the main system manipulated during these tests.
     */
    private SystemModel_c getMainSystem()
    {
        return SystemModel_c.SystemModelInstance(
            Ooaofooa.getDefaultInstance(),
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    SystemModel_c selected = (SystemModel_c) candidate;
                    return selected.getName().equals(mainSystemName);
}
            });
    }
}
.emit to file "src/${filename}"
.//=====================================================================
.//
.// These are excluded from the rename test as they are
.// not shown in model explorer.  At some point the
.// canvas rename code shall be exercised to test these
.// elements.
.function isExcludedInGenerics
  .param String kl
  .assign attr_result = false
  .if(kl == "MSG_R")
    .assign attr_result = true
  .elif(kl == "SQ_TM")
    .assign attr_result = true
  .elif(kl == "SQ_TS")
    .assign attr_result = true
  .elif(kl == "A_FJ")
    .assign attr_result = true
  .elif(kl == "A_E")
    .assign attr_result = true
  .elif(kl == "A_AP")
    .assign attr_result = true
  .elif(kl == "A_DM")
    .assign attr_result = true
  .// these tests are ignored for release 1.4.1
  .elif(kl == "CP_CP")
    .assign attr_result = true
  .elif(kl == "IP_IP")
    .assign attr_result = true
  .elif(kl == "C_C")
    .assign attr_result = true
  .elif(kl == "C_PO")
    .assign attr_result = true
  .elif(kl == "C_I")
    .assign attr_result = true
  .elif(kl == "C_P")
    .assign attr_result = true
  .elif(kl == "EP_PKG")
    .assign attr_result = true
  .elif(kl == "C_R")
    .assign attr_result = true
  .elif(kl == "C_AS")
    .assign attr_result = true
  .elif(kl == "C_IO")
    .assign attr_result = true
  .elif(kl == "C_PP")
    .assign attr_result = true
  .elif(kl == "PA_DIC")
    .assign attr_result = true
  .elif(kl == "PA_SIC")
    .assign attr_result = true
  .elif(kl == "C_PA_SICP")
    .assign attr_result = true
   .elif(kl == "SQ_PP") 
     .assign attr_result = true
   .elif(kl == "S_DOM") 
     .assign attr_result = true
   .elif(kl == "SQ_S") 
     .assign attr_result = true
   .elif(kl == "SQ_FPP") 
     .assign attr_result = true
   .elif(kl == "S_DPK") 
     .assign attr_result = true
   .elif(kl == "S_FPK") 
     .assign attr_result = true
   .elif(kl == "S_EEPK") 
     .assign attr_result = true
   .elif(kl == "S_EEPK") 
     .assign attr_result = true
   .elif(kl == "S_SS") 
     .assign attr_result = true
   .elif(kl == "COMM_COMM") 
     .assign attr_result = true
   .elif(kl == "A_A") 
     .assign attr_result = true
    .elif(kl == "UC_UCC") 
     .assign attr_result = true 
  .end if
.end function
.//
.//=====================================================================
.assign classname = "RenameTestGenerics"
.assign filename = "${path}/${classname}.java"
.//
package com.mentor.nucleus.bp.core.test;
//======================================================================
//
// File: ${filename}
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.73 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//

import java.io.FileInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.test.CoreTest;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.ui.*;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.io.mdl.ImportModel;
import com.mentor.nucleus.bp.ui.explorer.ExplorerTreeViewer;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;

public class ${classname} extends CoreTest
{
    private static boolean firstTime = true;
    private static PersistableModelComponent m_pmc = null;

    /**
     * The name of the main system and project manipulated during these tests.
     */
    private static final String mainSystemName = "testRename1";

    /**
     * Used by the ModelChangeListenersBatchingTest to reset the
     * project and modelRoot variables
     *
     * @throws CoreException
     */
    public void specialSetUp() throws CoreException {
		setupProject(mainSystemName);
		if (!initialized) {
			loadProject("testRename1");
			initialized = true;
			}
		PersistableModelComponent pmc = m_sys.getPersistableComponent();
		m_bp_tree.refresh();
		m_bp_tree.expandToLevel(pmc.getRootModelElement(),
				AbstractTreeViewer.ALL_LEVELS);
	
    }

    protected void setUp() throws Exception {
		super.setUp();
		Ooaofooa.setPersistEnabled(false);
		Ooaofooa.setConsistencyEnabled(true);
		if (!initialized) {

			loadProject("testRename1");
			initialized = true;
			m_pmc = m_sys.getPersistableComponent();

			Display d = Display.getDefault();
			while (d.readAndDispatch());
		}

		m_bp_tree.refresh();
		m_bp_tree.expandToLevel(m_pmc.getRootModelElement(),
				AbstractTreeViewer.ALL_LEVELS);
	
    }
    
    private void updateTreeItem( Object t1, String newValue, boolean useFocusChange ) throws Exception
    {
        StructuredSelection sel = new StructuredSelection(t1);
        Selection.getInstance().setSelection(sel, false);
        m_bp_tree.getTree().selectAll();
        TreeItem x [] = m_bp_tree.getTree().getSelection();
        assertNotNull( "Tree is empty", x );
        for ( int i = 0; i < x.length; ++i )
        {
            if ( x[i].getData() == t1 )
            {
                TreeItem [] x_set = { x[i] };
                m_bp_tree.getTree().setSelection(x_set);
                RenameAction t2 = (RenameAction)CorePlugin.getRenameAction(m_bp_tree);
                t2.run();
                t2.getTextEditor().setText(newValue);
                Event e = new Event();
                Display d = Display.getDefault();
                if (useFocusChange) {
                    if (i > 0) {
                      String oldName = x[i-1].getText();
                      TreeItem [] y_set = { x[i-1] };
                      m_bp_tree.getTree().setSelection(y_set);
                      e.type = SWT.FocusOut;
                      e.widget = t2.getTextEditor();
                      t2.getTextEditor().notifyListeners(e.type, e);
                      assertTrue( "Focus target name was corrupted", oldName.equals(x[i-1].getText()));
                    }
                  }
                  else {
                    e.type = SWT.Traverse;
                    e.detail = SWT.TRAVERSE_RETURN;
                    e.widget = t2.getTextEditor();
                    t2.getTextEditor().notifyListeners(e.type, e);
                  }
                while ( d.readAndDispatch() ) ;
                return;
            }
        }
        fail( "Tree item not found" );
    }

.//
.select many rename_action_set from instances of CME where ( selected.Specialism == "Rename" )
.for each rename_action in rename_action_set
  .invoke isExcluded = isExcludedInGenerics(rename_action.Key_Lett)
  .if(isExcluded.result == false)
    .invoke crat = create_rename_action_test( rename_action, "false" )
${crat.body}\
  .end if
  .// SYS rename by lost focus fails with a race condition,
  .// special casing this test out until we find the problem.
  .if (rename_action.Key_Lett != "S_SYS")
    .if(isExcluded.result == false)
      .invoke crat = create_rename_action_test( rename_action, "true" )
${crat.body}\
    .end if
  .end if
.end for

.for each rename_action in rename_action_set
  .invoke isIncluded = isIncludedForSpace(rename_action.Key_Lett)
  .if(isIncluded.result == true)
    .invoke crat = create_rename_with_space_action_test( rename_action, "false" )
${crat.body}\
  .end if
  .// SYS rename by lost focus fails with a race condition,
  .// special casing this test out until we find the problem.
  .if (rename_action.Key_Lett != "S_SYS")
    .if(isIncluded.result == true)
      .invoke crat = create_rename_with_space_action_test( rename_action, "true" )
${crat.body}\
    .end if
  .end if
.end for


}
.emit to file "src/${filename}"
///////////////////

.//
.//=====================================================================
.//=====================================================================
.//
.function create_handle_rename_test_generics
  .param inst_ref action   .// CME
    public void test${action.Specialism}${action.Key_Lett}$r{action.Label}() throws Exception
    {
          Package_c root= createRootPackge();
    	  .if (action.Key_Lett == "S_SYS")
	       	 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(root));
		     CanvasTestUtils.openDiagramEditor(root);
           .elif (action.Key_Lett == "C_I")
             Interface_c iface= createInterface(root);
		     Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(iface));
		     UITestingUtilities.clearGraphicalSelection();
		     UITestingUtilities.addElementToGraphicalSelection(iface);
		     .elif (action.Key_Lett == "S_EE")
             ExternalEntity_c ee= createExternalEntity(root);
		     Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(ee));
		     UITestingUtilities.clearGraphicalSelection();
		     UITestingUtilities.addElementToGraphicalSelection(ee);
		     .elif (action.Key_Lett == "S_EE")
             ExternalEntity_c ee= createExternalEntity(root);
		     Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(ee));
		     UITestingUtilities.clearGraphicalSelection();
		     UITestingUtilities.addElementToGraphicalSelection(ee);
		      .elif (action.Key_Lett == "C_C")
             Component_c comp= createRootComponent(root);
		     Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(comp));
		     UITestingUtilities.clearGraphicalSelection();
		     UITestingUtilities.addElementToGraphicalSelection(comp);
		     .elif (action.Key_Lett == "O_OBJ")
             ModelClass_c mdlClass= createModelClass(root);
		     Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(mdlClass));
		     UITestingUtilities.clearGraphicalSelection();
		     UITestingUtilities.addElementToGraphicalSelection(mdlClass);
		     .elif (action.Key_Lett == "SM_ISM") 
             ModelClass_c mdlClass= createModelClass(root);
             StateMachine_c sm = createStateMachine(mdlClass,InstanceStateMachine_c.class);
	         CanvasTestUtils.openInstanceStateDiagram((Ooaofooa)mdlClass.getModelRoot(), mdlClass.getName());
		     .elif (action.Key_Lett == "SM_ASM") 
             ModelClass_c mdlClass= createModelClass(root);
             StateMachine_c sm = createStateMachine(mdlClass,ClassStateMachine_c.class);
		     CanvasTestUtils.openDiagramEditor(ClassStateMachine_c.getOneSM_ASMOnR517(sm));
		    .elif (action.Key_Lett == "S_SYNC") 
             Function_c function = createFunction (root);
		     Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(function));
             .elif (action.Key_Lett == "S_BRG") 
             ExternalEntity_c ee = createExternalEntity(root);
		     Bridge_c bridgeOp = createBridgeOperation(ee); 
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(bridgeOp));
             .elif (action.Key_Lett == "MSG_SM") 
             SynchronousMessage_c msg = createSyncMessage(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(msg));
              .elif (action.Key_Lett == "MSG_AM") 
             AsynchronousMessage_c msg = createASyncMessage(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(msg));
		      .elif (action.Key_Lett == "MSG_R") 
             ReturnMessage_c msg = createReturnMessage(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(msg));  
		     .elif (action.Key_Lett == "SQ_CIP") 
             ClassInstanceParticipant_c instPart = createClassInstanceParticipant(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(instPart));  
		     .elif (action.Key_Lett == "SQ_CP") 
             ClassParticipant_c classPart = createClassParticipant(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(classPart));  
		    .elif (action.Key_Lett == "O_TFR") 
             ModelClass_c mdlClass = createModelClass(root);
       		 Operation_c op = createClassOperation(mdlClass);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(op));  
		    .elif (action.Key_Lett == "SM_EVT") 
             ModelClass_c mdlClass = createModelClass(root);
       		 StateMachine_c sm = createStateMachine(mdlClass,ClassStateMachine_c.class);
		     StateMachineEvent_c evt  = createSMEvent(sm);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(evt));  
		     .elif (action.Key_Lett == "S_SDT") 
             StructuredDataType_c sdt = createStructuredDataType(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(sdt)); 
		     .elif (action.Key_Lett == "S_EDT") 
             EnumerationDataType_c edt= createEnumDataType(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(edt)); 
		     .elif (action.Key_Lett == "CNST_CSP") 
             ConstantSpecification_c cdt =createConstDataType(root);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(cdt)); 
		      .elif (action.Key_Lett == "C_IO") 
             Interface_c iface= createInterface(root);
             InterfaceOperation_c op= creaInterfaceOperation (iface);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(op)); 
		      .elif (action.Key_Lett == "C_AS") 
             Interface_c iface= createInterface(root);
             InterfaceSignal_c signal= creInterfaceSignal (iface);
       		 Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(signal)); 
		    .else
		     UITestingUtilities.clearGraphicalSelection();
		     Selection.getInstance().clear();
		     Selection.getInstance().setSelection(new StructuredSelection(root));
		     CanvasTestUtils.openDiagramEditor(root);
		    .end if
		
    	CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, false);
     	setNameUsingRenameDialog(200, false, "OK","newElement");
    	MenuItem item = getMenuItem("${action.Specialism}", "${action.Label}");
    	UITestingUtilities.activateMenuItem(item);
    	TestUtil.sleepWithDispatchOfEvents(300);
    	TestingUtilities.allowJobCompletion();
    	.select any new_thing from instances of O_OBJ where (selected.Key_Lett == action.Resultant_Class)
        .invoke ntcn = get_class_name(new_thing)
       	NonRootModelElement recentElement = getTheMostRecentElement( ${ntcn.body}.class);
		
		.if (action.Label=="Event")
		 assertTrue(((StateMachineEvent_c) recentElement).getMning().equalsIgnoreCase("newElement"));
		 .elif (action.Key_Lett == "CNST_CSP")
		 assertTrue(SymbolicConstant_c.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c.getOneCNST_LFSCOnR1503((LiteralSymbolicConstant_c)recentElement)).getName().equalsIgnoreCase("newElement"));
		
		.else
		assertTrue(recentElement.getName().equalsIgnoreCase("newElement"));
		.end if
     	.assign unnamedElementName = "Unnamed";
	    .if (action.Label=="User DataType")
	    
	    .elif ((action.label=="Component") and (action.Specialism== "Interaction"))
	    .assign unnamedElementName = "Informal Component"
	    .elif ((action.label=="Class") and (action.Specialism== "Interaction"))
	    .assign unnamedElementName = "Informal Class"
	    .elif ((action.label=="External Entity") and (action.Specialism== "Interaction"))
	    .assign unnamedElementName = "Informal External Entity"
         .elif ((action.label=="Package Participant") and (action.Specialism== "Interaction"))
	    .assign unnamedElementName = "Informal Package"
	    .elif (action.Label=="Constant Specification")
	    .assign unnamedElementName = ""
	    .elif (((action.Key_Lett== "MSG_AM") or(action.Key_Lett == "MSG_SM") ) or (action.Key_Lett=="MSG_R") )
	    .assign unnamedElementName ="Informal Argument"
	    .elif ((action.Key_Lett== "SQ_CP") or(action.Key_Lett == "SQ_CIP"))  
	    .assign unnamedElementName ="Informal Attribute"
	   
	    .end if
	    
        CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
    	item = getMenuItem("${action.Specialism}", "${action.Label}");
    	UITestingUtilities.activateMenuItem(item);
    	recentElement = getTheMostRecentElement( ${ntcn.body}.class);
    	.if (action.Label=="Event")
		 assertTrue(((StateMachineEvent_c) recentElement).getMning().contains("${unnamedElementName}"));
		 .elif (action.Key_Lett == "CNST_CSP")
		 assertTrue(SymbolicConstant_c.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c.getOneCNST_LFSCOnR1503((LiteralSymbolicConstant_c)recentElement)).getName().contains("${unnamedElementName}"));
		
		.else
		assertTrue(recentElement.getName().contains("${unnamedElementName}"));
		.end if
       // TestingUtilities.allowJobCompletion();
    }
.end function
.//
.//====================================================================
.//=====================================================================



.//
.//====================================================================
.//=====================================================================
.// Main code
.//
.assign path = "com/mentor/nucleus/bp/core/test"
.assign classname = "HandleRenameTestGenerics"
.assign filename = "${path}/${classname}.java"
.//
package com.mentor.nucleus.bp.core.test;
//======================================================================
//
// File: ${filename}
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.73 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.handlers.WizardHandler.New;
import org.eclipse.ui.internal.progress.BlockedJobsDialog;

import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.test.CoreTest;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.ui.*;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;



public class ${classname} extends CoreTest
{
   private static boolean initialized = false;
	private static GraphicalEditor editor = null;
	private Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();

    protected void setUp() throws Exception {
	    super.setUp();
		Ooaofooa.setPersistEnabled(true);
		modelRoot.clearDatabase(new NullProgressMonitor());
		m_sys = ProjectUtilities.getSystemModel(project);
		CanvasTestUtils.openDiagramEditor(m_sys);
		editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
			
	}
   	protected void tearDown() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
		for (int i = 0; i < pkgs.length; i++) {
			pkgs[i].Dispose();
		}
		Ooaofooa[] mdlroots = Ooaofooa.getInstances();
		for (int i =0 ; i<mdlroots.length; i++)
		{
			mdlroots[i].delete();
			
		}
	CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
	} 
   private Package_c createRootPackge()
	{	
		 CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
		 StructuredSelection sel = new StructuredSelection(m_sys);
		 Selection.getInstance().setSelection(sel, true);
		 NewPackageOnS_SYSAction act = new NewPackageOnS_SYSAction();
		 Action a = new Action() {
			};
			act.run(a);
			Package_c root = Package_c.getOneEP_PKGOnR1401(m_sys);
			root.setName("Root");
			return root;	
	
	}
	private Operation_c createClassOperation(ModelClass_c mdlClass) {
		mdlClass.Newoperation();
		Operation_c op = Operation_c.getOneO_TFROnR115(mdlClass);
		return op;
	}
	private ModelClass_c createModelClass(Package_c pkg) {
		CanvasTestUtils.openCanvasEditor(pkg);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Classes", "Class");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		if (convertMouseCoor) {
			creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,
					model);
		}
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseRelease(creationPoint.x, creationPoint.y);
		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		ModelClass_c iroot = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg));
		return iroot;
	}
   private ExternalEntity_c createExternalEntity(Package_c pkg) {
		CanvasTestUtils.openCanvasEditor(pkg);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("External", "External Entity");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		if (convertMouseCoor) {
			creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,
					model);
		}
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseRelease(creationPoint.x, creationPoint.y);
		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		ExternalEntity_c iroot = ExternalEntity_c.getOneS_EEOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg));
		return iroot;
	}
	private StateMachine_c createStateMachine(ModelClass_c mdlClass,Class Type) {
		
		StructuredSelection sel = new StructuredSelection(mdlClass);
		Selection.getInstance().setSelection(sel, true);
		StateMachine_c sm = null;
		if (Type==InstanceStateMachine_c.class ){
		NewInstanceStateMachineOnO_OBJAction act = new NewInstanceStateMachineOnO_OBJAction();
		Action a = new Action() {
		};
		act.run(a);
	    sm= StateMachine_c.getOneSM_SMOnR517((InstanceStateMachine_c.getOneSM_ISMOnR518(mdlClass)));
		}
		else if (Type==ClassStateMachine_c.class ){
			NewClassStateMachineOnO_OBJAction act = new NewClassStateMachineOnO_OBJAction();
			Action a = new Action() {
			};
			act.run(a);
		}

		sm= StateMachine_c.getOneSM_SMOnR517((ClassStateMachine_c.getOneSM_ASMOnR519(mdlClass)));
		return sm;

	}
	private StateMachineEvent_c createSMEvent(StateMachine_c sm)
    {
      sm.Newevent();
      StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR502(sm);
       return evt;
     }
     StructuredDataType_c createStructuredDataType(Package_c pkg)
{
	pkg.Newstructureddatatype();
	StructuredDataType_c sdt = StructuredDataType_c.getOneS_SDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
	return sdt;
}
EnumerationDataType_c createEnumDataType(Package_c pkg)
{
	pkg.Newenumeration();
	EnumerationDataType_c edt = EnumerationDataType_c.getOneS_EDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
	return edt;
}
ConstantSpecification_c createConstDataType(Package_c pkg)
{
	pkg.Newconstantspecification();
	ConstantSpecification_c cdt = ConstantSpecification_c.getOneCNST_CSPOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
	return cdt;
}
	private Component_c createRootComponent(Package_c pkg) {
		if (pkg==null)
		{	
		 pkg = createRootPackge();
		}
		StructuredSelection sel = new StructuredSelection(pkg);
		Selection.getInstance().setSelection(sel, true);
		ComponentsComponentOnEP_PKGAction act = new ComponentsComponentOnEP_PKGAction();
		Action a = new Action() {
		};
		act.run(a);
		Component_c root = Component_c.getOneC_COnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg));
		root.setName("Root");
		return root;

	}
	private Interface_c createInterface(Package_c pkg) {
		CanvasTestUtils.openCanvasEditor(pkg);
		GraphicalEditor activeEditor =((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Components", "Interface");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(500,300);
		if (convertMouseCoor){
			creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint, model);
		}
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseRelease(creationPoint.x, creationPoint.y);
		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		Interface_c iroot = Interface_c.getOneC_IOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg));
		return iroot;
	}
	private Bridge_c createBridgeOperation(ExternalEntity_c ee) {
		ee.Newbridgeoperation();
		Bridge_c bridge= Bridge_c.getOneS_BRGOnR19(ee);
		return bridge;
	}
   private ClassInstanceParticipant_c createClassInstanceParticipant(Package_c pkg) {
		pkg.Newclassinstance();
		ClassInstanceParticipant_c classInstanceParticipant= ClassInstanceParticipant_c.getOneSQ_CIPOnR930(InteractionParticipant_c.getOneSQ_POnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
		return classInstanceParticipant;
	}
	
	private ClassParticipant_c createClassParticipant(Package_c pkg) {
		pkg.Newclassparticipant();
		ClassParticipant_c classParticipant = ClassParticipant_c.getOneSQ_CPOnR930( InteractionParticipant_c.getOneSQ_POnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
		return classParticipant;
	}
	InterfaceOperation_c creaInterfaceOperation (Interface_c iface)
 {
	 iface.Newexecutableproperty(false);
	InterfaceOperation_c op = InterfaceOperation_c.getOneC_IOOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
	 return op;
	 
 }
 InterfaceSignal_c creInterfaceSignal (Interface_c iface)
 {
	 iface.Newexecutableproperty(true);
	InterfaceSignal_c signal = InterfaceSignal_c.getOneC_ASOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
	 return signal;
 }
	private SynchronousMessage_c createSyncMessage(Package_c pkg) {
		CanvasTestUtils.openCanvasEditor(pkg);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Interaction", "Synchronous Message");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		org.eclipse.swt.graphics.Point destinationPoint = new org.eclipse.swt.graphics.Point(
				700, 500);		
	if (convertMouseCoor) {
		creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,model);
		destinationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,model);
					
	}
		CanvasUtilities.doMouseMove(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseMove(destinationPoint.x, destinationPoint.y);
		CanvasUtilities.doMouseRelease(destinationPoint.x, destinationPoint.y);
		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		SynchronousMessage_c msg = SynchronousMessage_c.getOneMSG_SMOnR1018(Message_c.getOneMSG_MOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg)));
		return msg;
	}
	private AsynchronousMessage_c createASyncMessage(Package_c pkg) {
		CanvasTestUtils.openCanvasEditor(pkg);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Interaction", "Asynchronous Message");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

				org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		org.eclipse.swt.graphics.Point destinationPoint = new org.eclipse.swt.graphics.Point(
				700, 500);		
	if (convertMouseCoor) {
		creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,model);
		destinationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,model);
					
	}
		CanvasUtilities.doMouseMove(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseMove(destinationPoint.x, destinationPoint.y);
		CanvasUtilities.doMouseRelease(destinationPoint.x, destinationPoint.y);

		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		AsynchronousMessage_c msg = AsynchronousMessage_c.getOneMSG_AMOnR1018(Message_c.getOneMSG_MOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg)));
		return msg;
	}
	private ReturnMessage_c createReturnMessage(Package_c pkg) {
		CanvasTestUtils.openCanvasEditor(pkg);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Interaction", "Return Message");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		org.eclipse.swt.graphics.Point destinationPoint = new org.eclipse.swt.graphics.Point(
				700, 500);		
	if (convertMouseCoor) {
		creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,model);
		destinationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,model);
					
	}
		CanvasUtilities.doMouseMove(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseMove(destinationPoint.x, destinationPoint.y);
		CanvasUtilities.doMouseRelease(destinationPoint.x, destinationPoint.y);

		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		ReturnMessage_c msg = ReturnMessage_c.getOneMSG_ROnR1018(Message_c.getOneMSG_MOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg)));
		return msg;
	}
	private Function_c createFunction(Package_c pkg) {
		if (pkg == null) {
			pkg = createRootPackge();
		}
		StructuredSelection sel = new StructuredSelection(pkg);
		Selection.getInstance().setSelection(sel, true);
		NewFunctionOnEP_PKGAction   act = new NewFunctionOnEP_PKGAction();
		Action a = new Action() {
		};
		act.run(a);
		Function_c root = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getOnePE_PEOnR8000(pkg));
		root.setName("Root");
		return root;

	}
		private static void setNameUsingRenameDialog(final long inHowManyMillis, 
	            final boolean shouldDismiss, final String button, final String elementName)
 {
		// run this on a separate thread, so that the dialog invocation to be
		// performed
		// by the caller may occur
		Thread dismissThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// wait to give the expected modal dialog time to get displayed
				// if the currently active shell is a dialog
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
						if (shell != null && shell.getData() instanceof Dialog) {
							// close the dialog
							if (!(shell.getData() instanceof ProgressMonitorDialog)
									&& !(shell.getData() instanceof BlockedJobsDialog)) {
								Control[] controlsFirstLevel = ((Dialog) shell.getData()).getShell().getChildren();
								Control[] controlsSecondLevel = ((Composite) controlsFirstLevel[0]).getChildren();
								Control[] controlsThirdLevel = ((Composite) controlsSecondLevel[0]).getChildren();
								Text elementNameText = ((Text) controlsThirdLevel[1]);
								elementNameText.setText(elementName);

								if (shouldDismiss) {
									((Dialog) shell.getData()).close();
								} else if (button != null) {
									Button foundButton = TestUtil.findButton(
											shell, button);
									foundButton.notifyListeners(SWT.Selection,
											null);
								} else {

									Control bb = ((Dialog) shell.getData()).buttonBar;
									Button cb = TestUtil.findButton(bb.getParent(), "Cancel");
									cb.notifyListeners(SWT.Selection, null);

								}
							}
						}

						else {
							Throwable t = new Throwable();
							t.setStackTrace(Thread.currentThread().getStackTrace());
							CorePlugin.logError("Failed to dismiss dialog", t);
						}
					}
				});
			}
		});
		dismissThread.start();
	}
	private NonRootModelElement getTheMostRecentElement(Class type) {
		List childrenList = new ArrayList();
		Ooaofooa[] modelroots = Ooaofooa.getInstances();
		for (int i = 0; i < modelroots.length; i++) {

			if ((modelroots[i].getInstanceList(type).size() != 0) && (modelroots[i]!=Ooaofooa.getDefaultInstance())){
				childrenList.addAll(modelroots[i].getInstanceList(type));
			}
		}
		Object[] children = childrenList.toArray();
		NonRootModelElement newElement = (NonRootModelElement) children[children.length - 1];
		return newElement;
	}
	private MenuItem getMenuItem(String parent, String child)
	{
		editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		Menu menu = null;
		if (child.equals("Parameter") || child.equals("Argument")||child.equals("Attribute")||child.equals("Member")||child.equals("Enumerator")||child.equals("Constant")) {
			menu = getExplorerView().getTreeViewer().getControl().getMenu();
		} else {

			menu = editor.getCanvas().getMenu();
		}
		MenuItem[] items = UITestingUtilities.getMenuItems(menu, "New");
		MenuItem item = null;
		for (int i = 0; items != null && i < items.length; i++) {
			if (items[i].getText().equals(parent)||items[i].getText().equals(child)) {
				item = items[i];
				break;
			}
		}
		MenuItem menuItem = null;
		if((item != null)&&(item.getMenu()!=null))
		{
			menuItem = UITestingUtilities.getMenuItem(item.getMenu(), child);
		}
		else if (item != null)
		{
			menuItem = item;
		}
		return menuItem;
	}

.select many new_action_set from instances of CME where (((((selected.Specialism == "Classes") or(selected.Specialism == "Components"))or ((selected.Specialism == "External")or(selected.Specialism == "Interaction")))or(((selected.Specialism == "New")or(selected.Specialism == "Activity"))or((selected.Specialism == "Types")or(selected.Specialism == "Usecase"))))and (selected.Resultant_Class_RelChain!=""))

.for each new_action in new_action_set

  .invoke cnat = create_handle_rename_test_generics( new_action )
${cnat.body}\

.end for
	public void testNoDialogForImportedClassFromPalette() {
		Package_c root = createRootPackge();
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(new StructuredSelection(root));
		CanvasTestUtils.openDiagramEditor(root);
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
				false);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Classes", "Imported Class");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		if (convertMouseCoor) {
			creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,
					model);
		}
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseRelease(creationPoint.x, creationPoint.y);
		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		ImportedClass_c iclass = ImportedClass_c.getOneO_IOBJOnR8001(PackageableElement_c.getOnePE_PEOnR8000(root));
		assertNotNull(iclass);
		
	}

	
	public void testNoDialogForComponentReferenceFromPalette() {
		Package_c root = createRootPackge();
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(new StructuredSelection(root));
		CanvasTestUtils.openDiagramEditor(root);
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
				false);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Components", "Component Reference");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		if (convertMouseCoor) {
			creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,
					model);
		}
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseRelease(creationPoint.x, creationPoint.y);
		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		ComponentReference_c compref = ComponentReference_c.getOneCL_ICOnR8001(PackageableElement_c.getOnePE_PEOnR8000(root));
		assertNotNull(compref);
		
	}
	public void testRenameDialogShowsWhenTheDefaultNameOptionIsSet() {
		Package_c root = createRootPackge();
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(new StructuredSelection(root));
		CanvasTestUtils.openDiagramEditor(root);
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
				true);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		AbstractTool tool = CanvasUtilities.getTool("Default Toolset", "Package");
		CanvasUtilities.activateTool(tool);
		boolean convertMouseCoor = true;
		Model_c model = activeEditor.getModel();

		org.eclipse.swt.graphics.Point creationPoint = new org.eclipse.swt.graphics.Point(
				500, 300);
		if (convertMouseCoor) {
			creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint,
					model);
		}
		CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
		CanvasUtilities.doMouseRelease(creationPoint.x, creationPoint.y);
		CanvasUtilities.deactivateTool(tool);
		activeEditor.zoomAll();
		Package_c pkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getOnePE_PEOnR8000(root));
		assertNotNull(pkg);
		assertEquals("Unnamed Package", pkg.getName());
		
		Selection.getInstance().clear();
		Selection.getInstance().setSelection(new StructuredSelection(pkg));

		setNameUsingRenameDialog(200, false, "OK", "Package One");
		editor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		
		MenuItem item =  UITestingUtilities.getMenuItem(editor.getCanvas().getMenu(), "Rename");
		UITestingUtilities.activateMenuItem(item);
		TestUtil.sleepWithDispatchOfEvents(300);
		TestingUtilities.allowJobCompletion();
		
		assertEquals("Package One",pkg.getName());

			
	}
	public void testCreatingComponentWithInvalidNameNotAffectFileSystem() throws IOException, InterruptedException, CoreException
	
	{  
	  
        // Turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
				false);
        String projectName = "VerifierFragmentExecutionTest";
        // Create the project contents
		IProject testProject = TestingUtilities.createProject(projectName);
		File sourceProject = new File(m_workspace_path + "../" + projectName);
		TestingUtilities.copyProjectContents(sourceProject, testProject);
		TestingUtilities.allowJobCompletion();
	 
		
	    SystemModel_c sys =	  getSystemModel(projectName); 
	    Package_c pkg =  Package_c.getOneEP_PKGOnR1401(sys,new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				Package_c selected =  (Package_c) candidate;
				return (selected.getName().equals("P1"));
			}
		});
	    
	    CanvasTestUtils.openDiagramEditor(pkg);
		GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
		
		String[] files = pkg.getFile().getRawLocation().removeLastSegments(1).toFile().list();
        int numberOfFilesBeforeNewElement =files.length;
		
		setNameUsingRenameDialog(200, false, "OK", "CP1");
		TestUtil.okToDialog(400);
        MenuItem item = getMenuItem("Components", "Component");
		UITestingUtilities.activateMenuItem(item);
		TestUtil.sleepWithDispatchOfEvents(300);
		TestingUtilities.allowJobCompletion();
 		
		
		files = pkg.getFile().getRawLocation().removeLastSegments(1).toFile().list();
		int numberOfFilesAfterNewElement = files.length;
		assertEquals(numberOfFilesBeforeNewElement, numberOfFilesAfterNewElement);
        testProject.delete(true, null);
	}
//dts0100911019.int 8.2
public void testCreatingComponentWithValidName() throws IOException, InterruptedException, CoreException
{  
    
    // Turn off autobuild to stop MC-3020 builders from running
	WorkspaceUtil.setAutobuilding(false);
	CorePlugin.getDefault().getPreferenceStore().setValue(
			BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
			false);
    String projectName = "VerifierFragmentExecutionTest";
    // Create the project contents
	IProject testProject = TestingUtilities.createProject(projectName);
	File sourceProject = new File(m_workspace_path + "../" + projectName);
	TestingUtilities.copyProjectContents(sourceProject, testProject);
	TestingUtilities.allowJobCompletion();
 
	
    SystemModel_c sys =	  getSystemModel(projectName); 
	    Package_c pkg =  Package_c.getOneEP_PKGOnR1401(sys,new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				Package_c selected =  (Package_c) candidate;
				return (selected.getName().equals("P1"));
			}
		});
		
    CanvasTestUtils.openDiagramEditor(pkg);
	GraphicalEditor activeEditor = ((ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getGraphicalEditor();
	
	String[] files = pkg.getFile().getRawLocation().removeLastSegments(1).toFile().list();
    int numberOfFilesBeforeNewElement =files.length;
	
	setNameUsingRenameDialog(200, false, "OK", "Cmp");
    MenuItem item = getMenuItem("Components", "Component");
	UITestingUtilities.activateMenuItem(item);
	TestUtil.sleepWithDispatchOfEvents(300);
	TestingUtilities.allowJobCompletion();
		
	
	files = pkg.getFile().getRawLocation().removeLastSegments(1).toFile().list();
	int numberOfFilesAfterNewElement = files.length;
	assertEquals(numberOfFilesBeforeNewElement+1, numberOfFilesAfterNewElement);
 

	File cmpFolder = pkg.getFile().getRawLocation().removeLastSegments(1).append("/Cmp").toFile();
	if(cmpFolder.exists())
	{
		files = cmpFolder.list();
		assertTrue(files[0].endsWith("Cmp.xtuml"));
	}
	else
	{
	  	assertTrue("Component with valid name was not created successfully",false);
	}
	testProject.delete(true, null);
}
}
.emit to file "src/${filename}"
