---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Dump BridgePoint Code into xtuml/editor Repository
### xtUML Project Design Note



1. Abstract
-----------
This note describes the work to be done to quickly make BridgePoint code available
in the Open Source repository.  

2. Document References
----------------------
[1] Issues 162, https://github.com/xtuml/internal/issues/162  
[2] CQ DEI dts0101036706 - Place more code into public repository  
[3] CQ DEI dts0100906711 - BridgePoint Open Source Phase II   
[4] CQ DEI dts0100919690 - Separate the open source code from the closed source code.  

3. Background
-------------
The BridgePoint team formally announced our intention to put the source code to
(most of) BridgePoint into the Open Source Domain in September of 2012.  Customers
had been informed privately in March of 2012.  In 
February of 2013, we announced via xtuml.org that code was available.  In reality,
little code was thereafter made available as we worked on the long-running project
to fully separate the editor and verifier code [3][4].  

Customers and customer advocates are concerned that the move to OSS is not
progressing quickly enough.  Customers are expressing concern over vendor
lock-in with xtUML and BridgePoint.  Dissatisfaction is growing.

In response to customer requests, we must move a bit more quickly.  While we
continue in parallel on the infrastructure to build OOAofOOA and while we
complete the extraction/separation of the key bits of Verifer and prebuilder
IP, we will make available now a version of the models and plugins.  These
models and plugins will satisfy the need of customers to be assured that
they are not locked in to closed source UML editing.

The strategy for xtUML/BP development will be to protect as little as
possible to maintain control of Verifier and prebuilder (model compilers).
Default to open source.  Anything that is verifier user interface is considered 
part of "the editor" and will not be kept private.

4. Requirements
---------------
4.1  Mentor Graphics proprietary IP for pre-builder and Verifier shall not be distributed.  
  
4.2  Generated Java shall be included for all plug-ins put into public xtuml/editor  
4.2.1  The java shall be generated from the OOAofOOA stripped of Verifier bits  
4.2.2  The plug-ins shall be compilable and exportable into usable plug-ins    
  
5. Analysis
-----------
5.1  The following projects & plug-ins __shall not__ be included:  
```
com.mentor.nucleus.bp.core.linux.x86
com.mentor.nucleus.bp.core.win.x86
com.mentor.nucleus.bp.docgen          ** See Note 1
com.mentor.nucleus.bp.internal.tools  ** See Note 2
com.mentor.nucleus.bp.mc
com.mentor.nucleus.bp.mc.c.binary
com.mentor.nucleus.bp.mc.c.source
com.mentor.nucleus.bp.mc.cpp.source
com.mentor.nucleus.bp.mc.mcpaas
com.mentor.nucleus.bp.mc.none         
com.mentor.nucleus.bp.mc.systemc.source
com.mentor.nucleus.bp.mc.template
com.mentor.nucleus.bp.mc.vhdl.source
com.mentor.nucleus.bp.sequencecapture
Installer_MIP_MIMIC
libTRANS
MC-Java
MC-Java.test
pt_antlr
```
  - __Note 1:__ com.mentor.nucleus.bp.docgen will not be made OSS.  It contains no real IP. However, 
  it relies entirely on tools we will not make OSS or put into xtuml/editor: pre-builder, xtumlmc_build.exe, docgen.exe, xsltproc.  It also has build dependencies on the bp.mc* plug-ins and will not compile without them.  
  - __Note 2:__ Some of the classes in this plug-in provide small utilities we use during development.
  However, the Java Export Builder cannot be made OSS.  Additionally, we don't want to move stuff to bp.utilities
  that would make bp.utilities dependent on other parts of the tool.  Other projects should depend on bp.utilities, 
  not it on others.  Thus, we will keep the bp.internal.tools plug-in private to contain MC-Java tools and 
  carefully consider moving other classes to the OSS plug-in bp.utilities.

5.2  The OOAofOOA model in bp.core will be stripped of the following subsystems/packages
  (marked with X) before the project is posted to xtuml/editor:
```
  - Datatypes
  ** Functions
  ** External Entities
  - Activity
  - Association
  - Body
  X Breakpoint
  - Communication
  - Communication And Access
  - Component
  - Constants
  - Domain
  - Element Packaging
  X Engine (new subsystem)
  - Event
  - Globals
  - Instance [see note 5.2.2]
  - Instance Access
  - Interaction
  - Interface Package
  - Invocation
  X Local
  - Message
  - Model Integrity
  - Packageable Element
  - Persistence Associations
  - Relate and Unrelate
  X Runtime Value
  ** Search
  - Selection
  - Sequence
  - State Machine
  - Subsystem
  - System Level Datatypes
  - Use Case
  - Value
  - Wiring
```  
5.2.1  Items marked with ** were previously stripped from the OOAofOOA shipped
  in the bp.welcome plug-in, but they will no longer be stripped.  
5.2.2  The Instance package is divided so that Verifier specific
  IP is placed into a separate package (Engine) which is then reserved. The 
  elements left public will be Instance, Attribute Value, Link
  Participation and Link. The future possibility of pre-created events shall also
  be supported, requiring Pending Event and Data Item Value to also be left public. The Timer, 
  Component Instance and Component Instance Container classes will also be made public. The new reserved
  package will be named Engine. The pruned Instance package will be published.  

5.3  The license files in the plug-ins that are put into xtuml/editor needs to be
  changed from the Mentor Graphics EULA to the Apache license.    
  
5.4 Mentor IP   
5.4.1 The Java Licensing Component (JLC) and SVX jars shall be removed from bp.core
  before the project is posted to xtuml/editor.    
5.4.2 The demo BP encryption pieces shall be removed from bp.core before the project 
  is posted to xtuml/editor.   
5.4.3 The expected results in io.mdl.test has an ooaofooa model that shall be removed 
  before the project is posted to xtuml/editor.  
5.4.4 The BridgePointLicenseManager.java shall be removed from bp.core before the project
  is posted to xtuml/editor.  If we leave it, people can modify the license code to 
  just never check a license.  They could then put this change in the licensed version of the tool.  
                           

6. Design
---------
6.1  Process  
6.1.1  Create a branch of xtuml/internal  
6.1.2  Extract  the model data from bp.core/ooaofooa as specified in 5.2  
6.1.3  Build the branch  
6.1.4  Create a new private repository xtuml/editor-test, branch it  
6.1.5  Copy all the plug-ins and projects from the xtuml/internal branch to the xtuml/editor-test branch  
6.1.6  Modify the data copied into xtuml/editor-test    
6.1.6.1  Remove the projects & plug-ins specified in 5.1    
6.1.6.2  Update license files to Apache  
6.1.6.3  Extract Mentor IP as specified in 5.4  
6.1.6.4  Update the projects to not run the Ant builder (since we won't be generating any code from models)  
6.1.6.5  Update ```.gitignore``` files to allow the check-in of generated Java files.  
6.1.7  Commit the files to xtuml/editor-test branch  
6.1.8  Create a HOWTO/Readme in xtuml/editor-test that explains how to build the plug-ins  
6.1.9  Promote the files to master in xtuml/editor-test  
6.1.10  Have other members of the development team review xtuml/editor-test for sensitive data
6.1.11  Create a branch of xtuml/editor  
6.1.12  Copy files from private repo xtuml/editor-test to xtuml/editor branch  
6.1.13  Promote files from branch in xtuml/editor to master  
6.1.14  Remove temporary private repo xtuml/editor-test  
6.1.15  Make announcement on xtuml.org that lots of code has been released  

6.2  Capture the details of the actual steps performed in 6.1 into a reusable 
  document that can be ran through again upon each BP release until we have fully
  implemented our code separation under [4].  Put this HOWTO document under our
  internal repository at ```https://github.com/xtuml/internal/tree/master/doc-internal/process```.
  Note that it may make sense for the reusable process to rely on merging in updates from the private repository 
  upon a new release rather than reapplying all the steps from 6.1 again.  This
  process will also help when we switch our development process to use code from
  the public repository rather than the private repository.    
  
  
7. Design Comments
------------------
None.

8. Unit Test
------------
8.1  Any user who desires shall be able to perform the following steps:  
  - Start vanilla eclipse on clean workspace
  - Clone xtuml/editor
  - Import projects from xtuml/editor
  - Build Automatically
  - __R__ Build succeeds on all projects
  - Create an eclipse Run Launch configuration for all internal and workspace plug-ins
  - Run the launch
  - __R__ The launch contains the xtUML Modeling perspective.  
  - Create a new project
  - __R__ The project is created
  - __R__ User can draw with modeling tools as normal
  - __R__ User can run JUnit tests using the eclipse development environment and they pass

End
---

