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

3. Background
-------------
BridgePoint was enhanced to support generic packages in 2011 (BridgePoint version 3.3.0).  An analysis note what written at that time that described the plan, include removal of specialized packages [dts0100631941.ant] (#dts0100631941-ant).

As that work progressed, there were many subtasks created.  These subtasks can be seen by looking at[Redmine Historical issue #1227](#Redmine-1227).  Additionally, those tasks produced some additional documentation, and that documentation is found in the [same folder as the dts0100631941.ant analysis note](#dts0100631941-ant).

In section 6 (work required), the (analysis note)[#dts0100631941-ant] describes 4 phases for the project. The final phase, phase 4, describes a "cleanup phase" where all the specialized package artifacts are removed.  It is that section that this issue shall complete. 

Specialized packages have not been used now for several years.  Every association that was previously made with any of the organization classes (specialized packages) is now replaced by traversals across the subtype
association with the Packageable Element class and across the new association
between Packageable Element and Package. 

This issue is now raised to perform the clean up and remove specialized package artifacts including all the OAL that is in place to support them.

4. Requirements
---------------
4.1 All specialized package model classes shall be removed from the ooaofooa.
4.1.1 The elements to be removed shall be identified by using the script that was created for the work that introduced generic packages [].
4.2 All generic package model migration code shall be removed from BridgePoint
4.3 All global data type migration code shall be removed
4.4 When this work is complete, all existing unit tests must pass.  
4.5 This work shall note include removal of specialized package testing artifacts. A separate issue shall be raised for this task.

5. Analysis
-----------
See [dts0100631941.ant](#dts0100631941-ant), especially section 6.4.

6. Design
---------
6.1 Remove deprecated metamodel elements
6.1.1 Before removal make a note of all associations that were made to these elements for later use.
6.1.2 The list of elements to remove:
"A_A", "COMM_COMM", "CP_CP", "S_DPK", "IP_IP", "SQ_S", "UC_UCC", "EP_PIP", "S_SS", "S_EEPK", "S_FPK", "S_DOM"

6.3 Rework OAL
6.3.1 Deal with the fall out from the previous changes, by removing all OAL to
  relate, unrelate and traverse the old associations. 
6.3.2 Repeat following the same policy for hand written code.
6.3.3 When this step is complete, there will be no OAL code that refers to the old associations.
6.3.4 When the whole job is complete, only data upgrade and backwards compatible
  model transformation code will access the old associations.



7. Design Comments
------------------
7.1 In 6.4.1.2, the [Analysis note](#dts0100631941-ant) calls out that "State Model" will be removed.  There is nothing in the ooaofooa named "State Model".  It is assumed this was referring to class State Machine (SM_SM).  As the generic package work proceeded it was determined that State Machine (SM_SM) should NOT be removed, and is was not removed.  This fact is clear by looking at the subtypes of PE_PE in the ooaofooa to see that there are no SM_* subtypes of PE_PE.  Therefore, SM_SM it is NOT being removed now either.


8. Unit Test
------------
8.1 All existing unit tests shall pass.
8.2 Assure that the data upgrade mechanism is properly test in existing unit tests.  If it is not create new test(s) as needed to exercise this.

End
---
