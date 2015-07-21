---

This work is licensed under the Creative Commons CC0 License

---

# Move BridgePoint to eclipse 4.4
### xtUML Project Analysis Note

1. Abstract
-----------
This note analyzes the work required to move BridgePoint onto the eclipse 4.4
code base.

2. Document References
----------------------
[1] [BridgePoint DEI #36](https://support.onefact.net/redmine/issues/36)  
[2] https://support.onefact.net/redmine/issues/7702 - Install BridgePoint into eclipse 4.4   
[3] https://support.onefact.net/redmine/issues/7682 - Setup BridgePoint to build with eclipse 4.4 base   
[4] https://support.onefact.net/redmine/issues/7683 - Build BridgePoint using the build scripts   
[5] https://support.onefact.net/redmine/issues/7684 - Address API changes   

3. Background
-------------
BridgePoint has worked with the eclipse 3.x branch for a very long time.  
Upgrades to the eclipse 4.4 branch have been pushed off due to other work.  We 
are now ready to begin upgrading.   

BridgePoint currently uses the EMT 3.7.2 version of eclipse.   

4. Requirements
---------------  
4.1 Any API that has been changed or removed shall have a replacement which
    allows for the same functionality.     
4.2 All existing unit tests shall pass.   
4.3 Use the latest eGit plug-ins for the eclipse base.   
4.4 The design shall call out the exact version of eclipse we use for the base.  This base must allow users to develop BridgePoint as well as non-BridgePoint plug-ins.   
4.5 When this work is complete the tool shall still work under the eclipse 3.7.2 environment.   
4.6 OS specific launch configurations shall be removed, the common ones shall work on all supported OSs.   

5. Analysis
-----------
5.1 Installing and running BridgePoint

The first step to installing BridgePoint will be to decide on the eclipse
version to use.  Currently it is expected to be eclipse 4.4 (Luna).
 
Installing and running BridgePoint on 4.4 is very different from building on
the eclipse 4.4 base.  Section 5.2 below describes the process of building
BridgePoint on the eclipse 4.4 base. 

In order to build BridgePoint we must install BridgePoint in the 4.4 base.  To
do so we currently have to do the following:

- osgi.compatibility.plugins must be installed
- BridgePoint must be installed in the dropins folder under the eclipse install
- org.antlr* plugins must also be installed in the dropins folder.

We shall also install the unit test plug-ins.   

A smoke test as well as all automated tests shall be run.   

During the initial install we must use the dropins folder, the latest OSGI p2
support does not allow us to place our plug-ins in the plugins folder.  An
attempt was made without success, the plug-ins were never registered.   

Conversion of all plugins to the 4.4 format needs to be considered.  In doing so
the compatibility plugins would not be required.  Using the dropins folder will
allow us to simply place the BridgePoint folder with the addition of the
org.antlr* plugins.  This may be nice considering that it would be easier to
differentiate between eclipse plugins and BridgePoint's plugins.

For any issues found they shall be applied in our current eclipse base.  This   
will allow for us to run the automated tests under our current version and    
provide a testing base for backward compatibility.   

5.2 Building the tool

5.2.1 Existing eclipse APIs   

5.2.2 IFile API   

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

When initially used the DefaultClassLoader class was an internal class.  Using
it provided a quicker solution.

If the DefaultClassLoader class cannot be used anymore or there is no migration
guide for it then we shall re-implement the design based off of the java
ClassLoader API.  In this case the original documentation shall be consulted
[5].

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
we shall investigate what eclipse does to run multiple launches.  A complete
solution shall work in the UI.  We also shall consider the utility that allows
automatic collection of the test results.  Automatic collection of the results
shall work and be stored as it was before.   

5.4.3 UI test utilities

The current unit tests rely on manipulation of the UI dialogs.  The attempt to
run a recreated launch showed problems while trying to load test model data.
This is likely related to SWT differences.  These differences shall be looked
into and resolved.  There are likely many more issues as the one found was only
related to setting information into the import project wizard.

5.5 Tool issues found during analysis   

5.5.1 eGit issues   

5.5.1.1 Workspace synchronization   

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
 
5.5.1.2 Missing git projects

In one case the tool was shut down and the machine restarted.  When eclipse was
reopened none of the existing projects were shown in the workspace.  This issue
was not specific to xtUML as they did not show in Navigator either.  We shall
test this issue further.  As with the other issue a later version of eGit shall
be looked into.

6. Work Required
----------------
BridgePoint 5.0 shall be used for setting up the initial install base.   

Note that code generation is working by default.  The issues we have are in code
compilation.   

6.1 Install and run BridgePoint with the 4.4 base   

Estimated time: 1 week   
Milestone: Deliver 7702 [2]

6.1.1 Install BridgePoint into 4.4 eclipse base   
6.1.2 Install osgi compatibility plugins using p2 installer   
6.1.2.1 Help > Install New Software...  
6.1.2.2 Select work with: The Eclipse Project Updates   
6.1.2.3 Check the Eclipse 2.0 Style Plugin Support   
6.1.2.4 Run the installer and restart the tool   
6.1.3 Unzip BridgePoint plugins-only to eclipse/dropins   
6.1.4 Copy org.antlr* plugins to eclipse/dropins   
6.1.5 Copy org.xtuml.*.test plugins to eclipse dropins
6.1.6 Run a smoke test on the installed BridgePoint   
6.1.7 Run automated tests for the BridgePoint suite
6.1.8 Report any issues found with testing   
6.1.9 Add eclipse install bases under the packaging repository   

6.2 Setup BridgePoint to build with 4.4 base

6.2.1 Build BridgePoint with 4.4 eclipse base    

In this step BridgePoint will get generated and built.  There will be   
compilation errors which will be addressed later.   

6.2.1.1 Address API issues

Estimated time: 1 week   
Milestone: Deliver 7684 [5]   

6.2.1.1.1 Update for the required IFile API changes   
6.2.1.1.2 Create a new ClassLoader implementation   
6.2.1.1.3 Update the launch configurations as needed   
6.2.1.1.4 Look into the CDT launcher group that is used to run all tests at once   
6.2.1.1.5 Address plugin requirement issues in ui.text (missing org.eclipse.core.boot)   
6.2.1.1.6 Convert existing plugins to 3.x style versions by adding the required   
        MANIFEST data.   
        
6.2.1.2 Check out addressed API changes into eclipse 3.7

In this step we will test building with the API changes in an eclipse 3.7 base.   

Estimated time: 1 week   
Milestone: Deliver 7682 [3]   
 
6.2.1.2.1 Build the source with API fixes in the eclipse 3.7 environment   
6.2.1.2.2 Address any build issues in the eclipse 3.7 environment   
6.2.1.2.3 Run automated tests and address any issues    

6.3 Update build scripts   

6.3.1 Update build scripts to point at the new install base   
6.3.2 Update build scripts to support a version parameter   
6.3.3 Create a build based off of the eclipse 4.4 version   
6.3.4 Run automated tests with an installed BP/eclipse 4.4 version   
6.3.5 Report any issues found with the unit tests
6.3.6 Run a smoke test and report any issues   
   
6.4 Build BridgePoint using the built version based on eclipse 4.4
   
Estimated time: 1 week   
Milestone: Deliver 7683 [4]   

6.4.1 Use the build server to create a BridgePoint release   
6.4.2 Run unit tests for build   
6.4.3 Copy all *.test plugins into the installation folder   
6.4.4 Run all unit tests   
6.4.5 Document any unit test issues

7. Acceptance Test
------------------
7.1 Installation into 4.4

The tool shall be usable and unit tests shall pass with an installed version of
BridgePoint.   

7.2 Build version of BridgePoint on 4.4

The tool shall not require the compatibility plugins.      

7.3 Code base

The entire BridgePoint code base shall build without error.   

7.3.1 Testing

All unit tests shall run and pass.

7.3.2 Automated testing

All tests shall be able to run with a single action.   

7.4 DefaultClassLoader removal

Manual tests shall be performed regarding realized implementations in verifier.
The automated tests may not include full coverage.   

End
---
