<pre>
========================================================================

(c) Copyright 2007-2012 by Mentor Graphics Corp. All rights reserved.

========================================================================
This document contains information proprietary and confidential to
Mentor Graphics Corp., and is not for external distribution.
========================================================================
BridgePoint Project Checklist
Promoting an Issue

Abstract
--------
This procedure is used by a reviewer or a manager when promoting an issue.
Promoting an issue is defined as changing the status of the issue from either
assigned to fixed or from fixed to closed.

Document References
-------------------
[1] Documentation/internal/process/process.txt
[2] Documentation/internal/process/Procedures/reviewer_procedures.txt
[3] Documentation/internal/process/templates/checklists/release.chk

Tag Format
----------
1. Format of the promotion tag.
    1.1 The tag for this promotion is: chgset-dts0100931135

Checklist
---------
x- Verify that all required process documentation exists for this issue.  That
   is: analysis note, design note, implementation note, review minutes, and
   checklists.  Remember that, depending on the complexity of the issue, some
   notes are not required.

x- From the Navigator View select the project you are promoting.  Right-click,
   select "Replace With" and choose the branch you are promoting the changes
   into.

x- Right-click the project you are promoting and select "Team->Merge".

x- Select the starting point for the merge.
   The starting point of the merge is the version that was created when the
   branch was created.  This will be named "Root-Review-<issue number>".  If
   you do not see the branch listed, then you need then you'll need to
   "Configure Tags".  For instructions on Configuring Tags see [3].

x- Select where you want to merge changes from.
   This will be named "Review-<issue number>"

x- Go the Team Synchronizing perspective and verify that all files listed as
   modified are listed in the implementation note "Code Changes" section.

x- In the Synchronize view look through the modified files.  When you are
   satisfied that the changes are correct select "Merge".

x- At this point, in the Navigator view you will see the changed files marked as
   dirty on your machine.

x- Once again verify that these are the correct files (according to the
   implementation note).

p- Run all the BridgePoint JUnit tests and any extra tests specified in the
   development notes.
    NOTE: Only ran manual test specified in the design note.
    
x- Commit the dirty files.

x- Fill in the tag entry above (Tag Format 1.1)

x- Copy the tag to the clipboard.

x- Tag each of the committed files with the tag in the clipboard.  Note that you
   can get the names of the files from the implementation note.

x- Log into ClearQuest and enter the defect number to go to the issue.

x- Precheck the next two steps and check in this file under
   documentation/checklists/<release_id>/i<issue number>-promotion-01.chk

x- Select "Change State->Resolve"

  _- On the Resolution tab, fill in the fields as follows:
    _- Resolution = Fixed
    _- Fixed Version = <the target release version> (Should be the current
                       version of the plugin)
    _- Resolution Note:
      _- Summary = <none required>
      _- Note = Changes look good. Tagged with: <tag from clipboard>
      
  _- On the Customer tab:
    _- Check if a customer service request is specified.  If there is no SR, 
       the following steps may be skipped.
    _- If an SR is specified, enter a Customer note:
      _- Summary = <none required>
      _- Note = :   
   The issue has been resolved and is schedule for release in  the next version.

x- Click "Save"

End
---

</pre>