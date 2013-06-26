
File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.5 $
Modified:  $Date: 2008/09/05 15:29:02 $


- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'IO Sql Test'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.io.sql.test' and test class 'IOSqlTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/io_sql'
- Change VM Arguments to 
  '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.io.sql.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/io_sql/.metadata/.log'
- Check 'Clear workspace data before launching'

The test results will be written to a file in the Eclipse top-level folder.

End
---

$Log: Test_Readme.txt,v $
Revision 1.5  2008/09/05 15:29:02  rmulvey
Job:1845 3672 3657
Promoted from branch R2_0_4-SKB-RWM-t-i1845-i3672-i3657

Revision 1.4.68.1  2008/08/22 18:25:14  rmulvey
Job:1845 3672 3657
Introduced working branch R2_0_4-SKB-RWM-i1845-i3672-i3657.  This is based on the current work in branch R2_0_4-SKB-i1845-01 (which is complete but blocked by bug 3672 and bug 3657).

Revision 1.4.66.1  2008/08/19 21:34:13  kbrown
Job:1845
Updated test readme in io.sql.test with info on new setup config requirements.

Revision 1.4  2006/08/31 20:16:06  tlondon
Job: 845

Promoting changes from Review-i845 and some additional fixes from Review-i845-02.

These changes include PLCM and CC support.  There are still a few issues that will be addressed shortly and will be treated as new issues against HEAD.

Revision 1.3.428.1  2006/07/28 14:32:55  araza
Job:845
Merged with Head, committing for sharing purpose.

Revision 1.3.400.1  2006/07/13 08:58:29  babar
Job:845
Checking in merged workspace so that other members can help in unit tests.

Revision 1.3  2004/12/21 18:00:04  greg
Job: 434
Modified instructions for running tests within the IDE to comply with Eclipse 3.0 conventions.

Revision 1.2.8.1  2004/12/14 15:23:14  jmather
Job: 434
Merge for final review.

Revision 1.2  2004/12/08 21:04:35  greg
Job: 384
Change to run in Eclipse workspace

Revision 1.1.62.1  2004/12/08 13:10:41  campbell
Job: 384
Changed to run in Eclipse workspace.

Revision 1.1.36.1  2004/11/18 03:09:20  greg
Job: 384
Change to run in Eclipse workspace

Revision 1.1.32.1  2004/11/17 05:32:11  greg
Job: 384
Change to run as plugin in Eclipse workspace

Revision 1.1  2004/03/25 22:23:14  greg
Job: 173
Initial introduction

