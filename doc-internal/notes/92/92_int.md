---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Prevent parse errors when finding more than one external entity
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the changes required to prevent a parse error
when locating more than one External Entity with the same key letters.

2. Document References
----------------------
[1] Issues 92, https://github.com/xtuml/doc/issues/92   
[2] CQ Issue: dts0100939989

3. Background
-------------
External Entities have recently changed [2] such that they can be referenced
from other projects.  This change did not remove the parse logic that created
errors when finding multiple External Entities with the same key letters.  This
situation will be common at first and there is no reason to create a parse error
as the collection mechanism will return the first External Entity found.

4. Requirements
---------------
4.1 The tool should not create a parse error when multiple External Entities
exist with the same key letters along the search path.

5. Work Required
----------------
5.1 Do not consider multiple External Entities found during parse

In the following functions the logic is to throw a parse error when encountering
more than one located External Entity.  In each case this logic is removed and
the existing OAL for the situation where only one External Entity was found is
left as is.  The existing OAL will select the first External Entity in the
search list.

Invocation_function_validation   
EE_KeyLetters_Validate   
Identifier_validate

6. Implementation Comments
--------------------------
6.1 Search globally for EEs in all cases

During this work it was noticed that not all searches for External Entities were
global enabled.  This would cause parse issues when the initial check for a
global External Entity was successful, yet the validation of a bridge was not
successful.  All places that we look for External Entities are modified to
search globally.

6.2 Address typo in dangling reference message

A typo was found in NonRootModelElement.checkReferentialIntegrity().  This typo
is in a message to the user about dangling references.  It is addressed and the
associated test results is updated to reflect this.

7. Unit Test
------------
_- Load agilegc models   
_- Navigate to the PM component and run Parse All   
_R There are no parse errors   

8. Code Changes
---------------
Branch name: 92_ee_parse_issues   

<pre>
com.mentor.nucleus.bp.core/models/com.mentor.nucleus.bp.core/ooaofooa/  
	Functions/OAL Validation Functions/OAL Validation Functions.xtuml  
com.mentor.nucleus.bp.core/models/com.mentor.nucleus.bp.core/ooaofooa/  
	Functions/OAL Validation Utility Functions/  
	OAL Validation Utility Functions.xtuml  
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/common/  
	NonRootModelElement.java  

com.mentor.nucleus.bp.core.test/expected_results/Dangling_Reference_Integrity/  
	Dangling_Reference_Integrity.txt
</pre>

End
---

