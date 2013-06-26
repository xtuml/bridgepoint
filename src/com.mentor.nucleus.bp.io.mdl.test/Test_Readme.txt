
File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.14 $
Modified:  $Date: 2008/12/01 21:19:05 $

Procedure for executing none-restore tests.
---------------------------------
- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'IO Mdl Test Suite'
- On the Test tab, under 'Run a single test', specify
  project 'com.mentor.nucleus.bp.io.mdl.test' and test class 'IOMdlTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/io_mdl'
- Change VM Arguments to
  '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.io.mdl.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/io_mdl/.metadata/.log'
- Check 'Clear workspace data before launching'
- Until Bugzilla issues 1156 and 1158 are addressed, select the 'Tracing' tab:
  On the checkbox on the left, select bp.core and ui.canvas as plugins that
  support tracing.
  For each plugin, on the checkbox on the right, select the 'debug' and
  'debug/consistency' checkboxes.
- On the Plug-ins and Fragments page, check the bottom radio button.
   (Choose plug-ins and fragments to launch from the list)
   _- Disable (uncheck) the following external plugins
   		com.mentor.nucleus.builder.welcome
   		com.mentor.nucleus.debug*
   		com.mentor.nucleus.target.windows
   		com.mentor.nucleus.ts*

- Repeat the above steps only for this setup use PkgCMTestSuite as the test
  class
- Repeat the above steps only for this setup use IOMdlTestSuite2 as the test
  class

The test results will be written to a file in the Eclipse top-level folder.

Procedure for executing restore tests.
--------------------------------------
Restore tests can either be run manualy or automatically.

The test results will be written to a file in the Eclipse top-level folder.

1. Manually
1.1. Junit launch configuration
- Select Debug... from run menu
- Create one JUnit launch configuration with name 'PkgCM Restore Test'
  and fill following values.
- Project    : com.mentor.nucleus.bp.io.mdl.test
- Test Class : com.mentor.nucleus.bp.io.mdl.test.pkgcm.restore.PkgCMRestoreTestSuite
- Location   : bp_tests/pkgCM_mdl
- VM Argument: -DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.io.mdl.test/
               -DLOGFILE_PATH=${eclipse_home}/bp_tests/pkgCM_mdl/.metadata/.log
               -DProperty_File=bp_tests/pkgCM_mdl/.metadata/properties.txt

Repeate step 1.2 and 1.3 for each testcase under manual test.

1.2 Setup workspace.
- add following values to junit launch configuration named 'PkgCM Restore Test'.
- Clear Workspace: check (Only for first test in suite)
- VM Argument: -DTestCaseName=NameOfTestCaseUnderTest
	           -DSETUP_WORKSPACE=True
- debug or run 'PkgCM Restore Test' configuration.

1.3 Perform test
- add following values to junit launch configuration named 'PkgCM Restore Test'.
- Clear Workspace: Uncheck
- VM Argument: -DTestCaseName=NameOfTestCaseUnderTest -DSETUP_WORKSPACE=False
- debug or run 'PkgCM Restore Test'.

2. Automated.
To perform automatic rstore testing perform following steps.

2.1. Base Junit launch configuration.
Create base Junit launch configuration as given at 1.1. Or if you are using
existing configuration then make sure no VM arguments are given as per section
1.2 or 1.3 should not be there. These values will be set by restore test
launcher.

2.2. RestoreTest Launcher configuration

- Create a new configuration of type 'RestoreTest Launcher' configuration
- Name it as 'Restore test'
- Fill following values to 'restore test' launch configuration.
- JUnit Plug-in Test configuration Name: PkgCM Restore Test
- Click environment tab and add press New Button. In new environment variable
  dialog fill following values
- Name : testcase0
- Value: com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMCreateTest

Note: Other tests can be added by adding new variables from Environment tab with
name of the testcase with postfix 0,1,2...
For example another testcase class can be added to suite .
  name  = testcase1
  value = com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMRenameTest

End
---
