==========================================================================

File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.15 $
Modified:  $Date: 2013/01/10 23:22:07 $

(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.

==========================================================================
This document contains information proprietary and confidential to
Mentor Graphics Corp., and is not for external distribution.
==========================================================================

To execute the test in your workspace
-------------------------------------
- Ensure that the com.mentor.nucleus plugin (rev 1.1.1 or later) is present
- Ensure that the com.mentor.nucleus.internal.test plugin is present
- A version of the MC-3020 model compiler revision 4.3.0 or later must be
  installed.

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'MC3020 Test No Code Builder'
- On the Test tab, under 'Run a single test', specify project 
  'com.mentor.nucleus.bp.mc.mc3020.test' and test class 'MC3020TestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/mc3020_nocb'
- Check 'Clear workspace data before launching'
- On the Plugins tab, ensure the com.mentor.nucleus.internal.test is not checked

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'MC3020 Test With Code Builder'
- On the Test tab, under 'Run a single test', specify project 
  'com.mentor.nucleus.bp.mc.mc3020.test' and test class 'MC3020CBTestSuite'
- On the Main tab, change workspace data to '${eclipse_home}/bp_tests/mc3020_cb'
- Check 'Clear workspace data before launching'
- On the Plugins tab, select Choose plug-ins and fragments to launch from list
    - Uncheck all workspace plugins except com.mentor.nucleus.bp.mc.mc3020.test,
      com.mentor.nucleus.bp.test, and com.mentor.nucleus.internal.test
    - Verify all External plug-ins are checked except com.mentor.nuclues.bp.test

The test results will be written to a file in the Eclipse top-level folder.

$Log: Test_Readme.txt,v $
Revision 1.15  2013/01/10 23:22:07  rmulvey
job:dts0100939343
Updated the copyright to 2013

Revision 1.14  2011/05/30 20:30:42  kbrown
job:dts0100742889
Updated copy right.

Revision 1.13  2010/01/05 03:59:12  kbrown
job:dts0100644853
Batch commit of copyright change from 2009 to 2010 for BP CVS projects.

Revision 1.12  2009/01/01 23:28:08  rmulvey
Job:4060
Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.

Revision 1.11.4.1  2008/12/31 16:13:33  rmulvey
Job:4060
This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.

Revision 1.11  2008/09/05 15:29:17  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.10.6.1  2008/08/22 18:24:56  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.10.4.1  2008/08/19 21:42:12  kbrown
Job:1845
Updated test readme in mc.mc3020.test with info on new setup config requirements.

Revision 1.10  2008/01/08 20:20:33  rmulvey
Job:3349
Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.

Revision 1.9.2.1  2007/12/21 18:10:28  rmulvey
Job:3339
Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
in the report produced by the utility used to changesthese messages.  This report is here:
Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt

Revision 1.9  2007/09/05 01:58:38  kbrown
Job:2673
Promoting copyright changes for plugins:
internal.metrics
internal.tools
io.core
io.mdl
io.mdl.test
io.sql
io.sql.test
mc.mc2020
mc.mc2020.pkg
mc.mc2020.pkg-feature
mc.mc2020.test
mc.mc3020
mc.mc3020.pkg
mc.mc3020.pkg-feature
mc.mc3020.test

Revision 1.8.20.1  2007/09/01 01:39:12  rmulvey
Job:2673
Updated copyright messages and version numbers in Review-i2673-01.

Revision 1.8  2006/10/06 03:32:10  kbrown
Job:2438
Promoting documentation updates to HEAD.

Revision 1.7.158.1  2006/10/04 20:31:52  rmulvey
Job:2438
ROX_PT_HOME is no longer required (same change for 3020 as 2020).

Revision 1.7  2005/11/02 22:04:09  cstarret
Job:1246
committing merged files to HEAD

Revision 1.6.60.3  2005/10/31 14:15:49  kbrown
Job:1246
Improving the instructions on how to run a test.

Revision 1.6.60.2  2005/10/26 16:48:02  kbrown
Job:1246
Fixed bad instructions in Test_Readme files.

Revision 1.6.60.1  2005/10/25 16:01:03  kbrown
Job:1246
Updated Test documentation.

Revision 1.6  2005/05/10 00:08:45  campbell
Job: 904
Added more MC-3020 setup directions.

Revision 1.5  2005/05/10 00:08:10  campbell
Job: 904
Added more MC-3020 setup directions.

Revision 1.4  2005/05/09 19:30:36  campbell
Job: 904
Added MC-3020 setup directions.

Revision 1.3  2005/05/02 23:39:48  greg
Job: 654
Add pdksh note

Revision 1.2  2005/05/02 22:19:48  greg
Job: 654
Rewrite for this plugin

