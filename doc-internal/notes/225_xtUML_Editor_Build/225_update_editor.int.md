---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Update the xtUML Editor Builder
### xtUML Project Implementation Note


1. Abstract
-----------
A customer encountered a problem in v4.1.12 of the source xtUML Editor that
that causes running error in debug mode.  This issue is raised to resolve this 
problem and to create a mechanism for maintaining the xtUML Editor in 
parallel with BridgePoint.

2. Document References
----------------------
[1] Git internal, Issues 225, https://github.com/xtuml/internal/issues/225		
	Update the xtUML Editor based on the 4.1.10 release  
[2] Git Editor, Issues 21, https://github.com/xtuml/editor/issues/21
	Update the OSS source for 4.1.10
[3] Git Editor, Issues 18, https://github.com/xtuml/editor/issues/18
	Can not delete objects from model when running editor built from source
[4]	Clearquest dts0101036706 
	Place more code into public repository
[5]	Git internal, issues 162,  https://github.com/xtuml/internal/issues/162
	Place more code into public repository
[6] https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-create-xtUML-Editor.md
[7]	https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-upload-xtUML-editor-installer.md

3. Background
-------------
A customer hit a problem with v4.1.10 of the xtUML Editor which was the first 
version of the xtUML Editor that provided the ability to modify and rebuild the
editor [3]. The version of the editor that had this problem was v1.4.10, and it
was built using BridgePoint v4.1.6 as its base [4][5].  The initial deployment
was done in a way that quickly sumped the source for users, but it did not 
define a process for how to rebuild and maintain the xtUML Editor in parallel 
with BridgePoint development.   This issue is raised to resolved the customer
issue that was found in the original deployment of the full-souce editor [3],
and to do so in a way that creates a process that allows the editor to be 
easily maintained for both BridgePoint changes as well as changes coming from 
the public.
 
4. Requirements
---------------
4.1 The xtUML Editor shall not encounter errors when deleting objects during
    a session run from the xtUML Editor launch configuration [3].
4.2 The xtUML Editor source and binary shall be maintained in parallel.
4.3 It shall be possible to have xtUML Editor releases independent of
    BridgePoint releases.
4.4 Process shall allow BridgePoint and the xtUML Editor to main maintained
    in parallel with minimum effort while moving toward full code code 
    separation (separation of the EDitor source and BridgePoint licensed 
    components).     


5. Work Required
----------------
This section borrowed heavily from the design and implementation notes written
for the initial xtUML Editor source dump [4][5].  This describes the procedure 
used to create this xtUML Editor.  

5.1. Item 1  
5.2. Item 2  

5.3. Move the internal/xtuml_editor branch into the editor repository
  
* branch the editor repository
* delete all src/* that will be updated (note: some things exists only in editor, so they will remain)
* commit the deletion of files
* copy the new src/*
* commit the new source
* build and test

6. Implementation Comments
--------------------------


7. Unit Test
------------
Unit tests are still not passing (they were not passing in the prior release 
either).  However, results are checked-in.   The failures are because of tests
that need to be modified because of code removal.  The test will not pass until 
the full-code separation is done so we only put the effort into reworking 
tests one time.

8. Code Changes
---------------

8.1 Branch name: editor/21_Update_OSS_Source
--------------------------------------------
EVERY files is changed. The process of migrating the code into the editor 
repository from the internal repository is such that the old editor files are 
deleted and the new ones are then copied into place.


8.2 Branch name: internal/xtuml_editor_builder
----------------------------------------------
This is binary editor branch.  This is where the xtUML Editor binary is 
maintained.  When changes are promoted to either internal/master OR
editor/master they shall also be promoted into this branch.

8.3 Branch name: internal/xtuml_editor
--------------------------------------
This is the xtUML Editor source branch.  This branch was created from 
the xtuml_editor_builder branch.  This branch is a mirror of what is found in 
the  editor/master branch.   This branch exists because there are a lot of
plugins and source files that do not exist in the xtUML EDitor Source that 
are necessary in order to build the binary editor.

<pre>

< Put the file list here >

</pre>

End
---

