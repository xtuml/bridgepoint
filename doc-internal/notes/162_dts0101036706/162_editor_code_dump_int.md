---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Dump BridgePoint Code into xtuml/editor Repository
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work to be done to quickly make BridgePoint code available in the Open Source repository.

2. Document References
----------------------
[1] Issues 162, https://github.com/xtuml/internal/issues/162  
[2] CQ DEI dts0101036706 - Place more code into public repository  
[3] 162_editor_code_dump_dnt.md  
[4] https://github.com/xtuml/editor/blob/master/doc-editor/HOWTO/HOWTO-build-xtumlEditor.md  

3. Background
-------------
See [3].

4. Requirements
---------------
See [3]

5. Work Performed
----------------
5.1 [3 §6.1.1] Create a branch of xtuml/internal
  - Created new branch named ```162_oss_prep```
  
5.2 [3 §6.1.2] Extract the model data from bp.core/ooaofooa as specified in [3 §5.2]
  - Removed Breakpoint SS, Engine SS, Local SS, and Runtime Value SS.  This removed some referential
  attributes from some classes under Instance SS, which is OK.
  - Ran Parse All, remove content of functions with parse errors due to missing classes from removed subsystems.  Leave comment:
  
``` 
// Mentor Graphics Verifier-specific Implementation
return GD::NULL_UNIQUE_ID(); 
or 
// Mentor Graphics Verifier-specific Implementation
return GD::NULL_INSTANCE();
```

  - Re-ran Parse All Activities until ooaofooa parsed clean
  - Edited java native functions in the model, pasted in the verifier-specific comment from above
    - ```Value/BinaryOperation.compareInstRefSets()```
    - ```Value/Value.getRuntimeValue()```
  - Cleaned up old ```*.oal``` files that may be left over from parse
  - Cleaned all projects
  - Commited the updated branch
  - Edited ```ooaofooa_hierarchy.pei.sql```, removed:
    - T_TNS 290 for "Runtime Value", T_TPS entry that starts at 290
    - T_TNS 299 for "Class in Engine"
  - Edited ```bp.core/common/ModelRoot.java``` around line 136.  Removed all the classes in the "runtimes" array.  Clean up imports.
  - Edited ```lib/ARCH.java``` to remove stuff related to Stack_c
  - Edited ```bp.ui.canvas/CL_c.java```, clean out contents of ```Ishighlighted()```, updated imports
  - Removed all the T_TNS and T_TPS entries in ```bp.ui.explorer/sql/MonUITree.pei.sql```
  - Edited ```bp.ui.explorer/arc/create_mon_content_provider.inc::addTo()```, remove contents
  - Edited ```bp.io.mdl/sql/stream.pei.sql```, removed all of 2.1.6 section and reference to 2.1.6
  
5.3 [3 §6.1.3] Build the branch  

5.4 [3 §6.1.4] Create a new private repository xtuml/editor-private, branch it  
  
5.5 [3 §6.1.5] Copy all the plug-ins and projects from the xtuml/internal branch to the xtuml/editor-private branch
  - Did not copy over ```bp.dap, debug.ui, bp.qa.odometer, verifier.pkg, help.bp.mc```
  - Had to use cygwin to copy to get around long path problems with Windows copy
  
5.6 [3 §6.1.6] Modify the data copied into xtuml/editor-private
5.6.1 [3 §6.1.6.1] Remove the projects & plug-ins specified in [3 §5.1]
  - Removed ```bp.core/lib/T.java```
  - Removed ```bp.core/Vm_c.java```
  - Removed model element ```bp.core/ooaofooa/External Entities/Virtual Machine```
  - Removed ```bp.cli's Execute.java and ExecuteWorkbenchAdvisor.java``` and the ```MANIFEST.MF``` dependency on ```bp.debug.ui```
  - Removed ```bp.welcome/.../library/ExecuteElementAction.java and LaunchVerifierAction.java```
  - Edited ```bp.welcome/.../actions/CheatSheetAction.java``` to fix code errors
  - Edited ```bp.welcome/plugin.xml``` to remove imports for ```bp.debug.ui``` and ```bp.ui.session```
  
5.6.2 [3 §6.1.6.2] Update license files to Apache
  - This was already done prior to this work.
  
5.6.3 [3 §6.1.6.3] Extract Mentor IP as specified in [3 §5.4]
  - Removed JLC jar file.  Edit properties of ```bp.core``` to remove reference to JLC jar file from build path
  - Removed ```bp.core/src/.../util/BridgePointDemoEncryptor.java```
  - Removed ```bp.core/src/.../util/BridgePointLicenseManager.java```
  - Removed code the references the ```BridgePointLicenseManager```.  Remove "Export OAL Instances" preference bits.
  - Updated metamodel in ```bp.welcome``` plug-in (it still had some private Instance stuff)
  
5.6.4 [3 §6.1.6.4] Update the projects to not run the Ant builder (since we won't be generating any code from models)
  - Disabled ANT (or antlr) builders in: 

```
  bp.als
  bp.als.oal
  bp.compare
  bp.core
  bp.core.test
  bp.io.core
  bp.io.mdl
  bp.io.sql
  bp.model.compare
  bp.model.compare.test
  bp.ui.canvas
  bp.ui.canvas.test
  bp.ui.explorer
  bp.ui.properties
  bp.ui.properties.test
  bp.ui.text
```

  - Updated clean targets to not remove java files, ```.g``` files, generated matrices
  - Built in xtuml/editor-private
  
5.6.5 [3 §6.1.6.5] Update .gitignore files to allow the check-in of generated Java files.
  - Allowed java files in ```.gitignore``` files.  Skipped over ```.gitignore``` files under ```bin/``` folders.
  - Allowed generated ```*.txt``` files (for test matrices) to be committed
  - Allowed ```bp.[model].compare/src/.../ComparePluginMessages.properties```
  - Allowed ```plugin.xml``` in ```bp.core/.gitignore``` and ```bp.ui.text/.gitignore```
  
5.6.6 [3 §6.1.7] Commit the files to xtuml/editor-private branch  
  
5.6.7 [3 §6.1.8] Create a HOWTO [4] in xtuml/editor that explains how to build the plug-ins   
  
5.6.8 [3 §6.1.9] Promote the files to master in xtuml/editor-private  
  
5.6.9 [3 §6.1.10] Have other members of the development team review xtuml/editor-private for sensitive data   
  
5.6.10 [3 §6.1.11] Create a branch of xtuml/editor  
  
5.6.11 [3 §6.1.12] Copy files from private repo xtuml/editor-private to xtuml/editor branch  
  
5.6.12 [3 §6.1.13] Promote files from branch in xtuml/editor to master  
  
5.6.13 [3 §6.1.14] Remove temporary private repo xtuml/editor-private  

6. Implementation Comments
--------------------------
6.1  The design note called for us to ship the Verifier UI plug-ins as part of the initial OSS 
  release.  However, those plug-ins are full of dependencies on the private parts of the 
  metamodel.  We decided it was not worth the effort to try to publish them at this time.  Therefore, 
  the OSS code dump does not include 
  
```
  bp.debug.ui
  bp.debug.ui.test
  bp.verifier.pkg
  bp.verifier.pkg-feature
  bp.ui.session
```  

6.2  Contrary to what [3 §5.4.3] says, there is no private stuff in the ```io.mdl.test/ooaofooa``` test model.  So it was left.    

6.3  The next time this process needs to be done, the developer doing the work will hopefully be able to get a
  head-start by merging the work done for this branch into the new OSS code dump branch.  Ideally, by merging 
  that work into the new development branch, many of the steps outlined above will already be done.  

7. Unit Test
------------
7.1  The build test specified in [3 §8.1]  is able to be performed successfully.  However, the JUnits do not all pass. 
  The successful test is:

  - Start vanilla eclipse on clean workspace
  - Clone xtuml/editor
  - Import projects from xtuml/editor
  - Build Automatically
  - __R__ Build succeeds on all projects
  - Run the xtUML Editor Application launch configuration
  - __R__ The launch contains the xtUML Modeling perspective.
  - Create a new project
  - __R__ The project is created
  - __R__ User can draw with modeling tools as normal

8. Code Changes
---------------
Branch name: 162_oss_prep  

No promoteable changes.  


End
---

