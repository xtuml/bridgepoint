---

This work is licensed under the Creative Commons CC0 License

---

# Make unit tests run in the order they are listed in a test class
### xtUML Project Design Note


1. Abstract
-----------
JUnit test methods execution order is modified since the migration to Eclipse 
Mars. This causes many tests failure.
This document describe how to restore the test methods execution order back to 
avoid these failures.  

2. Document References
----------------------

<a id="2.1"></a>2.1 [BridgePoint DEI #8352](https://support.onefact.net/issues/8352) 
<a id="2.2"></a>2.2 [Analysis note](https://github.com/nmohamad/bridgepoint/blob/8005_Test_Orderring/doc-bridgepoint/notes/8005_Test_Orderring/8005_Test_Orderring_ant.md) 

3. Background
-------------
See [[2.2]](#2.2)  

4. Requirements
---------------
See [[2.2]](#2.2)  

5. Analysis
-----------
See [[2.2]](#2.2)  

6. Design
---------
See [[2.2]](#2.2)  

7. Design Comments
------------------
7.1 Unlike what is mentioned in [[2.2]](#2.2) in 6.4, all JUnit Suite needs to be
migrated as well to use the custom test run order. 

7.2 JUnit 4 test class must have single zero-argument constructor.  
7.2.1 To get the test method name -was passed to class constructor as argument 
in JUnit 3- @Rule TestName is used as in [ParseAllInDomain_Generics](https://github.com/nmohamad/bridgepoint/blob/8005_Test_Orderring/src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/ParseAllInDomain_Generics.java)  
7.2.2 Argument constructor is used for parameterized test (a test that has test 
method or more will run with different data set). An example for parameterized
test in [MCModelConsistencyGenericsGlobals](https://github.com/nmohamad/bridgepoint/blob/8005_Test_Orderring/src/org.xtuml.bp.io.mdl.test/src/org/xtuml/bp/io/mdl/test/MCModelConsistencyGenericsGlobals.java)  

7.3 It is not possible to use @BeforeClass for out initialSetup as the function
implementation depend on objects existence, while JUnit @BeforeClass requires a 
static function, therefore @Before is used with a static flag to ensure single 
run for the function.  

8. Unit Test
------------
See [[2.2]](#2.2)   

End
---

