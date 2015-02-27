.//====================================================================
.//
.// File:      $RCSfile: class2table.arc,v $
.// Version:   $Revision: 1.1.2.1 $
.// Modified:  $Date: 2004/09/24 22:00:53 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.// Generate the SQL insert statements to populate the 
.//  SQL Table and Column classes for the file_io model
.//
.//====================================================================
.//
.function attr_is_persistent
  .param inst_ref attr   .// O_ATTR
  .assign attr_result = true
  .if ( not_empty attr )
    .assign attr_result = ("$l{attr.Descrip:Persistent}" != "false") and (attr.Name != "current_state")
  .end if
.end function
.//
.///
.select any dt from instances of S_DT;
.//
.select many obj_set from instances of O_OBJ
.for each obj in obj_set
  .if ( "$l{obj.Descrip:Persistent}" != "false" )
    .assign domain_name = ""
    .select one domain related by obj->S_SS[R2]->S_DOM[R1]
    .if (not_empty domain)
      .assign domain_name = domain.Name
    .else
      .select one root_pkg related by obj->PE_PE[R8001]->EP_PKG[R8000]->PE_PE[R8001]->EP_PKG[R8000]
      .select one parent_pkg related by root_pkg->PE_PE[R8001]->EP_PKG[R8000]
      .while (not_empty parent_pkg)
        .assign root_pkg = parent_pkg
        .select one parent_pkg related by root_pkg->PE_PE[R8001]->EP_PKG[R8000]
      .end while
      .if (not_empty root_pkg)
        .assign domain_name = "${root_pkg.Name}"
      .end if
    .end if
INSERT INTO EI VALUES ( '${obj.Name}' );
INSERT INTO T VALUES ( '${obj.Name}', '${obj.Key_Lett}', '${obj.Name}', '${domain_name}' );
    .select many attr_set related by obj->O_ATTR[R102]
    .for each attr in attr_set
      .invoke aip = attr_is_persistent(attr)
      .if ( aip.result )
        .select one battr related by attr -> O_BATTR[R106]
        .if (not_empty battr)
          .select one dt related by attr -> S_DT[R114]
        .else
          .select one ba related by attr -> O_RATTR[R106] -> O_BATTR[R113]
          .select one dt related by ba -> O_ATTR[R106] -> S_DT[R114]
          .select one ra related by attr -> O_RATTR[R106]
        .end if
        .select one edt related by dt->S_EDT[R17]
        .if ( not_empty edt )
          .// represent enums as integers
          .select any dt from instances of S_DT where (selected.Name == "integer" )
        .end if
        .select one udt related by dt->S_UDT[R17]
        .if ( not_empty udt )
          .// represent user defined types as their core type
          .select one dt related by udt->S_DT[R18]
        .end if
        .select one next_attr related by attr->O_ATTR[R103.'succeeds']
        .invoke na_ip = attr_is_persistent(next_attr)
        .select one attr_after_next related by next_attr->O_ATTR[R103.'succeeds']
        .invoke aan_ip = attr_is_persistent(attr_after_next)
        .if ( empty next_attr )
INSERT INTO C VALUES ( '${obj.Name}', '${attr.Name}', '', '${dt.Name}'\
        .elif ( not na_ip.result )
          .// next attr is not persistent
          .// find the next persistent attr
          .while ((not aan_ip.result) AND ( not_empty attr_after_next ))
            .select one attr_after_next related by attr_after_next->O_ATTR[R103.'succeeds']
            .invoke aan_ip = attr_is_persistent(attr_after_next)
          .end while
          .if ( empty attr_after_next )
INSERT INTO C VALUES ( '${obj.Name}', '${attr.Name}', '', '${dt.Name}'\
          .else
INSERT INTO C VALUES ( '${obj.Name}', '${attr.Name}', '${attr_after_next.Name}', '${dt.Name}'\
          .end if
        .else 
INSERT INTO C VALUES ( '${obj.Name}', '${attr.Name}', '${next_attr.Name}', '${dt.Name}'\
        .end if
        .select any oida related by attr->O_OIDA[R105]
        .if ( empty oida )
, false\
        .else
, true\
        .end if
        .if ( empty battr )
          .select any rel related by attr->O_RATTR[R106]->O_REF[R108]->R_RGO[R111]->R_SUB[R205]->R_SUBSUP[R213]->R_REL[R206]
          .if (not_empty rel)
            .if ("${rel.Descrip:Optional}" != ""))
, true, ${rel.Descrip:Optional} );
            .else
, true, false );
            .end if
          .else
, true, false );
          .end if
        .else
, false, false );
        .end if
      .end if  
    .end for

  .end if
.end for
.//
.emit to file "ooa.pei.sql"
.//
