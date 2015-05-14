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
5.1 Building the tool

In order to build BridgePoint we must install BridgePoint in the 4.x base.  To
do so we currently have to do the following:

- osgi.compatibility.plugins must be installed
- BridgePoint must be installed in the dropins folder under the eclipse install
- org.antlr* plugins must also be installed in the dropins folder.

Conversion of all plugins to the 4.x format needs to be considered.  In doing so
the compatibility plugins would not be required.  Using the dropins folder will
allow us to simply place the BridgePoint folder with the addition of the
org.antlr* plugins.  This may be nice considering that it would be easier to
differentiate between eclipse plugins and BridgePoint's plugins.

5.2 Existing eclipse APIs   

5.2.1 IFile API   

Upgrading shows that the IFile API has changed.  The API change is a newly added
interface requirement.  This addition is IFile.accept(IResourceProxyVisitor
visitor, int depth, int memberFlags).

The tool has two places which implement the IFile interface, CanvasDummyIFile
from ui.canvas as well as PlaceHolderEntry from ui.text.  In both cases another
accept method shall be added, just with a different signature.  For the   
CanvasDummyIFile case nothing is done for the method.  This is the same
procedure we have followed in the past.  In the PlaceHolderEntry case the
original IFile worked against has the method call forwarded.
  
5.3 Removed APIs   

Verifier makes use of the eclipse ClassLoader API.  In BridgePoint's case the
tool made use of the class, DefaultClassLoader.  This class has been removed or
at least hidden from the public API.  This class did not provide much over
java's default ClassLoader class.  It did implement the ParallelClassLoader
interface from eclipse which requires consideration.

If the DefaultClassLoader class cannot be used anymore or there is no migration
guide for it then we shall re-implement the design based off of the java
ClassLoader API.  In this case the original documentation and author shall be
consulted.

5.4 Unit tests

Once the code was successfully built an attempt to run the existing unit tests
was made.  This resulted in the following issues.

5.4.1 Unit test launch configurations

None of the existing unit test launch configurations could be used.  Each time
one was launched an exception occurred.  This was looked into but no useful
information was found.  A manual recreation of the launch configuration showed
that it was related to an eclipse change in the launch configuration data as the
recreation allowed the tests to run.  We shall recreate all of our existing
launch configurations.  The naming shall not be changed.

5.4.2 Removal of CDT launch groups

It appears that the launch group support from CDT has been removed.  This
support allowed us to run all unit tests without interaction.  Investigation
into this removal shall be performed.  If the functionality cannot be returned
we shall bring back the in-house action that performed similar functionality.
We also shall consider the utility that allows automatic collection of the test
results.

5.4.3 UI test utilities

The current unit tests rely on manipulation of the UI dialogs.  The attempt to
run a recreated launch showed problems while trying to load test model data.
This is likely related to SWT differences.  These differences shall be looked
into and resolved.  There are likely many more issues as the one found was only
related to setting information into the import project wizard.

5.5 Tool issues found during analysis   

5.5.1 eGit issues   

5.5.2 Workspace synchronization   

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
 
5.5.3 Missing git projects

In one case the tool was shut down and the machine restarted.  When eclipse was
reopened none of the existing projects were shown in the workspace.  This issue
was not specific to xtUML as they did not show in Navigator either.  We shall
test this issue further.  As with the other issue a later version of eGit shall
be looked into.

6. Work Required
----------------
6.1 Install BridgePoint into 4.x eclipse base   
6.1.1 Install osgi compatibility plugins using p2 installer   
6.1.2 Unzip BridgePoint plugins-only to eclipse/dropins   
6.1.3 Copy org.antlr* plugins to eclipse/dropins   
6.1.4 Build BridgePoint with 4.x eclipse base   
6.1.5 Convert existing plugins to 4.x versions   
6.2 Update build scripts   
6.2.1 Adjust build scripts to place BridgePoint plugins under the dropins folder   
6.2.2 Adjust build scripts to include org.antlr* plugins under the dropins   
      folder   
6.3 Update install base to 4.x   
6.4 Build BridgePoint using the build scripts   
6.4.1 Run unit tests for build   
6.4.2 Copy all *.test plugins into the installation folder   
6.4.3 Run all unit tests   
6.5 Update for the required IFile API changes   
6.6 Create a new ClassLoader implementation   
6.7 Update the launch configurations as needed   
6.8 Look into the CDT launcher group that is used to run all tests at once   
6.9 Address plugin requirement issues in ui.text (missing org.eclipse.core.boot)   

7. Acceptance Test
------------------
7.1 Installation into 4.x

The tool shall be usable and unit tests shall pass with an installed version of
BridgePoint

7.2 Build version of BridgePoint on 4.x

The tool shall not require the compatibility plugins.   

7.2 Code base

The entire BridgePoint code base shall build without error.

7.2.1 Testing

All unit tests shall run and pass.

7.3 DefaultClassLoader removal

Manual tests shall be performed regarding realized implementations in verifier.
The automated tests may not include full coverage.   

End
---
