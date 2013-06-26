==========================================================================

File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.11 $
Modified:  $Date: 2013/01/10 23:05:15 $

(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.

==========================================================================
This document contains information proprietary and confidential to
Mentor Graphics Corp., and is not for external distribution.
==========================================================================
To run test:

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'WelcomeTest'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.welcome.test' and test class 'WelcomeTestSuite.java'
- Change workspace data to '${eclipse_home}/bp_tests/welcome'
- Check 'Clear workspace data before launching'
- Set VM args to '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.welcome.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/welcome/.metadata/.log'
- On the Plug-ins tab, make sure the com.mentor.nucleus.internal.test plugin 
	is enabled in order to test issue 1833 - the name change of the EDGE Builder.
	
	
$Log: Test_Readme.txt,v $
Revision 1.11  2013/01/10 23:05:15  rmulvey
job:dts0100939343
Updated the copyright to 2013

Revision 1.10  2011/05/30 20:30:22  kbrown
job:dts0100742889
Updated copy right.

Revision 1.9  2010/01/05 03:55:52  kbrown
job:dts0100644853
Batch commit of copyright change from 2009 to 2010 for BP CVS projects.

Revision 1.8  2009/01/01 23:20:05  rmulvey
Job:4060
Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.

Revision 1.7.2.1  2008/12/31 15:54:52  rmulvey
Job:4060
This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.

Revision 1.7  2008/09/05 15:29:13  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.6.12.1  2008/08/22 18:25:07  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.6.10.1  2008/08/19 21:49:54  kbrown
Job:1845
Updated test readme in welcome.test with info on new setup config requirements.

Revision 1.6  2008/01/08 20:16:42  rmulvey
Job:3349
Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.

Revision 1.5.2.1  2007/12/21 18:06:37  rmulvey
Job:3339
Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
in the report produced by the utility used to changesthese messages.  This report is here:
Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt

Revision 1.5  2007/09/05 02:22:03  kbrown
Job:2673
Promoting copyright changes for plugins:
ui.text
ui.text.test
ui.tree
verifer.pkg
verifier.pkg-feature
welcome
welcome.test
help.bp.mc
help.bp.mc2020
internal.test

Revision 1.4.70.1  2007/09/01 01:43:40  rmulvey
Job:2673
Updated copyright messages and version numbers in Review-i2673-01.

Revision 1.4  2006/04/17 21:54:44  tlondon
Job: 1870

Change readme file to test for change of EDGE Builder name.

Revision 1.3.42.1  2006/04/13 23:19:42  pablo
Job: 1833
Branch: Review-1870-Names-01
Purpose: Change readme file to test for change of EDGE Builder name.

Revision 1.3.38.1  2006/04/13 19:14:28  pablo
Job: 1833
Update welcome.test Test_Readme.txt to call out a plugin that must be enabled in order to test this issue.

Revision 1.3  2006/01/11 17:59:49  jmather
Job: 1413
Added a note about making sure the com.mentor.nucleus.internal.test plugin is disabled for these tests.

Revision 1.2  2005/10/25 22:06:16  jmather
Job: 626
Fixed wording to correctly reflect test project

Revision 1.1.16.1  2005/10/21 22:51:27  tlondon
Job: 626

Fixed welcome.test readme so that it was appropriate to this test

Revision 1.1.2.1  2005/09/13 18:47:57  tlondon
Job: 1099

Reworded readme to be specific for this plugin

Revision 1.1  2005/09/12 18:34:37  tlondon
Job: 1099

New welcome.test plugin
