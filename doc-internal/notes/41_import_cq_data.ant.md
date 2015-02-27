---

Copyright 2014 One Fact Inc.  All Rights Reserved.

---

# Import Mentor CQ data into Redmine
### xtUML Project Analysis Note

1. Abstract
-----------
ClearQuest (CQ) issues created during the time at Mentor Graphics need to be
imported into the One Fact Redmine issue tracker.


2. Document References
----------------------
[2.1] [1F Redmine issue 41, Import ClearQuest data][ref1]  
[2.2] [1F Redmine issue 593, Import ClearQuest data for the SMA QA BridgePoint product.][ref2]  
[2.3] [1F Redmine issue 594, Import SR data][ref3]  
[2.4] [DefectDBMainDump.csv][ref4]  
[2.5] [DefectDBNotes.csv][ref5]  
[2.6] [DefectDBRelatedTo.csv][ref6]

[ref1]: https://support.onefact.net/redmine/issues/41
[ref2]: https://support.onefact.net/redmine/issues/593
[ref3]: https://support.onefact.net/redmine/issues/594
[ref4]: https://drive.google.com/open?id=0Bw01o4iXr5Fuc1h4S2hPeEF4VWM&authuser=0
[ref5]: https://drive.google.com/open?id=0Bw01o4iXr5FuQTFjZWctWE1yNlU&authuser=0
[ref6]: https://drive.google.com/open?id=0Bw01o4iXr5FuWmZyeUpFbzQtVFE&authuser=0


3. Background
-------------
The One Fact team is using Redmine for issue tracking.  There were two types
of issues created at Mentor Graphics, CQ and SR.  This note only covers the
CQ data.

There are three CSV files that contain the CQ data:
  * [DefectDBMainDump.csv [2.4]][ref4] is the CQ data export.  
  * [DefectDBNotes.csv [2.5]][ref5] contains all follow-up data associated with
     the CQ records found in [[2.4]][ref4].  
  * [DefectDBRelatedTo.csv [2.6]][ref6] links CQ records that are related.  


4. Requirements
---------------
4.1  Data Integrity

  4.1.1  Current Redmine data shall not be compromised in any way by the import.  

  4.1.2  No data from the CQ export shall be lost in the import.

  4.1.3  CQ notes from [DefectDBNotes.csv [2.5]][ref5] shall be imported into
    the proper newly created Redmine issues.

  4.1.4  "Related To" mappings in [DefectDBRelatedTo.csv [2.6]][ref6] shall be
     maintained.

4.2  Only the BridgePoint product items shall be imported into Redmine.

  4.2.1  A separate issue shall be raised to handle the CQ product named
  SMA QA BridgePoint.  [1F Redmine issue 593 [2.2]][ref2]
    
  4.2.2  A separate issue shall be raised to handle import of SR data.
  [1F Redmine issue 594 [2.3]][ref3]

4.3  CQ identifiers will be imported, but they will not be the primary key.  

  4.3.1  Redmine IDs shall be the primary key.
  
4.4  A document shall be produced that shows the mapping from CQ attributes to
  Redmine attributes.
  
4.5  User accounts shall be created in Redmine for CQ users that do not yet
  exist.
 
  4.5.1  Accounts created for CQ users that are not active users in Redmine
  shall be flagged as inactive in Redmine.


5. Analysis
-----------
#### 5.1  Redmine import plug-ins
The redmine_importer and RedmineIssueImporter plug-ins were both investigated.
Both packages had their bugs and were only well suited very small and specific
imports.

#### 5.2  MySQL Workbench
The MySQL Workbench tool can be used to connect to a MySQL server using a
SSH tunnel.  Once connected, the CSV files can be loaded and written directly
to a table in the database.


6. Work Required
----------------
6.1  Filter out the SMA QA BridgePoint items from the CQ records in
  [DefectDBMainDump.csv [2.4]][ref4].  

6.2  Analyze data to be imported and create a document mapping CQ to Redmine
  fields.

6.3  Create custom fields in Redmine for CQ fields that cannot be mapped.

6.3  Test Redmine import plug-ins discussed in 5.1 and select a method of
  import.

6.3  Perform the import on the Redmine staging server.


7. Acceptance Test
------------------
7.1  Verify the number of issues created is equal to the number imported.

7.2  Verify CQ notes are imported into the correct Redmine issue. 

7.3  

End
---
