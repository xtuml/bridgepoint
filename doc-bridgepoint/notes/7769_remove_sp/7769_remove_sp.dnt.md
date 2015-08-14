---

This work is licensed under the Creative Commons CC0 License

---

# Meta-model cleanup from generic package migration (remove specialized package support)
### xtUML Project Design Note


1. Abstract
-----------
This issue is raised to remove specialize package support from BridgePoint.

2. Document References
----------------------
In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.  
[1] [BridgePoint DEI #7769](https://support.onefact.net/redmine/issues/7769)  
[2] <a id="Redmine-1227"></a> [BridgePoint Historical DEI #1227  dts0100631941](https://support.onefact.net/redmine/issues/1227) Migrate away from specialized packages (to generic package support.  
[3] <a id="dts0100631941-folder"></a> [Documenation for dts0100631941] (https://github.com/xtuml/internal/tree/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20110614/technical/notes/dts0100631941) This is a link to a folder that contains all the documentation associated with original project that introduced generic packages into BridgePoint. This includes the analysis note.  
[4] <a id="dts0100631941-ant"></a> [dts0100631941.ant] (https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20110614/technical/notes/dts0100631941/dts0100631941.ant)  This is a link to the original project analysis note. This note resulted in many separate projects being spawned, including this one.  
[5] <a id="cmtSpecPkgOAL.pl"></a> [cmtSpecPkgOAL.pl](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20110614/technical/notes/dts0100631941/cmtSpecPkgOAL.pl)  This is a utility used to identify OAL language that contains use of any specialized packages. This script was used in the [original work to introduce generic packages](dts0100631941-ant) to identify all places that needed to be modified.  
[6] <a id="PE_PE-comment"></a> This is the comment that was introduced as a "breadcrumb" by [cmtSpecPkgOAL.pl](#cmtSpecPkgOAL.pl) during [dts0100631941 analysis](#dts0100631941-ant) to locate all places where OAL existed that potentially needed to be modified for the introduction of generic packages:  
```// TODO: PE_PE navigation is present (isInGenericPackage).  Do not remove this comment.  ```



3. Background
-------------
BridgePoint was enhanced to support generic packages in 2011 (BridgePoint version 3.3.0).  An analysis note was written at that time that described the plan, including removal of specialized packages, [see dts0100631941.ant] (#dts0100631941-ant).

As that work progressed, there were many subtasks created.  These subtasks can be seen by looking at [Redmine Historical issue #1227](#Redmine-1227).  Additionally, those tasks produced some additional documentation, and that documentation is found in the [same folder as the dts0100631941.ant analysis note](#dts0100631941-ant).

In section 6 (work required), the [analysis note](#dts0100631941-ant) describes 4 phases for the project. The final phase, phase 4, describes a "cleanup phase" where all the specialized package artifacts are removed.  It is that section that this issue shall complete. 

Specialized packages have not been used now for several years.  Every association that was previously made with any of the organization classes (specialized packages) is now replaced by traversals across the subtype
association with the Packageable Element class and across the new association between Packageable Element and Package. 

This issue is now raised to perform the clean up and remove specialized package artifacts including all the OAL that is in place to support them.

4. Requirements
---------------
4.1 All specialized package model classes shall be removed from the ooaofooa.  
4.1.1 The elements to be removed shall be identified by using the [script that was created for the work that introduced generic packages](#cmtSpecPkgOAL.pl).  
4.2 All generic package model migration code shall be removed from BridgePoint  
4.3 All global data type migration code shall be removed  
4.4 When this work is complete, all existing unit tests must pass  
4.5 This work shall not include removal of specialized package testing artifacts. A separate issue shall be raised for this task.  
4.6 The ["breadcrumbs" left behind](#PE_PE-comment) by the work to introduce generic packages shall be removed as the OAL is updated by this issue to remove specialized packages.  

5. Analysis
-----------
See [dts0100631941.ant](#dts0100631941-ant), especially section 6.4.

6. Design
---------
6.1 Remove EP_SPKG, R1400, and all of it's subtypes.  
This is where persistence for the specialized packages was described.  

6.2 For each of these elements: "A_A", "COMM_COMM", "CP_CP", "S_DPK", "IP_IP", "SQ_S", "UC_UCC", "EP_PIP", "S_SS", "S_EEPK", "S_FPK", "S_DOM"  
- note deprecated relationships  
- unformalize relationships  
- delete relationships  
- delete the sp  

6.3 Remove more OAL that is specialized package specific
- search for isInGenericPackage in OAL.  
- for each hit  
  - remove the SP OAL  
  - remove the PE_PE comment that was left as as a "breadcrumb" by [cmtSpecPkgOAL.pl](#cmtSpecPkgOAL.pl), if present.  
- end for  

6.3.1 With the removal of S_DPK the System Level Datatypes (SLD_*) package becomes obsolete, removed it.  

6.3.2 Remove the Packaging Linking package and everything in it (PL_*). After the SP removal this is left with no functionality in it.  

6.4 Parse all and clean up the remaining SP references  
6.4.1 Remove the OAL SP Functionality <a id="removal"></a>  
- for each error in the errors view:  
  - Deal with the fall out from the previous changes by removing all OAL to relate, unrelate and traverse the old associations as well as OAL that may create or remove instances of the model elements removed.  
  - While making these changes, remove the [PE_PE comment](#PE_PE-comment) that was left as as a "breadcrumb" by [cmtSpecPkgOAL.pl](#cmtSpecPkgOAL.pl), if present.  
- end for  
- The result at this point is that no errors are present in the problems view after parse all.  

6.5 Update remaining places where specialized packages are referenced in OAL and java code. This includes hand-craft code and RSL, where the introduction of generic packages resulted in modifications to BridgePoint.  
- search for remaining [PE_PE comments](#PE_PE-comment)  
- For each comment found perform the same [SP Removal Procedure done](#removal) earlier.

6.6 Update the bp.core pei files to account for the removal of specialized packages
6.6.1 Update ooaofooa_hierarchy.pei.sql
6.6.2 Update context_menu.pei.sql

6.6 Build bp.core
- Close all projects except bp.core, and bp.als.*
- Build bp.core  
- Work through any remaining problems that prevent bp.core from building successfully.  
- The result at this point is a clean bp.core build.  

6.7 Update and build bp.io.core and bp.io.mdl  

6.7.1 Update the pei files, file_io.pei.sql and stream_io.pei.sql  

Note that while these files are similar, they are not close enough that we can compare and "merge" changes from one to another. There are several reasons they are differnt, but one big one is that the stream persistence includes parsed instance data and the file persistence does not. Because of these differences, go through each file separately and make the updates.  
- for each specialized package  
  - look for the package name at the bottom of the file (in the EI and GD data)  
  - copy the package name into the clipboard  
  - remove the GD and EI entries  
  - search for the package name  
  - remove the package and all its children (packages have all their children in order, so this was not hard)  
  - search again for the package name to find places where the removed package was referred to  
    - for each of these places, remove the entry that referred to the package and fix up the alternate children entry to account for the removal  
- end for  

6.8 Build bp.io.core and bp.io.mdl  
Work through any remaining problems that prevent bp.core from building successfully.  

6.9 Perform a full build of BridgePoint (excluding tests)  
Work through any remaining problems that prevent bp.core from building successfully.  

6.10 Build the test plugins  
Work through any remaining problems that prevent bp tests from building successfully.  

6.11 Run the unit tests
Work through any remaining problems that prevent bp tests from running successfully.  

6.12 Assure that model update is tested properly and is still functioning.  Add new tests if needed.  

6.13 The model compiler was modified when generic packages were introduced to no longer use Specialized packages.  However, search the Model Compiler for the elements that were removed in 6.1.2 to assure there are no references to them remaining.   

6.13.1 Introduce an item in the release notes issue to call out that customer should also take this step to assure their MCs have been updated, and note that if not already updated they they must do so now.   

6.14 Revisit the following functions and remove them if they are now obsolete.  
- message.participatesInCommunication and remove it if it can be removed.  
- MSG_M.participatesInCommunication

6.15 As this work progresses, "breadcrumbs" shall be left behind at places that it is best to revisit after we get a full good build.  These comments are of the form: "TODO: BOB". These places shall be revisited and removed.  

6.16 Capture some metrics  
6.16.1 Lines of code before and after this work.  
6.16.2 BridgePoint parse-all time  
6.16.3 BridgePoint build time  

6.17 The following referential ID “Placeholders” have been put into ooaofooa:  
- R_REL.SS_ID (this was at the end)  
- O_IOBJ.SS_ID (this one is in the middle)  
- O_OBJ.SS_ID (after Descrip)  
- S_EE.Dom_ID (after Key_Lett)  
- S_DT.Dom_ID (after DT_ID)  
- CA_COMM.SS_ID (after CPAth_ID)  
- CA_ACC.SS_ID (after APath_ID)  
- S_EEM.SS_ID (after Modl_Typ)  
- S_SYNC.Dom_ID (after Sync_ID)  
This was done to bootstrap Bridgepoint.  When we are self hosted in the version being built these shall be removed.  

6.18 Interaction diagrams still have formalize and unformalize CME options (ie SQ_{CIP CP EEP FPP}). Since there was no OAL in the CME bodies code generation was failing. I put code back in the action bodies, but left "TODO: BOB" to reexamine if formalize/unformalize make sense for these interaction diagrams any more or not. This item is here to assure this takes occurs.  

7. Design Comments
------------------
7.1 In 6.4.1.2, the [Analysis note](#dts0100631941-ant) calls out that "State Model" will be removed.  There is nothing in the ooaofooa named "State Model".  It is assumed this was referring to class State Machine (SM_SM).  As the generic package work proceeded it was determined that State Machine (SM_SM) should NOT be removed, and it was not removed.  This fact is clear by looking at the subtypes of PE_PE in the ooaofooa to see that there are no SM_* subtypes of PE_PE.  Therefore, SM_SM it is NOT being removed now either.  

7.2 In ooaofooa::functions::Context Menu Entry Functions::C_C_New{Component | ComponentReference | Package}
a pattern was followed in the OLD OAL where the first line was:
```select any component from instances of C_C where USER::selectOne(id:selected.Package_ID);```

It was wrong. That first line is critical in the code generation. It is used during wfl_pop.arc (workflow population) to create the bp.core/wfl.pei.sql data that is used to generate CME functionality. What this first line of OAL in the action body is doing is assuring that the new element type ends-up in the same component as the selected component. The problem is, C_C.Package_ID no longer exists (it was removed because it came from the old EP_* subsystem). Thus, that code was bad all along because it was relying on population of the Element Packaging class relationships that were specific to SPs. Since C_C.Package_ID no longer exists, this code was changed to:
```select any component from instances of C_C where USER::selectOne(id:selected.Id);```

This is correct because this ID is a referential obtained across R8001.  

7.3 In performing this work several places where we could now take advantages of class references jump out. I am noting these here:  
-new<Element>  
-paste<Element>  
-canPaste<Element>  
-get<Element>Count  
-get<Element>Id  

7.4 Found and fixed a bug in Component Reference::componentHasMatchingInterface() that causes that operation to always return true. An S_SYS instance was always null in the SP case because it was not properly obtained.   

7.5 Found and fixed a bug in SQ_CIP::formalClassHasIBEvents() (Class InstanceParticipant) in the case where we have to use the component to find associated state machines events.  There was what looks like a copy/paste error that caused no events to ever be found.  This same error was also in SQ_CIP::getInstanceEventCount.   

7.6 The Search package had a Number Range Attribute of 0. I set it to 9000 because it's child packages are in the 9000 range   

7.7 The subtypes of SP_SE (Searchable Element) all had the same class number (9702), I corrected this problem to clear some parse errors.  

7.8 In ooaofooa::functions::Context Menu Entry Functions I removed C_C_ComponentFormalize because there is no such thing anymore.   

7.9 Update the bp.ui.explorer project  
This project contained a model that was not used. Furthermore, this model was still in SP format and contained only a Datatypes package. I removed this model and also removed the xtuml nature from this project because it does not require an xtuml nature. This project uses the model UITree model found in the bp.ui.tree project and that model was in very odd condition.  When you looked at it in ME you see only 1 package, UITree. However, on disk it contained 2 Datatype packages that were not hooked up, which is why they were not visible in ME. This caused code gen problem because it was creating the S_DPK instances from this model. I deleted these Datatype packages since they are no longer needed now that we use global datatypes. I also modified the ui.explorer/sql/UITree.pei.sql file to account for SP removal.   

7.10 Updated bp.ui.properties/arc/BuildPropertySource.arc to remove SP source generation. There is still a "isInGenericPackage" marker in here, but it is in a comment.  That tells us we there is some optimization we can now do. I am leaving that one in place for now. An issue shall be raisied for this.  


8. Unit Test
------------
8.1 All existing unit tests shall pass.  
8.2 Assure that the data upgrade mechanism is properly tested in existing unit tests.  If it is not, create new test(s) as needed to exercise this.

End
---
