
File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.15 $
Modified:  $Date: 2008/10/27 01:49:43 $

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
- Place the file canvas.bat (content should be pasted from below) in the eclipse-test
- Install unzip (www.info-zip.org) and put it in the path

To execute the test
-------------------

- cd eclipse-test
- canvas.bat
- Results are in eclipse-test\CanvasTestSuite.xml


canvas.bat contents
-------------------

rmdir /S /Q eclipse\canvas_folder

cd  eclipse\plugins

rem this is the project where the test plugin was built
set WORKSPACE_PATH=C:\eclipse\workspace\com.mentor.nucleus.bp.ui.canvas.test\

unzip -qq -o %WORKSPACE_PATH%com.mentor.nucleus.bp.ui.canvas.test_*.zip 

cd ..\..
java -DWORKSPACE_PATH=%WORKSPACE_PATH% -DLOGFILE_PATH=canvas_folder/.metadata/.log -cp eclipse\startup.jar org.eclipse.core.launcher.Main -noupdate -application org.eclipse.test.uitestapplication -dev bin -data eclipse\canvas_folder formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,CanvasTestSuite.xml -testPluginName com.mentor.nucleus.bp.ui.canvas.test -className CanvasTestSuite -dev bin -os win32 -ws win32 -arch x86 -nl en_US

rem end of file

To execute the test in your workspace
-------------------------------------

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'Canvas Test'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.ui.canvas.test' and test class 'CanvasTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/canvas'
- Change VM Arguments to 
  '-Xmx512m -XX:MaxPermSize=80m -DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.ui.canvas.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/canvas/.metadata/.log'
- Add '-noupdate ' to the front of the Program Arguments  
- Check 'Clear workspace data before launching'
- On the Plug-ins and Fragments page, check the top radio button.
   (Launch with all workspace and enabled external plug-ins)
- Select the 'Tracing' tab:
  On the checkbox on the left, select bp.core and ui.canvas as plugins that
  support tracing.
  For each plugin, on the checkbox on the right, select the 'debug' and
  'debug/consistency' checkboxes.
The test results will be written to a file in the Eclipse top-level folder.

Perform the same steps above, replacing the test suite for
CanvasCCPTestSuite.

Creation of Expected Results
----------------------------

This should only be done when the plugin behavior has been altered such that
the expected results change.

To do this, perform the steps in the previous section if you have not 
already done so.  However, specify 'CanvasTestResultSuite' as the test class.

When run, this overwrites the files in the subdirectories of expected_results.
If necessary, the files that you wish to be updated should be made read write 
with Team | Edit before recreation. Finally, check these files into CVS, 
providing a comment explaining what changed.


End
---

$Log: Test_Readme.txt,v $
Revision 1.15  2008/10/27 01:49:43  rmulvey
Job:3982
Bump the version from 2.1.0 to 2.1.1

Revision 1.14  2008/09/05 15:28:43  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.13.88.1  2008/08/22 18:25:18  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.13.86.1  2008/08/19 21:29:14  kbrown
Job:1845
Updated test readme in ui.canvas.test with info on new setup config requirements.

Revision 1.13  2007/03/23 06:21:08  rmulvey
Job:2803
Promoting from Review-i2803.
Added instructions for CCP test suite.

Revision 1.12.38.1  2007/03/21 00:29:42  tlondon
Job: 2803

Add note that a new launch should be created for the new test suite.

Revision 1.12  2006/08/31 20:37:17  tlondon
Job: 845

Promoting changes from Review-i845 and some additional fixes from Review-i845-02.

These changes include PLCM and CC support.  There are still a few issues that will be addressed shortly and will be treated as new issues against HEAD.

Revision 1.11.292.3  2006/08/25 21:38:13  rmulvey
Job:845
Promoting changes from Review-i845-Lahore afer verifying fixes for unit tests , 1598, and 2444.

Revision 1.11.292.1.2.1  2006/08/24 11:47:59  satta
Job:845
Performance VM arg and Enable Tracing added.

Revision 1.11.292.1  2006/07/28 14:32:03  araza
Job:845
Merged with Head, committing for sharing purpose.

Revision 1.11.268.1  2006/07/13 09:02:39  babar
Job:845
Checking in merged workspace so that other members can help in unit tests.

Revision 1.11.172.1  2006/03/13 17:11:10  babar
Job: 845
Merged with HEAD on PLCM_1_0

Revision 1.11.130.1  2005/12/06 19:07:20  tlondon
Job: 845

Creating new branch 845-HEAD

Revision 1.11.120.1  2005/11/30 23:08:31  greg
Job: 845
Merge -UT branch changes into -alt1 branch

Revision 1.11.122.1  2005/11/30 19:05:37  greg
Job: 845
Remove instructions to create actual_results folder, as test creates it automatically as needed

Revision 1.11  2005/04/05 05:02:12  greg
Job: 293
Changed refs to old .mdl ext and model location

Revision 1.10.168.1  2005/03/30 23:35:00  tlondon
Job: 293

Changed refs to old model ext and location. Also change any refs to
com.mentor.nucleus

Revision 1.10.164.1  2005/03/29 22:14:36  tlondon
Job: 293

Changing refs to old model folder and extension

Revision 1.10  2005/01/05 17:38:17  jmather
Job: 620
Correct project path.

Revision 1.9.16.2  2005/01/05 01:15:06  campbell
Job: 620
Correct project path.

Revision 1.9.16.1  2004/12/30 19:29:48  campbell
Job: 620
Instruct user to create actual_results folder.

Revision 1.9  2004/12/21 18:30:32  greg
Job: 434
Modified instructions for running tests within the IDE to comply with Eclipse 3.0 conventions.

Revision 1.8.76.1  2004/12/14 15:23:04  jmather
Job: 434
Merge for final review.

Revision 1.8.42.1  2004/12/06 21:23:21  jmather
Job: 434
Modified instructions for running tests within the IDE to comply with Eclipse 3.0 conventions.

Revision 1.8  2004/09/10 22:31:14  tlondon
Job: 387

Updated test readme file

Revision 1.7  2004/08/20 22:19:20  greg
Job: 165
Fix typo

Revision 1.6  2004/08/20 22:17:39  greg
Job: 165
Added LOGFILE_PATH definition

Revision 1.5  2004/07/13 23:01:02  greg
Job: 246
Add VM arguments setting

Revision 1.4  2004/07/13 16:51:11  greg
Job: 246
More updates from review

Revision 1.3  2004/07/08 18:48:51  greg
Job: 246
Update with plugin changes

Revision 1.2  2004/03/15 17:36:01  campbell
Job: 100
Added procedure for creating test results.

Revision 1.1  2004/03/10 21:28:51  campbell
Job: 100
Introduced.
