.//=======================================================================
.//
.// File:      $RCSfile: test_parse_chain.inc,v $
.// Version:   $Revision: 1.7 $
.// Modified:  $Date: 2011/05/30 20:20:41 $
.//
.// (c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//=======================================================================
.// This document contains information proprietary and confidential to
.// Mentor Graphics Corp., and is not for external distribution.
.//=======================================================================
.//
.//  This file is .include'd by Delete_Check_test/color/Deletecheck_package_spec.clr
.//  since that is the least intrusive place to get this function invoked 
.//  when executing java.arc
.//
.function test_chain_parse
  .assign test_string0 = "->A[R1]->B[R2]"
  .assign test_string1 = "->C[R3]->D[R4.'b1 phrase']"
  .assign test_string2 = "->E[R5.'a2 phrase']->F[R6]"
  .assign test_string3 = "->G[R7.'a3 phrase']->H[R8.'b3 phrase']"

  .print "Expect 32 tests to pass"

  .invoke tcf_kl_test(test_string0, "A", 1)
  .invoke tcf_kl_test(test_string1, "C", 2)
  .invoke tcf_kl_test(test_string2, "E", 3)
  .invoke tcf_kl_test(test_string3, "G", 4)
  .invoke tcb_kl_test(test_string0, "B", 5)
  .invoke tcb_kl_test(test_string1, "D", 6)
  .invoke tcb_kl_test(test_string2, "F", 7)
  .invoke tcb_kl_test(test_string3, "H", 8)

  .invoke tcf_rel_test(test_string0, "1", 9)
  .invoke tcf_rel_test(test_string1, "3", 10)
  .invoke tcf_rel_test(test_string2, "5", 11)
  .invoke tcf_rel_test(test_string3, "7", 12)
  .invoke tcb_rel_test(test_string0, "2", 13)
  .invoke tcb_rel_test(test_string1, "4", 14)
  .invoke tcb_rel_test(test_string2, "6", 15)
  .invoke tcb_rel_test(test_string3, "8", 16)

  .invoke tcf_rest_test(test_string0, "->B[R2]", 17)
  .invoke tcf_rest_test(test_string1, "->D[R4.'b1 phrase']", 18)
  .invoke tcf_rest_test(test_string2, "->F[R6]", 19)
  .invoke tcf_rest_test(test_string3, "->H[R8.'b3 phrase']", 20)
  .invoke tcb_rest_test(test_string0, "->A[R1]", 21)
  .invoke tcb_rest_test(test_string1, "->C[R3]", 22)
  .invoke tcb_rest_test(test_string2, "->E[R5.'a2 phrase']", 23)
  .invoke tcb_rest_test(test_string3, "->G[R7.'a3 phrase']", 24)

  .invoke tcf_phrase_test(test_string0, "", 25)
  .invoke tcf_phrase_test(test_string1, "", 26)
  .invoke tcf_phrase_test(test_string2, "a2 phrase", 27)
  .invoke tcf_phrase_test(test_string3, "a3 phrase", 28)
  .invoke tcb_phrase_test(test_string0, "", 29)
  .invoke tcb_phrase_test(test_string1, "b1 phrase", 30)
  .invoke tcb_phrase_test(test_string2, "", 31)
  .invoke tcb_phrase_test(test_string3, "b3 phrase", 32)
.end function
.function tcf_kl_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcf_kl{test_string}", expected, test_num)
.end function
.function tcb_kl_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcb_kl{test_string}", expected, test_num)
.end function
.function tcf_rel_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcf_rel{test_string}", expected, test_num)
.end function
.function tcb_rel_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcb_rel{test_string}", expected, test_num)
.end function
.function tcf_phrase_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcf_phrase{test_string}", expected, test_num)
.end function
.function tcb_phrase_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcb_phrase{test_string}", expected, test_num)
.end function
.function tcf_rest_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcf_rest{test_string}", expected, test_num)
.end function
.function tcb_rest_test
  .param string test_string
  .param string expected
  .param integer test_num
  .invoke assertEquals("$tcb_rest{test_string}", expected, test_num)
.end function
.function assertEquals
  .param string actual
  .param string expected
  .param integer test_num
  .if ( actual != expected )
    .print "ERROR: test ${test_num} failed, expected = ${expected} actual = ${actual}"
    .exit 100
  .else
    .print "Test ${test_num} passed"
  .end if
.end function
