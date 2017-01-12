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
5.1 Add Alf, java and c to the ActionDialect enumeration.  
5.2 Detect empty services during population in the m2x processing.  Set
the dialect attribute to C instead of MASL in these cases.  
5.3 Skip the creation of MASL service files for activities that have
a dialect of C.  

6. Implementation Comments
--------------------------

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

xtuml/mc

</pre>

End
---

