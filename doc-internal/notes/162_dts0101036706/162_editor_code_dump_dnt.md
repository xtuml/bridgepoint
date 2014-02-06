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
Default to open source.

4. Requirements
---------------
4.1  Models and plug-ins that make up the xtUML Editor shall be available in the public
  xtuml/editor repository on github.  
4.1.1  The JUnit test plug-ins shall be included.  
4.1.2  The generated java shall be included.  Compiled Java class files shall not.   
    
4.2  Mentor Graphics proprietary IP not specifically part of the xtUML Editor 
  code base shall not be distributed.  
  
4.3  The java code shall be compilable into usable plug-ins.  
  
5. Analysis
-----------
5.1  The following projects & plug-ins __shall__ be included:  
```
com.mentor.nucleus.bp.als
com.mentor.nucleus.bp.als.oal
com.mentor.nucleus.bp.als.oal.test
com.mentor.nucleus.bp.bld.pkg
com.mentor.nucleus.bp.bld.pkg-feature
com.mentor.nucleus.bp.cdt
com.mentor.nucleus.bp.cli
com.mentor.nucleus.bp.compare
com.mentor.nucleus.bp.core
com.mentor.nucleus.bp.core.test
com.mentor.nucleus.bp.dap.pkg
com.mentor.nucleus.bp.dap.pkg-feature
com.mentor.nucleus.bp.docgen
com.mentor.nucleus.bp.io.core
com.mentor.nucleus.bp.io.image
com.mentor.nucleus.bp.io.mdl
com.mentor.nucleus.bp.io.mdl.test
com.mentor.nucleus.bp.io.sql
com.mentor.nucleus.bp.io.sql.test
com.mentor.nucleus.bp.mc
com.mentor.nucleus.bp.mc.c.binary
com.mentor.nucleus.bp.mc.c.source
com.mentor.nucleus.bp.mc.cpp.source
com.mentor.nucleus.bp.mc.systemc.source
com.mentor.nucleus.bp.mc.template
com.mentor.nucleus.bp.mc.vhdl.source
com.mentor.nucleus.bp.mc.xmiexport
com.mentor.nucleus.bp.model.compare
com.mentor.nucleus.bp.model.compare.test
com.mentor.nucleus.bp.pkg
com.mentor.nucleus.bp.pkg-feature
com.mentor.nucleus.bp.search
com.mentor.nucleus.bp.search.test
com.mentor.nucleus.bp.sequencecapture
com.mentor.nucleus.bp.test
com.mentor.nucleus.bp.ui.canvas
com.mentor.nucleus.bp.ui.canvas.test
com.mentor.nucleus.bp.ui.explorer
com.mentor.nucleus.bp.ui.explorer.test
com.mentor.nucleus.bp.ui.graphics
com.mentor.nucleus.bp.ui.properties
com.mentor.nucleus.bp.ui.properties.test
com.mentor.nucleus.bp.ui.search
com.mentor.nucleus.bp.ui.sem
com.mentor.nucleus.bp.ui.session
com.mentor.nucleus.bp.ui.text
com.mentor.nucleus.bp.ui.text.test
com.mentor.nucleus.bp.ui.tree
com.mentor.nucleus.bp.utilities
com.mentor.nucleus.bp.welcome
com.mentor.nucleus.bp.welcome.test
com.mentor.nucleus.help.bp.mc
com.mentor.nucleus.internal.test
```  

5.2  The following projects & plug-ins __shall not__ be included:  
```
com.mentor.nucleus.bp.core.linux.x86
com.mentor.nucleus.bp.core.win.x86
com.mentor.nucleus.bp.debug.ui
com.mentor.nucleus.bp.debug.ui.test
com.mentor.nucleus.bp.doc
com.mentor.nucleus.bp.internal.tools
com.mentor.nucleus.bp.mc.mcpaas
com.mentor.nucleus.bp.qa.odometer
com.mentor.nucleus.bp.verifier.pkg
com.mentor.nucleus.bp.verifier.pkg-feature
Installer_MIP_MIMIC
libTRANS
MC-Java
MC-Java.test
org.antlr_2.7.2
pt_antlr
```

5.3  The OOAofOOA model in bp.core will be stripped of the following subsystems/packages
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
  - Event
  - Globals
  - Instance
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
5.3.1  Items marked with ** were previously stripped from the OOAofOOA shipped
  in the bp.welcome plug-in, but they will no longer be stripped.      

5.4  The license files in the plug-ins that are put into xtuml/editor needs to be
  changed from the Mentor Graphics EULA to the Apache license.    
  
5.5 The Java Licensing Component (JLC) and SVX jars shall be removed from bp.core
  before the project is posted to xtuml/editor.    

6. Design
---------
6.1  Process  
6.1.1  Build the latest master files for xtuml/internal  
6.1.2  Create a branch of xtuml/editor  
6.1.3  Copy all the desired plug-ins from xtuml/internal to xtuml/editor  
6.1.4  Extract  the model data from bp.core/ooaofooa as specified in 5.3  
6.1.5  Update license files to Apache  
6.1.6  Extract Mentor IP as specified in 5.5  
6.1.7  Commit the files to xtuml/editor branch  
6.1.8  Promote the files to master  
6.1.9  Make announcement on xtuml.org that lots of code has been released  

7. Design Comments
------------------
None.

8. Unit Test
------------
None.

End
---

