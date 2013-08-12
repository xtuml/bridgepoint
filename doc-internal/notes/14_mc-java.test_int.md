---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Update MC-Java.test model files 
### xtUML Project Implementation Note


1. Abstract
-----------
MC-Java.test is causing the "Your model needs to be upgraded" dialog to show
up.  This note describes the fix for this problem.

2. History
----------
None.

3. Document References
----------------------
[1] Issues 14, https://github.com/xtuml/internal/doc-internal/issues/14 

4. Background
-------------
The MC-Java.test project was recently brought from CVS to git.  After it was 
brought over and accessed with the new BridgePoint 4.0 development environement
we found that the model upgrade dialog was triggering on this project at each
application launch.

5. Requirements
---------------
5.1  The model upgrade dialog must not indicate that the MC-Java.test project
  needs to be updated.

6. Work Required
----------------
6.1. Because MC-Java does not yet handle generic packages, we do not want to 
  actually upgrade this model at this time.  In order to make the model upgrade
  checker not trigger on this project, all of the model files need to have 
  their persistence version manually updated to 7.1.6. 

7. Implementation Comments
--------------------------
None.

8. Unit Test
------------
8.1  Test for correctness:  
  - Exit BridgePoint  
  - Start BridgePoint  
  - __R__ The Model Upgrade dialog does not indicate MC-Java.test needs to be
  updated.  

9. Code Changes
---------------
Branch name:  14_skb_mcjavatest_update   
Pull request: https://github.com/xtuml/internal/pull/27/files  

<pre>
M	src/MC-Java.test/models/MC-Java.test/Datatypes/Datatypes.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Association/Association.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Association/Association/Association.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Association/Class In Association/Class In Association.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Association/Referred To Class in Assoc/Referred To Class in Assoc.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Association/Referring Class In Assoc/Referring Class In Assoc.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Datatypes/Datatypes.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/Delete Check Bad SS.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/DeleteCheckBadClass/DeleteCheckBadClass.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassBadLink/TestClassBadLink.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassOneSideBad1/TestClassOneSideBad1.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassOthSideBad1/TestClassOthSideBad1.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassOtherSideBad1/TestClassOtherSideBad1.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassReflLnkBad/TestClassReflLnkBad.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassSimpleAssocBad/TestClassSimpleAssocBad.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassSubtypeBad/TestClassSubtypeBad.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Bad SS/TestClassSupertypeBad/TestClassSupertypeBad.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/Delete Check Good SS.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/DeleteCheckGoodClass/DeleteCheckGoodClass.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassLink/TestClassLink.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassOneSide1/TestClassOneSide1.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassOthSide1/TestClassOthSide1.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassOtherSide1/TestClassOtherSide1.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassReflLnk/TestClassReflLnk.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassSimpleAssocGood/TestClassSimpleAssocGood.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassSubtypeGood/TestClassSubtypeGood.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Delete Check Good SS/TestClassSupertypeGood/TestClassSupertypeGood.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/DeleteCheck.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Functions/Functions.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Attribute Reference in Class/Attribute Reference in Class.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Attribute/Attribute.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Class Identifier Attribute/Class Identifier Attribute.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Class Identifier/Class Identifier.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Model Class/Model Class.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Referential Attribute/Referential Attribute.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Referred To Identifier Attribute/Referred To Identifier Attribute.xtuml
M	src/MC-Java.test/models/MC-Java.test/DeleteCheck/Subsystem/Subsystem.xtuml
M	src/MC-Java.test/models/MC-Java.test/MC-Java.test.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Datatypes/Datatypes.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_EE_Pkg/Verify_Selects_Test_EE_Pkg.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_Function_Pkg/Verify_Selects_Test_Function_Pkg.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_SS/Test Class 1/InstanceStateMachine/InstanceStateMachine.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_SS/Test Class 1/Test Class 1.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_SS/Test Class 2/Test Class 2.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_SS/Test Class Link 1/Test Class Link 1.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_SS/Verify_Selects_Test_Class_1/InstanceStateMachine/InstanceStateMachine.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_SS/Verify_Selects_Test_Class_1/Verify_Selects_Test_Class_1.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/Verify_Selects_Test_SS/Verify_Selects_Test_SS.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel/verify_sel.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Datatypes/Datatypes.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Link Class/Link Class.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Test Class 1/InstanceStateMachine/InstanceStateMachine.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Test Class 1/Test Class 1.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Test Class 2/Test Class 2.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Test Class 4/Test Class 4.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Test Class 5/Test Class 5.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Test Class 6/Test Class 6.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Test Class 7/Test Class 7.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality/Verify_Selects_Test_Conditionality.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality2/Test Class 1/Test Class 1.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality2/Test Class 2/Test Class 2.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality2/Test Class 3/Test Class 3.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/Verify_Selects_Test_Conditionality2/Verify_Selects_Test_Conditionality2.xtuml
M	src/MC-Java.test/models/MC-Java.test/verify_sel_cond_test/verify_sel_cond_test.xtuml

</pre>

End
---

