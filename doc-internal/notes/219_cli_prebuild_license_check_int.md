---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# dts0101052685 - Update prebuildOnly license check
### xtUML Project Implementation Note


1. Abstract
-----------
During release testing we found that the new CLI -prebuildOnly functionality was not
checking out a license when executed.

2. Document References
----------------------
[1] Issues 219, https://github.com/xtuml/internal/issues/219  
[2] CQ DEI dts0101052685  
[3] Issues 201, https://github.com/xtuml/internal/issues/201 - Implement CLI Build as truly headless  
[4] CQ DEI dts0101052706 - Reconsider DocGen licensing  

3. Background
-------------
CLI pre-build functionality changed with [3].  The implementation was based on DocGen
functionality, which we incorrectly thought required a license.  DocGen does not require 
a license.

4. Requirements
---------------
4.1  CLI -prebuildOnly must check out a license

5. Work Required
----------------
5.1. Add a new entry point to the ExportBuilder that will export a system with a license check.  
5.2. We have chosen not to require the prebuild license during docgen at this time and raised [4].  

6. Implementation Comments
--------------------------
None.

7. Unit Test
------------
7.1  Using the CLI Build launch, run with -prebuildOnly and valid licenses enabled, verify the code checks 
out a license  
7.2  Using the CLI Build launch, run with -prebuildOnly and no valid licenses, verify the code fails when 
checking out a license  

8. Code Changes
---------------
Branch name: 219_prebuildonly_lic_check

<pre>
com.mentor.nucleus.bp.cli [internal 219_prebuildonly_lic_check]/src/com/mentor/
    nucleus/bp/cli/BuildWorkbenchAdvisor.java

com.mentor.nucleus.bp.mc [internal 219_prebuildonly_lic_check]/src/com/mentor/
    nucleus/bp/mc/AbstractExportBuilder.java



</pre>

End
---
