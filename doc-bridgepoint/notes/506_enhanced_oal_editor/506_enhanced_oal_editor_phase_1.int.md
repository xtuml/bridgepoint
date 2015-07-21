---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Support phase one of the xtext based OAL editor
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the codes changes that provide a prototype of an Xtext based
editor.

2. Document References
----------------------
[1] Redmine Issue, https://support.onefact.net/redmine/issues/506  
[2] https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/506_enhanced_oal_editor/506_enhanced_oal_editor_phase_1.dnt.md  

3. Background
-------------
See [2].

4. Requirements
---------------
See [2].

5. Work Required
----------------
See [2].

Note that [1] will be promoted with this work.  This promotion does not include
moving the code to master.  This promotion simply provides a prototype of the
new editor to the customer.

6. Implementation Comments
--------------------------

7. Unit Test
------------
See [2].

8. Code Changes
---------------
Branch name: 506_stage1_enhanced_oal_editor_2

<pre>

org.xtuml.bp.xtext.oal/META-INF/
    MANIFEST.MF
org.xtuml.bp.xtext.oal/src/org/
    xtuml/bp/xtext/oal/GenerateXoal.mwe2
org.xtuml.bp.xtext.oal/src/org/
    xtuml/bp/xtext/oal/Xoal.xtext
org.xtuml.bp.xtext.oal/src-gen/
    readme.txt
org.xtuml.bp.xtext.oal/
    xtend-gen/readme.txt
org.xtuml.bp.xtext.oal/.classpath
org.xtuml.bp.xtext.oal/.gitignore
org.xtuml.bp.xtext.oal/.project
org.xtuml.bp.xtext.oal/
    build.properties
org.xtuml.bp.xtext.oal/
    GenerateXoal.mwe2.launch
org.xtuml.bp.xtext.oal/
    xtext_instructions.txt

org.xtuml.bp.xtext.oal.tests/
    META-INF/MANIFEST.MF
org.xtuml.bp.xtext.oal.tests/
    src/readme.txt
org.xtuml.bp.xtext.oal.tests/
    src-gen/readme.txt
org.xtuml.bp.xtext.oal.tests/
    .classpath
org.xtuml.bp.xtext.oal.tests/
    .gitignore
org.xtuml.bp.xtext.oal.tests/
    .project
org.xtuml.bp.xtext.oal.tests/
    build.properties

org.xtuml.bp.xtext.oal.ui/
    META-INF/MANIFEST.MF
org.xtuml.bp.xtext.oal.ui/src/
    readme.txt
org.xtuml.bp.xtext.oal.ui/
    src-gen/readme.txt
org.xtuml.bp.xtext.oal.ui/
    .classpath
org.xtuml.bp.xtext.oal.ui/
    .gitignore
org.xtuml.bp.xtext.oal.ui/
    .project
org.xtuml.bp.xtext.oal.ui/
    build.properties

</pre>

End
---

