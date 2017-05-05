---

This work is licensed under the Creative Commons CC0 License

---

# Title goes here
### xtUML Project Review Minutes

Reviewed:  https://github.com/travislondon/bridgepoint/blob/8605_unify_testing/doc-bridgepoint/notes/8605_unify_testing/8605_unify_testing.dnt.md
  15d2023a55bbb00a21b20a27b3c03e7c2474d2ea
Present:  Cort, Bob, Levi, Travis

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   bob  4       use the requirements from the issue's contract:
            This job has the following requirements:
            1. The BridgePoint unit tests shall report 100% passing (consistently) 
            2. Unused expected results shall be removed
            3. There shall be only 1 set of test results (no environment specific results)
            4. Unit tests shall run with the same result on linux and mac  
            5. The number of passing unit tests shall be greater than or equal the 6.2.4 result
            6. Redmine Issues shall be raised for any test/suites that are removed
            6.1 Any such issue shall use Redmine issue 8605 as a parent. 
            7. Enhance the HOWTO Run BridgePoint Unit Tests document to describe how to add a test suite. (https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md)
            8. Work with the One Fact team to improve the build server to automation to assure the build server can:
            8.1 Launch build with or without unit testing from a trigger (no need to log into AWS)
            8.2 Optionally terminate the instance when complete


_- 02  min   cds  8      grammar
_- 03  min   cds  6.1.2      grammar
_- 04  min   bob  6.4      instead of commenting out the tests, remove them
_- 05  min   bob  6.5      list the issues that were closed by this work in the references section and refer them here.
_- 06  min   cds  6.5      Raise an issue: Always build using maven. Even in eclipse based build. 
                          It needs to be incremental like our current build env. 

</pre>
   
No major observations, a re-review is not required.


End
---
