.//====================================================================
.//
.// File:      $Source: /arch1/products/tiger/repository/MC-Java/do_type.inc,v $
.// Version:   $Revision: 1.14.34.1 $
.// Modified:  $Date: 2013/07/19 12:26:01 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.function do_type
.param inst_ref type
  .assign typedecl = type.Name
  .select one edt related by type->S_EDT[R17]
  .if (not_empty edt)
    .assign typedecl = "int "
  .else
    .select one udt related by type->S_UDT[R17]
    .if (not_empty udt)
      .select one type related by udt->S_DT[R18]
    .end if
    .if (type.Name == "unique_id")
      .assign typedecl = "java.util.UUID        "
    .end if
    .if (type.Name == "integer") 
      .assign typedecl = "int         "
    .end if
    .if (type.Name == "real")
      .assign typedecl = "float       "
    .end if
    .if (type.Name == "boolean")
      .assign typedecl = "boolean     "
    .end if
    .if ((type.Name == "string") OR (type.Name == "timestamp"))
      .assign typedecl = "String      "
    .end if
    .if (type.Name == "inst_ref<Mapping>")
      .if (not_empty udt)
        .select one utype related by udt->S_DT[R17]
        .if (empty utype)
          .print "ERROR: No DT found for UDT: ->${typedecl}<-"
        .else
          .if ( utype.Name == "inst_ref<Timer>" )
            .assign typedecl = "Tim_c";
          .else
            .assign typedecl = "${utype.Name}";
          .end if
        .end if
      .else
        .print "ERROR: No UDT found for: ->${typedecl}<-"
      .end if
      .// Added to decouple modeled type names from Java names
      .if (typedecl == "instance")
        .assign typedecl = "Object"
      .elif (typedecl == "class")
        .assign typedecl = "Class"
      .elif (typedecl == "reentrantlock")
        .assign typedecl = "ReentrantLock"
      .end if
    .end if
  .end if
${typedecl}\
.end function
.//
.//
.//////////////////////////////////////////////////////////////////////////////////////////////////////////
.// This function checks whether the given 'type' is a java primitive data type or not, which are:
.// integer, long, float, boolean
.// It sets the return attribute "isPrimitive".
.////////////////////////////////////////////////////////////////////////////////////////////////////////////
.function is_java_primitive_type
	.param inst_ref type
	.assign attr_isPrimitive = false
	.assign attr_javaType = type.Name    
    .select one edt related by type->S_EDT[R17]
    .if (not_empty edt)
       .assign attr_isPrimitive = true
    .end if
    .if (((type.Name == "integer")) OR (((type.Name == "real") OR ((type.Name == "boolean") OR (type.Name == "long")))))
      .assign attr_isPrimitive = true
    .end if    	
.end function
.function get_java_wrapper_type
  .param inst_ref type
.//  
  .assign attr_javaType = type.Name
  .select one edt related by type->S_EDT[R17]
  .if (not_empty edt)
    .assign attr_javaType = "Integer"
  .else
    .if (type.Name == "integer") 
      .assign attr_javaType = "Integer"
    .end if
    .if (type.Name == "real")
      .assign attr_javaType = "Float"
    .end if
    .if (type.Name == "boolean")
      .assign attr_javaType = "Boolean"
    .end if
	.if (type.Name == "long")
      .assign attr_javaType = "Long"
    .end if
  .end if
.end function
