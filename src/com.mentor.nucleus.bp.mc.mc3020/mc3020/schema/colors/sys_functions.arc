.//============================================================================
.// File:  sys_functions.arc
.//
.// Description:
.// This archetype file contains system level user definable archetype
.// functions.
.//
.// Notice:
.// (C) Copyright 1999-2010 Mentor Graphics Corporation
.//     All rights reserved.
.// Enhancements provided by TOYO Corporation.
.//============================================================================
.//
.//
.//============================================================================
.// *** Main Task utilities:
.//============================================================================
.//
.//============================================================================
.// GetMainTaskEntryDeclaration
.//
.// <Abstract>
.//  This function defines a main task entry.
.// <Input>
.// <Output>
.//  body:main function or main task entry
.// <Example>
.//  .function GetMainTaskEntryDeclaration
.//  void mainTask ( int taskId )
.//  .end function
.//============================================================================
.function GetMainTaskEntryDeclaration
  .select any te_target from instances of TE_TARGET
int
${te_target.main}( int argc, char ** argv )
.end function
.//
.//============================================================================
.// GetMainTaskEntryReturn
.//
.// <Abstract>
.//  This function defines return statements in a main function or 
.//  a main task entry.  If the main function has a non-void return
.//  type, a return statement should be defined.
.// <Input>
.// <Output>
.//  body:very last statements in main function or main task entry 
.// <Example>
.//  .function GetMainTaskEntryReturn
.//    return 0;
.//  .end function
.//============================================================================
.function GetMainTaskEntryReturn
  return 0;
.end function
.//
.//
.//============================================================================
.// fixing extents to a base address
.//============================================================================
.//
.//============================================================================
.// UserFixExtentBaseBegin
.//
.// <Abstract>
.//  This function gets called at the point just before the storage
.//  for the class extent (array of structures) is defined.
.//  Return a value used to ORG the object instance storage in memory.
.// <Input>
.//  BASE_ADDRESS information extracted from object description        
.// <Output>
.//  pragma or ORG statement to fix memory address for following define
.// <Example>
.//  .invoke UserFixExtentBaseBegin( base_address )
.//============================================================================
.function UserFixExtentBaseBegin
  .param string base_address
  .//
  .assign attr_result = "/* ${base_address} */"
.end function
.//
.//============================================================================
.// UserFixExtentBaseEnd
.//
.// <Abstract>
.//  This function gets called at the point just after the storage
.//  for the class extent (array of structures) is defined.
.//  Return a value used to complete the ORG the object instance storage.
.// <Input>
.//  BASE_ADDRESS information extracted from object description        
.// <Output>
.//  end pragma or blank
.// <Example>
.//  .invoke UserFixExtentBaseEnd( base_address )
.//============================================================================
.function UserFixExtentBaseEnd
  .param string base_address
  .//
  .assign attr_result = "/* ${base_address} */"
.end function
.//
.//
.//============================================================================
.// enabling/disabling interrupts
.//============================================================================
.//
.//============================================================================
.// UserEnableInterrupts
.//
.// <Abstract>
.//  This archetype function returns a string which represents
.//  the target instruction for enabling interrupts.
.// <Input>
.//  none
.// <Output>
.//  enable interrupts statement (or nothing)
.// <Example>
.//  .invoke UserEnableInterrupts()
.//============================================================================
.function UserEnableInterrupts
  .//
  .assign attr_result = "/* enable interrupts */"
.end function
.//
.//============================================================================
.// UserDisableInterrupts
.//
.// <Abstract>
.//  This archetype function returns a string which represents
.//  the target instruction for disabling interrupts.
.// <Input>
.//  none
.// <Output>
.//  disable interrupts statement (or nothing)
.// <Example>
.//  .invoke UserDisableInterrupts()
.//============================================================================
.function UserDisableInterrupts
  .//
  .assign attr_result = "/* disable interrupts */"
.end function
.//
.//
.//============================================================================
.// Do not add anything at the end of this file!
.//============================================================================
