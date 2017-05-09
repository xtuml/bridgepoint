# HOWTO Add tests to the BridgePoint testing environment
This document provides the information required to add tests to the BridgePoint testing environment.  

Preparation 
-----------
1) See the [HOWTO run BridgePoint Unit Tests](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md) for getting the proper environment set for running the BridgePoint development unit tests.  

Creating a new test
-----------  
- Create the j-unit test class in the UI
  - This class should extend the org.xtuml.bp.test.common.BaseTest class.  This allows the test to fit well with the current test infrastructure.
  ```java
  import org.xtuml.bp.test.common.BaseTest;
  
  public class TestClass extends BaseTest {
  ```
  - Create a setUp method that overrides the BaseTest.setUp() method, annotate the method with the Before annotation:
  ```java
  @Override
  @Before
  public void setUp() throws Exception {
       // calling the super setup will create some common
       // data used amongst most of the tests, see that
       // method implementation for details
       super.setUp();
       // add any code here that will run at
       // the start of each test defined later
       // in this class
  }
  ```
  - Create a tearDown method that overrides the BaseTest.tearDown() method, annotate the method with the After annotation:
  ```java
  @Override
  @After
  public void tearDown() throws Exception {
       // calling the super tearDown will automatically
       // provide log file checking at the end of each
       // tests run
       super.tearDown();
       // add any code here that will run at
       // the end of each test defined later
       // in this class
   }
   ```

Adding a single test to existing suite     
-------  
- Add the new j-unit test class to the desired test suite  
  - Edit the suite class as follows:
  ```java
  // File: src/org/xtuml/bp/project/test/TestSuite.java
  
  @RunWith(Suite.class)
  @Suite.SuiteClasses({
	  ShapeResizeTest.class,
	  TestClass.class // <-- newly created class

  ```  
- At this point the new test class has been added to the BridgePoint testing environment, both for the CLI and UI cases.  

Adding a new test suite  
-------  
- Add a new test suite class
  - Create a new class with the following format in the desired test project:
  ```java  
  // File: src/org/xtuml/bp/project/test/NewSuite.java
  
  @RunWith(Suite.class)
  @Suite.SuiteClasses({
      // add all required tests class here
	  TestClass.class,
	  TestClassOther.class,
  })
  public class NewSuite extends TestSuite {
	  // With junit 4 and greater the body
	  // of the class remains empty
  }
  ```  
- Add the new test suite to the UI  
  - Create a new junit Plug-in launch configuration  
    - Open Run > Debug Configurations...  
    - In the left tree right click on the JUnit Plug-in Tests entry and choose New  
    - On the Test tab set the following:  
      - Name
      - Project (Project containing the test suite)  
      - Test class (The new test suite class)  
    - On the Main tab set the following:  
      - Location: ${eclipse_home}/bp_tests/[test_suite_name]  
      - Check Clear and Workspace  
      - Check Run an application and choose org.eclipse.ui.ide.workbench  
      - Set Java executable to default  
    - On the Arguments tab set the following:  
      - Program arguments: -pluginCustomization ${resource_loc:/org.xtuml.bp.core}/../org.xtuml.bp.pkg/plugin_customization.ini  
      - VM arguments: -Xmx1024m -Xms512m -Dorg.eclipse.epp.logging.aeri.ui.skipReports=true -DWORKSPACE_PATH=${resource_loc:/org.xtuml.bp.test}/../org.xtuml.bp.project.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/[test_suite_name]/.metadata/.log -Dfile.encoding=UTF-8  
    - Click Apply  
    - Right click on the new launch configuration in the left tree and choose Duplicate  
    - Change the name appending " (OSX)"  
    - On the Arguments tab add the following at the beginning of VM Arguments:  
      - -XstartOnFirstThread  
    - Click Apply
  - You can now run or debug the new test suite in the UI  
- Add the new test suite to the CLI  
  - Edit the pom.xml file located under the test project
    - Locate the tycho-surefire-plugin entry  
    - Under this entry locate the includes entry  
    - Add a new include entry for the new test suite:  
  ```
    <include>**/NewSuite.java</include> 
  ```  
  - The suite will now be run along with the CLI build and test procedure  
  
Adding a new test project  
-------  
- Add a new Plug-in project  
  - Right click in Project Explorer and choose New > Project...  
  - Choose the Plug-in Development > Plug-in Project wizard and choose Next  
  - Name the project and choose Next  
  - Modify any of the Properties on this page and click Finish  
- In the plug-in editor that is opened select the Dependencies tab  
  - Click the Add... button under Required Plug-ins  
  - Choose the org.junit plug-in and click OK  
  - Repeat adding any BridgePoint plug-ins that are to be tested  
  - Repeat adding the org.xtuml.bp.test plug-in  
  - If the tests that will exist under the new project will make use of the Canvas result comparison infrastructure add the org.xtuml.bp.ui.canvas.test plug-in  
- Create a pom.xml file at the root of the project  
  - Right click on the new project in Project Explorer and choose New > File  
  - Enter pom.xml for the file name  
  - Enter the required surefire configuration data: 
```
<?xml version="1.0" encoding="UTF-8"?>
<project
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
	xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.xtuml.bp</groupId>
		<artifactId>org.xtuml.bp.releng.parent.tests</artifactId>
		<version>6.2.5</version>
		<relativePath>../../releng/org.xtuml.bp.releng.parent.tests/</relativePath>
	</parent>
	<groupId>org.xtuml.bp</groupId>
	<artifactId>org.xtuml.bp.project.test</artifactId>
	<version>1.0.0</version>
	<packaging>eclipse-test-plugin</packaging>


	<build>
		<plugins>			
			<plugin>
				<groupId>org.eclipse.tycho</groupId>
				<artifactId>tycho-surefire-plugin</artifactId>
				<version>${tycho-version}</version>
				<configuration>
					<includes>
						<include>**/TestSuite.java</include>
					</includes>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
   ```  
- Modify the parent test pom file located here: org.xtuml.bp.releng.parent.tests/pom.xml.  
  - Add a new module entry point at the new project's pom file.  
  - Locate the modules entry and add a new module entry.  
```
<module>../../src/org.xtuml.bp.project.test</module>
```  
- Follow the above sections for adding tests and suites to the new project  

