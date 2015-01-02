---

Copyright 2014 One Fact Inc.  All Rights Reserved.

---

# Import Mentor CQ data into Redmine
### xtUML Project Analysis Note


1. Abstract
-----------
Issues created during the time at Mentor Graphics need to be imported into
the One Fact Redmine issue tracker.

2. Document References
----------------------
[1] Issues 41, https://support.onefact.net/redmine/issues/41

[2] CQ and SR Data, notes when the CSV files were created, https://drive.google.com/open?id=19T0vfYDAMa8ArpiiN7iujqkBy9X2yOynxIKF0_qSn0E&authuser=0

[3] DefectDBMainDump.csv is the CQ database, https://drive.google.com/open?id=0Bw01o4iXr5Fuc1h4S2hPeEF4VWM&authuser=0

[4] DefectDBNotes.csv notes related to CQ issues, https://drive.google.com/open?id=0Bw01o4iXr5FuQTFjZWctWE1yNlU&authuser=0

[5] DefectDBRelatedTo.csv is the CQ to CQ mapping, https://drive.google.com/open?id=0Bw01o4iXr5FuWmZyeUpFbzQtVFE&authuser=0

[6] SRDBMainDump.csv is the SR to CQ mapping, https://drive.google.com/open?id=1n_2oEhaf7QrIML9gxeCfVPTObq2QuJVyJapvwrWVGvQ&authuser=0

3. Background
-------------
The One Fact team is using Redmine for issue tracking.  CSV dumps of issues
created during the time at Mentor Graphics are available and need to be
imported into Redmine.  As can be seen in section 2 the data to import is
contained in more than one CSV file.

4. Requirements
---------------
* Analyze the data in the CSV dumps to get a sense of the data and how the 
different CSV dumps relate.
* Investigate existing Redmine import plug-ins to see if one will work.
* Map data fields from the CSV dumps to the appropriate Redmine fields and 
create new custom fields in Redmine where necessary.
* May need to create dummy user accounts for users not yet in Redmine for a
successful import.
* Import to the staging server.
* Import to the live server.

5. Analysis
-----------
This section is only required if there is no preceding analysis note. If present
it sets out a brief analysis of the problem to be resolved by this design note.

* Item 1
* Item 2
* Item 3

6. Work Required
----------------
In this section, break out the consequential work (as a numbered list) needed
to meet the requirements specified in the Requirements section.

7. Acceptance Test
------------------
In this section, list the tests that need to be performed in order to
verify that all the requirements are satisfied.

End
---

