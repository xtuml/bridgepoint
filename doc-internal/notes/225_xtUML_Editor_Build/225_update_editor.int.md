---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Update the xtUML Editor Builder
### xtUML Project Implementation Note


1. Abstract
-----------
A customer encountered a problem in v4.1.12 of the source xtUML Editor that
that causes running error in debug mode.  This issue is raised to resolve this 
problem and to create a mechanism for maintaining the xtUML Editor in 
parallel with BridgePoint.

2. Document References
----------------------
[1] Git internal, Issues 225, https://github.com/xtuml/internal/issues/225		
	Update the xtUML Editor based on the 4.1.10 release  
[2] Git Editor, Issues 21, https://github.com/xtuml/editor/issues/21
	Update the OSS source for 4.1.10  
[3] Git Editor, Issues 18, https://github.com/xtuml/editor/issues/18
	Can not delete objects from model when running editor built from source  
[4]	Clearquest dts0101036706 
	Place more code into public repository  
[5]	Git internal, issues 162,  https://github.com/xtuml/internal/issues/162
	Place more code into public repository  
[6] https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-create-xtUML-Editor.md  
[7] https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-upload-xtUML-editor-installer.md  
[8] https://github.com/xtuml/internal/blob/master/doc-internal/process/templates/checklists/promotion.chk 
The BridgePoint team's promotion check list.  
[9] HOWTO update the BridgePoint version, https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-update-the-BP-version-number.txt  

3. Background
-------------
A customer hit a problem with v4.1.10 of the xtUML Editor which was the first 
version of the xtUML Editor that provided the ability to modify and rebuild the
editor [3]. The version of the editor that had this problem was v1.4.10, and it
was built using BridgePoint v4.1.6 as its base [4][5].  The initial deployment
was done in a way that quickly supplied the source for users, but it did not 
define a process for how to rebuild and maintain the xtUML Editor in parallel 
with BridgePoint development.  It also did not define a way to maintain the binary 
and source xtULM Editor releases in parallel.  This issue is raised to resolved the customer
issue that was found in the original deployment of the full-souce editor [3],
and to do so in a way that creates a process that allows the editor to be 
easily maintained for both BridgePoint changes as well as changes coming from 
the public.
 
4. Requirements
---------------

> 4.1 The xtUML Editor shall not encounter errors when deleting objects during
a session run from the xtUML Editor launch configuration [3].  
> 4.2. The xtUML Editor source and binary shall be maintained in parallel.  
> 4.3. It shall be possible to have xtUML Editor releases independent of
BridgePoint releases.  
> 4.4. Process shall allow BridgePoint and the xtUML Editor to main maintained
in parallel with minimum effort while moving toward full code code 
separation (separation of the EDitor source and BridgePoint licensed 
components).

5. Work Required
----------------
This section borrowed heavily from the design and implementation notes written
for the initial xtUML Editor source dump [4][5].  This describes the procedure 
used to create this xtUML Editor.  

> 5.1. Create a branch, xtuml_editor_builder, from master in the internal 
repository. After the modifications called out below, this branch shall be
kept up to date with master in the internal repository until such time that
the editor becomes a separate product from BridgePoint.  The process followed 
to promote things into internal/master [8] is modified to require the changes 
to also be promoted into this editor_xtuml_build branch.  
Process for promoting changes from the xtUML Editor into master [6] requires
xtUML Editor changes to be promoted to internal/xtuml_editor_buidler and
internal/master when they are promoted into editor/master. 
 
> > 5.1.1 Remove parts of the ooaofooa that will not be distributed.  
> > > 5.1.1.1  Remove the following Subsystems  
* Breakpoint  
* Engine (new subsystem)  
* Local  
* Runtime Value  

> > > 5.1.1.2  Perform a Parse all and work through the parse errors to remove references 
to the parts of the model that were removed.  
* OAL in the following SSs that referenced SSs that were removed is
removed.  
  * Body  
  * Event  
  * Instance Access  
  * Instance  
  * Invocation  
  * Relate and Unrelate  
  * Selection  
  * Value  
  * Wiring  

> > > 5.1.1.3  A comment is left in its place that reads:  
// Mentor Graphics Verifier-specific Implementation  

> > > 5.1.1.4  Operations that required a return value have been given a default.  
Note that it is safe to do this in all these operations because these SSs 
are only used during parse and/or execution and therefore they are not needed.
Note that before this step there were over 4000 parse errors, 
and this brought it down to 220.  

``` 
// Mentor Graphics Verifier-specific Implementation
return GD::NULL_UNIQUE_ID(); 
or 
// Mentor Graphics Verifier-specific Implementation
return GD::NULL_INSTANCE();
```

> > > 5.1.1.5 Sort the remaining errors by path, and use the strings called out in
 
> > > 5.1.1.6 Replace all content that has errors that are found in the following 
subsystems.  Note that this is safe because this content is only used for 
execution and/or translation.  
	* Body  
	* Event  
	* Instance Access  
	* Instance  
	* Invocation  
	* Relate and Unrelate  
	* Selection  
	* Value  
	* Wiring  

> > > 5.1.1.7 Sort the remaining errors by Resource name.  For each of the following 
resources replace all content with the strings above.  Note that it is safe 
to do this in all these operations because these are only used during parse 
and/or execution and therefore they are not needed by the editor.   
After this change the number of errors remaining went from 220 to 103.  
	* checkBreakPoint()  
	* getRunTimeValue()  
	* getValue  
	* setValue()  
			
> > > 5.1.1.8 For the remaining syntax errors manually remove the blocks that are 
referring to removed SSs, but leave the rest of the OAL.  

> > > 5.1.1.9 Parse all again, and there should be no remaining system errors.

> > 5.1.2 Modify java native functions in the model, and paste-in the verifier-
specific comment described in 5.1.1.3.  
	```Value/BinaryOperation.compareInstRefSets()```
	```Value/Value.getRuntimeValue()```  
> > 5.1.3 Clean up old ```*.oal``` files that may be left over from parse

> > 5.1.4 Clean all projects

> > 5.1.5 Edit ```ooaofooa_hierarchy.pei.sql``` to remove entries that are no longer
in the model.
* T_TNS 290 for "Runtime Value"
* T_TPS entry that starts at 290 (there are 2 of these entries)
* T_TNS 299 for "Class in Engine"
	
> > 5.1.6 Edit ```bp.core/common/ModelRoot.java``` around line 136 to removed all 
the classes in the "runtimes" array.  

> > > 5.1.6.1 Clean up imports after making this change.
  
> > 5.1.7 Edit ```lib/ARCH.java``` to remove code related to Stack_c

> > 5.1.8 Edit ```bp.ui.canvas/CL_c.java```
* Delete the contents of ```Ishighlighted()```
* pdated imports
	
> > 5.1.9 Remove all the T_TNS and T_TPS entries in ```bp.ui.explorer/sql/MonUITree.pei.sql```

> > 5.1.10 Edit ```bp.ui.explorer/arc/create_mon_content_provider.inc::addTo()```
by removing the contents of this function.

> > 5.1.11 Edit ```bp.io.mdl/sql/stream.pei.sql``` and removed all section 
2.1.6 and references to 2.1.6
	
> > 5.1.12 Remove bp.core/src/lib/T.java

> > 5.1.13 Apply changes that are specific to the xtUML Editor.  This is done to 
pull-in editor graphics and make a few other editor-specific necessary changes.
*  To do this, merge-in the following change from xtuml/internal branch 
xtUMLeditor_changes:  
https://github.com/xtuml/internal/commit/c7b4c4b24fdee076d1efc28c65e1767d2d23e4ad  
Note: The comment for this change is:  
job #103 - distilled out the common changes for the xtUML Editor to
branch "xtUMLeditor_changes".  This branch does not, and should not in
the future, contain version updates.  It only contains the raw changes
we make to rebrand the master build from BridgePoint to xtUML Editor.

> > > 5.1.13.1 The previous merge (5.1.13) captured a change to remove all MCs from the 
installation except the c binary MC.  Since the product now has mc.none we 
need to remove the binary mc as well.  To do this:  
* modify Installer_MIM_MIMIC/post_install_script.bat
* locate the comment: "Delete unwanted MCs"
* Add:  
RMDIR /S /Q "%TARGET%\eclipse_extensions\BridgePoint\eclipse\plugins\com.mentor.nucleus.bp.mc.c.binary_%BPVER%"
* modify Installer_MIM_MIMIC/post_install_script.sh
* locate the comment: "Delete unwanted MCs
* Add:  
rm -rf "${TARGET}/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.mc.c.binary_${BPVER}"  


> >  5.1.14 Extract Mentor IP as specified in [5]  
	* Remove JLC jar file.  
		* Edit properties of ```bp.core``` to remove reference to JLC jar file from build path.  
	* Remove  
```bp.core/src/.../util/BridgePointDemoEncryptor.java```  
	* Remove  
```bp.core/src/.../util/BridgePointLicenseManager.java```  
	* Remove code that references the BridgePointLicenseManager.  
	* Remove the "Export OAL Instances" preference.  

> > 5.1.15 Remove additional projects & plug-ins not desired in the xtUML Editor as 
specified in [3 §5.1]  
	* Removed ```bp.core/lib/T.java```
	* Removed ```bp.core/Vm_c.java```
	* Removed model element ```bp.core/ooaofooa/External Entities/Virtual Machine```
	* Removed ```bp.welcome/.../library/ExecuteElementAction.java and LaunchVerifierAction.java```
	* Edited ```bp.welcome/.../actions/CheatSheetAction.java``` to fix code errors
	* Edited ```bp.welcome/plugin.xml``` to remove imports for ```bp.debug.ui``` and ```bp.ui.session```

> > 5.1.16 Remove unit test configurations for suites that don't have any code to test.
* Debug Verifier Test 
* Debug Verifier Test 2
* IO SQL Test

> >  5.1.17 Build and test the xtuml_editor branch (see the unit test section).

> 5.2 Create branch xtuml_editor from branch xtuml_editor_builder  
The xtuml_editor branch is a mirror of what is in editor/origin.  This branch
is create to facilitate the release of the xtUML Editor from the BridgePoint
source until such time that the xtUML Editor plugins are separate from 
the BridgePoint licensed components.  
The xtuml_editor_builder branch is where we merge changes made to BridgePoint, 
and it is where the editor source is generated.  The xtuml_editor branch is 
made from xtuml_editor_builder, and it strips out the plugins that we do not 
make available in the xtUML Editor and turns off the antrl builders.  

> > 5.2.1 Turn off ANTRL builders 
```
  bp.als
  bp.als.oal
  bp.compare
  bp.core
  bp.core.test
  bp.io.core (IO Core Builder AND ANTR Builder)
  bp.io.mdl
  bp.io.sql (IO Builder AND ANTR Builder)
  bp.model.compare
  bp.model.compare.test
  bp.ui.canvas  (Canvas AND ANTRL Builders)
  bp.ui.canvas.test
  bp.ui.explorer
  bp.ui.properties
  bp.ui.properties.test
  bp.ui.text
```  

> >  5.2.2 Update clean_all targets to NOT remove java files, "g" files, or 
generated matrices.  This target shall now only remove class files.
The following files are modified:
```
	> com.mentor.nucleus.bp.als [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.compare [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.core [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.core.test [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.io.core [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.io.mdl [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.io.sql [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.model.compare [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.model.compare.test [internal xtuml_editor ?1]/
			generate.xml
	> com.mentor.nucleus.bp.ui.canvas [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.ui.canvas.test [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.ui.explorer [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.ui.properties [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.ui.properties.test [internal xtuml_editor ?1]/
			generate.xml
	> com.mentor.nucleus.bp.ui.session [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.ui.text [internal xtuml_editor ?1]/generate.xml
	> com.mentor.nucleus.bp.ui.tree [internal xtuml_editor ?1]/templates/
			generate.xml
	> com.mentor.nucleus.bp.ui.tree [internal xtuml_editor ?1]/generate_schema.xml
	> com.mentor.nucleus.bp.ui.tree [internal xtuml_editor ?1]/
			generate_skeleton_files.xml
```  

> >  5.2.3 Remove plugins that are not included with the xtuml_editor source. 
The following shall not be included:  
BridgePointDemoKeyCreator  
com.mentor.nucleus.bp.cli  
com.mentor.nucleus.bp.core.linux.x86  
com.mentor.nucleus.bp.core.win.x86  
com.mentor.nucleus.bp.dap.pkg  
com.mentor.nucleus.bp.dap.pkg-feature           
com.mentor.nucleus.bp.debug.ui  
com.mentor.nucleus.bp.debug.ui.test  
com.mentor.nucleus.bp.docgen   
com.mentor.nucleus.bp.internal.tools    
com.mentor.nucleus.bp.mc  
com.mentor.nucleus.bp.mc.c.binary  
com.mentor.nucleus.bp.mc.c.source  
com.mentor.nucleus.bp.mc.cpp.source  
com.mentor.nucleus.bp.mc.mcpaas  
com.mentor.nucleus.bp.mc.systemc.source  
com.mentor.nucleus.bp.mc.template  
com.mentor.nucleus.bp.verifier.pkg  
com.mentor.nucleus.bp.verifier.pkg-feature  
com.mentor.nucleus.bp.mc.vhdl.source  
com.mentor.nucleus.bp.mc.xmiexport  
com.mentor.nucleus.bp.ui.session  
com.mentor.nucleus.bp.qa.odometer  
com.mentor.nucleus.bp.sequencecapture  
com.mentor.nucleus.help.bp.mc  
doc-internal 
Installer_MIP_MIMIC  
libTRANS  
MC-Java  
MC-Java.test  
pt_antlr  
utilities  

> >  5.2.4 Remove all .gitignore specifications except for those that call out the 
/bin folders.
* After this change, run: Project > Clean... > Clean all Projects  
* Commit the chanegs
* The result should be that there are no dirty files.
* Build all
* The result should be that the product is successfully built and there are
no dirty files. 

>  5.3 Move the internal/xtuml_editor branch into the editor repository    
* branch the editor repository
* delete all src/* projects that will be updated. All projects are deleted
except for the following:  
  * doc-editor
  * org.xtuml.sql.upgrade
  * org.xtuml.sql.upgrade.bin

> > 5.3.1 Commit the deletion of files  
* copy the new src/* from your local machines location for the  
	internal/xtuml_editor branch.
* commit the new source

> 5.4 Build and test (see the unit test section)

6. Implementation Comments
--------------------------
The Work Required section described how this xtUML Binary and source release
were made.  From this point forward it shall be much easer to release the
xtUML Editor.  The promotion process document has been modified so that the
xtuml_editor_builder branch is kept up to date with promtions into the
BridgePoint internal/orgin/master repository.  Therfore, to release the 
xtUML Editor does the following:

* Bump the version in the xtuml_editor branch [9]
* Merge changes from branch xtuml_editor_builder into branch xtuml_editor
* Perform the steps described in section 5.3 to move the internal/xtuml_editor
branch into a branch of editor/origin/master.
* Test the editor/origin/master branch
* Promote the branch into editor/origin/master
* Follow the procedure described in [6] and [7] to update the xtUML Editor 

7. Unit Test
------------
Unit tests are still not passing (they were not passing in the prior release 
either).  However, results are checked-in.   The failures are because of tests
that need to be modified because of code removal.  The test will not pass until 
the full-code separation is done so we only put the effort into reworking 
tests one time.

8. Code Changes
---------------

> 8.1 Branch name: editor/21_Update_OSS_Source  
EVERY files is changed. The process of migrating the code into the editor 
repository from the internal repository is such that the old editor files are 
deleted and the new ones are then copied into place.


> 8.2 Branch name: internal/xtuml_editor_builder  
This is binary editor branch.  This is where the xtUML Editor binary is 
maintained.  When changes are promoted to either internal/master OR
editor/master they shall also be promoted into this branch.

> 8.3 Branch name: internal/xtuml_editor  
This is the xtUML Editor source branch.  This branch was created from 
the xtuml_editor_builder branch.  This branch is a mirror of what is found in 
the  editor/master branch.   This branch exists because there are a lot of
plugins and source files that do not exist in the xtUML EDitor Source that 
are necessary in order to build the binary editor.


End
---

