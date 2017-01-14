---

This work is licensed under the Creative Commons CC0 License

---

# Add C Dialect to Support Wrapper Domains
### xtUML Project Implementation Note


1. Abstract
-----------
MASL services can be realized as C/C++.  Provide a means of marking these
services with the specific dialect.

2. Document References
----------------------
[1] [8962](https://support.onefact.net/issues/8962) - Incorrect import of wrapper domains  
[2] `xtuml/mc/model/maslin/test_data/8962_test`
[3] [8986](https://support.onefact.net/issues/8986) - Annotate MASL models to communicate to Import  

3. Background
-------------
This issue is more about taking advantage of the new Dialect attribute
on action homes than about supporting MASL "wrapper" domains.  MASL wrapper
domains are simply another name for marking linkage to functionality
realized as a different language.  BridgePoint could use marking that is
separate from the model, but often these dialects are actually part of
the model.  To some extent, BridgePoint allows C and Java and MASL and OAL
to coexist.  This work formalizes this more cleanly.

4. Requirements
---------------
4.1 Support C as a specified action dialect.  
4.1.1 While making this change, add support for Alf and Java.  
4.2 Import  
4.2.1 Upon import of masl into xtUML, detect empty MASL services and
set the Dialect for the `S_SYNC` to C.  
4.2 Export  
4.2.1 Upon export of xtUML to MASL, detect C dialect services and omit
generating a service file for these activities.  

5. Work Required
----------------
5.1 Add Alf, java and c to the ActionDialect enumeration in both ooaofooa
and mcooa.  
5.2 Detect empty services during population in the m2x processing.  Set
the dialect attribute to C instead of MASL in these cases.  
5.3 Skip the creation of MASL service files for activities that have
a dialect of C (or not MASL).  

6. Implementation Comments
--------------------------
6.1 `"masl_temporary"`  
Changed a constant string from `"temporary"` to `"masl_temporary"` for
consistency reasons.  This was a small update as follow-on to issue [3].

6.2 m2x crash  
A missing or misnamed domain caused m2x to crash.  This was found to be
a selection by name that returned empty when transformDomainFunction was
called on a service file with incorrectly domain qualifier.  A trace
has been added.  The code will still crash.

6.3 x2m Empty Handles  
```
UserEmptyHandleDetectedCallout element masl_element_R3784_Unlink_has_current.
UserEmptyHandleDetectedCallout element masl_element_R3787_Link_child_of.
UserEmptyHandleDetectedCallout element masl_element_R3784_Link_has_current.
```
Fixed these by testing for empty handles appropriately.

7. Unit Test
------------
7.1 Convert  
Convert the test model [2].  
7.2 Import  
Import the resulting model into BridgePoint.  
7.3 View  
View the model and see the correct class diagram.  An image of the
class diagram can be found in the issue [1].  
7.4 Export  
Export MASL Domain from BridgePoint Model Explorer.  
7.5 View  
View the exported domain and verify that only one service file was
generated.  

8. Code Changes
---------------
<pre>

Fork: cortlandstarrett/bridgepoint 8962_wrapper
      cortlandstarrett/mc 8962_c_dialect

xtuml/bridgepoint
 doc-bridgepoint/notes/8962_c_dialect_int.md                       | 119 ++++++++++++++++++++++++++++++++++++
 .../models/org.xtuml.bp.core/ooaofooa/Datatypes/Datatypes.xtuml   |  21 +++++++
 2 files changed, 140 insertions(+)

xtuml/mc
 bin/m2x                                                           | Bin 646657 -> 650753 bytes
 bin/masl                                                          | Bin 239623 -> 239586 bytes
 bin/x2m                                                           | Bin 680533 -> 680533 bytes
 model/masl/gen/masl_population_class.c                            |  18 ++++++---
 model/masl/models/masl/maslpopulation/file/file.xtuml             |  60 ++++++++++++++--------------
 model/masl/models/masl/maslpopulation/population/population.xtuml |  14 ++++---
 model/maslin/gen/masl2xtuml_ooapopulation_class.c                 |  19 ++++++---
 model/maslin/models/maslin/m2x/ooapopulation/ooapopulation.xtuml  |  16 ++++++--
 model/maslin/test_data/8962_test/actualmaslroutine.svc            |   5 +++
 model/maslin/test_data/8962_test/mydom.mod                        |  12 ++++++
 model/maslin/test_data/8962_test/mydom.smasl                      |  28 +++++++++++++
 model/maslout/models/maslout/lib/xtuml2masl/maslout/maslout.xtuml |  12 +++---
 model/mcooa/models/mcooa/ooaofooa/Datatypes/Datatypes.xtuml       |  21 ++++++++++
 13 files changed, 156 insertions(+), 61 deletions(-)

</pre>

End
---

