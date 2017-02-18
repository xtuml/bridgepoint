---

This work is licensed under the Creative Commons CC0 License

---

# Introduce quick start for a new MASL domain or project
### xtUML Project Implementation Note


1. Abstract
-----------
In the first training class for MASL, users requested a simple way to get a 
project pre-configured for MASL.  This work addresses this request and a few 
issues with the Welcome.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8993](https://support.onefact.net/issues/8993) Introduce quick start for a new MASL domain or project      
<a id="2.2"></a>2.2 [BridgePoint DEI #8575](https://support.onefact.net/issues/8575) Sample project imports takes two tries.      
<a id="2.3"></a>2.3 [BridgePoint DEI #8053](https://support.onefact.net/issues/8053) The welcome page needs some updates.      
<a id="2.4"></a>2.4 [CheatSheetSelectionAction](https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/ui/internal/cheatsheets/actions/CheatSheetSelectionAction.html)   
<a id="2.5"></a>2.5 [BridgePoint DEI #9042](https://support.onefact.net/issues/9042) Manual test for example projects         

3. Background
-------------
See Abstract.   

4. Requirements
---------------
4.1 Provide a MASL template project in the Welcome QuickStart.      

5. Work Required
----------------
5.1  Created a new project named ```MASLTemplate``` and put it into the bp.welcome
  plug-in.  
5.1.1  Linked the project in with a button on the Welcome QuickStart just like the
  other example projects.    
      
6. Implementation Comments
--------------------------
6.1  While testing the implementation we hit the old issue [[2.2]](#2.2).  The bug 
  was related to the programmatic switching between and data entry for the two 
  fields (dir path & file path).  We added some code to explicitly set focus 
  properly before we enter data and to send a keydown for "enter" after we enter
  the data.  This allows the dialog to process the change appropriately.   

6.2  Another issue [[2.3]](#2.3) called out some fixes needed in bp.welcome.  A
  number of the issues were resolved a while back.  We introduced a new class
  that prevents a NoClassDefFound Exception.  It simply does nothing when the user
  clicks the quickstart button for the cheat sheet walkthrough.  We intentionally 
  chose (a while ago) not to open the cheatsheet  selection dialog because there 
  are a couple of BridgePoint cheatsheets and the description text tells the user 
  how to open. There is a class inside eclipse to open the Cheat Sheet Selection 
  dialog but it is in  an internal package and we don't want to reference it. See 
  [[2.4]](#2.4).   

7. Unit Test
------------
7.1  Captured in [[2.5]](#2.5)   

8. User Documentation
---------------------
None.   

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint    
Branch: 8993_masl_quickstart   

<pre>

doc-bridgepoint/notes/8993_masl_quickstart_int.md

org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/ui/ProjectUtilities.java

org.xtuml.bp.welcome/models/MASLTemplate/.cproject
org.xtuml.bp.welcome/models/MASLTemplate/.project
org.xtuml.bp.welcome/models/MASLTemplate/.externalToolBuilders/
    Model Compiler.launch
org.xtuml.bp.welcome/models/MASLTemplate/gen/features.mark
org.xtuml.bp.welcome/models/MASLTemplate/models/MASLTemplate/MASLTemplate.xtuml
org.xtuml.bp.welcome/models/MASLTemplate/models/MASLTemplate/MASLTemplate/
    MASLTemplate.xtuml
org.xtuml.bp.welcome/models/MASLTemplate/models/MASLTemplate/MASLTemplate/
    MASLTemplate/MASLTemplate.xtuml
org.xtuml.bp.welcome/models/MASLTemplate/models/MASLTemplate/MASLTemplate/
    MASLTemplate/MASLTemplate/MASLTemplate.xtuml
org.xtuml.bp.welcome/models/MASLTemplate/models/MASLTemplate/MASLTemplate/
    Shared/Shared.xtuml
org.xtuml.bp.welcome/models/MASLTemplate/models/MASLTemplate/types/types.xtuml
org.xtuml.bp.welcome/src/org/xtuml/bp/welcome/gettingstarted/
    ShowCheatSheetWindowAction.java
org.xtuml.bp.welcome/introContent.xml


</pre>

End
---

