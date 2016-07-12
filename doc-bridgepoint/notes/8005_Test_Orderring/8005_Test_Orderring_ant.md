---

This work is licensed under the Creative Commons CC0 License

---

# Clean up JUnits tests
### xtUML Project Analysis Note



1. Abstract
-----------
JUnit test methods execution order is modified since the migration to Eclipse 
`Mars. This causes many tests failure.
This document describe how to restore the test methods execution order back to 
avoid these failures. 

2. Document References
----------------------
<a id="2.1"></a>2.1 [TestNG](http://testng.org/doc/index.html):  Testing Framework.    
<a id="2.2"></a>2.2 [How to Extend Runner Class](http://intellijava.blogspot.com.eg/2012/05/junit-and-java-7.html): A developer blog describe how to extend runner class to introduce new test method invocations order  
<a id="2.3"></a>2.3 [Add new annotation to JUnit 4](http://blog.jiffle.net/post/41125006846/extending-junit-functionality-with-additional): An article that describes how to add new annotation to JUnit 4  
<a id="2.4"></a>2.4  [JUnit 4 Test execution order](https://github.com/junit-team/junit/wiki/Test-execution-order): The available test method invocations order options in JUnit 4  
<a id="2.5"></a>2.5  [Timeout for tests](https://github.com/junit-team/junit4/wiki/Timeout-for-tests): How to implement timout for JUnit 4  

3. Background
-------------
JUnit test methods execution order in Eclipse 3.7, was determined by the test 
methods order in the source code file. This execution order is changed since the 
migration to Eclipse Mars. By design, JUnit does not specify the execution order 
of test method invocations, the methods are simply invoked in the order returned 
by the reflection API which is not guaranteed. JUnit assumes that all tests can
be performed in an arbitrary order. This new behavior causes number of tests
failure, since our tests are not always independent on each others, so
changing the invocation order causes failures.

JUnit states that well-written test code would not assume any order, but it is 
very hard to refactor our tests to resolve their dependencies.

4. Requirements
---------------
<a id="4.1"></a>4.1 JUnit test methods shall be invoked in the same order as they are written in
	the source file. New Junit Test may not 

5. Analysis
-----------
Investigation is made to find way to force JUnit to execute the test methods as 
required, or finding JUnit alternatives that address the requirement.
The following are the available approaches:

5.1 Using TestNG technology [[2.1]](#2.1):  TestNG is a very similar to JUnit, specially JUnit 4,
as it uses annotation to define the test , setup tear down functions and 
other execution options, one of them is Priority annotation, which can be used
to define test methods invocation order. TestNG offers also conversion action to 
convert JUnit classes to TestNG classes. But TestNG launcher has limited execution
options which prevent us from using it in Eclipse Plug-in testing, unlike JUnit which
offers two type of launchers, one is JUnit (this one is very similar to TestNG launcher)
and JUnit Plug-in Test, which is used in BrdigePoint testing.


5.2 Since JUnit 4.11, JUnit starts to offer a limited number of options for tests
invocation order. These options are [[2.4]](#2.4):
		Name Ascending:  Sorts the test methods by method name, in lexicographic order.  
		JVM:  Leaves the test methods in the order returned by the JVM. This order may vary from run to run.  
		Default:  a deterministic, but not predictable order, and it basically uses hashing to define the test invocation order.  
	The Name Ascending option sounds suitable, but it has two down sides:  
	- All test functions need to renamed to add a prefix that achieve the required test order (e.g. test_A1_foo(), test_A2_goo(), etc).  
	- Name Ascending order will execute test_A11_ before test_A2_ and it make it more uglier, and harder to rename the test methods
	to achieve the required order.  

<a id="5.3"></a>5.3 Using JUnit 4, and by extending the runner class (BlockJUnit4ClassRunner) [[2.2]](#2.2), and override computeTestMethods()
	method, it is possible to define any test methods invocation order. Test Classes needs to be annotated with RunWith
	to use the extended runner class.

<a id="5.4"></a>5.4 Similar to [[5.3]](#5.3) , it is possible to add a new custom annotation (e.g. Order) [[2.3]](#2.3), and use it to determined the test 
	invocation order, which increase the flexibility to change the order more easily.
	Adding incremental value for Order annotation would be easy in the generated test classes,
	but for non-generated test classes, a script is needed to add incremental value for
	Order annotation.
	

6. Work Required
----------------
[[5.3]](#5.3) is selected to implement, as work needed is most minimized, and there
is no need to switch the testing technology. Also is fairly easy to move
to Order annotation [[5.4]](#5.4) in future if needed.
The required work:

6.1 A new extended runner class needs to be introduced with the desired test invocation order as stated in [[4.1]](#4.1).  
6.2 Add annotation to all test classes as follows:    
6.2.1 Add @Test annotation for each test method.  
6.2.2 Add @Before annotation for each setup method.  
6.2.3 Add @After annotation for each teardown method.  
6.2.4 Add @RunWith annotation for each test class.  
6.2.5 Add @BeforeClass for each initialSetup method.  

6.3 Apply change annotation for generated tests in archetypes files, and Perl test
class generator as well.  

6.4 No changes are required for Test Suite classes.  

6.5 modify all the places where "dotest" was added.  Roll back the "doTest" 
    change in favor of the new annotations and ordering.  

6.6 Modify Junit Plugin Test Launchers to use JUnit 4 instead of JUnit 3  

6.7 JUnit 4 offers test timeout for time restriction test or skip hanging test,
for more information about how to use [[2.5]](#2.5)

7. Acceptance Test
------------------
7.1 All test methods shall be invoked in the same order as they are written in 
the source file.  

End
---

