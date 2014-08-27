---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Implement CLI Build as truly headless
### xtUML Project Design Note


Note: Each section has a description that states the purpose of that section.
Delete these section descriptions before checking in your note.  Delete this
note as well.

1. Abstract
-----------
BridgePoint proper currently relies on the Eclipse GUI.  

2. Document References
----------------------
In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.
[1] Issues 201  
https://github.com/xtuml/internal/issues/201  
Implement CLI Build as truly headless

[2] ClearQuest Issue dts0101047282
This is the CQ twin for [1]

[3] ClearQuest Issue dts0100887837
Introduce nightly build support (command line interface, CLI)

[4] <svn>Documentation_archive/20121102/technical/notes/dts0100887837/dts0100887837.ant
The analysis note for [3]

3. Background
-------------
The tool currently requires the Eclipse workbench when running in CLI mode. 
The tool hides the workbench so the user does not see it, but it is still there. 
In a build server environment there may be no console. Furthermore, some build 
servers will not have graphics libraries installed. This issue is raised to 
remove the need for the eclipse workbench when running the pre-builder from 
the command line.

4. Requirements
---------------
4.1 When running from the command-line an eclipse GUI shall not be required.

5. Analysis
-----------
BridgePoint currently does not do a good job of separating model, view and 
control.  The ooaofooa is the model, the bp.canvas and and bp.graphics plugins
define the view, and the control is mostly generated.  The root issue is that 
the code under bp.core (generated and hand-craft) contains a lot of 
dependencies on eclipse graphics (the workbench).  This dependence makes it 
impossible to run the tool in a truly headless mode.   

To locate places where this dependence exists, one can search for the 
following imports:  
	import org.eclipse.jface.*  
	import org.eclipse.swt.*  
	import org.eclipse.ui.*   
The following regular expression can be used to find these locations:
import\s+(org\.eclipse\.jface.*|org\.eclipse\.swt.*|org\.eclipse\.ui.*)

There are multiple options being considered for how to resolve this issue.  The
options are outlined below, and the conclusion is summarized at the end of this 
section.

5.1 Refactor the parser so it can run without any eclipse editors being 
required.

Create a bp.cli.prebuilder plugin that does not use a workbench.

Our front-end for the is bp.ui.text.AllActivityModifier.   This front-end is 
called in 2 main places:  
bp/debug/ui/model/BPDebugTarget.java::launchElement()
This is used by model execution

com/mentor/nucleus/bp/io/core/CoreExport.java::parseAllForExport()
This is used by model export (this includes prebuilder export)

My task at hand is this:
-run the prebuilder without any GUI dependencies

The approach I am currently looking at to handle this is to start with a new 
cli prebuilder that does not spawn a workbench.
It still does essentially the same thing the current prebuilder does, but it 
does it without a "WorkbenchAdvisor" class.  This simply means there is no
workbench (no GUI).  The cli.prebuilder shall be dependant on only a few things:
bp.core
bp.als.*
bp.io.core

Today, because of the fact that CoreExport is dependant on ui.text, ui.text is
also required.  I propose that for this project, ui.text be divorced from the
parser. This project, and also the upcoming xText and mcpaas projects
will benefit from this too.   

To do this, I am consider copying AllActivityModifier.java and TextParser.java
out of ui.text and putting this under bp.als.oal.   I will think refactor them
to remove all eclipse GUI dependencies from them.   If I can do this, it will 
be a huge step in the task at hand (run prebuilder with no GUI dependencies).
After this step of divorcing ui.text from export I will still need to 
remove existing GUI dependencies from bp.core and bp.io.core and my approach 
to doing that will be to refactor them as encountered as driven by the new 
cli prebuidler that does not use a workbench.  This firsts step of divorcing 
ui.text from the parser prevents me from seeing exactly how large this next task
is, but I stil lthink this is the best approach.  


5.2 The bp.core.CorePlugin.java class currently extends AbstractUIPlugin. 
Remove all eclipse dependencies from bp.core and other bp plugins that should
not have eclipse gui dependencies.

Refactor all these GUI dependencies into a separate class, and only
load the class ass needed
  TODO:  More info here ...

5.2.1 Remove all references to the Eclipse UI plugins [5.1]  

5.2.1.2 For each file found, remove the import(s) and replace it with the 
following import:  
import bp.core.ui.AbstractInterface.*;

5.2.2 Modify bp.core.CorePlugin.java to extend Plugin instead of extending 
AbstractUIPlugin

5.2.3 Create bp.ui.eclipseui.EclipseUI.java that implements 
org.eclipse.ui.plugin.AbstractUIPlugin to provide the same workbench 
integration that exists today in the current bp.core.CorePlugin.java.  This 
new plugin shall be the activator for the GUI version of the tool.

5.2.4 Introduce a new plugin, bp.ui.eclipseui.  This is where the Eclipse-specific  
GUI functionality shall exist.  

This plugin will serve as the new main activator for BridgePoint when run in 
GUI mode.  It shall effectively replace the GUI functionality in the current
com.mentor.nucleus.bp.core plugin.  The GUI functionality from 
com.mentor.nucleus.bp.core is being moved into this new plugin.  
The com.mentor.nucleus.bp.core plugin will no longer have any Eclipse GUI
dependencies.

5.2.4.1  bp.core.CorePlugin.java shall remain an activator, it will be used when
the GUI is not needed. 

5.2.5 Introduce a new package in the existing bp.core plugin named
com.mentor.nucleus.bp.core.abstractui.  The purpose for this is to make it
clear what functionality is being refactored out of the current plugin 
and into the new ui plugin.

5.2.4 Modify MC-Java so it no longer generates code that has Eclipse GUI 
dependencies

5.2.4.1 Search MC-Java for any Eclipse GUI dependencies.  Use the list call out 
[5.2.1.1] to perform the search in the MC-Java project.

5.2.4.1.1 Three matches were found:

  * The following 2 matches are used in a UI selection action filter:  
641: import org.eclipse.ui.IActionFilter;  
2,131: import org.eclipse.ui.IActionFilter;  
	
    * Solution: Introduce an abstraction for IActionFilter and replace the usage 
with it.
	
  * 647: import org.eclipse.ui.IContributorResourceAdapter;  


Note: At this point build the tool and make sure it still works.


5.2.5 Modify bp.core so it no longer has any Eclipse GUI dependencies
5.2.5.1 In the plugin manfest editor remove the dependencies on all eclipse GUI
packages.  This will call out all the places that must be refactor.
5.2.5.1.1 PErform the refactoring
5.2.5.2 Search under the bp.core project using the regular expression called out 
in [5.2.1.1] to find any remaining eclipse GUI dependencies.  
5.2.5.2.1 If there are any remaining dependencies refactor them out.

5.2.5.2.Search under the bp.mc projects using the regular expression called out in
[5.2.1.1] to assure there are no dependencies on the Eclipse GUI.  If any are 
found, refactor them.

5.2.7 Search under the bp.cli.* projects using the regular expression called out 
in [5.2.1.1] to assure there are no dependencies on the Eclipse GUI.  If any are 
found, refactor them.

5.2.8 A separate issue shall be raised to search for and refactor GUI dependencies 
from the bp.debug.ui plugin.   


5.3 Summary and decision

TODO
I currently see no better 
options, and I think the approach I am suggesting will benifit us on muyltiple
fronts, so it is what my plan is right now.





6. Design
---------


7. Design Comments
------------------
none

8. Unit Test
------------

These tests must be run on linux.  Therefore, they are manual tests.  

 This test is only valid in a linux installation  

1) Test to assure -prebuildOnly with no DISPLAY works
*  Launch BridgePoint and create a test workspace with the MicrowaveOven project installed in it.
* modify the CLI.sh shell script and add a valid license, for example:  
export MGLS_LICENSE_FILE=1717@wv-lic-01.wv.mentorg.com:1717@wv-lic-02.wv.mentorg.com:1717@svr-azt-eng-01
* delete the gen/code_generation folder under the MicrwaveOven project
* rename the .project file from the MicrwaveOven project to ".project-temp"
* Exit from Bridgepoint 
*  In CLI.sh add the following setting to force no DISPLAY to be available:  
export DISPLAY=
* open a shell and go to the folder where the CLI.sh that was modified above exists.
* run the following command:  
CLI.sh Build -project MicrowaveOven -prebuidlOnly
* RESULT - The MicroWaveOven.sql file exists in the gen/code_generation folder
* RESULT - No error were seen on the console
* RESULT - No <project>/.project file was created
* Restore the .project from the copied made in the test setup

2) Test to assure that errors are properly reported when no DISPLAY is present
* delete the gen/code_generation folder from under the MicrowaveOven project
* modify the CLI.sh shell script and remove the LICENSE (w want it to fail):  
export MGLS_LICENSE_FILE=
*  In CLI.sh add the following setting to force no DISPLAY to be available:  
export DISPLAY=
* open a shell and go to the folder where the CLI.sh that was modified above exists.
* run the following command:  
CLI.sh Build -project MicrowaveOven -prebuidlOnly
* RESULT - A license error is displayed on the console, but no exception stack it seen, just a license error
* RESULT - The MicrowaveOven.sql file is NOT present in the gen/code_generation folder


End
---
