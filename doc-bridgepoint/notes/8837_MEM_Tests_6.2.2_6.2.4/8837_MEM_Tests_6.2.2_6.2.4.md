---

This work is licensed under the Creative Commons CC0 License

---

# Move refactoring logic to occur during attribute value change
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes a change to move refactoring logic to the attribute change location.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9336](https://support.onefact.net/issues/9336) Investigate and resolve Model Element move problems from during 6.2.2 and 6.2.4 testing   
<a id="2.2"></a>2.2 [BridgePoint DEI #8837](https://support.onefact.net/issues/8837) Model Element Move (cut/paste) testing  

3. Background
-------------
Work done to allow MASL updates to be seen by the xtUML tool has triggered issues with normal xtUML behavior.

4. Requirements
---------------
4.1 The MEM tests here, [MEM test spreadsheet](https://docs.google.com/spreadsheets/d/1eJmEWtx3EDawwCslxL2MfvaqoJm8JawFnoCTLPuX9SM/edit#gid=1793892663), shall pass with changes from this issue merged into the 6.2.2 branch.   
4.2 The MEM tests here, [MEM test spreadsheet](https://docs.google.com/spreadsheets/d/1eJmEWtx3EDawwCslxL2MfvaqoJm8JawFnoCTLPuX9SM/edit#gid=1793892663), shall pass with changes from this issue merged into the 6.2.4 branch.   

5. Work Required
----------------
5.1 Move refactor logic into listener rename location   

The initial logic was set in the start of the listener, before determining what type of change it was.  This was causing the refactor to run when unnecessary.  The refactor logic was moved under the location which checks for an attribute value change (all name changes).  Additionally, the RenameActionUtil.java class was modified to support determining the attribute value name.  This class defaults to return "Name", but is not an issue as it checks the attribute value changes name and if not "Name" or others it will not trigger the code.    

5.2 Modify coloring to mark C_I as a component root   

Core uses coloring to modify the way certain elements are treated in the tool.  Issue 8923 removed a group definitions for specialized packages.  This incorrectly included C_I, as it is a component root.  This was added back for this work as the marking allows for all necessary events to occur.   

6. Implementation Comments
--------------------------

7. Unit Test
------------
Run the MEM test suite.   

8. User Documentation
---------------------
   

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon
Branch: 8837_mem_6.2.2_6.2.4_2

<pre>

src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentTransactionListener.java
src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/RenameActionUtil.java

</pre>

End
---

