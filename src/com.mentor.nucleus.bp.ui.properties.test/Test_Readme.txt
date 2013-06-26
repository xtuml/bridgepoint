
File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.4 $
Modified:  $Date: 2008/10/27 01:49:52 $

To build the test plugin
------------------------

- Rebuild the project  (PT_HOME and PT_HOME_DRIVE environment
    variables must be defined in order to find bak2sql, which
    must be installed)
- To create build.xml, select plugin.xml, and invoke context
    menu 'Create Ant Build File'
- Select build.xml, invoke context menu 'Run Ant...'
- Select the targets in this order:  clean, build.jars, zip.plugin
- Run Ant

To setup the test environment
-----------------------------

- Create a directory named 'eclipse-test' as a sibling to your main eclipse directory
- Unzip the eclipse SDK into eclipse-test
- Unzip org.eclipse.test, and org.eclipse.ant.optional.junit plugins
   into the eclipse-test/eclipse/plugins directory.  These plugins are
   found in the phoenix:/software/software_archive/Eclipse/plug-ins/org.eclipse.test
- Copy the org.antlr_2.7.2 plugin into eclipse-test/eclipse/plugins

- Either get the Tiger plugin distribution from http://phoenix.projtech.com/tiger_releases
   or build it using feature-pkg/build.xml
- Unzip the BridgePoint distribution into the eclipse-test/eclipse directory
- Place the file properties.bat (content should be pasted from below) in the eclipse-test
- Install unzip (www.info-zip.org) and put it in the path

To execute the test
-------------------

- cd eclipse-test
- properties.bat
- Results are in eclipse-test\PropertiesTestSuite.xml


properties.bat contents
-------------------

rmdir /S /Q eclipse\properties_folder

cd  eclipse\plugins

rem this is the project where the test plugin was built
set WORKSPACE_PATH=C:\eclipse\workspace\com.mentor.nucleus.bp.ui.properties.test\

unzip -qq -o %WORKSPACE_PATH%com.mentor.nucleus.bp.ui.properties.test_*.zip 

cd ..\..
java -DWORKSPACE_PATH=%WORKSPACE_PATH% -DLOGFILE_PATH=properties_folder/.metadata/.log -cp eclipse\startup.jar org.eclipse.core.launcher.Main -noupdate -application org.eclipse.test.uitestapplication -dev bin -data eclipse\properties_folder formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,PropertiesTestSuite.xml -testPluginName com.mentor.nucleus.bp.ui.properties.test -className PropertiesTestSuite -dev bin -os win32 -ws win32 -arch x86 -nl en_US

rem end of file

To execute the test in your workspace
-------------------------------------

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'Properties Test'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.ui.properties.test' and test class 
  'PropertiesTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/properties'
- Change VM Arguments to 
  '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.ui.properties.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/properties/.metadata/.log'
- Check 'Clear workspace data before launching'
- On the Plug-ins and Fragments page, check the top radio button.
   (Launch with all workspace and enabled external plug-ins)
  If you have the Tiger plugins installed in you workspace, check the bottom radio button, and
  deselect the Tiger plugins from the external plugin list.

The test results will be written to a file in the Eclipse top-level folder.


End
---

$Log: Test_Readme.txt,v $
Revision 1.4  2008/10/27 01:49:52  rmulvey
Job:3982
Bump the version from 2.1.0 to 2.1.1

Revision 1.3  2008/09/05 15:28:38  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.2.570.1  2008/08/22 18:25:02  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.2.568.1  2008/08/19 21:30:01  kbrown
Job:1845
Updated test readme in ui.properties.test with info on new setup config requirements.

Revision 1.2  2004/12/21 18:37:54  greg
Job: 434
Modified instructions for running tests within the IDE to comply with Eclipse 3.0 conventions.

Revision 1.1.60.1  2004/12/14 15:23:30  jmather
Job: 434
Merge for final review.

Revision 1.1.28.1  2004/12/06 21:23:23  jmather
Job: 434
Modified instructions for running tests within the IDE to comply with Eclipse 3.0 conventions.

Revision 1.1  2004/09/10 23:27:07  greg
Job: 31
Initial introduction

Revision 1.3  2004/09/10 17:36:47  greg
Job: 31
Ready for review

