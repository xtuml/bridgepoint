---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Inter-project reference synchronization
### xtUML Project Analysis Note


1. Abstract
-----------
This note analysis an approach to dealing with out of sync references.

2. Document References
----------------------
[1] Redmine Issue 58, https://support.onefact.net/redmine/issues/58   

3. Background
-------------
Customers have found an issue where updating a referred to element while the 
referring elements did not exist in the workspace causes a dangling reference.  
The modifications are mostly related to renaming the referring element.  The 
xtUML tool uses a name based reference to locate referred to elements.  During 
a rename the tool will update the references so that the name matches, however 
if the referring file does not exist at rename time the tool cannot update the 
references.

4. Requirements
---------------
4.1 The tool shall provide an approach that allows a user to recover from such 
a situation as described in section 3.

5. Analysis
-----------
5.1 Support locating referred to element

5.1.1 Support a quick fix for reported dangling reference problems

The integrity tool runs after file loads/reloads as well as after every 
transaction.  In the situation reported the tool will run an integrity check 
and report any dangling references.  These reports will be added as problems to 
the problems view.

Each problem in the problems view shall allow a quick fix, which can be 
initiated by right clicking or pressing CTRL + 1.  The result of the quick fix 
shall be a chooser dialog that allows a user to pick the appropriate element 
that shall be referred to.  The problem report shall contain enough information 
to allow the user to easily find the required referred to element.  This 
information shall include the UUID for the referred to element as well as the 
original path to the element's containing file.  Once the element is chosen the 
tool shall appropriately hook up the reference.

5.2 Warning during rename

We shall consider a warning dialog when renaming, if a project has external 
projects referencing it.  In this case we would have to introduce referring 
proxies which do not exist at this point.  In this case the tool could display 
a dialog with all external referring projects.  This would allow the user to 
import the required projects prior to a modification.

If this work was to be done it shall only include referring proxies for system 
models.  The warning dialog shall display only the systems that refer to 
elements in the referred to system.  Otherwise the amount of referring proxies 
would get fairly large.

6. Work Required
----------------
6.1 Support locating referred to element

6.1.1 Support a quick fix for reported dangling reference problems

6.1.1.1 Define an xtUML problem type
6.1.1.2 Register a quick fix action against the new problem type
6.1.1.2.1 Design action to display element chooser dialog
6.1.1.2.2 Associate chosen element to expected referred to type

6.2 Warning during rename

6.2.1 Introduce referring system model proxies in system model file
6.2.2 During rename check for any system that is referring to the system 
containing the renamed element
6.2.2.1 If a referring system model proxy exists and the system model is not 
present add it to a list in a warning dialog

7. Acceptance Test
------------------
7.1 Support locating referred to element

_- Setup two projects with inter-project referencing on
_- In one add a data type and refer to it in the other project
_- Close the referring project and rename the data type
_- Open the referring project
_R A dangling reference problem is added
_- Hit CTRL + 1 while selecting the problem in the problems view
_R A dialog is opened that allows you to choose the appropriate referred to 
element
_- Choose the element and click OK
_R The referring element is hooked up with the expected referred to element
_- Restart the tool
_R The referring element is still hooked up with the expected referred to 
element

7.2 Warning during rename

_- Setup two projecs with inter-project referencing on
_- In one add a data type and refer to it in the other project
_- Close the referring project and rename the data type
_R A warning dialog is opened that mentions another project is referring to an 
element in this project and may become out of sync

End
---

