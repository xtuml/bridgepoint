---

This work is licensed under the Creative Commons CC0 License

---

# Inter-project reference synchronization
### xtUML Project Analysis Note


1. Abstract
-----------
This note analyzes an approach to dealing with out of sync references.

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

5.1.1 Support an eclipse quick fix for reported dangling reference problems

The integrity tool runs after file loads/reloads as well as after every 
transaction.  In the situation reported the tool will run an integrity check 
and report any dangling references.  These reports will be added as problems to 
the problems view.

Each problem in the problems view shall allow an eclipse quick fix, which can be 
initiated by right clicking or pressing CTRL + 1.  The result of the eclipse
quick fix shall be a chooser dialog that allows a user to pick the appropriate
element that shall be referred to.  The problem report shall contain enough
information to allow the user to easily find the required referred to element.  
This information shall include the UUID for the referred to element as well as
the original path to the element's containing file.  Once the element is chosen
the tool shall appropriately hook up the reference.

5.2 Warning during referred to element modification

We considered a warning dialog when modifying a referred to element.  In this
case the tool would have to be modified to add referring element proxies in the
persisted files.  When modifying the referred to element the proxies would be
checked to ensure that the referring element existed.  If not the tool would
display a warning dialog prior to modification.

It was decided that this approach is not favorable for a few reasons.  The main
one being that adding proxies to the referred to element's file would cause too
much data being added.  It makes more sense to handle this issue on the
referring element side.  In addition the tool would have to do more to keep
these referring element proxies in synchronization.  This synchronization
approach leaves the tool open to further issues.  Finally, when modifying a
referred to element the user should not require knowledge of all referring
elements.

5.3  Automatically update referring element references

A second approach to 5.1.1 is to locate the element by unique id.  In this
approach the tool would determine that an element could not be found in the
proxy element's file.  Once this situation was determined the tool could locate
the element by unique id.  This would require loading the entire workspace, then
searching the element maps to locate the appropriate element.

The main drawback of this approach would be performance degradation due to
loading the entire workspace.  Additionally the workspace load would get
triggered for cases where the referred to element was removed.

This solution could be used along with 5.1.1, where both are implemented but the
problem view is only populated when an element could not be located.

The benefit of this solution is that the user pulling in a referring project
would have no other action required.  However, in this approach the tool would
not persist the changes and would require full workspace reload every time until
the referring element's file was modified.

5.4 Conclusion

This analysis determines that both 5.1.1 and 5.3 shall be implemented.  In the
various modification situations a delete shall be the only case where a problem
is created.  In this case the problem can be selected and the user shall be able
to select a new element to refer to.  This shall be done using the eclipse quick
fix described in 5.1.1.

6. Work Required
----------------
6.1 Support locating referred to element   

6.1.1 Support a eclipse quick fix for reported dangling reference problems   

6.1.1.1 Define an xtUML problem type   
6.1.1.2 Register a eclipse quick fix action against the new problem type   
6.1.1.2.1 Design action to display element chooser dialog   
6.1.1.2.2 Associate chosen element to expected referred to type   

6.2 Automatically update referring element references

6.2.1 During load if an element cannot be found using the normal proxy load
      conventions, load and search the entire workspace using the unique id
      
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

7.2 Automatically update referring element references

_- Setup two projects with inter-project referencing on   
_- In one add a data type and refer to it in the other project   
_- Close the referring project and rename the data type   
_- Open the referring project   
_R The referring element is automatically updated to point at the renamed data
   type   

End
---

