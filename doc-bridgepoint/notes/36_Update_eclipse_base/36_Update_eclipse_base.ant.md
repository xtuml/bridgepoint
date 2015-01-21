---

This work is licensed under the Creative Commons CC0 License

---

# Move BridgePoint to eclipse 4.x
### xtUML Project Analysis Note

1. Abstract
-----------
This note analyzes the work required to move BridgePoint onto the eclipse 4.x
code base.

2. Document References
----------------------
[1] [BridgePoint DEI #36](https://support.onefact.net/redmine/issues/36)  

3. Background
-------------
BridgePoint has worked with the eclipse 3.x branch for a very long time.  
Upgrades to the eclipse 4.x branch have been pushed off due to other work.  We 
are now ready to begin upgrading.   

4. Requirements
---------------
4.1 All existing eclipse APIs used shall be updated to work with the new eclipse
    code base.      
4.2 Any API that has been changed or removed shall have a replacement which
    allows for the same functionality.     
4.3 All existing unit tests shall pass.   

5. Analysis
-----------
5.1 Existing eclipse APIs   

5.1.1 IFile API   

Upgrading shows that the IFile API has changed.  The API change is a newly added
interface requirement.  This addition is IFile.accept(IResourceProxyVisitor
visitor, int depth, int memberFlags).

The tool has two places which implement the IFile interface, CanvasDummyIFile
from ui.canvas as well as PlaceHolderEntry from ui.text.  In both cases another
accept method shall be added, just with a different signature.  For the   
CanvasDummyIFile case nothing is done for the method.  This is the same
procedure we have followed in the past.  In the PlaceHolderEntry case the
original IFile worked against has the method call forwarded.
  
5.2 Removed APIs   

Verifier makes use of the eclipse ClassLoader API.  In BridgePoint's case the
tool made use of the class, DefaultClassLoader.  This class has been removed or
at least hidden from the public API.  This class did not provide much over
java's default ClassLoader class.  It did implement the ParallelClassLoader
interface from eclipse which requires consideration.

If the DefaultClassLoader class cannot be used anymore or there is no migration
guide for it then we shall re-implement the design based off of the java
ClassLoader API.  In this case the original documentation and author shall be
consulted.

5.3 Unit tests

Once the code was successfully built an attempt to run the existing unit tests
was made.  This resulted in the following issues.

5.3.1 Unit test launch configurations

None of the existing unit test launch configurations could be used.  Each time
one was launched an exception occurred.  This was looked into but no useful
information was found.  A manual recreation of the launch configuration showed
that it was related to an eclipse change in the launch configuration data as the
recreation allowed the tests to run.  We shall recreate all of our existing
launch configurations.  The naming shall not be changed.

5.3.2 Removal of CDT launch groups

It appears that the launch group support from CDT has been removed.  This
support allowed us to run all unit tests without interaction.  Investigation
into this removal shall be performed.  If the functionality cannot be returned
we shall bring back the in-house action that performed similar functionality.
We also shall consider the utility that allows automatic collection of the test
results.

5.3.3 UI test utilities

The current unit tests rely on manipulation of the UI dialogs.  The attempt to
run a recreated launch showed problems while trying to load test model data.
This is likely related to SWT differences.  These differences shall be looked
into and resolved.  There are likely many more issues as the one found was only
related to setting information into the import project wizard.

5.4 Tool issues found during analysis   

5.4.1 eGit issues   

5.4.2 Workspace synchronization   

During the analysis phase it was found that with eGit the 'Synchronize with
workspace' action no longer works.  It is present but exceptions occur.  Further
investigation into this shall be performed including trying to find a later eGit
version.  The only workaround for this would be to use 'Compare With' while all
projects were selected.

The 'Compare With' workaround seems to be a good replacement but requires more
steps for the user.  In the 'Synchronize workspace' case you right click any
project in the workspace for the same repository and a compare is done against
all projects from that repository.  In the other case you must manually select
all projects.

Another data point is that if using 'Synchronize with workspace' on the git
bridgepoint repository, with only the doc-bridgepoint project checked out it
works.
 
5.4.3 Missing git projects

In one case the tool was shut down and the machine restarted.  When eclipse was
reopened none of the existing projects were shown in the workspace.  This issue
was not specific to xtUML as they did not show in Navigator either.  We shall
test this issue further.  As with the other issue a later version of eGit shall
be looked into.

6. Work Required
----------------
6.1 To be determined

7. Acceptance Test
------------------
7.1 Code base

The entire BridgePoint code base shall build without error.

7.1.1 Testing

All unit tests shall run and pass.

7.2 DefaultClassLoader removal

Manual tests shall be performed regarding realized implementations in verifier.
The automated tests may not include full coverage.   

End
---

