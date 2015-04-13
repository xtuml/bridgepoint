# HOWTO-update-the-BP-version-number.txt


BridgePoint Technical Note
HOWTO Update the BridgePoint Version Number


Abstract
--------
This note describes the steps to be performed in order to update or "bump"
the version number of BridgePoint.

History
-------
None.

Document References
-------------------
None

Background
----------
It is the policy of the BridgePoint development team to update the version
number of BridgePoint with each external delivery of the code.  This mostly
involves updating the plug-in version numbers.

Intended Audience
-----------------
This document is intended for the BridgePoint developers.

Steps
-----

  - Turn off Build Automatically if it is turned on
  
  - Update your development workspace with the latest version from github master
   of all plug-in and support projects (e.g. installer, utilities, mc, etc...)
   
  - Create an issue in github for the version bump.
  
  - Branch the xtuml/bridgepoint repository.

  - Edit bp.internal.tools/update_BP_version.xml to contain the new version and
   commit this to the issue used for the version bump.
  - Right-click update_BP_version.xml, select "Run As > Ant Build"

  - Go to the bp.doc plug-in and search under that plug-in for the old version 
   number(example: 1.5.1)
  RESULT - Several html files are found

  - Copy the new version (ex 1.5.2)  to the Windows Clipboard

  - Work through the Search view in Eclipse and for each match found open the 
   file in MS Word and update the version using MS Word.
   
       IMPORTANT: 
         1) Do not update any HTML file versions inside eclipse.  Navigate
            to the file on disk and edit the file with MS Word.
         2) The Release Notes and What's New files should be written with 
            MS Word "Save As..." as type "Web Page, Filtered"
                  
  - Select all projects in the workspace, refresh them to pick up the changes

  R Version update is now complete, all files that must be modified are now
   "dirty" in your workspace.   

  - Use "Team > Synchronize Workspace" to compare the locally changed projects 
   with the root of the branch.  Use the code change scraper to get the list of 
   changed files.  

  - Commit and push the files changed with this work.  Record the changed files
   list in the commit message.  As always, commit against the issue for this
   task.

  - Create a pull request for the branch.

  - Merge the pull request.


