---

This work is licensed under the Creative Commons CC0 License

---

# Import Mentor CQ data into Redmine
### xtUML Project Analysis Note

1. Abstract
-----------
ClearQuest (CQ) issues created during the time at Mentor Graphics need to be
imported into the One Fact Redmine issue tracker.

2. Document References
----------------------
[1] [1F Redmine issue 41, Import ClearQuest data](https://support.onefact.net/redmine/issues/41)  
[2] [Preceeding analysis note](https://github.com/xtuml/internal/blob/master/doc-internal/notes/41_import_cq_data.ant.md)  

3. Background
-------------
See preceding analysis note. 

4. Requirements
---------------
See preceding analysis note. 

5. Analysis
-----------
See preceding analysis note. 

6. Design
---------
6.1  Import the CQ issue data.  This makes heavy use of command line tools
found in csvkit.

6.1.1  Prepare the CQ issue data for import using command line tools.
```
  Convert the CSV dump to UTF-8 format using the XLS dump file.
    $  in2csv DefectDBMainDump.xls > MainDump.csv

  Cut the dump file into 3 files that will be joined together later.  When the
  long quoted description is included bad things happen so storing it in temp
  file makes the mapping easier.
    $  csvcut -c 1,5,9,10,12-19 MainDump.csv > non-redmine.csv
    $  csvcut -c 4,8,6,7 MainDump.csv > cols-to-map.csv
    $  csvcut -c 3,2,11 MainDump.csv > temp.csv

  Use an awk script to search and replace with the correct Redmine data.
    $  sh ./my-awk-script  > cols-mapped.csv

  Join all of the data.  Columns that will map into Redmine are at the end.
    $  csvjoin cols-mapped.csv temp.csv > mapped-issues.csv
    $  csvjoin non-redmine.csv mapped-issues.csv > all-rejoined.csv

  Add line number column (which also becomes the primary key for the cq_data table).
    $  csvcut -l all-rejoined.csv > ready-to-import.csv
```

6.1.2  Create a new table for the data from 6.1.1 and use MySQL Workbench to
import it.
```SQL
-- -----------------------------------------------------
-- Table `onefact_redmine`.`cq_data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onefact_redmine`.`cq_data` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cq_id` VARCHAR(13) NOT NULL,
  `severity` VARCHAR(10) NOT NULL,
  `product` VARCHAR(20) NOT NULL,
  `component` VARCHAR(21) NOT NULL,
  `associated_files` VARCHAR(717) NULL DEFAULT NULL,
  `CurrentCustomerPriority` VARCHAR(32) NULL DEFAULT NULL,
  `DeferredFrom` VARCHAR(32) NULL DEFAULT NULL,
  `InternalPlannedRelease` VARCHAR(16) NULL DEFAULT NULL,
  `GitTwinIssue` VARCHAR(89) NULL DEFAULT NULL,
  `resolution` VARCHAR(21) NULL DEFAULT NULL,
  `workaround` VARCHAR(3) NOT NULL,
  `version_fixed` VARCHAR(23) NULL DEFAULT NULL,
  `defect_type` INT(11) NOT NULL,
  `owner` INT(11) DEFAULT NULL,
  `priority` INT(11) NOT NULL,
  `submitter` INT(11) NOT NULL,
  `Submit_Date` VARCHAR(20) NOT NULL,
  `headline` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
```

6.1.3  Create the issues in Redmine with the `INSERT INTO` command.
```SQL
INSERT INTO `onefact_redmine`.`issues` ( 
  tracker_id, 
  project_id, 
  subject,
  description,
  status_id,
  assigned_to_id,
  priority_id,
  author_id,
  created_on,
  updated_on,
  root_id,
  lft,
  rgt
) 
SELECT
  defect_type, 
  '26', 
  headline,
  CONCAT_WS( "\n\n", cq_id, description ),
  '5',
  owner,
  priority,
  submitter,
  STR_TO_DATE( Submit_Date, '%c/%e/%y %h:%i %p' ),
  STR_TO_DATE( Submit_Date, '%c/%e/%y %h:%i %p' ),
  NULL,
  '1',
  '2'
FROM `onefact_redmine`.`cq_data`;

-- ----------------------------------------------------------------------------
-- Update the RedmineIssueID field with the primary issue key for Redmine.
-- ----------------------------------------------------------------------------
UPDATE `onefact_redmine`.`cq_data`
  SET `RedmineIssueID`=id - 1 + LAST_INSERT_ID();
```

6.2  Import the CQ notes data.
Uploading the notes to a temporary table and then inserting into the proper
Redmine table was the best way to handle the long quoted description fields.

6.2.1  Prepare the notes file for import using the csvkit.
```
  Convert the CSV dump to UTF-8 format using the XLS dump file.
    $  in2csv DefectDBNotes.xls > notesDump.csv

  Add line numbers to each row.
    $  csvcut -l notesDump.csv > notesDump-ready-to-import.csv 
```

6.2.2  Create a temporary table and import the data using MySQL Workbench.
```SQL
-- -----------------------------------------------------
-- Table `onefact.redmine`.`cq_notes_temp`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onefact_redmine`.`cq_notes_temp` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cq_id` VARCHAR(13) NOT NULL,
  `note_id` VARCHAR(22) NOT NULL,
  `note_type` VARCHAR(20) NOT NULL,
  `note_date` VARCHAR(20) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
```

6.2.3  Remove redundant data.  The rows with the `note_type` of "Original Description"
were already imported in section 6.1 and can safely be deleted.
```SQL
DELETE FROM `onefact_redmine`.`cq_notes_temp` WHERE `note_type`="Original Description";
```

6.2.4  Insert the notes to their corresponding Redmine issues with the following:
```SQL
INSERT INTO `onefact_redmine`.`journals` ( 
  journalized_id, 
  journalized_type, 
  notes,
  created_on
) 
SELECT
  cq_data.RedmineIssueID,
  'Issue',
  CONCAT_WS( ":\n", cq_notes_temp.note_type, cq_notes_temp.description ),
  STR_TO_DATE( cq_notes_temp.note_date, '%c/%e/%y %h:%i %p' )
FROM onefact_redmine.cq_notes_temp
LEFT JOIN onefact_redmine.cq_data
ON cq_notes_temp.cq_id=cq_data.cq_id ;
```

6.2.5  The temporary table can be dropped.
```SQL
DROP TABLE `onefact_redmine`.`cq_notes_temp`;
```


6.3  Import the CQ related-to data.

6.3.1  Create a temporary table and import the data.
```SQL
-- ---------------------------------------------------------------------------
-- Table `onefact.redmine`.`cq_relatedTo_temp`
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `onefact_redmine`.`cq_relatedTo_temp` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cq_id` VARCHAR(13) NOT NULL,
  `related_to_id` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
```

6.3.2  First pass.  Create entries in the journals table, this is the first
half of the related-to mapping.
```SQL
-- ---------------------------------------------------------------------------
-- Add a column to store the journal IDs created by the next step.
-- ---------------------------------------------------------------------------
ALTER TABLE onefact_redmine.cq_relatedTo_temp
  ADD journalID INT(11) AFTER cq_id;

-- ---------------------------------------------------------------------------
-- Insert rows into the journals table using the join to map dts# to redmine#
-- ---------------------------------------------------------------------------
INSERT INTO `onefact_redmine`.`journals` ( 
  journalized_id, 
  journalized_type,
  created_on
) 
SELECT
  cq_data.RedmineIssueID,
  'Issue',
  now()
FROM onefact_redmine.cq_relatedTo_temp
LEFT JOIN onefact_redmine.cq_data
ON cq_relatedTo_temp.cq_id=cq_data.cq_id ;

-- ----------------------------------------------------------------------------
-- Update the journalID field with the IDs just created in journals table.
-- ----------------------------------------------------------------------------
UPDATE `onefact_redmine`.`cq_relatedTo_temp`
  SET `journalID`=id - 1 + LAST_INSERT_ID();
```

6.3.3  Second pass.  Create the entry in the journal_details table, this is the
second half of the the related-to mapping.
```SQL
INSERT INTO `onefact_redmine`.`journal_details` ( 
  journal_id, 
  property,
  prop_key,
  value 
) 
SELECT
  cq_relatedTo_temp.journalID,
  'relation',
  'label_relates_to',
  cq_data.RedmineIssueID
FROM onefact_redmine.cq_relatedTo_temp
LEFT JOIN onefact_redmine.cq_data
ON cq_relatedTo_temp.related_to_id=cq_data.cq_id ;
```


7. Design Comments
------------------
7.1  There was a consensus decision to not pollute the Redmine install with
custom fields for old stale CQ data.  Therefore only the fields that mapped
directly were imported into Redmine.  The remainder of the data was imported
into a separate and newly created table within the Redmine schema.


8. Unit Test
------------
Outline all the unit tests that need to pass and describe the method that you
will use to design and perform the tests.

End
---

