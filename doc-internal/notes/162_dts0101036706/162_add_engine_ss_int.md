---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Dump BridgePoint Code into xtuml/editor Repository - Instance SS Split Baby Step
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work done to complete part of the requirements for the
OSS code dump.  Specifically, this addresses splitting the Instance subsystem
in OOAofOOA into public and private parts.

2. Document References
----------------------
[1] Issues 162, https://github.com/xtuml/internal/issues/162  
[2] CQ DEI dts0101036706 - Place more code into public repository  
[3] OSS code dump design note, https://github.com/xtuml/internal/blob/master/doc-internal/notes/162_dts0101036706/162_editor_code_dump_dnt.md  

3. Background
-------------
The move to OSS will involve a number of steps (outlined in [3]).  One of the key
initial steps is to separate the verifier meta-model classes into public and private
parts.  This work will go into the mainline BridgePoint meta-model unlike some of 
the later steps.  Therefore, this work is handled separately and concisely as part
of the larger project of OSS code dump.  

4. Requirements
---------------
4.1  The Instance SS shall no longer contain any parts of the verifier meta-model
  we intend to keep private.
  
5. Work Required
----------------
5.1. Create Engine subsystem by copying Instance subsystem     
5.2. Edit Instance SS such that the following classes are left  
  - Attribute Value
  - Component Instance 
  - Component Instance Container
  - Data Item Value
  - Instance
  - Link
  - Link Participation
  - Pending Event
  - Timer
  
Also leave any imported classes that are related to these classes.  Remove all
other classes and imported classes.  Remove the Class State Machine Execution SS.  
  
5.3. Engine SS changes  
  - Remove the classes listed in 5.2 along with any imported classes that are related to these classes
  - Add imported classes: Instance, Component Instance, Pending Event
  - Re-add relationships (re-using the previous relationship numbers and cardinality) to the new imported classes

5.4. Class State Machine Execution SS changes  
  - Remove the classes: Instance, Component Instance, Pending Event
  - Add imported classes: Instance, Component Instance, Pending Event
  - Re-add relationships (re-using the previous relationship numbers and cardinality) to the new imported classes

5.5  Local SS changes  
  - Remove the class: Block in Stack Frame
  - Add imported classes: Block in Stack Frame (now from Engine)
  - Re-add relationship (re-using the previous relationship number and cardinality) to the new imported class

5.6  Runtime Value SS changes  
  - Remove the class: Stack Frame, Value in Stack Frame
  - Add imported classes: Stack Frame, Value in Stack Frame (now from Engine)
  - Re-add relationships (re-using the previous relationship numbers and cardinality) to the new imported classes

5.7  Inspect each attribute, and attribute order of all classes in the updated Instance, 
  Engine, and Class State Machine Execution subsystems.  Fix any order problems.  Fix any
  missing attributes due to formalization changes.  
  
  
6. Implementation Comments
--------------------------
6.1  While running JUnits I noticed that we had an old and unnecessary test 
  file ```CoreTestSuiteIIGenerics.java```.  This file is removed.  
  
6.2  We could leave SQE and EQE public in the Instance SS.  However, there is 
  sentiment in the team that these probably shouldn't even be in the meta-model 
  proper.  Therefore, this will change not be done and these classes will go 
  into Engine SS.  
  
6.3  We currently strip all I_ instances before model compilation.  We could 
  potentially leave the new public Engine instances.  However, we will not do so.
  We will continue to strip all of I_.  The value of making this change is low
  right now and in order to implement we would have to proceed with re-prefixing
  all classes in the Engine SS from I_ to ENG_.  Then we would modify xtumlmc_build
  to strip ENG_ in ```xtumlmc_cleanse_model``` and leave I_.  
  
6.4  While doing this work I noted there were three unassigned imported classes
  in the Instance SS.  These are removed.  
  
6.5  We could potentially change the Instance SS marking (in the subsystem Description)
  ```TRANSLATE_FOR_EXTERNAL_USE:FALSE``` to TRUE.  However, we have decided to make
  as few changes as possible with this work and will not change it at this time.  
  
7. Unit Test
------------
7.1  All JUnit tests must pass
7.2  MC-Java runs schema-gen and puts the output schema in ```bp.core/sql/metamodel_schema.sql```.
  Compare the schema from the workspace with the new branch against master.  Verify functional
  equivalence.  
      
8. Code Changes
---------------
Branch name: 162_add_engine_ss

<pre>

com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/Datatypes/Datatypes.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/ooaofooa.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Engine.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Block in Stack Frame/
    Block in Stack Frame.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Class State Machine Execution/
    Class State Machine Execution.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Class State Machine Execution/
    Class In Engine/Class In Engine.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Class State Machine Execution/
    Class In State/Class In State.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Class State Machine Execution/
    Class Monitor/Class Monitor.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Event Queue Entry/
    Event Queue Entry.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Intercomponent Queue Entry/
    Intercomponent Queue Entry.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Monitor/Monitor.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Runtime Channel/
    Runtime Channel.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Self Queue Entry/
    Self Queue Entry.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Stack/Stack.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Stack Frame/Stack Frame.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Engine/Value in Stack Frame/
    Value in Stack Frame.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Instance.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Block in Stack Frame/
    Block in Stack Frame.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Class State Machine Execution/
    Class State Machine Execution.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Class State Machine Execution/
    Class In Engine/Class In Engine.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Class State Machine Execution/
    Class In State/Class In State.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Class State Machine Execution/
    Class Monitor/Class Monitor.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Event Queue Entry/
    Event Queue Entry.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Instance/Instance.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Intercomponent Queue Entry/
    Intercomponent Queue Entry.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Monitor/Monitor.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Pending Event/
    Pending Event.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Runtime Channel/
    Runtime Channel.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Self Queue Entry/
    Self Queue Entry.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Stack/Stack.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Stack Frame/Stack Frame.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Instance/Value in Stack Frame/
    Value in Stack Frame.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Local/Local.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Local/Local/Local.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Runtime Value/Runtime Value.xtuml
com.mentor.nucleus.bp.core/models/
    com.mentor.nucleus.bp.core/ooaofooa/Runtime Value/Runtime Value/
    Runtime Value.xtuml

com.mentor.nucleus.bp.core.test/src/
    CoreGlobalsTestSuite2Generics.java
com.mentor.nucleus.bp.core.test/src/com/mentor/
    nucleus/bp/core/test/CoreTestSuiteIIGenerics.java
com.mentor.nucleus.bp.core.test/.gitignore
com.mentor.nucleus.bp.core.test/generate.xml


</pre>

End
---

