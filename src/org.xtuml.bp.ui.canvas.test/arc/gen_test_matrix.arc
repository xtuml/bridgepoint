.//====================================================================
.//
.// File:      $RCSfile: gen_test_matrix.arc,v $
.// Version:   $Revision: 1.9 $
.// Modified:  $Date: 2013/01/10 22:44:11 $
.//
.// (c) Copyright 2004-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//  Purpose: This archetype is used to generate the test matrix to be
.//  performed as Unit Test on any given model.
.//
.//=======================================================================
.//
.select many sms from instances of SM_SM
.for each sm in sms
  .select one class related by sm->SM_ISM[R517]->O_OBJ[R518]
  .if (empty class)
    .select one class related by sm->SM_ASM[R517]->O_OBJ[R519]
  .end if
  .select many nsts related by sm->SM_TXN[R505]->SM_NSTXN[R507]
  .if (not_empty nsts)
--------------------------------------------------------------------------------
Tests for class: ${class.Name}
--------------------------------------------------------------------------------
    .select many states related by sm->SM_STATE[R501]
    .for each state in states
      .select many semes related by state->SM_SEME[R503]
      .for each seme in semes
        .select one nst related by seme->SM_NSTXN[R504]
        .if (not_empty nst)
          .select one evt related by seme->SM_SEVT[R503]->SM_EVT[R525]
          .select one new_state related by nst->SM_TXN[R507]->SM_STATE[R506]
_- (test_${info.unique_num}) In '${state.Name}' generate $U_{class.Key_Lett}${evt.Numb}:${evt.Mning}
R- State is changed to '${new_state.Name}'

        .end if
      .end for
    .end for
  .end if
.end for
.emit to file "./tests.txt"