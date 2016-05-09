---

This work is licensed under the Creative Commons CC0 License

---

# 8466- States/events missing parameters
# 8450 - parameters are alpha sorted on model load
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes two changes that provide fixes for issues with the MASL version
of the BridgePoint tool.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8466](https://support.onefact.net/issues/8466) states/events missing parameters  
<a id="2.2"></a>2.2 [BridgePoint DEI #8450](https://support.onefact.net/issues/8450) parameters are alpha sorted on model load    
<a id="2.3"></a>2.3 [BridgePoint DEI #8474](https://support.onefact.net/issues/8474) MASL editor opens by default  

3. Background
-------------
In MASL, states and events have formal parameters. These are not being rendered.
MASL follows the same data rule, so in maslin, we only need to add parameters 
for events and then on maslout infer the signature of the states based on the 
transitions. This is done in maslout, but needs to be implemented in maslin. [[2.1]](#2.1)  

After model import, file order is still right but the reflexive relationship has
been reordered to be alphabetical. [[2.2]](#2.2)  We looked into the import 
flow and found the precedes/succeeds relationship is not getting created because
it always hits upgrade code for order. There is a model load bug.  

4. Requirements
---------------
4.1  Parameter shall not be automatically alpha-sorted on import.  
4.2  BridgePoint shall find the default action language editor to use in the 
  preferences and not always default to MASL editor.  

5. Work Required
----------------
5.1  Fix upgrade code   
5.1.1  Remove dead upgrade code that affects relationships on load. Some unique 
  ids are not in the proper UUID format, so in order to upgrade gracefully, BP 
  replaces the unique ids with the null id. This is causing the parameter order 
  when importing MASL models to be sorted alphanumerically.   

5.1.2  Additionally, part of the upgrade code messes up the relationship between
  State Machine Event and State Machine Event Data Item. This was introduced 
  when upgrading from the supplemental data way of doing event data. This is 
  also no longer needed and can be removed.

6. Implementation Comments
--------------------------
6.1 During testing of BridgePoint 5.3.4 we noticed that the MASL editor is
  opened by default for new models.  This is not the desired behavior and an
  issue was opened [[2.3]](#2.3).   
  
  Investigation showed that this was due to some debugging code being promoted. 
  The debug code is removed and the proper functional code put into place.     

7. Unit Test
------------
7.1  Run the round trip "convert/import/export/diff" test on SAC and PSC. Verify
  event and function paramter ordering is correct.
7.2  IO MDL JUnit tests shall pass at the same level or better than the testing
  branch.

8. User Documentation
---------------------
None.  

9. Code Changes
---------------
Repository:  bridgepoint
Branch name: leviathan747:8466_parameters

<pre>

src/org.xtuml.bp.io.core/arc/gen_import_java.inc
src/org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc


</pre>

End
---

