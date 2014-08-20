.//=====================================================================
.//
.// File:      $RCSfile: create_dispose_test.arc,v $
.// Version:   $Revision: 1.50 $
.// Modified:  $Date: 2013/01/10 22:49:22 $
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
.//
.//=====================================================================
.//
.function get_subtypes
  .param inst_ref obj  .// O_OBJ
  .//
  .select many attr_rel_set related by obj->R_OIR[R201]->R_RTO[R203]->R_OIR[R203]->R_REL[R201]->R_SUBSUP[R206]->R_REL[R206]
  .select many attr_result related by attr_rel_set->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201] where ( (selected.Key_Lett != "SM_MEALY") and (selected.Key_Lett != "SM_MEAH"))
.end function
.//
.//=====================================================================
.//
.function get_part_rels
  .param inst_ref obj  .// O_OBJ
  .//
  .assign attr_has_subtypes = false
  .// declare an empty part_rel_set
  .select many attr_result from instances of R_REL where ( selected.Rel_ID == 0 )
  .select many part_oir_set related by obj->R_OIR[R201]->R_RTO[R203]->R_OIR[R203]
  .for each oir in part_oir_set
    .select one rel related by oir->R_REL[R201]
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one form related by srel->R_FORM[R208]
      .if ( not_empty form )
        .// only add to set if this is the one side of a 1-M binary association
        .if ( form.Mult == 1 )
          .// assume there aren't any 1-M reflexive
          .assign attr_result = attr_result | rel
        .end if
      .end if
    .else
      .select one ssrel related by rel->R_SUBSUP[R206]
      .if ( not_empty ssrel )
        .assign attr_has_subtypes = true
        .assign attr_result = attr_result | rel
      .else
        .assign attr_result = attr_result | rel
      .end if
    .end if
  .end for
.end function
.//
.//=====================================================================
.//
.function get_form_rels
  .param inst_ref obj  .// O_OBJ
  .//
  .// declare an empty form_rel_set
  .select many attr_result from instances of R_REL where ( selected.Rel_ID == 0 )
  .select many form_oir_set related by obj->R_OIR[R201]->R_RGO[R203]->R_OIR[R203]
  .for each oir in form_oir_set
    .select one rel related by oir->R_REL[R201]
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one part related by srel->R_PART[R207]
      .select one form related by oir->R_RGO[R203]->R_FORM[R205]
      .if ( not_empty part )
        .// only add to set if this is a 1-1 association
        .if ( (part.Mult == 0) and (form.Mult == 0) )
          .if ( part.Obj_ID != form.Obj_ID )
            .// don't test reflexive relationships
            .assign attr_result = attr_result | rel
          .end if
        .else
          .select one part_obj related by part->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
          .if ( not_empty part_obj )
            .// only test if this is a relationship from a OAL to a non-OAL subsystem, or
            .// if the participant is a V_VAR or V_VAL,
            .// if the relationship is R506
            .if ( ((obj.Numb >= 600) and (part_obj.Numb < 600)) or (((part_obj.Key_Lett == "V_VAR") or (part_obj.Key_Lett == "V_VAL")) or (rel.Numb == 506)) )
              .if ( part.Obj_ID != form.Obj_ID )
                .// don't test reflexive relationships
                .assign attr_result = attr_result | rel
              .end if
            .end if
          .end if
        .end if
      .end if
    .else
      .select one ssrel related by rel->R_SUBSUP[R206]
      .if ( not_empty ssrel )
        .// sub/supertype is handled on the participant side
      .else
        .// associative relationships
        .assign attr_result = attr_result | rel
      .end if
    .end if
  .end for
.end function
.function ignore_rel_null_check
  .param inst_ref obj
  .param inst_ref rel
  .param String subKL
  .assign attr_result = false
  .if ( obj.Key_Lett == "C_I" )
    .if(rel.Numb == 4012)
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_IR" )
    .if(rel.Numb == 4701)
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_EP" )
    .if(rel.Numb == 4500)
      .assign attr_result = true
    .end if
    .if(rel.Numb == 4501)
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_C" )
    .if(rel.Numb == 4201)
      .assign attr_result = true
    .end if
   .elif ( obj.Key_Lett == "C_PO" )
    .if(rel.Numb == 4709)
      .assign attr_result = true
    .end if    
  .end if
.end function
.function ignore_rel_null_check_generics
  .param inst_ref obj
  .param inst_ref rel
  .param String subKL
  .assign attr_result = false
  .if ( obj.Key_Lett == "C_I" )
    .if(rel.Numb == 4012)
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_IR" )
    .if(rel.Numb == 4701)
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_EP" )
    .if(rel.Numb == 4500)
      .assign attr_result = true
    .end if
    .if(rel.Numb == 4501)
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_C" )
    .if(rel.Numb == 4201)
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_PO" )
    .if(rel.Numb == 4709)
      .assign attr_result = true
    .end if      
  .end if
.end function
.//
.//=====================================================================
.//
.function ignore_rel
  .param inst_ref obj  .// O_OBJ
  .param inst_ref rel  .// R_REL
  .param String subKL
  .//
  .assign attr_result = false
  .if ( obj.Key_Lett == "S_SS" )
    .if ( rel.Numb == 3 )
      .assign attr_result = true
    .elif ( rel.Numb == 4 )
      .assign attr_result = true
    .elif ( rel.Numb == 5 )
      .assign attr_result = true
    .elif ( rel.Numb == 6 )
      .assign attr_result = true
    .elif ( rel.Numb == 41 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "S_DOM" )
    .if ( rel.Numb == 1 )
      .assign attr_result = true
    .elif ( rel.Numb == 8 )
      .assign attr_result = true
    .elif ( rel.Numb == 14 )
      .assign attr_result = true
    .elif ( rel.Numb == 23 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "R_SIMP" )
    .if ( rel.Numb == 207 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "R_SUBSUP" )
    .if ( rel.Numb == 213 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "SM_SM" )
    .if ( rel.Numb == 501 )
      .// this case is handled by special case code
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "S_BRG" )
    .if ( rel.Numb == 1012 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "S_SYNC" )
    .if ( rel.Numb == 1010 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "SM_EVT" )
    .if ( rel.Numb == 1009 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "O_TFR" )
    .if ( rel.Numb == 1011 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "O_ATTR" )
    .if ( rel.Numb == 938 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "SQ_AV" )
    .if ( rel.Numb == 938 )
      .if(subKL == "SQ_IAV")
        .assign attr_result = true
      .end if
    .end if
  .elif ( obj.Key_Lett == "UC_UCC" )
    .if( rel.Numb == 1209 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_C" )
    .if((rel.Numb == 4000) or (rel.Numb == 4001))
      .assign attr_result = true
    .end if
    .if((rel.Numb == 4203) or (rel.Numb == 4202))
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "S_DPK" )
    .if((rel.Numb == 4401) or (rel.Numb == 4403))
      .assign attr_result = true
    .end if
  .end if
.end function
.//
.//====================================================================
.//
.function get_reflexive_phrase
  .param inst_ref rel  .// R_REL
  .param inst_ref rto  .// R_RTO
  .param boolean reverse
  .//
  .assign attr_result = ""
  .invoke is_refl = is_reflexive( rel )
  .if ( is_refl.result )
    .if(reverse)
      .select one part related by rel->R_SIMP[R206]->R_PART[R207]
      .if(not_empty part)
        .assign attr_result = "$cr{part.Txt_Phrs}"
      .else
        .select one aone related by rto->R_AONE[R204]
        .if (not_empty aone)
          .assign attr_result = "$cr{aone.Txt_Phrs}"
        .else
          .select one aoth related by rto->R_AOTH[R204]
          .assign attr_result = "$cr{aoth.Txt_Phrs}"
        .end if
      .end if
    .else
      .select one form related by rel->R_SIMP[R206]->R_FORM[R208]
      .if (not_empty form)
        .assign attr_result = "$cr{form.Txt_Phrs}"
      .else
        .select one aone related by rto->R_AONE[R204]
        .if (not_empty aone)
          .assign attr_result = "$cr{aone.Txt_Phrs}"
        .else
          .select one aoth related by rto->R_AOTH[R204]
          .assign attr_result = "$cr{aoth.Txt_Phrs}"
        .end if
      .end if 
    .end if
  .end if
.end function
.//
.//=====================================================================
.//
.function create_dispose_test
  .param inst_ref obj   .// O_OBJ
  .param string op   .// "Dispose" or "Clear"
  .param inst_ref_set part_rels  .// R_REL
  .param inst_ref_set form_rels  .// R_REL
  .param integer rel1
  .param string subobj1 .// if obj is a supertype, this is the first subtype to use
  .param integer rel2
  .param string subobj2 .// if obj is a supertype, this is the second subtype to use
  .//
  .invoke gcn = get_class_name( obj )
  .assign obj_classname = gcn.body
  .if ( obj.Key_Lett == "SM_ACT" )
      StateMachine_c sm = new StateMachine_c(modelRoot);
      MooreStateMachine_c msm = new MooreStateMachine_c(modelRoot);
      msm.relateAcrossR510To(sm);
      StateMachineState_c st = new StateMachineState_c(modelRoot);
      Action_c testInst = createAction_c(sm, st);
  .elif ( obj.Key_Lett == "CA_EESME" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMSME" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMEEE" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMOAA" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMEED" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif(obj.Key_Lett == "R_SUB")
      ${obj_classname} testInst = create${obj_classname}(null);
  .else
      ${obj_classname} testInst = new ${obj_classname}(modelRoot);
  .end if
  .if ( (obj.Key_Lett == "ACT_AT") or (obj.Key_Lett == "ACT_CR") )
      testInst.setIs_implicit(true);
  .end if
  .if ( (obj.Key_Lett == "SM_ISM") or (obj.Key_Lett == "SM_ASM") )
      StateMachine_c sm = new StateMachine_c(modelRoot);
      testInst.relateAcrossR517To(sm);
  .end if
  .if ( obj.Key_Lett == "SM_SM" )
    .// this instance needs to be created first
        // Rel R501
        StateMachineState_c testR501Inst = new StateMachineState_c(modelRoot);
        testR501Inst.relateAcrossR501To(testInst);
  .end if
      // Participant rels
  .for each rel in part_rels
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .invoke ir = ignore_rel( obj, rel, subobj1 )
      .if ( not ir.result )
        .select one form_obj related by srel->R_FORM[R208]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
        .invoke fcn = get_class_name( form_obj)
      // Rel R${rel.Numb}
        .if ( form_obj.Key_Lett == "O_ATTR" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "O_REF" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "S_DT" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "S_IRDT" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "S_DPK" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
        .elif ( form_obj.Key_Lett == "S_FPK" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
        .elif ( form_obj.Key_Lett == "S_EEPK" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
        .elif ( form_obj.Key_Lett == "R_OIR" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "SM_ACT" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst, testR501Inst);
        .elif ( form_obj.Key_Lett == "CA_EESMC" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEEC" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEEE" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMSMC" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEEA" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEED" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMOA" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "SM_NETXN" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .else
      ${fcn.body} testR${rel.Numb}Inst = new ${fcn.body}(modelRoot);
        .end if
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To(testInst);
      .if(obj.Key_Lett == "SQ_S")
        .// this association needs a sequence package
        .// to be created for the disposal to work
        .// as designed
        .if(rel.Numb == 911)
		Sequence_c newSequence = new Sequence_c(modelRoot);
		testR911Inst.relateAcrossR928To(newSequence);
        .end if
        .// we need to create a message for the test to work
        .if(rel.Numb == 953)
        Message_c message = new Message_c(modelRoot);
        message.relateAcrossR954To(testR953Inst);
        .end if
      .elif(obj.Key_Lett == "UC_UCC")
        .// we need to create a use case association to pass
        .if(rel.Numb == 1214)
        UseCaseAssociation_c uca = new UseCaseAssociation_c(modelRoot);
        uca.relateAcrossR1215To(testR1214Inst);
        .end if
      .elif(obj.Key_Lett == "C_AS")
        .// we need to create a MSG_M, MSG_AM
        .// to properly test here
        .if(rel.Numb == 1021)
		AsynchronousMessage_c asyncMessage = new AsynchronousMessage_c(modelRoot);
		Message_c message = new Message_c(modelRoot);
		testR1021Inst.relateAcrossR1019To(asyncMessage);
		asyncMessage.relateAcrossR1018To(message);
        .end if
      .elif(obj.Key_Lett == "C_IO")
        .// we need to create a MSG_SM, MSG_M
        .// to properly test here
        .if(rel.Numb == 1022)
		SynchronousMessage_c syncMessage = new SynchronousMessage_c(modelRoot);
		Message_c message = new Message_c(modelRoot);
		testR1022Inst.relateAcrossR1020To(syncMessage);
		message.relateAcrossR1018To(syncMessage);
        .end if
      .elif(obj.Key_Lett == "EP_PKG")
        .if(rel.Numb == 1403)
    Package_c nestedPackage = new Package_c(modelRoot);
    testR1403Inst.relateAcrossR1404To(nestedPackage);
         .elif(rel.Numb == 956)
        .// this association needs an interaction participant to be related to the package participant
        .//  for the disposal to work as designed  
        InteractionParticipant_c interactionParticipant = new InteractionParticipant_c(modelRoot);
		testR956Inst.relateAcrossR930To(interactionParticipant);
		PackageableElement_c pe = new PackageableElement_c(modelRoot);	
		interactionParticipant.relateAcrossR8001To(pe);
		Package_c pkg = new Package_c(modelRoot);
		pkg.relateAcrossR8000To(pe);            
        .end if
      .elif(obj.Key_Lett == "C_I")
        .if(rel.Numb == 4012)
    Provision_c pro = new Provision_c(modelRoot);
    pro.relateAcrossR4009To(testR${rel.Numb}Inst);
        .end if
      .elif(obj.Key_Lett == "COMM_COMM")
        .if(rel.Numb == 1130)
		Communication_c communication = new Communication_c(modelRoot);
		testR1130Inst.relateAcrossR1129To(communication);
        .end if
      .elif(obj.Key_Lett == "UC_UCC")
        .if(rel.Numb == 1208)
    UseCaseDiagram_c ucp = new UseCaseDiagram_c(modelRoot);
    testR1208Inst.relateAcrossR1209To(ucp);
        .end if
      .elif(obj.Key_Lett == "IP_IP")
        .if(rel.Numb == 4300)
		InterfacePackage_c diagram = new InterfacePackage_c(modelRoot);
		testR4300Inst.relateAcrossR4301To(diagram);
        .end if
      .elif(obj.Key_Lett == "CD_CD")
        .if(rel.Numb == 4202)
		ComponentDiagram_c diagram = new ComponentDiagram_c(modelRoot);
		testR4202Inst.relateAcrossR4203To(diagram);
        .end if
      .elif(obj.Key_Lett == "S_CDT")
        .if(rel.Numb == 1501)
        SymbolicConstant_c syc = new SymbolicConstant_c(modelRoot);
        LeafSymbolicConstant_c lfsc = new LeafSymbolicConstant_c(modelRoot);
        syc.relateAcrossR1502To(lfsc);
        lfsc.relateAcrossR1503To(testR1501Inst);
        .end if
      .end if
    .end if
  .else
      .select one ssrel related by rel->R_SUBSUP[R206]
      .if ( not_empty ssrel )
        .assign rel_numb = rel1
        .assign form_kl = subobj1
        .if ( rel.Numb == rel2 )
          .assign rel_numb = rel2
          .assign form_kl = subobj2
        .end if
        .select any form_obj from instances of O_OBJ where ( selected.Key_Lett == form_kl )
          .invoke fcn = get_class_name( form_obj)
      // Rel R${rel_numb} - supertype
          .if ( form_obj.Key_Lett == "SM_NSTXN" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
          .elif(form_obj.Key_Lett == "R_SUB")
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
          .elif(form_obj.Key_Lett == "SM_MOORE")
      ${fcn.body} testR${rel.Numb}Inst = ${fcn.body}.getOneSM_MOOREOnR510(testInst);
      if(testR${rel.Numb}Inst == null)
	      testR${rel.Numb}Inst = new ${fcn.body}(modelRoot);
          .else
      ${fcn.body} testR${rel_numb}Inst = new ${fcn.body}(modelRoot);
          .end if
      testR${rel_numb}Inst.relateAcrossR${rel_numb}To(testInst);
        .else
          .// associative relationships
          .select one arel related by rel->R_ASSOC[R206]
          .select one form_obj related by arel->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
          .invoke ir = ignore_rel( obj, rel, "")
          .if(not ir.result)
            .invoke fcn = get_class_name( form_obj)
      // Rel R${rel.Numb} - associative
            .if ( form_obj.Key_Lett == "SM_MOAH" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
            .elif ( form_obj.Key_Lett == "R_OIR" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
            .else
      ${fcn.body} testR${rel.Numb}Inst = new ${fcn.body}(modelRoot);
            .end if
            .select one the_part related by arel->R_AONE[R209]->R_RTO[R204]
            .invoke grf = get_reflexive_phrase( rel, the_part, false )
            .assign func_suffix = grf.result
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To${func_suffix}(testInst);
            .if ( the_part.Obj_ID == obj.Obj_ID )
              .// create and relate the 'other' instance
              .select one the_part related by arel->R_AOTH[R210]->R_RTO[R204]
            .end if
            .select one part_obj related by the_part->R_OIR[R203]->O_OBJ[R201]
            .invoke pcn = get_class_name( part_obj)
            .if ( part_obj.Key_Lett == "S_DT" )
      ${pcn.body} testR${rel.Numb}InstOth = create${pcn.body}();
            .elif ( part_obj.Key_Lett == "S_SS" )
      ${pcn.body} testR${rel.Numb}InstOth = create${pcn.body}(testInst);
            .else
      ${pcn.body} testR${rel.Numb}InstOth = new ${pcn.body}(modelRoot);
            .end if
            .invoke grf = get_reflexive_phrase( rel, the_part, false )
            .assign func_suffix = grf.result
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To${func_suffix}(testR${rel.Numb}InstOth);
            .// setup a package container for package linking associations
            .// as the disposal of the package linking associations is done
            .// through the package itself and not the domain
            .if(obj.Key_Lett == "S_DOM")
              .if(rel.Numb == 300)
		EePackageInPackage_c eepInPkg = new EePackageInPackage_c(modelRoot);
		testR36Inst.relateAcrossR34To(eepInPkg);
		eepInPkg.relateAcrossR35To(testR300InstOth);
	            .elif(rel.Numb == 301)
		FunctionPackageInPackage_c fpInPkg = new FunctionPackageInPackage_c(modelRoot);
		testR29Inst.relateAcrossR30To(fpInPkg);
		fpInPkg.relateAcrossR32To(testR301InstOth);
		          .end if
            .end if
          .end if
        .end if
      .end if
  .end for

      // Formalizer rels
  .for each rel in form_rels
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one part_obj related by srel->R_PART[R207]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
      .invoke ir = ignore_rel(obj, rel, subobj1)
      .invoke pcn = get_class_name( part_obj)
      .if(((part_obj.Key_Lett == "SM_ACT") and (rel.Numb == 514)) or (ir.result == true))
        .// ignore
      .else
      // Rel R${rel.Numb}
        .if ( part_obj.Key_Lett == "R_ASSOC" )
      ${pcn.body} testR${rel.Numb}Inst = create${pcn.body}(testInst);
        .elif ( part_obj.Key_Lett == "V_TRV" )
      ${pcn.body} testR${rel.Numb}Inst = create${pcn.body}();
        .elif ( part_obj.Key_Lett == "V_AVL" )
      ${pcn.body} testR${rel.Numb}Inst = create${pcn.body}();
        .else
      ${pcn.body} testR${rel.Numb}Inst = new ${pcn.body}(modelRoot);
        .end if
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To(testInst);
      .end if
    .else
      .// associative relationships
      .select one arel related by rel->R_ASSOC[R206]
      .select one one_part related by arel->R_AONE[R209]->R_RTO[R204]
      .select one oth_part related by arel->R_AOTH[R210]->R_RTO[R204]
      .select one part_obj related by one_part->R_OIR[R203]->O_OBJ[R201]
      .invoke pcn = get_class_name( part_obj)
      ${pcn.body} testR${rel.Numb}InstOne = new ${pcn.body}(modelRoot);
      .invoke grf = get_reflexive_phrase( rel, one_part, false )
      .assign func_suffix = grf.result
      testR${rel.Numb}InstOne.relateAcrossR${rel.Numb}To${func_suffix}(testInst);
      .select one part_obj related by oth_part->R_OIR[R203]->O_OBJ[R201]
      .invoke pcn = get_class_name( part_obj)
      ${pcn.body} testR${rel.Numb}InstOth = new ${pcn.body}(modelRoot);
      .invoke grf = get_reflexive_phrase( rel, oth_part, false )
      .assign func_suffix = grf.result
      testR${rel.Numb}InstOth.relateAcrossR${rel.Numb}To${func_suffix}(testInst);
    .end if
  .end for

      testInst.${op}();

      // Participant rels
  .for each rel in part_rels
    .invoke ir = ignore_rel_null_check( obj, rel, subobj1 )
    .if(not ir.result)
      // Rel R${rel.Numb}
      .select one srel related by rel->R_SIMP[R206]
      .if ( not_empty srel )
        .select one form_obj related by srel->R_FORM[R208]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
        .invoke gnfn = get_nav_func_name( form_obj, rel, "one" )
        .invoke fcn = get_class_name( form_obj)
      ${fcn.body} testR${rel.Numb}Inst2 = ${fcn.body}.${gnfn.body}(testInst);
        .// if the object being disposed is not a system, as
        .// a system's disposal does not (at least, on its own)
        .// cause the disposal of its child domains
        .if (not (obj.Key_Lett == "S_SYS"))
      assertNull ( testR${rel.Numb}Inst2 );
        .end if
      .else
        .select one ssrel related by rel->R_SUBSUP[R206]
        .if ( not_empty ssrel )
          .assign rel_numb = rel1
          .assign form_kl = subobj1
          .if ( rel.Numb == rel2 )
            .assign rel_numb = rel2
            .assign form_kl = subobj2
          .end if
          .select any form_obj from instances of O_OBJ where ( selected.Key_Lett == form_kl )
          .invoke fcn = get_class_name( form_obj)
          .invoke gnfn = get_nav_func_name( form_obj, rel, "one" )
      ${fcn.body} testR${rel_numb}Inst2 = ${fcn.body}.${gnfn.body}(testInst);
      assertNull ( testR${rel_numb}Inst2 );
        .else
          .// associative relationships
          .select one arel related by rel->R_ASSOC[R206]
          .select one form_obj related by arel->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
          .invoke fcn = get_class_name( form_obj)
          .invoke gnfn = get_nav_func_name( form_obj, rel, "one" )
          .select one rto related by arel->R_AONE[R209]->R_RTO[R204]
          .invoke grf = get_reflexive_phrase( rel, rto, false )
          .assign func_suffix = grf.result
      ${fcn.body} testR${rel.Numb}Inst2 = ${fcn.body}.${gnfn.body}${func_suffix}(testInst);
      assertNull ( testR${rel.Numb}Inst2 );
          .if (func_suffix != "")
            .// Its reflexive, test the other side too
            .select one rto related by arel->R_AOTH[R210]->R_RTO[R204]
            .invoke grf = get_reflexive_phrase( rel, rto, false )
            .assign func_suffix = grf.result
      testR${rel.Numb}Inst2 = ${fcn.body}.${gnfn.body}${func_suffix}(testInst);
      assertNull ( testR${rel.Numb}Inst2 );
          .end if
        .end if
      .end if
    .end if
  .end for

  .for each rel in form_rels
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one part_obj related by srel->R_PART[R207]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
      .invoke gnfn = get_nav_func_name( part_obj, rel, "one" )
      .invoke pcn = get_class_name( part_obj)
      ${pcn.body} testR${rel.Numb}Inst2 = ${pcn.body}.${gnfn.body}(testInst);
      assertNull ( testR${rel.Numb}Inst2 );
    .else
       .// TODO associative relationships
    .end if
  .end for

      testInst.delete();
.end function
.//
.//=====================================================================
.//
.function create_all_dispose_tests
  .param inst_ref obj   .// O_OBJ
  .param string op   .// "Dispose" or "Clear"
  .//
  .invoke gpr = get_part_rels(obj)
  .invoke gfr = get_form_rels(obj)
  .if ( gpr.has_subtypes )
    .invoke gs = get_subtypes( obj )
    .assign sub_set = gs.result
    .assign rel_set = gs.rel_set
    .if ( (cardinality rel_set) == 1 )
      .assign rel_num = -1
      .for each rel in rel_set
        .assign rel_num = rel.Numb
      .end for
      .for each sub in sub_set
    public void test$r{obj.Name}Dispose$r{sub.Name}() throws Exception
    {
  .invoke cdt = create_dispose_test( obj, op, gpr.result, gfr.result, rel_num, sub.Key_Lett, -1, "ERROR" )
${cdt.body}\
    }

      .end for
    .else
      .for each loop1 in rel_set
        .select many l1_sub_set related by loop1->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201] where ( (selected.Key_Lett != "SM_MEALY") and (selected.Key_Lett != "SM_MEAH"))
        .for each l1_sub in l1_sub_set
          .for each loop2 in rel_set
            .if ( loop2 != loop1 )
              .select many l2_sub_set related by loop2->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201] where ( (selected.Key_Lett != "SM_MEALY") and (selected.Key_Lett != "SM_MEAH"))
              .for each l2_sub in l2_sub_set
                .invoke l2fcn = get_class_name( l2_sub )
    public void test$r{obj.Name}Dispose$r{l1_sub.Name}$r{l2_sub.Name}() throws Exception
    {
  .invoke cdt = create_dispose_test( obj, op, gpr.result, gfr.result, loop1.Numb, l1_sub.Key_Lett, loop2.Numb, l2_sub.Key_Lett )
${cdt.body}\
    }

              .end for
            .end if
          .end for
        .end for
      .end for
    .end if
  .else
    public void test$r{obj.Name}Dispose() throws Exception
    {
  .// last arguments will be ignored
  .invoke cdt = create_dispose_test( obj, op, gpr.result, gfr.result, -1, "ERROR", -1, "ERROR" )
${cdt.body}\
    }

  .end if
.end function
.//
.//=====================================================================
.// Main code
.//
.assign path = "com/mentor/nucleus/bp/core/test"
.assign classname = "DisposeTest"
.assign filename = "${path}/${classname}.java"
.//
package com.mentor.nucleus.bp.core.test;
//======================================================================
//
// File: ${filename}
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.50 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//

import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.test.CoreTest;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

public class ${classname} extends CoreTest
{
    Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();

    /**
     * Whether this class's test fixture has been set up.
     */
    private static boolean fixtureSetUp = false;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        class DataType_test228_c implements ClassQueryInterface_c {
            public boolean evaluate(Object candidate) {
                DataType_c selected = (DataType_c) candidate;
                return (selected.getName().equals("integer"));
            }
        }
		Domain_c dom = Domain_c.DomainInstance(modelRoot);
		if (dom == null) {
			dom = new Domain_c(modelRoot);
		}
		DataType_c intDt = DataType_c.getOneS_DTOnR14(dom, new DataType_test228_c());
		if (intDt == null) {
			intDt = new DataType_c(modelRoot);
			intDt.setName("integer");
			intDt.relateAcrossR14To(dom);
		}
		intDt = DataType_c.getOneS_DTOnR14(dom, new DataType_test228_c());

        class DataType_test229_c implements ClassQueryInterface_c {
            public boolean evaluate(Object candidate) {
                DataType_c selected = (DataType_c) candidate;
                return (selected.getName().equals("void"));
            }
        }
		DataType_c voidDt = DataType_c.getOneS_DTOnR14(dom, new DataType_test229_c());
		if (voidDt == null) {
			voidDt = new DataType_c(modelRoot);
			voidDt.setName("void");
			voidDt.relateAcrossR14To(dom);
		}
		voidDt = DataType_c.getOneS_DTOnR14(dom, new DataType_test229_c());

        if (!fixtureSetUp) {
            // clear any data remaining in the model-root from previous test classes
            modelRoot.clearDatabase(new NullProgressMonitor());

            TestUtil.createMockDefaultDataTypes(modelRoot, null);

            fixtureSetUp = true;
        }
		Ooaofooa.setInUnitTest(true);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Ooaofooa.setInUnitTest(false);
	}
.select many dispose_set from instances of O_TFR where ( selected.Name == "dispose" )
.for each dispose_op in dispose_set
  .select one obj related by dispose_op->O_OBJ[R115]
  .invoke cadt = create_all_dispose_tests( obj, "Dispose" )
${cadt.body}\
.end for
    ClassAsSubtype_c createClassAsSubtype_c(ReferringClassInAssoc_c rcia) {
			ClassAsSubtype_c subtype = new ClassAsSubtype_c(modelRoot);
			ModelClass_c subtypeClass = new ModelClass_c(modelRoot);
			Association_c assoc = new Association_c(modelRoot);
			if(rcia == null)
				rcia = new ReferringClassInAssoc_c(modelRoot);
			ClassInAssociation_c cia = new ClassInAssociation_c(modelRoot);
			subtype.relateAcrossR205To(rcia);
			rcia.relateAcrossR203To(cia);
			cia.relateAcrossR201To(subtypeClass);
			ClassAsSupertype_c superType = new ClassAsSupertype_c(modelRoot);
			SubtypeSupertypeAssociation_c subsup = new SubtypeSupertypeAssociation_c(
					modelRoot);
			superType.relateAcrossR212To(subsup);
			subsup.relateAcrossR206To(assoc);
			subsup.relateAcrossR213To(subtype);
			ReferredToClassInAssoc_c rtcia = new ReferredToClassInAssoc_c(modelRoot);
			ClassInAssociation_c superCIA = new ClassInAssociation_c(modelRoot);
			ModelClass_c supertypeClass = new ModelClass_c(modelRoot);
			supertypeClass.relateAcrossR201To(superCIA);
			rtcia.relateAcrossR203To(superCIA);
			superType.relateAcrossR204To(rtcia);
			return subtype;
    }
    private Subsystem_c createSubsystem_c(Domain_c d)
    {
        Subsystem_c retval = new Subsystem_c(modelRoot);
        d.relateAcrossR1To(retval);
        return retval;
    }
    private DataType_c createDataType_c()
    {
        DataType_c retval = new DataType_c(modelRoot);
        EnumerationDataType_c edt = new EnumerationDataType_c(modelRoot);
        retval.relateAcrossR17To(edt);
        return retval;
    }
.select any dpkg from instances of O_OBJ where (selected.Key_Lett == "S_DPK" )
.if ( not_empty dpkg )
    private DataTypePackage_c createDataTypePackage_c(Domain_c d)
    {
        DataTypePackage_c retval = new DataTypePackage_c(modelRoot);
        DataType_c dt = createDataType_c();
        dt.relateAcrossR14To(d);
        DataTypeInPackage_c dip = new DataTypeInPackage_c(modelRoot);
        dip.relateAcrossR39To(dt);
        dip.relateAcrossR39To(retval);
        return retval;
    }
.end if
.select any fpkg from instances of O_OBJ where (selected.Key_Lett == "S_FPK" )
.if ( not_empty fpkg )
    private FunctionPackage_c createFunctionPackage_c(Domain_c d)
    {
        FunctionPackage_c retval = new FunctionPackage_c(modelRoot);
        Function_c f = new Function_c(modelRoot);
        f.relateAcrossR23To(d);
        FunctionInPackage_c fip = new FunctionInPackage_c(modelRoot);
        fip.relateAcrossR31To(f);
        fip.relateAcrossR31To(retval);
        return retval;
    }
.end if
.select any eepkg from instances of O_OBJ where (selected.Key_Lett == "S_EEPK" )
.if ( not_empty eepkg )
    private ExternalEntityPackage_c createExternalEntityPackage_c(Domain_c d)
    {
        ExternalEntityPackage_c retval = new ExternalEntityPackage_c(modelRoot);
        ExternalEntity_c ee = new ExternalEntity_c(modelRoot);
        ee.relateAcrossR8To(d);
        ExternalEntityInPackage_c eeip = new ExternalEntityInPackage_c(modelRoot);
        eeip.relateAcrossR33To(ee);
        eeip.relateAcrossR33To(retval);
        return retval;
    }
.end if
    private Attribute_c createAttribute_c()
    {
        Attribute_c retval = new Attribute_c(modelRoot);
        BaseAttribute_c battr = new BaseAttribute_c(modelRoot);
        retval.relateAcrossR106To(battr);
        NewBaseAttribute_c nbattr = new NewBaseAttribute_c(modelRoot);
        battr.relateAcrossR107To(nbattr);
        return retval;
    }
    private AttributeReferenceInClass_c createAttributeReferenceInClass_c()
    {
        AttributeReferenceInClass_c retval = new AttributeReferenceInClass_c(modelRoot);
        ReferringClassInAssoc_c rgo = new ReferringClassInAssoc_c(modelRoot);
        rgo.relateAcrossR111To(retval);
        ReferredToIdentifierAttribute_c rto = new ReferredToIdentifierAttribute_c(modelRoot);
        rto.relateAcrossR111To(retval);
        return retval;
    }
    private ClassInAssociation_c createClassInAssociation_c()
    {
        ClassInAssociation_c retval = new ClassInAssociation_c(modelRoot);
        ReferredToClassInAssoc_c rto = new ReferredToClassInAssoc_c(modelRoot);
        rto.relateAcrossR203To(retval);
        ClassAsSimpleParticipant_c part = new ClassAsSimpleParticipant_c(modelRoot);
        rto.relateAcrossR204To(part);
        return retval;
    }
    private LinkedAssociation_c createLinkedAssociation_c(Object inst)
    {
        LinkedAssociation_c retval = new LinkedAssociation_c(modelRoot);
        Association_c rel = new Association_c(modelRoot);
        retval.relateAcrossR206To(rel);
        if ( inst instanceof ClassAsAssociatedOneSide_c )
        {
            ClassAsAssociatedOtherSide_c oth = new ClassAsAssociatedOtherSide_c(modelRoot);
            retval.relateAcrossR210To(oth);
            ClassAsLink_c link = new ClassAsLink_c(modelRoot);
            retval.relateAcrossR211To(link);
        }
        else if ( inst instanceof ClassAsAssociatedOtherSide_c )
        {
            ClassAsAssociatedOneSide_c one = new ClassAsAssociatedOneSide_c(modelRoot);
            retval.relateAcrossR209To(one);
            ClassAsLink_c link = new ClassAsLink_c(modelRoot);
            retval.relateAcrossR211To(link);
        }
        else if ( inst instanceof ClassAsLink_c )
        {
            ReferringClassInAssoc_c rgo = new ReferringClassInAssoc_c(modelRoot);
            ((ClassAsLink_c)inst).relateAcrossR205To(rgo);
            ClassInAssociation_c oir = new ClassInAssociation_c(modelRoot);
            rgo.relateAcrossR203To(oir);
            ModelClass_c mc = new ModelClass_c(modelRoot);
            oir.relateAcrossR201To(mc);
            oir.relateAcrossR201To(rel);

            ClassAsAssociatedOneSide_c one = new ClassAsAssociatedOneSide_c(modelRoot);
            retval.relateAcrossR209To(one);
            ReferredToClassInAssoc_c rto1  = new ReferredToClassInAssoc_c(modelRoot);
            one.relateAcrossR204To(rto1);
            ClassInAssociation_c oir1 = new ClassInAssociation_c(modelRoot);
            rto1.relateAcrossR203To(oir1);
            ModelClass_c mc1 = new ModelClass_c(modelRoot);
            oir1.relateAcrossR201To(mc1);
            oir1.relateAcrossR201To(rel);

            ClassAsAssociatedOtherSide_c oth = new ClassAsAssociatedOtherSide_c(modelRoot);
            retval.relateAcrossR210To(oth);
            ReferredToClassInAssoc_c rto2  = new ReferredToClassInAssoc_c(modelRoot);
            oth.relateAcrossR204To(rto2);
            ClassInAssociation_c oir2 = new ClassInAssociation_c(modelRoot);
            rto2.relateAcrossR203To(oir2);
            ModelClass_c mc2 = new ModelClass_c(modelRoot);
            oir2.relateAcrossR201To(mc2);
            oir2.relateAcrossR201To(rel);
        }
        return retval;
    }
    private NewStateTransition_c createNewStateTransition_c()
    {
        NewStateTransition_c retval = new NewStateTransition_c(modelRoot);
        Transition_c t = new Transition_c(modelRoot);
        retval.relateAcrossR507To(t);
        StateEventMatrixEntry_c seme = new StateEventMatrixEntry_c(modelRoot);
        retval.relateAcrossR504To(seme);
        return retval;
    }
    private NoEventTransition_c createNoEventTransition_c()
    {
        NoEventTransition_c retval = new NoEventTransition_c(modelRoot);
        Transition_c t = new Transition_c(modelRoot);
        retval.relateAcrossR507To(t);
        return retval;
    }
    private MooreActionHome_c createMooreActionHome_c()
    {
        MooreActionHome_c retval = new MooreActionHome_c(modelRoot);

        Action_c a = new Action_c(modelRoot);
        ActionHome_c ah = new ActionHome_c(modelRoot);
        a.relateAcrossR514To(ah);
        ah.relateAcrossR513To(retval);
        return retval;
    }
    private Action_c createAction_c(StateMachine_c sm, StateMachineState_c st)
    {
        Action_c retval = new Action_c(modelRoot);
		ActionHome_c ah = new ActionHome_c(modelRoot);
        retval.relateAcrossR514To(ah);
        MooreStateMachine_c msm = MooreStateMachine_c.getOneSM_MOOREOnR510(sm);
        if(msm == null)
        	msm = new MooreStateMachine_c(modelRoot);
        if ( msm != null )
        {
            MooreActionHome_c moah = new MooreActionHome_c(modelRoot);
            ah.relateAcrossR513To(moah);
            moah.relateAcrossR511To(msm);
            moah.relateAcrossR511To(st);
        }
        return retval;
    }
    private OperationValue_c createOperationValue_c()
    {
        OperationValue_c retval = new OperationValue_c(modelRoot);
        Value_c v = new Value_c(modelRoot);
        v.relateAcrossR801To(retval);
        return retval;
    }
    private SmToEeAccessPath_c createSmToEeAccessPath_c()
    {
        SmToEeAccessPath_c retval = new SmToEeAccessPath_c(modelRoot);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(retval);
        return retval;
    }
    private SmToObjAccessPath_c createSmToObjAccessPath_c()
    {
        SmToObjAccessPath_c retval = new SmToObjAccessPath_c(modelRoot);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(retval);
        return retval;
    }
    private SmToObjAttributeAccess_c createSmToObjAttributeAccess_c()
    {
        SmToObjAttributeAccess_c retval = new SmToObjAttributeAccess_c(modelRoot);
        SmToObjAccessPath_c soap = new SmToObjAccessPath_c(modelRoot);
        soap.relateAcrossR418To(retval);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(soap);
        return retval;
    }
    private SmToEeDataItemAccess_c createSmToEeDataItemAccess_c()
    {
        SmToEeDataItemAccess_c retval = new SmToEeDataItemAccess_c(modelRoot);
        SmToEeAccessPath_c seap = new SmToEeAccessPath_c(modelRoot);
        seap.relateAcrossR422To(retval);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(seap);
        return retval;
    }
    private EeToSmCommPath_c createEeToSmCommPath_c()
    {
        EeToSmCommPath_c retval = new EeToSmCommPath_c(modelRoot);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(retval);
        return retval;
    }
    private SmToEeCommPath_c createSmToEeCommPath_c()
    {
        SmToEeCommPath_c retval = new SmToEeCommPath_c(modelRoot);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(retval);
        return retval;
    }
    private SmToSmCommPath_c createSmToSmCommPath_c()
    {
        SmToSmCommPath_c retval = new SmToSmCommPath_c(modelRoot);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(retval);
        return retval;
    }
    private SmToEeEventComm_c createSmToEeEventComm_c()
    {
        SmToEeEventComm_c retval = new SmToEeEventComm_c(modelRoot);
        SmToEeCommPath_c smeecp = new SmToEeCommPath_c(modelRoot);
        smeecp.relateAcrossR412To(retval);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(smeecp);
        return retval;
    }
    private SmToSmEventComm_c createSmToSmEventComm_c()
    {
        SmToSmEventComm_c retval = new SmToSmEventComm_c(modelRoot);
        SmToSmCommPath_c smsmcp = new SmToSmCommPath_c(modelRoot);
        smsmcp.relateAcrossR408To(retval);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(smsmcp);
        return retval;
    }
    private EeToSmEventComm_c createEeToSmEventComm_c()
    {
        EeToSmEventComm_c retval = new EeToSmEventComm_c(modelRoot);
        EeToSmCommPath_c eesmcp = new EeToSmCommPath_c(modelRoot);
        eesmcp.relateAcrossR404To(retval);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(eesmcp);
        return retval;
    }
	private AttributeValueReference_c createAttributeValueReference_c() {
		AttributeValueReference_c retval = new AttributeValueReference_c(
				modelRoot);
		Value_c v = new Value_c(modelRoot);
		v.relateAcrossR801To(retval);
		Attribute_c a = new Attribute_c(modelRoot);
		v.relateAcrossR801To(retval);
		return retval;
	}
	private InstanceReferenceDataType_c createInstanceReferenceDataType_c() {
		InstanceReferenceDataType_c retval = new InstanceReferenceDataType_c(
				modelRoot);
		DataType_c dt = new DataType_c(modelRoot);
		dt.relateAcrossR17To(retval);
		return retval;
    }
}
.emit to file "src/${filename}"
.//===================================================================== 
.// create Dispose test for Generics
.//=====================================================================
.//
.//=====================================================================
.//
.function get_part_rels_generics
  .param inst_ref obj  .// O_OBJ
  .//
  .assign attr_has_subtypes = false
  .// declare an empty part_rel_set
  .select many attr_result from instances of R_REL where ( selected.Rel_ID == 0 )
  .select many part_oir_set related by obj->R_OIR[R201]->R_RTO[R203]->R_OIR[R203]
  .for each oir in part_oir_set
    .select one rel related by oir->R_REL[R201]
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one form related by srel->R_FORM[R208]
      .if ( not_empty form )
        .// only add to set if this is the one side of a 1-M binary association
        .if ( form.Mult == 1 )
          .// assume there aren't any 1-M reflexive
          .assign attr_result = attr_result | rel
        .end if
      .end if
    .else
      .select one ssrel related by rel->R_SUBSUP[R206]
      .if ( not_empty ssrel )
        .assign attr_has_subtypes = true
        .assign attr_result = attr_result | rel
      .else
        .assign attr_result = attr_result | rel
      .end if
    .end if
  .end for
.end function
.//
.//=====================================================================
.//
.function get_form_rels_generics
  .param inst_ref obj  .// O_OBJ
  .//
  .// declare an empty form_rel_set
  .select many attr_result from instances of R_REL where ( selected.Rel_ID == 0 )
  .select many form_oir_set related by obj->R_OIR[R201]->R_RGO[R203]->R_OIR[R203]
  .for each oir in form_oir_set
    .select one rel related by oir->R_REL[R201]
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one part related by srel->R_PART[R207]
      .select one form related by oir->R_RGO[R203]->R_FORM[R205]
      .if ( not_empty part )
        .// only add to set if this is a 1-1 association
        .if ( (part.Mult == 0) and (form.Mult == 0) )
          .if ( part.Obj_ID != form.Obj_ID )
            .// don't test reflexive relationships
            .assign attr_result = attr_result | rel
          .end if
        .else
          .select one part_obj related by part->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
          .if ( not_empty part_obj )
            .// only test if this is a relationship from a OAL to a non-OAL subsystem, or
            .// if the participant is a V_VAR or V_VAL,
            .// if the relationship is R506
            .if ( ((obj.Numb >= 600) and (part_obj.Numb < 600)) or (((part_obj.Key_Lett == "V_VAR") or (part_obj.Key_Lett == "V_VAL")) or (rel.Numb == 506)) )
              .if ( part.Obj_ID != form.Obj_ID )
                .// don't test reflexive relationships
                .assign attr_result = attr_result | rel
              .end if
            .end if
          .end if
        .end if
      .end if
    .else
      .select one ssrel related by rel->R_SUBSUP[R206]
      .if ( not_empty ssrel )
        .// sub/supertype is handled on the participant side
      .else
        .// associative relationships
        .assign attr_result = attr_result | rel
      .end if
    .end if
  .end for
.end function
.//
.//=====================================================================
.//
.function ignore_rel_generics
  .param inst_ref obj  .// O_OBJ
  .param inst_ref rel  .// R_REL
  .param String subKL
  .//
  .assign attr_result = false
  .if ( obj.Key_Lett == "S_SS" )
    .if ( rel.Numb == 3 )
      .assign attr_result = true
    .elif ( rel.Numb == 4 )
      .assign attr_result = true
    .elif ( rel.Numb == 5 )
      .assign attr_result = true
    .elif ( rel.Numb == 6 )
      .assign attr_result = true
    .elif ( rel.Numb == 41 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "S_DOM" )
    .if ( rel.Numb == 1 )
      .assign attr_result = true
    .elif ( rel.Numb == 8 )
      .assign attr_result = true
    .elif ( rel.Numb == 14 )
      .assign attr_result = true
    .elif ( rel.Numb == 23 )
      .assign attr_result = true
    .end if 
  .elif ( obj.Key_Lett == "R_SIMP" )
    .if ( rel.Numb == 207 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "R_SUBSUP" )
    .if ( rel.Numb == 213 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "SM_SM" )
    .if ( rel.Numb == 501 )
      .// this case is handled by special case code
      .assign attr_result = true 
    .end if 
  .elif ( obj.Key_Lett == "S_BRG" ) 
    .if ( rel.Numb == 1012 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "S_SYNC" )
    .if ( rel.Numb == 1010 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "SM_EVT" )
    .if ( rel.Numb == 1009 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "O_TFR" )
    .if ( rel.Numb == 1011 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "O_ATTR" )
    .if ( rel.Numb == 938 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "SQ_AV" )
    .if ( rel.Numb == 938 )
      .if(subKL == "SQ_IAV")
        .assign attr_result = true
      .end if
    .end if
  .elif ( obj.Key_Lett == "UC_UCC" )
    .if( rel.Numb == 1209 )
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "C_C" )
    .if((rel.Numb == 4000) or (rel.Numb == 4001))
      .assign attr_result = true
    .end if
    .if((rel.Numb == 4203) or (rel.Numb == 4202))
      .assign attr_result = true
    .end if
  .elif ( obj.Key_Lett == "S_DPK" )
    .if((rel.Numb == 4401) or (rel.Numb == 4403))
      .assign attr_result = true
    .end if
  .end if
.end function
.//
.//====================================================================
.//
.function get_reflexive_phrase_generics
  .param inst_ref rel  .// R_REL
  .param inst_ref rto  .// R_RTO
  .param boolean reverse
  .//
  .assign attr_result = ""
  .invoke is_refl = is_reflexive( rel )
  .if ( is_refl.result )
    .if(reverse)
      .select one part related by rel->R_SIMP[R206]->R_PART[R207]
      .if(not_empty part)
        .assign attr_result = "$cr{part.Txt_Phrs}"
      .else
        .select one aone related by rto->R_AONE[R204]
        .if (not_empty aone)
          .assign attr_result = "$cr{aone.Txt_Phrs}"
        .else
          .select one aoth related by rto->R_AOTH[R204]
          .assign attr_result = "$cr{aoth.Txt_Phrs}"
        .end if
      .end if
    .else
      .select one form related by rel->R_SIMP[R206]->R_FORM[R208]
      .if (not_empty form)
        .assign attr_result = "$cr{form.Txt_Phrs}"
      .else
        .select one aone related by rto->R_AONE[R204]
        .if (not_empty aone)
          .assign attr_result = "$cr{aone.Txt_Phrs}"
        .else
          .select one aoth related by rto->R_AOTH[R204]
          .assign attr_result = "$cr{aoth.Txt_Phrs}"
        .end if
      .end if 
    .end if
  .end if
.end function
.//
.//=====================================================================
.//
.function create_dispose_test_generics
  .param inst_ref obj   .// O_OBJ
  .param string op   .// "Dispose" or "Clear"
  .param inst_ref_set part_rels  .// R_REL
  .param inst_ref_set form_rels  .// R_REL
  .param integer rel1
  .param string subobj1 .// if obj is a supertype, this is the first subtype to use
  .param integer rel2
  .param string subobj2 .// if obj is a supertype, this is the second subtype to use
  .//
  .invoke gcn = get_class_name( obj )
  .assign obj_classname = gcn.body
  .if ( obj.Key_Lett == "SM_ACT" )
      StateMachine_c sm = new StateMachine_c(modelRoot);
      MooreStateMachine_c msm = new MooreStateMachine_c(modelRoot);
      msm.relateAcrossR510To(sm);
      StateMachineState_c st = new StateMachineState_c(modelRoot);
      Action_c testInst = createAction_c(sm, st);
  .elif ( obj.Key_Lett == "CA_EESME" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMSME" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMEEE" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMOAA" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif ( obj.Key_Lett == "CA_SMEED" )
      ${obj_classname} testInst = create${obj_classname}();
  .elif(obj.Key_Lett == "R_SUB")
      ${obj_classname} testInst = create${obj_classname}(null);
  .else
      ${obj_classname} testInst = new ${obj_classname}(modelRoot);
  .end if
  .if ( (obj.Key_Lett == "ACT_AT") or (obj.Key_Lett == "ACT_CR") )
      testInst.setIs_implicit(true);
  .end if
  .if ( (obj.Key_Lett == "SM_ISM") or (obj.Key_Lett == "SM_ASM") )
      StateMachine_c sm = new StateMachine_c(modelRoot);
      testInst.relateAcrossR517To(sm);
  .end if
  .if ( obj.Key_Lett == "SM_SM" )
    .// this instance needs to be created first
        // Rel R501
        StateMachineState_c testR501Inst = new StateMachineState_c(modelRoot);
        testR501Inst.relateAcrossR501To(testInst);
  .end if
  .if(obj.Key_Lett == "CNST_CSP")
    .// add PE_PE supertype for testing
    PackageableElement_c pe = new PackageableElement_c(modelRoot);
    pe.relateAcrossR8001To(testInst);
  .end if
      // Participant rels
  .for each rel in part_rels
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .invoke ir = ignore_rel_generics( obj, rel, subobj1 )
      .if ( not ir.result )
        .select one form_obj related by srel->R_FORM[R208]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
        .invoke fcn = get_class_name( form_obj)
      // Rel R${rel.Numb}
        .if ( form_obj.Key_Lett == "O_ATTR" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "O_REF" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "S_DT" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(); 
        .elif ( form_obj.Key_Lett == "S_IRDT" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "S_DPK" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
        .elif ( form_obj.Key_Lett == "S_FPK" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
        .elif ( form_obj.Key_Lett == "S_EEPK" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
        .elif ( form_obj.Key_Lett == "R_OIR" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "SM_ACT" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst, testR501Inst);
        .elif ( form_obj.Key_Lett == "CA_EESMC" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEEC" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEEE" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMSMC" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEEA" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMEED" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "CA_SMOA" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .elif ( form_obj.Key_Lett == "SM_NETXN" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
        .else
      ${fcn.body} testR${rel.Numb}Inst = new ${fcn.body}(modelRoot);
        .end if
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To(testInst);
      .if(obj.Key_Lett == "SQ_S")
        .// this association needs a sequence package
        .// to be created for the disposal to work
        .// as designed
        .if(rel.Numb == 911)
		Sequence_c newSequence = new Sequence_c(modelRoot);
		testR911Inst.relateAcrossR928To(newSequence);
        .end if
      .elif(obj.Key_Lett == "C_AS")
        .// we need to create a MSG_M, MSG_AM
        .// to properly test here
        .if(rel.Numb == 1021)
		AsynchronousMessage_c asyncMessage = new AsynchronousMessage_c(modelRoot);
		Message_c message = new Message_c(modelRoot);
		testR1021Inst.relateAcrossR1019To(asyncMessage);
		asyncMessage.relateAcrossR1018To(message);
        .end if
      .elif(obj.Key_Lett == "C_IO")
        .// we need to create a MSG_SM, MSG_M
        .// to properly test here
        .if(rel.Numb == 1022)
		SynchronousMessage_c syncMessage = new SynchronousMessage_c(modelRoot);
		Message_c message = new Message_c(modelRoot);
		testR1022Inst.relateAcrossR1020To(syncMessage);
		message.relateAcrossR1018To(syncMessage);
        .end if
      .elif(obj.Key_Lett == "EP_PKG")
        .if(rel.Numb == 1403)
    Package_c nestedPackage = new Package_c(modelRoot);
    testR1403Inst.relateAcrossR1404To(nestedPackage);
         .elif(rel.Numb == 956)
        .// this association needs an interaction participant to be related to the package participant
        .//  for the disposal to work as designed  
        InteractionParticipant_c interactionParticipant = new InteractionParticipant_c(modelRoot);
		testR956Inst.relateAcrossR930To(interactionParticipant);
		PackageableElement_c pe = new PackageableElement_c(modelRoot);	
		interactionParticipant.relateAcrossR8001To(pe);
		Package_c pkg = new Package_c(modelRoot);
		pkg.relateAcrossR8000To(pe);            
        .end if
      .elif(obj.Key_Lett == "C_I")
        .if(rel.Numb == 4012)
    Provision_c pro = new Provision_c(modelRoot);
    pro.relateAcrossR4009To(testR${rel.Numb}Inst);
        .end if
      .elif(obj.Key_Lett == "COMM_COMM")
        .if(rel.Numb == 1130)
		Communication_c communication = new Communication_c(modelRoot);
		testR1130Inst.relateAcrossR1129To(communication);
        .end if
      .elif(obj.Key_Lett == "UC_UCC")
        .if(rel.Numb == 1208)
    UseCaseDiagram_c ucp = new UseCaseDiagram_c(modelRoot);
    testR1208Inst.relateAcrossR1209To(ucp);
        .end if
      .elif(obj.Key_Lett == "IP_IP")
        .if(rel.Numb == 4300)
		InterfacePackage_c diagram = new InterfacePackage_c(modelRoot);
		testR4300Inst.relateAcrossR4301To(diagram);
        .end if
      .elif(obj.Key_Lett == "CD_CD")
        .if(rel.Numb == 4202)
		ComponentDiagram_c diagram = new ComponentDiagram_c(modelRoot);
		testR4202Inst.relateAcrossR4203To(diagram);
        .end if
      .elif(obj.Key_Lett == "S_CDT")
        .if(rel.Numb == 1501)
        SymbolicConstant_c syc = new SymbolicConstant_c(modelRoot);
        LeafSymbolicConstant_c lfsc = new LeafSymbolicConstant_c(modelRoot);
        syc.relateAcrossR1502To(lfsc);
        lfsc.relateAcrossR1503To(testR1501Inst);
        .end if
      .end if
    .end if
  .else
      .select one ssrel related by rel->R_SUBSUP[R206]
      .if ( not_empty ssrel )
        .assign rel_numb = rel1
        .assign form_kl = subobj1
        .if ( rel.Numb == rel2 )
          .assign rel_numb = rel2
          .assign form_kl = subobj2
        .end if
        .select any form_obj from instances of O_OBJ where ( selected.Key_Lett == form_kl )
          .invoke fcn = get_class_name( form_obj)
      // Rel R${rel_numb} - supertype
          .if ( form_obj.Key_Lett == "SM_NSTXN" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
          .elif(form_obj.Key_Lett == "R_SUB")
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}(testInst);
          .elif(form_obj.Key_Lett == "SM_MOORE")
      ${fcn.body} testR${rel.Numb}Inst = ${fcn.body}.getOneSM_MOOREOnR510(testInst);
      if(testR${rel.Numb}Inst == null)
	      testR${rel.Numb}Inst = new ${fcn.body}(modelRoot);
          .else
      ${fcn.body} testR${rel_numb}Inst = new ${fcn.body}(modelRoot);
          .end if
      testR${rel_numb}Inst.relateAcrossR${rel_numb}To(testInst);
        .else
          .// associative relationships
          .select one arel related by rel->R_ASSOC[R206]
          .select one form_obj related by arel->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
          .invoke ir = ignore_rel_generics( obj, rel, "")
          .if(not ir.result)
            .invoke fcn = get_class_name( form_obj)
      // Rel R${rel.Numb} - associative
            .if ( form_obj.Key_Lett == "SM_MOAH" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
            .elif ( form_obj.Key_Lett == "R_OIR" )
      ${fcn.body} testR${rel.Numb}Inst = create${fcn.body}();
            .else
      ${fcn.body} testR${rel.Numb}Inst = new ${fcn.body}(modelRoot);
            .end if
            .select one the_part related by arel->R_AONE[R209]->R_RTO[R204]
            .invoke grf = get_reflexive_phrase_generics( rel, the_part, false )
            .assign func_suffix = grf.result
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To${func_suffix}(testInst);
            .if ( the_part.Obj_ID == obj.Obj_ID )
              .// create and relate the 'other' instance
              .select one the_part related by arel->R_AOTH[R210]->R_RTO[R204]
            .end if
            .select one part_obj related by the_part->R_OIR[R203]->O_OBJ[R201]
            .invoke pcn = get_class_name( part_obj)
            .if ( part_obj.Key_Lett == "S_DT" )
      ${pcn.body} testR${rel.Numb}InstOth = create${pcn.body}();
            .elif ( part_obj.Key_Lett == "S_SS" )
      ${pcn.body} testR${rel.Numb}InstOth = create${pcn.body}(testInst);
            .else
      ${pcn.body} testR${rel.Numb}InstOth = new ${pcn.body}(modelRoot);
            .end if
            .invoke grf = get_reflexive_phrase_generics( rel, the_part, false )
            .assign func_suffix = grf.result
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To${func_suffix}(testR${rel.Numb}InstOth);
            .// setup a package container for package linking associations
            .// as the disposal of the package linking associations is done
            .// through the package itself and not the domain
            .if(obj.Key_Lett == "S_DOM")
              .if(rel.Numb == 300)
		EePackageInPackage_c eepInPkg = new EePackageInPackage_c(modelRoot);
		testR36Inst.relateAcrossR34To(eepInPkg);
		eepInPkg.relateAcrossR35To(testR300InstOth);
	            .elif(rel.Numb == 301)
		FunctionPackageInPackage_c fpInPkg = new FunctionPackageInPackage_c(modelRoot);
		testR29Inst.relateAcrossR30To(fpInPkg);
		fpInPkg.relateAcrossR32To(testR301InstOth);
		          .end if
            .end if
          .end if
        .end if
      .end if
  .end for

      // Formalizer rels
  .for each rel in form_rels
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one part_obj related by srel->R_PART[R207]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
      .invoke ir = ignore_rel_generics(obj, rel, subobj1)
      .invoke pcn = get_class_name( part_obj)
      .if(((part_obj.Key_Lett == "SM_ACT") and (rel.Numb == 514)) or (ir.result == true))
        .// ignore
      .else
      // Rel R${rel.Numb}
        .if ( part_obj.Key_Lett == "R_ASSOC" )
      ${pcn.body} testR${rel.Numb}Inst = create${pcn.body}(testInst);
        .elif ( part_obj.Key_Lett == "V_TRV" )
      ${pcn.body} testR${rel.Numb}Inst = create${pcn.body}();
        .elif ( part_obj.Key_Lett == "V_AVL" )
      ${pcn.body} testR${rel.Numb}Inst = create${pcn.body}();
        .else
      ${pcn.body} testR${rel.Numb}Inst = new ${pcn.body}(modelRoot);
        .end if
      testR${rel.Numb}Inst.relateAcrossR${rel.Numb}To(testInst);
      .end if
    .else
      .// associative relationships
      .select one arel related by rel->R_ASSOC[R206]
      .select one one_part related by arel->R_AONE[R209]->R_RTO[R204]
      .select one oth_part related by arel->R_AOTH[R210]->R_RTO[R204]
      .select one part_obj related by one_part->R_OIR[R203]->O_OBJ[R201]
      .invoke pcn = get_class_name( part_obj)
      ${pcn.body} testR${rel.Numb}InstOne = new ${pcn.body}(modelRoot);
      .invoke grf = get_reflexive_phrase_generics( rel, one_part, false )
      .assign func_suffix = grf.result
      testR${rel.Numb}InstOne.relateAcrossR${rel.Numb}To${func_suffix}(testInst);
      .select one part_obj related by oth_part->R_OIR[R203]->O_OBJ[R201]
      .invoke pcn = get_class_name( part_obj)
      ${pcn.body} testR${rel.Numb}InstOth = new ${pcn.body}(modelRoot);
      .invoke grf = get_reflexive_phrase_generics( rel, oth_part, false )
      .assign func_suffix = grf.result
      testR${rel.Numb}InstOth.relateAcrossR${rel.Numb}To${func_suffix}(testInst);
    .end if
  .end for

      testInst.${op}();

  .if(obj.Key_Lett == "CNST_CSP")
      PackageableElement_c peAfter = PackageableElement_c.getOnePE_PEOnR8001(testInst);
      assertNull(peAfter);
  .end if
      // 
  .for each rel in part_rels
    .invoke ir = ignore_rel_null_check_generics( obj, rel, subobj1 )
    .if(not ir.result)
      // Rel R${rel.Numb}
      .select one srel related by rel->R_SIMP[R206]
      .if ( not_empty srel )
        .select one form_obj related by srel->R_FORM[R208]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
        .invoke gnfn = get_nav_func_name( form_obj, rel, "one" )
        .invoke fcn = get_class_name( form_obj)
      ${fcn.body} testR${rel.Numb}Inst2 = ${fcn.body}.${gnfn.body}(testInst);
          .// if the object being disposed is not a system, as
          .// a system's disposal does not (at least, on its own)
          .// cause the disposal of its child domains
          .if (not (obj.Key_Lett == "S_SYS"))
      assertNull ( testR${rel.Numb}Inst2 );
          .end if
      .else
        .select one ssrel related by rel->R_SUBSUP[R206]
        .if ( not_empty ssrel )
          .assign rel_numb = rel1
          .assign form_kl = subobj1
          .if ( rel.Numb == rel2 )
            .assign rel_numb = rel2
            .assign form_kl = subobj2
          .end if
          .select any form_obj from instances of O_OBJ where ( selected.Key_Lett == form_kl )
          .invoke fcn = get_class_name( form_obj)
          .invoke gnfn = get_nav_func_name( form_obj, rel, "one" )
      ${fcn.body} testR${rel_numb}Inst2 = ${fcn.body}.${gnfn.body}(testInst);
      assertNull ( testR${rel_numb}Inst2 );
        .else
          .// associative relationships 
          .select one arel related by rel->R_ASSOC[R206]
          .select one form_obj related by arel->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
          .invoke fcn = get_class_name( form_obj)
          .invoke gnfn = get_nav_func_name( form_obj, rel, "one" )
          .select one rto related by arel->R_AONE[R209]->R_RTO[R204]
          .invoke grf = get_reflexive_phrase_generics( rel, rto, false )
          .assign func_suffix = grf.result
      ${fcn.body} testR${rel.Numb}Inst2 = ${fcn.body}.${gnfn.body}${func_suffix}(testInst);
      assertNull ( testR${rel.Numb}Inst2 );
          .if (func_suffix != "")
            .// Its reflexive, test the other side too
            .select one rto related by arel->R_AOTH[R210]->R_RTO[R204]
            .invoke grf = get_reflexive_phrase_generics( rel, rto, false )
            .assign func_suffix = grf.result
      testR${rel.Numb}Inst2 = ${fcn.body}.${gnfn.body}${func_suffix}(testInst);
      assertNull ( testR${rel.Numb}Inst2 );
          .end if
        .end if
      .end if
    .end if
  .end for

  .for each rel in form_rels
    .select one srel related by rel->R_SIMP[R206]
    .if ( not_empty srel )
      .select one part_obj related by srel->R_PART[R207]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
      .invoke gnfn = get_nav_func_name( part_obj, rel, "one" )
      .invoke pcn = get_class_name( part_obj)
      ${pcn.body} testR${rel.Numb}Inst2 = ${pcn.body}.${gnfn.body}(testInst);
      assertNull ( testR${rel.Numb}Inst2 );
    .else
       .// TODO associative relationships
    .end if
  .end for

      testInst.delete();
.end function
.//
.//=====================================================================
.//
.function create_all_dispose_tests_generics
  .param inst_ref obj   .// O_OBJ
  .param string op   .// "Dispose" or "Clear"
  .//
  .invoke gpr = get_part_rels_generics(obj)
  .invoke gfr = get_form_rels_generics(obj)
  .if ( gpr.has_subtypes )
    .invoke gs = get_subtypes( obj )
    .assign sub_set = gs.result
    .assign rel_set = gs.rel_set
    .if ( (cardinality rel_set) == 1 )
      .assign rel_num = -1
      .for each rel in rel_set
        .assign rel_num = rel.Numb
      .end for
      .for each sub in sub_set
        .if (obj.Key_Lett != "S_DT")   .// this execlude is addressed in issue  dts0100762545
	    public void test$r{obj.Name}Dispose$r{sub.Name}() throws Exception
	    {   
	      .invoke cdt = create_dispose_test_generics( obj, op, gpr.result, gfr.result, rel_num, sub.Key_Lett, -1, "ERROR" )
	      ${cdt.body}\
	    }
       .end if
      .end for
    .else
      .for each loop1 in rel_set
        .select many l1_sub_set related by loop1->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201] where ( (selected.Key_Lett != "SM_MEALY") and (selected.Key_Lett != "SM_MEAH"))
        .for each l1_sub in l1_sub_set
          .for each loop2 in rel_set
            .if ( loop2 != loop1 )
              .select many l2_sub_set related by loop2->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201] where ( (selected.Key_Lett != "SM_MEALY") and (selected.Key_Lett != "SM_MEAH"))
              .for each l2_sub in l2_sub_set
                .invoke l2fcn = get_class_name( l2_sub )
    public void test$r{obj.Name}Dispose$r{l1_sub.Name}$r{l2_sub.Name}() throws Exception
    {
  .invoke cdt = create_dispose_test_generics( obj, op, gpr.result, gfr.result, loop1.Numb, l1_sub.Key_Lett, loop2.Numb, l2_sub.Key_Lett )
${cdt.body}\
    }

              .end for
            .end if
          .end for
        .end for
      .end for
    .end if
  .else
.// Exclude these instances as they are replaced with packages in generics 
        .if   (obj.Key_Lett == "S_DOM")
        .elif (obj.Key_Lett == "A_A" )
        .elif (obj.Key_Lett == "COMM_COMM")
        .elif (obj.Key_Lett == "SQ_S")
        .elif (obj.Key_Lett == "CP_CP")
        .elif (obj.Key_Lett == "UC_UCC" )
        .elif (obj.Key_Lett == "IP_IP")
        .elif (obj.Key_Lett == "S_SS")
        .elif (obj.Key_Lett == "IP_IP")
        .elif (obj.Key_Lett == "S_FPK")
        .elif (obj.Key_Lett == "S_EEPK")        
        .else   					  
	    public void test$r{obj.Name}Dispose() throws Exception
	    {
	  .// last arguments will be ignored
	  .invoke cdt = create_dispose_test_generics( obj, op, gpr.result, gfr.result, -1, "ERROR", -1, "ERROR" )
    	${cdt.body}\
	    }
   .end if 
  .end if
.end function
.//=====================================================================
.//=====================================================================
.// Main code
.//
.assign path = "com/mentor/nucleus/bp/core/test"
.assign classname = "DisposeTestGenerics"
.assign filename = "${path}/${classname}.java"
.//
package com.mentor.nucleus.bp.core.test;
//======================================================================
//
// File: ${filename}
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.50 $$
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//

import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.test.CoreTest;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

public class ${classname} extends CoreTest
{
    Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();

    /**
     * Whether this class's test fixture has been set up.
     */
    private static boolean fixtureSetUp = false;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (!fixtureSetUp) {
            // clear any data remaining in the model-root from previous test classes
            modelRoot.clearDatabase(new NullProgressMonitor());

            TestUtil.createMockDefaultDataTypes(modelRoot, null);

            fixtureSetUp = true;
        }
		Ooaofooa.setInUnitTest(true);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Ooaofooa.setInUnitTest(false);
	}
.select many dispose_set from instances of O_TFR where ( selected.Name == "dispose" )
.for each dispose_op in dispose_set
  .select one obj related by dispose_op->O_OBJ[R115]
   .invoke cadt = create_all_dispose_tests_generics( obj, "Dispose" )
   ${cadt.body}
.end for
    ClassAsSubtype_c createClassAsSubtype_c(ReferringClassInAssoc_c rcia) {
		ClassAsSubtype_c subtype = new ClassAsSubtype_c(modelRoot);
		ModelClass_c subtypeClass = new ModelClass_c(modelRoot);
		Association_c assoc = new Association_c(modelRoot);
		if (rcia == null)
			rcia = new ReferringClassInAssoc_c(modelRoot);
		ClassInAssociation_c cia = new ClassInAssociation_c(modelRoot);
		subtype.relateAcrossR205To(rcia);
		rcia.relateAcrossR203To(cia);
		cia.relateAcrossR201To(subtypeClass);
		ClassAsSupertype_c superType = new ClassAsSupertype_c(modelRoot);
		SubtypeSupertypeAssociation_c subsup = new SubtypeSupertypeAssociation_c(
				modelRoot);
		superType.relateAcrossR212To(subsup);
		subsup.relateAcrossR206To(assoc);
		subsup.relateAcrossR213To(subtype);
		ReferredToClassInAssoc_c rtcia = new ReferredToClassInAssoc_c(modelRoot);
		ClassInAssociation_c superCIA = new ClassInAssociation_c(modelRoot);
		ModelClass_c supertypeClass = new ModelClass_c(modelRoot);
		supertypeClass.relateAcrossR201To(superCIA);
		rtcia.relateAcrossR203To(superCIA);
		superType.relateAcrossR204To(rtcia);
		return subtype;
    }
    
    private DataType_c createDataType_c()
    {
        DataType_c retval = new DataType_c(modelRoot);
        EnumerationDataType_c edt = new EnumerationDataType_c(modelRoot);
        retval.relateAcrossR17To(edt);
        return retval;
    }



    private Attribute_c createAttribute_c()
    {
        Attribute_c retval = new Attribute_c(modelRoot);
        BaseAttribute_c battr = new BaseAttribute_c(modelRoot);
        retval.relateAcrossR106To(battr);
        NewBaseAttribute_c nbattr = new NewBaseAttribute_c(modelRoot);
        battr.relateAcrossR107To(nbattr);
        return retval;
    }
    private AttributeReferenceInClass_c createAttributeReferenceInClass_c()
    {
        AttributeReferenceInClass_c retval = new AttributeReferenceInClass_c(modelRoot);
        ReferringClassInAssoc_c rgo = new ReferringClassInAssoc_c(modelRoot);
        rgo.relateAcrossR111To(retval);
        ReferredToIdentifierAttribute_c rto = new ReferredToIdentifierAttribute_c(modelRoot);
        rto.relateAcrossR111To(retval);
        return retval;
    }
    private ClassInAssociation_c createClassInAssociation_c()
    {
        ClassInAssociation_c retval = new ClassInAssociation_c(modelRoot);
        ReferredToClassInAssoc_c rto = new ReferredToClassInAssoc_c(modelRoot);
        rto.relateAcrossR203To(retval);
        ClassAsSimpleParticipant_c part = new ClassAsSimpleParticipant_c(modelRoot);
        rto.relateAcrossR204To(part);
        return retval;
    }
    private LinkedAssociation_c createLinkedAssociation_c(Object inst)
    {
        LinkedAssociation_c retval = new LinkedAssociation_c(modelRoot);
        Association_c rel = new Association_c(modelRoot);
        retval.relateAcrossR206To(rel);
        if ( inst instanceof ClassAsAssociatedOneSide_c )
        {
            ClassAsAssociatedOtherSide_c oth = new ClassAsAssociatedOtherSide_c(modelRoot);
            retval.relateAcrossR210To(oth);
            ClassAsLink_c link = new ClassAsLink_c(modelRoot);
            retval.relateAcrossR211To(link);
        }
        else if ( inst instanceof ClassAsAssociatedOtherSide_c )
        {
            ClassAsAssociatedOneSide_c one = new ClassAsAssociatedOneSide_c(modelRoot);
            retval.relateAcrossR209To(one);
            ClassAsLink_c link = new ClassAsLink_c(modelRoot);
            retval.relateAcrossR211To(link);
        }
        else if ( inst instanceof ClassAsLink_c )
        {
            ReferringClassInAssoc_c rgo = new ReferringClassInAssoc_c(modelRoot);
            ((ClassAsLink_c)inst).relateAcrossR205To(rgo);
            ClassInAssociation_c oir = new ClassInAssociation_c(modelRoot);
            rgo.relateAcrossR203To(oir);
            ModelClass_c mc = new ModelClass_c(modelRoot);
            oir.relateAcrossR201To(mc);
            oir.relateAcrossR201To(rel);

            ClassAsAssociatedOneSide_c one = new ClassAsAssociatedOneSide_c(modelRoot);
            retval.relateAcrossR209To(one);
            ReferredToClassInAssoc_c rto1  = new ReferredToClassInAssoc_c(modelRoot);
            one.relateAcrossR204To(rto1);
            ClassInAssociation_c oir1 = new ClassInAssociation_c(modelRoot);
            rto1.relateAcrossR203To(oir1);
            ModelClass_c mc1 = new ModelClass_c(modelRoot);
            oir1.relateAcrossR201To(mc1);
            oir1.relateAcrossR201To(rel);

            ClassAsAssociatedOtherSide_c oth = new ClassAsAssociatedOtherSide_c(modelRoot);
            retval.relateAcrossR210To(oth);
            ReferredToClassInAssoc_c rto2  = new ReferredToClassInAssoc_c(modelRoot);
            oth.relateAcrossR204To(rto2);
            ClassInAssociation_c oir2 = new ClassInAssociation_c(modelRoot);
            rto2.relateAcrossR203To(oir2);
            ModelClass_c mc2 = new ModelClass_c(modelRoot);
            oir2.relateAcrossR201To(mc2);
            oir2.relateAcrossR201To(rel);
        }
        return retval;
    }
    private NewStateTransition_c createNewStateTransition_c()
    {
        NewStateTransition_c retval = new NewStateTransition_c(modelRoot);
        Transition_c t = new Transition_c(modelRoot);
        retval.relateAcrossR507To(t);
        StateEventMatrixEntry_c seme = new StateEventMatrixEntry_c(modelRoot);
        retval.relateAcrossR504To(seme);
        return retval;
    }
    private NoEventTransition_c createNoEventTransition_c()
    {
        NoEventTransition_c retval = new NoEventTransition_c(modelRoot);
        Transition_c t = new Transition_c(modelRoot);
        retval.relateAcrossR507To(t);
        return retval;
    }
    private MooreActionHome_c createMooreActionHome_c()
    {
        MooreActionHome_c retval = new MooreActionHome_c(modelRoot);

        Action_c a = new Action_c(modelRoot);
        ActionHome_c ah = new ActionHome_c(modelRoot);
        a.relateAcrossR514To(ah);
        ah.relateAcrossR513To(retval);
        return retval;
    }
    private Action_c createAction_c(StateMachine_c sm, StateMachineState_c st)
    {
        Action_c retval = new Action_c(modelRoot);
		ActionHome_c ah = new ActionHome_c(modelRoot);
        retval.relateAcrossR514To(ah);
        MooreStateMachine_c msm = MooreStateMachine_c.getOneSM_MOOREOnR510(sm);
        if(msm == null)
        	msm = new MooreStateMachine_c(modelRoot);
        if ( msm != null )
        {
            MooreActionHome_c moah = new MooreActionHome_c(modelRoot);
            ah.relateAcrossR513To(moah);
            moah.relateAcrossR511To(msm);
            moah.relateAcrossR511To(st);
        }
        return retval;
    }
    private OperationValue_c createOperationValue_c()
    {
        OperationValue_c retval = new OperationValue_c(modelRoot);
        Value_c v = new Value_c(modelRoot);
        v.relateAcrossR801To(retval);
        return retval;
    }
    private SmToEeAccessPath_c createSmToEeAccessPath_c()
    {
        SmToEeAccessPath_c retval = new SmToEeAccessPath_c(modelRoot);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(retval);
        return retval;
    }
    private SmToObjAccessPath_c createSmToObjAccessPath_c()
    {
        SmToObjAccessPath_c retval = new SmToObjAccessPath_c(modelRoot);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(retval);
        return retval;
    }
    private SmToObjAttributeAccess_c createSmToObjAttributeAccess_c()
    {
        SmToObjAttributeAccess_c retval = new SmToObjAttributeAccess_c(modelRoot);
        SmToObjAccessPath_c soap = new SmToObjAccessPath_c(modelRoot);
        soap.relateAcrossR418To(retval);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(soap);
        return retval;
    }
    private SmToEeDataItemAccess_c createSmToEeDataItemAccess_c()
    {
        SmToEeDataItemAccess_c retval = new SmToEeDataItemAccess_c(modelRoot);
        SmToEeAccessPath_c seap = new SmToEeAccessPath_c(modelRoot);
        seap.relateAcrossR422To(retval);
        AccessPath_c ap = new AccessPath_c(modelRoot);
        ap.relateAcrossR415To(seap);
        return retval;
    }
    private EeToSmCommPath_c createEeToSmCommPath_c()
    {
        EeToSmCommPath_c retval = new EeToSmCommPath_c(modelRoot);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(retval);
        return retval;
    }
    private SmToEeCommPath_c createSmToEeCommPath_c()
    {
        SmToEeCommPath_c retval = new SmToEeCommPath_c(modelRoot);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(retval);
        return retval;
    }
    private SmToSmCommPath_c createSmToSmCommPath_c()
    {
        SmToSmCommPath_c retval = new SmToSmCommPath_c(modelRoot);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(retval);
        return retval;
    }
    private SmToEeEventComm_c createSmToEeEventComm_c()
    {
        SmToEeEventComm_c retval = new SmToEeEventComm_c(modelRoot);
        SmToEeCommPath_c smeecp = new SmToEeCommPath_c(modelRoot);
        smeecp.relateAcrossR412To(retval);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(smeecp);
        return retval;
    }
    private SmToSmEventComm_c createSmToSmEventComm_c()
    {
        SmToSmEventComm_c retval = new SmToSmEventComm_c(modelRoot);
        SmToSmCommPath_c smsmcp = new SmToSmCommPath_c(modelRoot);
        smsmcp.relateAcrossR408To(retval);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(smsmcp);
        return retval;
    }
    private EeToSmEventComm_c createEeToSmEventComm_c()
    {
        EeToSmEventComm_c retval = new EeToSmEventComm_c(modelRoot);
        EeToSmCommPath_c eesmcp = new EeToSmCommPath_c(modelRoot);
        eesmcp.relateAcrossR404To(retval);
        CommunicationPath_c cp = new CommunicationPath_c(modelRoot);
        cp.relateAcrossR401To(eesmcp);
        return retval;
    }
	private AttributeValueReference_c createAttributeValueReference_c() {
		AttributeValueReference_c retval = new AttributeValueReference_c(
				modelRoot);
		Value_c v = new Value_c(modelRoot);
		v.relateAcrossR801To(retval);
		Attribute_c a = new Attribute_c(modelRoot);
		v.relateAcrossR801To(retval);
		return retval;
	}
	private InstanceReferenceDataType_c createInstanceReferenceDataType_c() {
		InstanceReferenceDataType_c retval = new InstanceReferenceDataType_c(
				modelRoot);
		DataType_c dt = new DataType_c(modelRoot);
		dt.relateAcrossR17To(retval);
		return retval;
    }
}
.emit to file "src/${filename}"
