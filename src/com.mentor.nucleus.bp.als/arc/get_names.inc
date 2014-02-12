.//====================================================================
.//
.// File:      $RCSfile: get_names.inc,v $
.// Version:   $Revision: 1.12 $
.// Modified:  $Date: 2013/01/10 23:21:16 $
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.function get_lang_name
  .invoke lang_name_env = GET_ENV_VAR( "ALS_NAME" )
  .assign attr_result = lang_name_env.result
  .if ( attr_result == "" )
    .print "\nERROR: Environment variable ALS_NAME not set."
    .exit 100
  .end if
.end function
.//
.function get_validation_class_name
  .invoke x = get_lang_name()
  .assign attr_result = "$c{x.result}_validate"
.end function
.//
.function get_validation_context_name
  .invoke x = get_lang_name()
  .assign attr_result = "m_$l{x.result}_context"
.end function
.//
.function get_common_package_name
  .assign attr_result = "com.mentor.nucleus.bp.als"
.end function
.//
.function get_common_package_source_path
  .assign attr_result = "com/mentor/nucleus/bp/als"
.end function
