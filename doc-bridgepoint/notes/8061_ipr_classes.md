---

This work is licensed under the Creative Commons CC0 License

---

# Allow Imported Classes via IPR (Phase 1)
### xtUML Project Implementation Note


1. Abstract
-----------
This document describes the first implementation of a new feature to BridgePoint
that allows modelers to access classes and functions via inter-project
references.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8061](https://support.onefact.net/issues/8061) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #8072](https://support.onefact.net/issues/8072) Imported packages

3. Background
-------------
BridgePoint should allow an imported class inside a project to be assigned to a 
class that lives in another project. This will allow for the creation of 
"library" classes.   

An imported class assigned via an inter-project reference shall then be treated 
like a locally-assigned imported class. Create, relate, select, member access 
shall all work.  

This work is the first step towards greater support in BridgePoint for using 
imported elements from other "library" projects.  A follow on issue is raised
for the next phase (2.2).  

4. Requirements
---------------
4.1 A new preference shall be created that controls whether or not classes and
  functions may be accessed via IPR.  
  
4.2 UI  
4.2.1  The "Assign class" CME operation shall allow assignments to classes 
  outside the current project.
4.2.2  The modeler shall be able to draw a relationship to an IPR class.  
    
4.3 OAL - Modeler shall be able to:
4.3.1 ```create``` instances of classes that live in other xtUML projects (does
  not require an imported class assigned via IPR in the local project)  
4.3.2 ```select``` instances of classes that live in other xtUML projects (does
  not require an imported class assigned via IPR in the local project)  
4.3.3 Access and assign attributes on IPR classes brought into context via 4.3.1
  or 4.3.2  
4.3.4 Access operations on IPR classes brought into context via 4.3.1 or 4.3.2  
4.3.5 Associations  
4.3.5.1 ```relate``` an instance of a local class to an IPR imported class  
4.3.5.2 ```relate``` two classes, neither of which are in the local project  
4.3.6 Access domain functions in other projects  

5. Work Required
----------------
5.1 Add a new project-level preference to ```BridgePointProjectReferencesPreferences.java```
  to control the new IPR class and function feature.  
5.1.1  The preference is named "Allow class and function access via IPR"  
5.1.2  The new preference defaults to "off / unchecked"  
 
5.2 Update the parser OAL Validation Functions where classes and domain 
  function names are validated.  In these functions, there are 
  places where the parser calls ```collectVisibleElementsForName```.  This 
  call is passed a parameter whether to collect globally (IPR projects) or not. 
  Prior to this work, the collection was never done globally for classes and 
  functions.  This parameter is replaced with a preference check for the new
  preference that determines if we shall check other projects via IPR or not.   
5.2.1  The following functions are modified: 
 ```
 Object_keyletters_validate
 Relationship_validate
 Identifier_validate
 Invocation_function_validate
 Function_function_validate
 ```

5.3 Imported Class assignments  
5.3.1  Update Imported Class (O_IOBJ) ```actionFilter``` OAL operation to check 
  the new preference when deciding if we shall look for classes to assign to 
  globally or not.  
5.3.2 Update ```bp.core/arc/chooser_elements_provider.inc``` to also check the 
  preference for the case of assigning classes.  


6. Implementation Comments
--------------------------
None   

7. Unit Test
------------
Setup: Import ```8061_library``` and ```8061_application``` models from the 
xtuml/models repository ```test/``` folder  

7.1  Parse test  
  - Open CME on ```8061_application```
  - Run Parse All Activities
  - __R__ Success (no problems flagged)
  - Open CME  on ```8061_application```
  - Select Project Preferences
  - Uncheck "Allow class and function access via IPR", click OK
  - Run Parse All Activities
  - __R__ Problems flagged in app::appfunction()

7.2  Assign Test (continued from 7.1)  
  - Open the ```app``` package canvas
  - Add an ```Imported Class``` element to the canvas
  - Open the CME on the new glyph for "Unassigned Imported Class"
  - Choose "Assign Class..."
  - __R__ selection box does not contain ```fooclass``` or ```barclass```
  - Close the dialog box
  - Open CME  on ```8061_application```
  - Select Project Preferences
  - Check "Allow class and function access via IPR", click OK
  - Open the CME on the new glyph for "Unassigned Imported Class"
  - Choose "Assign Class..."
  - __R__ selection box contains ```fooclass``` or ```barclass```
  - Select ```barclass```
  - __R__ The imported class glyph changes to show assignment to ```barclass```
   
8. User Documentation
---------------------
None.

9. Code Changes
---------------
Fork: keithbrown/bridgepoint
Branch name: 8061_ipr_classes_2

<pre>

org.xtuml.bp.core/arc/chooser_elements_provider.inc
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/
    OAL Validation Functions/OAL Validation Functions.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Imported Class/
    Imported Class.xtuml
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/
    BridgePointProjectReferencesPreferences.java

</pre>

Fork: keithbrown/models
Branch name: 8061_ipr_classes_2

<pre>
New project: test/8061_application
New project: test/8061_library

</pre>
End
---

