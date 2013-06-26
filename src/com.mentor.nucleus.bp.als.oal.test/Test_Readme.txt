
File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.4 $
Modified:  $Date: 2008/09/05 15:29:21 $

To setup the Junit plugin test for the OALTestSuite test,

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'Parse All Test'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.als.oal.test' and test class 'OALTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/als.oal'
- Change VM Arguments to 
  '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.als.oal.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/als.oal/.metadata/.log'
- Check 'Clear workspace data before launching'

The test results will be written to a file in the Eclipse top-level folder.


$Log: Test_Readme.txt,v $
Revision 1.4  2008/09/05 15:29:21  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.3.424.1  2008/08/22 18:24:35  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.3.422.1  2008/08/19 21:25:58  kbrown
Job:1845
Updated test readme in als.oal.test with info on new setup config requirements.

Revision 1.3  2005/03/29 23:07:13  greg
Job: 673
Updated for all in one test suite

Revision 1.2.50.1  2005/03/29 14:25:36  babar
Job:673
Updated for all in one test suite

Revision 1.2  2005/03/08 01:06:42  campbell
Job: 561
Added note about needing to add VM argument for SWT DLL.
Added instructions for running Parse all test.

Revision 1.1.204.2  2005/03/07 18:11:52  greg
Job: 561
Add instructions for running Parse all test

Revision 1.1.204.1  2005/03/03 01:43:23  jmather
Job: 561
Added note about needing to add VM argument for SWT DLL.

Revision 1.1  2004/03/17 20:51:59  greg
Job: 130
Initial introduction

Revision 1.1  2003/07/16 19:41:44  greg
Job: 19
Introduced

