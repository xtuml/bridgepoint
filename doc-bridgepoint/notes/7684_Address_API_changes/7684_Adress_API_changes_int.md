---

This work is licensed under the Creative Commons CC0 License

---

# Address eclipse 4.4 API changes
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the file changes made to address issues with the move to the   
Eclipse 4.4 base.  Additionally, it includes files changes for issue #7745 [2].   

2. Document References
----------------------
[1] https://github.com/travislondon/bridgepoint/blob/7684_Adress_API_changes/doc-bridgepoint/notes/7684_Address_API_changes/7684_Address_API_changes_dnt.md  
[2] https://github.com/keithbrown/bridgepoint/blob/7745_port_oal/doc-bridgepoint/notes/7745_port_oal/7745_port_oal_dnt.md   

3. Background
-------------
See [1].    

4. Requirements
---------------
See [1].    

5. Work Required
----------------
See [1].   

6. Implementation Comments
--------------------------
6.1 Updated preference setting in parser tests
  A number of test failures in parser tests appeared because the preference for 
  the new Port Name requirement feature is not enabled to allow current 
  (BridgePoint 5) behavior.  This work updates ```ComponentSyntaxTest_Generics.java```
  and ```TestSelectWhere_Generics.java``` to set the preference to allow interface
  names in inter-component messages for the duration of the test, then set it back
  to disallow after the test is done.   

6.2 Updated results in io.mdl.test
  The expected results were invalid for linux in some cases.  The key indicator 
  was that the expected results such as ```/git/bridgepoint/src/org.xtuml.bp.io.mdl.test/expected_results/linux/models/InteractionDiagramUpgradeTestsGenericsGlobals.xtuml```
  still had some GD_GE and S_SYS_PROXY "paths" that have com.mentor in the name 
  instead of org.xtuml. There were UUID differences also.  This issue updated 
  the expected results.  The last time the linux results were updated was over 4
  months ago when we made a pass at making all the linux JUnits work better. We 
  were still on com.mentor names at that time. The windows versions of the 
  expected results have org.xtuml paths and not com.mentor.     

6.3  During promotion of this work, the upgrade of plug-ins from old eclipse 2.x
style to eclipse 3.x style for the plug-in metadata caused an issue.  After this
process was completed, the BridgePoint build started showing a number of errors like this:  

```Access restriction: The constructor BufferedReader(Reader) is not accessible due to restriction on required library Z:\bp\BP5win\jre\lib\rt.jar```

It turns out that moving the JRE_CONTAINER line up one spot in the project ```.classpath``` 
file solves the issue.  So the ```.classpath``` changed from this:

```<?xml version="1.0" encoding="UTF-8"?>
<classpath>
 <classpathentry kind="src" path="src"/>
 <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
 <classpathentry kind="con" path="org.eclipse.pde.core.requiredPlugins"/>
 <classpathentry kind="output" path="bin"/>
</classpath>```

to this:

```<?xml version="1.0" encoding="UTF-8"?>
<classpath>
 <classpathentry kind="src" path="src"/>
 <classpathentry kind="con" path="org.eclipse.pde.core.requiredPlugins"/>
 <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
 <classpathentry kind="output" path="bin"/>
</classpath>```

7. Unit Test
------------

8. Code Changes
---------------
# NOTE Adress is misspelled
Branch name: 7684_Adress_API_changes

<pre>

doc-bridgepoint/notes/52_workflow.rvm.md
doc-bridgepoint/notes/36_Update_eclipse_base/36_Update_eclipse_base.ant.md
doc-bridgepoint/notes/7684_Address_API_changes/7684_Address_API_changes_dnt.md
doc-bridgepoint/notes/7702_Install_BridgePoint_into_eclipse_4.4/
    7702_Install_BridgePoint_into_eclipse_4.4.dnt.md
doc-bridgepoint/notes/7702_Install_BridgePoint_into_eclipse_4.4/
    7702_Install_BridgePoint_into_eclipse_4.4.int.md
doc-bridgepoint/notes/7745_port_oal/7745_port_oal_dnt.md
doc-bridgepoint/notes/7745_port_oal/Development_Unit_Test_results.txt
doc-bridgepoint/process/Developer Getting Started Guide.md
doc-bridgepoint/process/Developer Workflow.md
doc-bridgepoint/process/FAQ.md
doc-bridgepoint/process/templates/launch_configs/BP Application.launch
doc-bridgepoint/process/templates/launch_configs/BridgePoint Unit Tests.launch
doc-bridgepoint/process/templates/launch_configs/
    Core Generic Upgrade Test.launch
doc-bridgepoint/process/templates/launch_configs/Core Test.launch
doc-bridgepoint/process/templates/launch_configs/Core Test 2.launch
doc-bridgepoint/process/templates/launch_configs/
    Core Test 2 - Consistency.launch
doc-bridgepoint/process/templates/launch_configs/
    Core Test - Existing Projects.launch
doc-bridgepoint/process/templates/launch_configs/Core Test - RTO Move.launch
doc-bridgepoint/process/templates/launch_configs/
    Core Test - System Level Tests.launch
doc-bridgepoint/process/templates/launch_configs/
    Core Test - Workspace Setup.launch
doc-bridgepoint/process/templates/launch_configs/Debug - Verifier Test.launch
doc-bridgepoint/process/templates/launch_configs/Debug - Verifier Test 2.launch
doc-bridgepoint/process/templates/launch_configs/IO MDL PkgCM Tests.launch
doc-bridgepoint/process/templates/launch_configs/IO MDL Tests.launch
doc-bridgepoint/process/templates/launch_configs/IO MDL Tests 2.launch
doc-bridgepoint/process/templates/launch_configs/IO SQL Test.launch
doc-bridgepoint/process/templates/launch_configs/Model Compare Test.launch
doc-bridgepoint/process/templates/launch_configs/Parse All Test.launch
doc-bridgepoint/process/templates/launch_configs/Search Test.launch
doc-bridgepoint/process/templates/launch_configs/UI Canvas CCP Test.launch
doc-bridgepoint/process/templates/launch_configs/UI Canvas Suite 1.launch
doc-bridgepoint/process/templates/launch_configs/UI Canvas Suite 2.launch
doc-bridgepoint/process/templates/launch_configs/UI Explorer Test.launch
doc-bridgepoint/process/templates/launch_configs/UI Properties Test.launch
doc-bridgepoint/process/templates/launch_configs/UI Text Test.launch
doc-bridgepoint/process/templates/launch_configs/Welcome Test.launch
doc-bridgepoint/process/templates/launch_configs/x BridgePoint Unit Tests.launch
doc-bridgepoint/process/templates/launch_configs/
    x Core Generic Upgrade Test.launch
doc-bridgepoint/process/templates/launch_configs/x Core Test.launch
doc-bridgepoint/process/templates/launch_configs/x Core Test 2.launch
doc-bridgepoint/process/templates/launch_configs/
    x Core Test 2 - Consistency.launch
doc-bridgepoint/process/templates/launch_configs/
    x Core Test - Existing Projects.launch
doc-bridgepoint/process/templates/launch_configs/x Core Test - RTO Move.launch
doc-bridgepoint/process/templates/launch_configs/
    x Core Test - System Level Tests.launch
doc-bridgepoint/process/templates/launch_configs/
    x Core Test - Workspace Setup.launch
doc-bridgepoint/process/templates/launch_configs/x Debug - Verifier Test.launch
doc-bridgepoint/process/templates/launch_configs/
    x Debug - Verifier Test 2.launch
doc-bridgepoint/process/templates/launch_configs/x IO MDL PkgCM Tests.launch
doc-bridgepoint/process/templates/launch_configs/x IO MDL Tests.launch
doc-bridgepoint/process/templates/launch_configs/x IO MDL Tests 2.launch
doc-bridgepoint/process/templates/launch_configs/x IO SQL Test.launch
doc-bridgepoint/process/templates/launch_configs/x Model Compare Test.launch
doc-bridgepoint/process/templates/launch_configs/x Parse All Test.launch
doc-bridgepoint/process/templates/launch_configs/x Search Test.launch
doc-bridgepoint/process/templates/launch_configs/x UI Canvas CCP Test.launch
doc-bridgepoint/process/templates/launch_configs/x UI Canvas Suite 1.launch
doc-bridgepoint/process/templates/launch_configs/x UI Canvas Suite 2.launch
doc-bridgepoint/process/templates/launch_configs/x UI Explorer Test.launch
doc-bridgepoint/process/templates/launch_configs/x UI Properties Test.launch
doc-bridgepoint/process/templates/launch_configs/x UI Text Test.launch
doc-bridgepoint/process/templates/launch_configs/x Welcome Test.launch
doc-bridgepoint/qa/results/R5.1.0/7684/Unit_test_summary.txt
doc-bridgepoint/review-minutes/36_Update_eclipse_base.ant.rvm.md
doc-bridgepoint/review-minutes/7745_port_oal_dnt-rvm.md

org.xtuml.bp.als.oal/META-INF/MANIFEST.MF
org.xtuml.bp.als.oal/build.properties
org.xtuml.bp.als.oal/plugin.xml

org.xtuml.bp.als.oal.test/META-INF/MANIFEST.MF
org.xtuml.bp.als.oal.test/src/OALGlobalsTestSuite_Generics.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/
    ParseAllInDomain_Generics.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/
    TestAllowInterfaceNameInICMsg_Generics.java
org.xtuml.bp.als.oal.test/plugin.xml

org.xtuml.bp.bld.pkg/META-INF/MANIFEST.MF
org.xtuml.bp.bld.pkg/build.properties
org.xtuml.bp.bld.pkg/plugin.xml

org.xtuml.bp.compare/META-INF/MANIFEST.MF
org.xtuml.bp.compare/build.properties
org.xtuml.bp.compare/plugin.xml

org.xtuml.bp.core/arc/create_core_plugin.inc
org.xtuml.bp.core/META-INF/MANIFEST.MF
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/
    OAL Validation Functions/OAL Validation Functions.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/
    State Machine/State Machine.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/
    State Machine Event/State Machine Event.xtuml
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/
    ActionLanguagePreferences.java
org.xtuml.bp.core/build.properties

org.xtuml.bp.core.test/arc/create_action_test.arc
org.xtuml.bp.core.test/expected_results/DeleteTest_test_1/DeleteTest_test_1-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_1/
    DeleteTest_test_1-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_10/
    DeleteTest_test_10-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_10/
    DeleteTest_test_10-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_11/
    DeleteTest_test_11-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_11/
    DeleteTest_test_11-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_12/
    DeleteTest_test_12-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_12/
    DeleteTest_test_12-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_13/
    DeleteTest_test_13-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_13/
    DeleteTest_test_13-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_14/
    DeleteTest_test_14-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_14/
    DeleteTest_test_14-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_15/
    DeleteTest_test_15-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_15/
    DeleteTest_test_15-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_16/
    DeleteTest_test_16-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_16/
    DeleteTest_test_16-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_17/
    DeleteTest_test_17-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_17/
    DeleteTest_test_17-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_18/
    DeleteTest_test_18-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_18/
    DeleteTest_test_18-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_19/
    DeleteTest_test_19-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_19/
    DeleteTest_test_19-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_2/DeleteTest_test_2-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_2/
    DeleteTest_test_2-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_20/
    DeleteTest_test_20-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_20/
    DeleteTest_test_20-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_21/
    DeleteTest_test_21-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_21/
    DeleteTest_test_21-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_22/
    DeleteTest_test_22-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_22/
    DeleteTest_test_22-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_23/
    DeleteTest_test_23-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_23/
    DeleteTest_test_23-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_24/
    DeleteTest_test_24-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_24/
    DeleteTest_test_24-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_25/
    DeleteTest_test_25-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_25/
    DeleteTest_test_25-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_26/
    DeleteTest_test_26-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_26/
    DeleteTest_test_26-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_27/
    DeleteTest_test_27-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_27/
    DeleteTest_test_27-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_28/
    DeleteTest_test_28-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_28/
    DeleteTest_test_28-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_29/
    DeleteTest_test_29-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_29/
    DeleteTest_test_29-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_3/DeleteTest_test_3-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_3/
    DeleteTest_test_3-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_30/
    DeleteTest_test_30-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_30/
    DeleteTest_test_30-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_31/
    DeleteTest_test_31-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_31/
    DeleteTest_test_31-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_32/
    DeleteTest_test_32-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_32/
    DeleteTest_test_32-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_33/
    DeleteTest_test_33-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_33/
    DeleteTest_test_33-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_34/
    DeleteTest_test_34-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_34/
    DeleteTest_test_34-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_35/
    DeleteTest_test_35-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_35/
    DeleteTest_test_35-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_36/
    DeleteTest_test_36-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_36/
    DeleteTest_test_36-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_37/
    DeleteTest_test_37-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_37/
    DeleteTest_test_37-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_38/
    DeleteTest_test_38-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_38/
    DeleteTest_test_38-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_39/
    DeleteTest_test_39-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_39/
    DeleteTest_test_39-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_4/DeleteTest_test_4-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_4/
    DeleteTest_test_4-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_40/
    DeleteTest_test_40-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_40/
    DeleteTest_test_40-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_41/
    DeleteTest_test_41-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_41/
    DeleteTest_test_41-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_42/
    DeleteTest_test_42-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_42/
    DeleteTest_test_42-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_43/
    DeleteTest_test_43-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_43/
    DeleteTest_test_43-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_44/
    DeleteTest_test_44-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_44/
    DeleteTest_test_44-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_45/
    DeleteTest_test_45-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_45/
    DeleteTest_test_45-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_46/
    DeleteTest_test_46-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_46/
    DeleteTest_test_46-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_47/
    DeleteTest_test_47-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_47/
    DeleteTest_test_47-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_48/
    DeleteTest_test_48-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_48/
    DeleteTest_test_48-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_49/
    DeleteTest_test_49-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_49/
    DeleteTest_test_49-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_5/DeleteTest_test_5-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_5/
    DeleteTest_test_5-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_50/
    DeleteTest_test_50-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_50/
    DeleteTest_test_50-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_51/
    DeleteTest_test_51-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_51/
    DeleteTest_test_51-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_52/
    DeleteTest_test_52-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_52/
    DeleteTest_test_52-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_53/
    DeleteTest_test_53-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_53/
    DeleteTest_test_53-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_54/
    DeleteTest_test_54-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_54/
    DeleteTest_test_54-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_55/
    DeleteTest_test_55-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_55/
    DeleteTest_test_55-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_56/
    DeleteTest_test_56-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_56/
    DeleteTest_test_56-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_57/
    DeleteTest_test_57-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_57/
    DeleteTest_test_57-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_6/DeleteTest_test_6-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_6/
    DeleteTest_test_6-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_7/DeleteTest_test_7-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_7/
    DeleteTest_test_7-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_8/DeleteTest_test_8-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_8/
    DeleteTest_test_8-112.jpg
org.xtuml.bp.core.test/expected_results/DeleteTest_test_9/DeleteTest_test_9-112
org.xtuml.bp.core.test/expected_results/DeleteTest_test_9/
    DeleteTest_test_9-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_55/
    FormalizeUnformalize_test_55-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_55/
    FormalizeUnformalize_test_55-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_56/
    FormalizeUnformalize_test_56-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_56/
    FormalizeUnformalize_test_56-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_57/
    FormalizeUnformalize_test_57-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_57/
    FormalizeUnformalize_test_57-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_58/
    FormalizeUnformalize_test_58-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_58/
    FormalizeUnformalize_test_58-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_59/
    FormalizeUnformalize_test_59-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_59/
    FormalizeUnformalize_test_59-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_60/
    FormalizeUnformalize_test_60-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalize_test_60/
    FormalizeUnformalize_test_60-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_45/
    FormalizeUnformalizeWithPrefix_test_45-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_45/
    FormalizeUnformalizeWithPrefix_test_45-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_46/
    FormalizeUnformalizeWithPrefix_test_46-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_46/
    FormalizeUnformalizeWithPrefix_test_46-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_47/
    FormalizeUnformalizeWithPrefix_test_47-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_47/
    FormalizeUnformalizeWithPrefix_test_47-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_48/
    FormalizeUnformalizeWithPrefix_test_48-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_48/
    FormalizeUnformalizeWithPrefix_test_48-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_49/
    FormalizeUnformalizeWithPrefix_test_49-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_49/
    FormalizeUnformalizeWithPrefix_test_49-112.jpg
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_50/
    FormalizeUnformalizeWithPrefix_test_50-112
org.xtuml.bp.core.test/expected_results/FormalizeUnformalizeWithPrefix_test_50/
    FormalizeUnformalizeWithPrefix_test_50-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_10/
    CombineSplitReferentials_10-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_10/
    CombineSplitReferentials_10-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_11/
    CombineSplitReferentials_11-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_11/
    CombineSplitReferentials_11-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_12/
    CombineSplitReferentials_12-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_12/
    CombineSplitReferentials_12-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_13/
    CombineSplitReferentials_13-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_13/
    CombineSplitReferentials_13-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_14/
    CombineSplitReferentials_14-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_14/
    CombineSplitReferentials_14-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_15/
    CombineSplitReferentials_15-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_15/
    CombineSplitReferentials_15-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_16/
    CombineSplitReferentials_16-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_16/
    CombineSplitReferentials_16-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_17/
    CombineSplitReferentials_17-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_17/
    CombineSplitReferentials_17-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_4/
    CombineSplitReferentials_4-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_4/
    CombineSplitReferentials_4-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_5/
    CombineSplitReferentials_5-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_5/
    CombineSplitReferentials_5-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_6/
    CombineSplitReferentials_6-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_6/
    CombineSplitReferentials_6-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_7/
    CombineSplitReferentials_7-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_7/
    CombineSplitReferentials_7-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_8/
    CombineSplitReferentials_8-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_8/
    CombineSplitReferentials_8-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_9/
    CombineSplitReferentials_9-112
org.xtuml.bp.core.test/expected_results/linux/CombineSplitReferentials_9/
    CombineSplitReferentials_9-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_1/
    CommunicationLinkTest_1-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_1/
    CommunicationLinkTest_1-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_10/
    CommunicationLinkTest_10-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_10/
    CommunicationLinkTest_10-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_2/
    CommunicationLinkTest_2-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_2/
    CommunicationLinkTest_2-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_3/
    CommunicationLinkTest_3-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_3/
    CommunicationLinkTest_3-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_4/
    CommunicationLinkTest_4-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_4/
    CommunicationLinkTest_4-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_5/
    CommunicationLinkTest_5-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_5/
    CommunicationLinkTest_5-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_6/
    CommunicationLinkTest_6-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_6/
    CommunicationLinkTest_6-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_7/
    CommunicationLinkTest_7-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_7/
    CommunicationLinkTest_7-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_8/
    CommunicationLinkTest_8-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_8/
    CommunicationLinkTest_8-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_9/
    CommunicationLinkTest_9-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationLinkTest_9/
    CommunicationLinkTest_9-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_1/
    CommunicationMessageTest_1-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_1/
    CommunicationMessageTest_1-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_10/
    CommunicationMessageTest_10-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_10/
    CommunicationMessageTest_10-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_11/
    CommunicationMessageTest_11-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_11/
    CommunicationMessageTest_11-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_12/
    CommunicationMessageTest_12-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_12/
    CommunicationMessageTest_12-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_13/
    CommunicationMessageTest_13-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_13/
    CommunicationMessageTest_13-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_14/
    CommunicationMessageTest_14-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_14/
    CommunicationMessageTest_14-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_15/
    CommunicationMessageTest_15-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_15/
    CommunicationMessageTest_15-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_16/
    CommunicationMessageTest_16-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_16/
    CommunicationMessageTest_16-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_17/
    CommunicationMessageTest_17-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_17/
    CommunicationMessageTest_17-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_18/
    CommunicationMessageTest_18-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_18/
    CommunicationMessageTest_18-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_2/
    CommunicationMessageTest_2-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_2/
    CommunicationMessageTest_2-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_3/
    CommunicationMessageTest_3-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_3/
    CommunicationMessageTest_3-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_4/
    CommunicationMessageTest_4-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_4/
    CommunicationMessageTest_4-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_5/
    CommunicationMessageTest_5-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_5/
    CommunicationMessageTest_5-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_6/
    CommunicationMessageTest_6-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_6/
    CommunicationMessageTest_6-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_7/
    CommunicationMessageTest_7-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_7/
    CommunicationMessageTest_7-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_8/
    CommunicationMessageTest_8-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_8/
    CommunicationMessageTest_8-112.jpg
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_9/
    CommunicationMessageTest_9-112
org.xtuml.bp.core.test/expected_results/linux/CommunicationMessageTest_9/
    CommunicationMessageTest_9-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_45/
    FormalizeUnformalizeWithPrefix_test_45-112
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_45/
    FormalizeUnformalizeWithPrefix_test_45-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_46/
    FormalizeUnformalizeWithPrefix_test_46-112
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_46/
    FormalizeUnformalizeWithPrefix_test_46-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_47/
    FormalizeUnformalizeWithPrefix_test_47-112
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_47/
    FormalizeUnformalizeWithPrefix_test_47-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_48/
    FormalizeUnformalizeWithPrefix_test_48-112
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_48/
    FormalizeUnformalizeWithPrefix_test_48-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_49/
    FormalizeUnformalizeWithPrefix_test_49-112
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_49/
    FormalizeUnformalizeWithPrefix_test_49-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_50/
    FormalizeUnformalizeWithPrefix_test_50-112
org.xtuml.bp.core.test/expected_results/linux/
    FormalizeUnformalizeWithPrefix_test_50/
    FormalizeUnformalizeWithPrefix_test_50-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    ModifyNonFullyLoadedModel_Generic1/ModifyNonFullyLoadedModel_Generic1-112
org.xtuml.bp.core.test/expected_results/linux/
    ModifyNonFullyLoadedModel_Generic1/
    ModifyNonFullyLoadedModel_Generic1-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    ModifyNonFullyLoadedModel_Generic1/
    ModifyNonFullyLoadedModel_Generic1-shape_deletion_transaction_generics.exp
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_1/SequenceTest_1-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_1/
    SequenceTest_1-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_10/
    SequenceTest_10-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_10/
    SequenceTest_10-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_11/
    SequenceTest_11-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_11/
    SequenceTest_11-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_12/
    SequenceTest_12-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_12/
    SequenceTest_12-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_13/
    SequenceTest_13-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_13/
    SequenceTest_13-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_14/
    SequenceTest_14-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_14/
    SequenceTest_14-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_15/
    SequenceTest_15-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_15/
    SequenceTest_15-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_16/
    SequenceTest_16-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_16/
    SequenceTest_16-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_17/
    SequenceTest_17-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_17/
    SequenceTest_17-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_18/
    SequenceTest_18-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_18/
    SequenceTest_18-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_19/
    SequenceTest_19-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_19/
    SequenceTest_19-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_2/SequenceTest_2-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_2/
    SequenceTest_2-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_20/
    SequenceTest_20-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_20/
    SequenceTest_20-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_21/
    SequenceTest_21-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_21/
    SequenceTest_21-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_22/
    SequenceTest_22-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_22/
    SequenceTest_22-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_23/
    SequenceTest_23-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_23/
    SequenceTest_23-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_24/
    SequenceTest_24-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_24/
    SequenceTest_24-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_25/
    SequenceTest_25-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_25/
    SequenceTest_25-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_26/
    SequenceTest_26-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_26/
    SequenceTest_26-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_27/
    SequenceTest_27-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_27/
    SequenceTest_27-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_28/
    SequenceTest_28-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_28/
    SequenceTest_28-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_29/
    SequenceTest_29-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_29/
    SequenceTest_29-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_3/SequenceTest_3-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_3/
    SequenceTest_3-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_30/
    SequenceTest_30-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_30/
    SequenceTest_30-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_31/
    SequenceTest_31-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_31/
    SequenceTest_31-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_32/
    SequenceTest_32-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_32/
    SequenceTest_32-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_33/
    SequenceTest_33-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_33/
    SequenceTest_33-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_34/
    SequenceTest_34-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_34/
    SequenceTest_34-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_35/
    SequenceTest_35-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_35/
    SequenceTest_35-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_36/
    SequenceTest_36-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_36/
    SequenceTest_36-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_37/
    SequenceTest_37-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_37/
    SequenceTest_37-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_38/
    SequenceTest_38-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_38/
    SequenceTest_38-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_39/
    SequenceTest_39-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_39/
    SequenceTest_39-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_4/SequenceTest_4-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_4/
    SequenceTest_4-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_40/
    SequenceTest_40-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_40/
    SequenceTest_40-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_41/
    SequenceTest_41-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_41/
    SequenceTest_41-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_42/
    SequenceTest_42-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_42/
    SequenceTest_42-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_5/SequenceTest_5-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_5/
    SequenceTest_5-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_6/SequenceTest_6-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_6/
    SequenceTest_6-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_7/SequenceTest_7-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_7/
    SequenceTest_7-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_8/SequenceTest_8-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_8/
    SequenceTest_8-112.jpg
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_9/SequenceTest_9-112
org.xtuml.bp.core.test/expected_results/linux/SequenceTest_9/
    SequenceTest_9-112.jpg
org.xtuml.bp.core.test/expected_results/linux/
    State_Machine_Event_Data_Item_Integrity/
    State_Machine_Event_Data_Item_Integrity.txt
org.xtuml.bp.core.test/expected_results/linux/State_Machine_Event_Integrity/
    State_Machine_Event_Integrity.txt
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    activity_editor_transaction.exp
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    delete_transaction_generics.exp
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    editor_transaction.exp
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    formalize_transaction.exp
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    menu_item_useraction_transaction.exp
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    property_transaction.exp
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    rename_transaction.exp
org.xtuml.bp.core.test/expected_results/linux/TransactionTest/
    shape_creation_transaction_generics.exp
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-after_delete
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-after_delete.jpg
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-after_delete2
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-after_delete2.jpg
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-before_delete-1
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-before_delete-1.jpg
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-before_delete-2
org.xtuml.bp.core.test/expected_results/linux/UndoRedo_1/
    UndoRedo_1-112-before_delete-2.jpg
org.xtuml.bp.core.test/expected_results/
    State_Machine_Event_Data_Item_Integrity/
    State_Machine_Event_Data_Item_Integrity.txt
org.xtuml.bp.core.test/expected_results/State_Machine_Event_Integrity/
    State_Machine_Event_Integrity.txt
org.xtuml.bp.core.test/META-INF/MANIFEST.MF
org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/
    ModelTransactionTestGenerics.java
org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/
    ModifyNonFullyLoadedModelTestsGenerics.java
org.xtuml.bp.core.test/build.properties
org.xtuml.bp.core.test/plugin.xml

org.xtuml.bp.dap.pkg/META-INF/MANIFEST.MF
org.xtuml.bp.dap.pkg/build.properties
org.xtuml.bp.dap.pkg/plugin.xml

org.xtuml.bp.debug.ui/META-INF/MANIFEST.MF
org.xtuml.bp.debug.ui/src/org/xtuml/bp/debug/java/access/
    VerifierInvocationAuditor.java
org.xtuml.bp.debug.ui/src/org/xtuml/bp/debug/ui/launch/
    VerifierLaunchConfiguration.java
org.xtuml.bp.debug.ui/src/org/xtuml/bp/debug/ui/model/BPDebugTarget.java
org.xtuml.bp.debug.ui/build.properties
org.xtuml.bp.debug.ui/plugin.xml

org.xtuml.bp.debug.ui.test/expected_results/verifier/
    Verifier_event_specification_statements_test.result
org.xtuml.bp.debug.ui.test/META-INF/MANIFEST.MF

org.xtuml.bp.doc/META-INF/MANIFEST.MF
org.xtuml.bp.doc/build.properties
org.xtuml.bp.doc/plugin.xml

org.xtuml.bp.docgen/META-INF/MANIFEST.MF
org.xtuml.bp.docgen/build.properties
org.xtuml.bp.docgen/plugin.xml

org.xtuml.bp.internal.tools/META-INF/MANIFEST.MF
org.xtuml.bp.internal.tools/build.properties
org.xtuml.bp.internal.tools/plugin.xml

org.xtuml.bp.io.core/META-INF/MANIFEST.MF
org.xtuml.bp.io.core/build.properties
org.xtuml.bp.io.core/plugin.xml

org.xtuml.bp.io.image/META-INF/MANIFEST.MF
org.xtuml.bp.io.image/build.properties
org.xtuml.bp.io.image/plugin.xml

org.xtuml.bp.io.mdl/META-INF/MANIFEST.MF
org.xtuml.bp.io.mdl/build.properties
org.xtuml.bp.io.mdl/plugin.xml

org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteDTPInPkg_ThruRN_CanvasFocused/
    testDeleteDTPInPkg_ThruRN_CanvasFocused-112
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteDTPInPkg_ThruRN_CanvasFocused/
    testDeleteDTPInPkg_ThruRN_CanvasFocused-112.jpg
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteDTPInPkg_ThruRN_DescriptionFocused/
    testDeleteDTPInPkg_ThruRN_DescriptionFocused-112
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteDTPInPkg_ThruRN_DescriptionFocused/
    testDeleteDTPInPkg_ThruRN_DescriptionFocused-112.jpg
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteEEP_ThruRN_CanvasFocused/testDeleteEEP_ThruRN_CanvasFocused-112
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteEEP_ThruRN_CanvasFocused/
    testDeleteEEP_ThruRN_CanvasFocused-112.jpg
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteEEP_ThruRN_DescriptionFocused/
    testDeleteEEP_ThruRN_DescriptionFocused-112
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteEEP_ThruRN_DescriptionFocused/
    testDeleteEEP_ThruRN_DescriptionFocused-112.jpg
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteMclass_ThruRN_DescriptionFocused/
    testDeleteMclass_ThruRN_DescriptionFocused-112
org.xtuml.bp.io.mdl.test/expected_results/linux/
    testDeleteMclass_ThruRN_DescriptionFocused/
    testDeleteMclass_ThruRN_DescriptionFocused-112.jpg
org.xtuml.bp.io.mdl.test/META-INF/MANIFEST.MF
org.xtuml.bp.io.mdl.test/build.properties
org.xtuml.bp.io.mdl.test/plugin.xml

org.xtuml.bp.io.sql/META-INF/MANIFEST.MF
org.xtuml.bp.io.sql/build.properties
org.xtuml.bp.io.sql/plugin.xml

org.xtuml.bp.io.sql.test/.settings/org.eclipse.core.resources.prefs
org.xtuml.bp.io.sql.test/META-INF/MANIFEST.MF
org.xtuml.bp.io.sql.test/build.properties
org.xtuml.bp.io.sql.test/plugin.xml

org.xtuml.bp.mc.xmiexport/META-INF/MANIFEST.MF
org.xtuml.bp.mc.xmiexport/build.properties
org.xtuml.bp.mc.xmiexport/plugin.xml

org.xtuml.bp.model.compare/META-INF/MANIFEST.MF
org.xtuml.bp.model.compare/build.properties
org.xtuml.bp.model.compare/plugin.xml

org.xtuml.bp.pkg/META-INF/MANIFEST.MF
org.xtuml.bp.pkg/build.properties
org.xtuml.bp.pkg/plugin_customization.ini
org.xtuml.bp.pkg/plugin.xml

org.xtuml.bp.qa.odometer/META-INF/MANIFEST.MF

org.xtuml.bp.test/META-INF/MANIFEST.MF
org.xtuml.bp.test/src/org/xtuml/bp/test/common/BaseTest.java
org.xtuml.bp.test/build.properties
org.xtuml.bp.test/plugin.xml

org.xtuml.bp.ui.canvas/META-INF/MANIFEST.MF
org.xtuml.bp.ui.canvas/build.properties
org.xtuml.bp.ui.canvas/plugin.xml

org.xtuml.bp.ui.canvas.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasTest.java
org.xtuml.bp.ui.canvas.test/build.properties
org.xtuml.bp.ui.canvas.test/plugin.xml

org.xtuml.bp.ui.explorer/META-INF/MANIFEST.MF
org.xtuml.bp.ui.explorer/build.properties
org.xtuml.bp.ui.explorer/plugin.xml

org.xtuml.bp.ui.explorer.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.explorer.test/build.properties
org.xtuml.bp.ui.explorer.test/plugin.xml

org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/editor/
    CanvasDummyIFile.java

org.xtuml.bp.ui.properties/META-INF/MANIFEST.MF
org.xtuml.bp.ui.properties/build.properties
org.xtuml.bp.ui.properties/plugin.xml

org.xtuml.bp.ui.properties.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.properties.test/build.properties
org.xtuml.bp.ui.properties.test/plugin.xml

org.xtuml.bp.ui.sem/META-INF/MANIFEST.MF
org.xtuml.bp.ui.sem/build.properties
org.xtuml.bp.ui.sem/plugin.xml

org.xtuml.bp.ui.session/META-INF/MANIFEST.MF
org.xtuml.bp.ui.session/build.properties
org.xtuml.bp.ui.session/plugin.xml

org.xtuml.bp.ui.text/arc/create_plugin_xml.arc
org.xtuml.bp.ui.text/META-INF/MANIFEST.MF
org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/placeholder/PlaceHolderEntry.java
org.xtuml.bp.ui.text/build.properties

org.xtuml.bp.ui.text.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.text.test/build.properties
org.xtuml.bp.ui.text.test/plugin.xml

org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/ui/ProjectUtilities.java

org.xtuml.bp.verifier.pkg/META-INF/MANIFEST.MF
org.xtuml.bp.verifier.pkg/build.properties
org.xtuml.bp.verifier.pkg/plugin.xml

org.xtuml.bp.welcome/META-INF/MANIFEST.MF
org.xtuml.bp.welcome/models/GPS Watch/models/GPS Watch/Library/
    HeartRateMonitor/HeartRateMonitor/HeartRateMonitor/ClassStateMachine/
    ClassStateMachine.xtuml
org.xtuml.bp.welcome/models/GPS Watch/models/GPS Watch/Library/Location/
    Location/GPS/ClassStateMachine/ClassStateMachine.xtuml
org.xtuml.bp.welcome/models/GPS Watch/models/GPS Watch/Library/Tracking/
    Tracking/WorkoutTimer/WorkoutTimer.xtuml
org.xtuml.bp.welcome/src/org/xtuml/bp/welcome/cheatsheets/library/
    AssignEvent.java
org.xtuml.bp.welcome/src/org/xtuml/bp/welcome/cheatsheets/library/
    LinkWithEditor.java
org.xtuml.bp.welcome/.classpath
org.xtuml.bp.welcome/build.properties
org.xtuml.bp.welcome/plugin.xml

org.xtuml.internal.test/META-INF/MANIFEST.MF
org.xtuml.internal.test/build.properties
org.xtuml.internal.test/plugin.xml



</pre>

End
---

