
File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.18 $
Modified:  $Date: 2008/10/27 01:49:50 $

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
- Place the file core.bat (content should be pasted from below) in the eclipse-test
- Install unzip (www.info-zip.org) and put it in the path

To execute the test
-------------------

- cd eclipse-test
- core.bat
- Results are in eclipse-test\CoreTestSuite.xml


core.bat contents
-------------------

rmdir /S /Q eclipse\core_folder

cd  eclipse\plugins

rem this is the project where the test plugin was built
set WORKSPACE_PATH=C:\eclipse\workspace\com.mentor.nucleus.bp.core.test\

unzip -qq -o %WORKSPACE_PATH%com.mentor.nucleus.bp.core.test_*.zip 

cd ..\..
java -DWORKSPACE_PATH=%WORKSPACE_PATH% -DLOGFILE_PATH=core_folder/.metadata/.log -cp eclipse\startup.jar org.eclipse.core.launcher.Main -noupdate -application org.eclipse.test.uitestapplication -dev bin -data eclipse\core_folder formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,CoreTestSuite.xml -testPluginName com.mentor.nucleus.bp.core.test -className CoreTestSuite -dev bin -os win32 -ws win32 -arch x86 -nl en_US

rem end of file

To execute the test in your workspace
-------------------------------------

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'Core Test'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.core.test' and test class 'CoreTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/core'
- Change VM Arguments to 
  '-Xmx512m -XX:MaxPermSize=80m -DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.core.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/core/.metadata/.log'
- Check 'Clear workspace data before launching'
- On the Plug-ins and Fragments page, check the top radio button.
  (Launch with all workspace and enabled external plug-ins)
  If you have the Tiger plugins installed in you workspace, check 
  the bottom radio button, and deselect the Tiger plugins from the external 
  plugin list.
- Until Bugzilla issues 1156 and 1158 are addressed, select the 'Tracing' tab:
  On the checkbox on the left, select bp.core and ui.canvas as plugins that
  support tracing.
  For each plugin, on the checkbox on the right, select the 'debug' and
  'debug/consistency' checkboxes.

- Repeat the above replacing CoreTestSuite with CoreTestSuite2.
- Repeat the above replacing CoreTestSuite with SystemLevelTestSuite.
- Repeat the above replacing CoreTestSuite with CopyPasteTestSuite.
- Repeat the above replacing CoreTestSuite with RTOMoveTestSuite.

- Setup existing projects nature test

  Note: This test is setup separate of the others because you must first run
  the test which creates the workspace to tests on.
    
- Repeat the steps above (except for the last step, tracing is not needed) for
  the following configurations replacing the following information:

  Class name: TigerNatureWorkspaceSetupTest
  
  Class name: TigerNatureExistingProjectsTest
  Do not check 'Clear workspace data before launching'

- Run existing projects test

  First run the TigerNatureWorkspaceSetupTest.  After this test complets run
  the TigerNatureExistingProjectsTest
  
- Repeat all of the setup steps above to create the consistency unit test:

   Class Name: ConsistencyTest
   
   Tracing: Until Bugzilla issue 1156 is addressed, enable tracing for
   ui.canvas.
   
The test results will be written to a file in the Eclipse top-level folder.

Creation of Expected Results
----------------------------

This should only be done when the plugin behavior has been altered such that
the expected results change.

To do this, perform the steps in the previous section if you have not 
already done so.  However, specify 'CoreTestResultSuite' as the test class.

When run, this overwrites the files in the subdirectories of expected_results.
If necessary, the files that you wish to be updated should be made read write 
with Team | Edit before recreation. Finally, check these files into CVS, 
providing a comment explaining what changed.

Creation of Copy Paste Test Expected Results
--------------------------------------------
To generate test results for the copy paste tests open the launch configuration
for the CopyPasteTestSuite.  In the launch configuration add the following
environment variable:

generate_results = true

After adding the above variable simply run the CopyPasteTestSuite again.

End
---

$Log: Test_Readme.txt,v $
Revision 1.18  2008/10/27 01:49:50  rmulvey
Job:3982
Bump the version from 2.1.0 to 2.1.1

Revision 1.17  2008/09/05 15:29:05  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.16.20.1  2008/08/22 18:25:24  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.16.18.1  2008/08/19 21:27:41  kbrown
Job:1845
Updated test readme in core.test with info on new setup config requirements.

Revision 1.16  2008/06/10 20:08:16  rmulvey
Job:3545
Batch promotion of changes from Review-i3545.

Revision 1.15.50.1  2008/06/06 20:06:24  tlondon
Job: 3545

Add step for rto move tests.

Revision 1.15  2007/11/21 23:44:14  campbell
Job: 2876
Document use of copy paste tests.

Revision 1.14.24.1  2007/11/12 17:50:19  tlondon
Job: 2876

Add entry for running copy/paste test suite, and result suite.

Revision 1.14.18.1  2007/11/05 16:32:04  tlondon
Job: 2876

Add read me info

Revision 1.14  2007/07/25 23:41:20  rmulvey
Job:2995
Promoting change from Review-i2995.
Add note that a duplicate lauch spec should be created for new suite.

Revision 1.13.40.1  2007/07/24 21:47:37  tlondon
Job: 2995

Add note that a duplicate lauch spec should be created for new suite.

Revision 1.13  2007/01/10 03:39:20  rmulvey
Job:2661
Promoting issue 2661.
Add note that setup should repeat against new suite CoreTestSuite2.

Revision 1.12.26.1  2007/01/09 18:19:01  tlondon
Job: 2661

Add note that setup should repeat against new suite CoreTestSuite2.

Revision 1.12  2006/08/31 20:21:52  tlondon
Job: 845

Promoting changes from Review-i845 and some additional fixes from Review-i845-02.

These changes include PLCM and CC support.  There are still a few issues that will be addressed shortly and will be treated as new issues against HEAD.

Revision 1.11.212.1  2006/08/25 21:38:08  rmulvey
Job:845
Promoting changes from Review-i845-Lahore afer verifying fixes for unit tests , 1598, and 2444.

Revision 1.11.218.1  2006/08/25 07:27:19  satta
Job:845
Performance arg added in the VM argument list.

Revision 1.11  2005/08/18 23:13:13  tlondon
Job: 1018

All changes look good, promoting to HEAD.

Revision 1.10  2005/03/10 01:52:17  campbell
Job: 561
Updated readme to include procedure for existing projects test

Revision 1.8.10.2  2005/03/09 21:43:44  campbell
Job: 561

Updated readme to include procedure for existing projects test

Revision 1.8.10.1  2005/03/04 20:33:19  campbell
Job: 561
Correct logfile path.

Revision 1.8  2005/02/25 23:49:15  campbell
Job: 561
Add instructions for existing projects test

Revision 1.7.98.1  2005/02/17 23:27:57  greg
Job: 561
Add instructions for existing projects test

Revision 1.7.66.1  2005/02/14 20:21:17  tlondon
Job: 561

Updated test readme to include instructions for existing project tests

Revision 1.7  2004/12/21 17:40:20  greg
Job: 434
Modified instructions for running tests within the IDE to comply with Eclipse 3.0 conventions.

Revision 1.6.8.1  2004/12/14 15:23:31  jmather
Job: 434
Merge for final review.

Revision 1.6  2004/12/08 20:34:56  greg
Job: 384
Fix xml file name for test result creator

Revision 1.5.54.1  2004/12/07 16:13:10  campbell
Job: 384
Fix xml file name for test result creator

Revision 1.5.26.1  2004/11/18 03:35:21  greg
Job: 384
Fix xml file name for test result creator

Revision 1.5.20.1  2004/11/13 02:26:28  greg
Job: 384
Fix xml file name for test result creator

Revision 1.5  2004/10/22 22:24:28  tlondon
Job: 379

Updating test readme to include result creation for new delete tests

Revision 1.4  2004/09/10 18:00:58  greg
Job: 31
Re create after copy to ui.properties.test

Revision 1.2  2004/08/20 22:18:45  greg
Job: 165
Added LOGFILE_PATH definition

Revision 1.1  2004/07/30 17:47:36  greg
Job: 332
Initial introduction

