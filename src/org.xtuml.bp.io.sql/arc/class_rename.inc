.//====================================================================
.//
.// File:      $RCSfile: class_rename.inc,v $
.// Version:   $Revision: 1.13 $
.// Modified:  $Date: 2013/05/10 16:18:02 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.// Change the Name of Table instances where the name of the
.//  class changed from BP 6.1 to Tiger.   We do this here so we
.//  don't have to hand edit the bp_ooa.pei.sql file.
.//
.//====================================================================
.//
.function rename_class
  .param string old_name
  .param string new_name
  .//
  .select any t from instances of T where ( selected.bpName == old_name )
  .if ( empty t )
    .print "ERROR: couldn't find instance of Table with bpName == '${old_name}' "
  .else
    .assign t.Name = new_name
  .end if
.end function
.//
.//====================================================================
.//
.invoke rename_class( "Synchronous Service", "Function" )
.invoke rename_class( "Synchronous Service Parameter", "Function Parameter" )
.invoke rename_class( "Imported Object", "Imported Class" )
.invoke rename_class( "Object", "Model Class" )
.invoke rename_class( "Transformer", "Operation" )
.invoke rename_class( "Transformer Parameter", "Operation Parameter" )
.invoke rename_class( "Object Identifier", "Class Identifier" )
.invoke rename_class( "Object Identifier Attribute", "Class Identifier Attribute" )
.invoke rename_class( "Attribute Reference in Object", "Attribute Reference in Class" )
.invoke rename_class( "Relationship", "Association" )
.invoke rename_class( "Simple Relationship", "Simple Association" )
.invoke rename_class( "Associative Relationship", "Linked Association" )
.invoke rename_class( "Subtype Supertype Relationship", "Subtype Supertype Association" )
.invoke rename_class( "Composition Relationship", "Derived Association" )
.invoke rename_class( "Object In Relationship", "Class In Association" )
.invoke rename_class( "Referred To Object in Rel", "Referred To Class In Assoc" )
.invoke rename_class( "Referring Object In Rel", "Referring Class In Assoc" )
.invoke rename_class( "Object As Simple Participant", "Class As Simple Participant" )
.invoke rename_class( "Object As Associated One Side", "Class As Associated One Side" )
.invoke rename_class( "Object As Associated Other Side", "Class As Associated Other Side" )
.invoke rename_class( "Object As Supertype", "Class As Supertype" )
.invoke rename_class( "Object As Simple Formalizer", "Class As Simple Formalizer" )
.invoke rename_class( "Object As Associator", "Class As Link" )
.invoke rename_class( "Object As Subtype" , "Class As Subtype" )
.invoke rename_class( "Object As Composition One Side", "Class As Derived One Side" )
.invoke rename_class( "Object As Composition Other Side", "Class As Derived Other Side" )
.invoke rename_class( "State Model", "State Machine" )
.invoke rename_class( "Instance State Model", "Instance State Machine" )
.invoke rename_class( "Assigner State Model", "Class State Machine" )
.invoke rename_class( "Instance State Model", "Instance State Machine" )
.invoke rename_class( "Moore State Model", "Moore State Machine" )
.invoke rename_class( "Mealy State Model", "Mealy State Machine" )
.invoke rename_class( "State Model State", "State Machine State" )
.invoke rename_class( "State Model Event Data Item", "State Machine Event Data Item" )
.invoke rename_class( "State Model Event", "State Machine Event" )
.invoke rename_class( "Graphical_Element", "Graphical Element" )
.invoke rename_class( "Line_Segment", "Line Segment" )
.invoke rename_class( "Derived Based Attribute", "Derived Base Attribute" )
.//

