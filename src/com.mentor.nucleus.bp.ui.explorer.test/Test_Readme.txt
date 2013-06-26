==========================================================================

File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.8 $
Modified:  $Date: 2013/01/10 23:19:42 $

(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.

==========================================================================
This document contains information proprietary and confidential to
Mentor Graphics Corp., and is not for external distribution.
==========================================================================

To execute the test suite in your workspace
-------------------------------------------
- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'Explorer Test'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.ui.explorer.test' and test class 
  'com.mentor.nucleus.bp.ui.explorer.test.ExplorerTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/explorer'
- Change VM Arguments to 
  '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.ui.explorer.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/explorer/.metadata/.log'
- Check 'Clear workspace data before launching'

The test results will be written to a file in the Eclipse top-level folder.

End
---

$Log: Test_Readme.txt,v $
Revision 1.8  2013/01/10 23:19:42  rmulvey
job:dts0100939343
Updated the copyright to 2013

Revision 1.7  2011/05/30 20:35:58  kbrown
job:dts0100742889
Updated copy right.

Revision 1.6  2010/01/05 04:10:34  kbrown
job:dts0100644853
Batch commit of copyright change from 2009 to 2010 for BP CVS projects.

Revision 1.5  2009/01/01 23:19:35  rmulvey
Job:4060
Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.

Revision 1.4.2.1  2008/12/31 15:53:19  rmulvey
Job:4060
This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.

Revision 1.4  2008/09/05 15:29:06  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.3.8.1  2008/08/22 18:25:08  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.3.6.1  2008/08/19 21:47:29  kbrown
Job:1845
Updated test readme in ui.explorer.test with info on new setup config requirements.

Revision 1.3  2008/01/08 20:20:18  rmulvey
Job:3349
Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.

Revision 1.2.8.1  2007/12/21 18:08:07  rmulvey
Job:3339
Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
in the report produced by the utility used to changesthese messages.  This report is here:
Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt

Revision 1.2  2007/09/05 02:09:16  kbrown
Job:2673
Promoting copyright changes for plugins:
mc.xmiexport
pkg
pkg-feature
test
ui.canvas
ui.canvas.test
ui.explorer
ui.explorer.test
ui.properties
ui.properties.test
ui.session

Revision 1.1.498.1  2007/09/01 01:43:22  rmulvey
Job:2673
Updated copyright messages and version numbers in Review-i2673-01.

Revision 1.1  2004/12/28 23:38:03  jmather
Job: 453
Introduced.
