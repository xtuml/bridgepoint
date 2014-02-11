.//====================================================================
.//
.// File:      $RCSfile: gen_cme_test_skel.arc,v $
.// Version:   $Revision: 1.10 $
.// Modified:  $Date: 2013/01/10 22:49:22 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//====================================================================
Manual unit tests for Context Menu Functionality

.select many cmes from instances of CME
.for each cme in cmes
 .if ( cme.Specialism == "Rename" )
- Right click on ${cme.Key_Lett} and select ${cme.Specialism}, enter a new name.
R ${cme.Key_Lett} is renamed.

 .end if
 .if ( cme.Specialism == "Delete" )
- Right click on ${cme.Key_Lett} and select ${cme.Specialism}.
R ${cme.Key_Lett} is deleted.

 .end if
 .if ( cme.Specialism == "New" )
- Right click on ${cme.Key_Lett} and select ${cme.Specialism} ${cme.Label}.
R A new ${cme.Label} with the name 'Unnamed ${cme.Label}' is created under ${cme.Key_Lett}.

 .end if
.end for
.emit to file "cme_unit_tests.txt"