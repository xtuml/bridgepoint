HOWTO Update MIMIC/MIP Installer Creation Tools
------------------------------------------------

### References
[1] http://inside.mentorg.com/it/global-help-desk/software/mentor-install-releases  


### Instructions
- Log into svr-azt-eng-03
- Go to Mentor Install Release page [1]
- Under "Current Release", click the MIMIC versioned release link
- Under "Location of Build", click the MIMIC versioned release link
- Download the "ixn" file
- Click the back button in your browser
- Click the "MIB AddOn" link under "Location of Build"
- Download the "AddOn" for the version of MIMIC you just downloaded
  - NOTE: If the link doesn't work, you can try to get it from this address: http://people.wv.mentorg.com/project/install/release/MIP/
- IMPORTANT: If this is a minor release of MIP/MIMIC, we probably want to just extract the update on top of the existing files and
  directories in C:\MIMIC\rlstools.  The "danger" is that if the update removed any files, then those will be left intact
  in our working copy.  However, since it is a minor version, we are likely only updating a few files and thus we will 
  accept this possibility.  If this is a major release of MIP/MIMIC, we probably want to consider clearing out the data 
  stored in SVN and then putting the new data down.  By deleting the files inside eclipse and committing the changed MIMIC
  project with the files removed, they will still be around in the SVN repository with old tags to pull them back out.  If
  this step is done, Delete all subfolders EXCEPT TK_Director\ and TK_Libraries\.  Leave these in place.  These contain 
  BridgePoint-specific pieces of the installer.
- Navigate to the folder containing the new archive files that were just downloaded
- Extract the mimic zipfile to this directory, this will create a "rlstools/" folder as a sibling to the zipfile
- Now extract the MIB-AddOn archive to this directory.  This may require two "unzip" operations.  In the end this will
  add the additional files to the local rlstools/ folder.  
- Open the rlstools\ folder, select all the contents and "Copy"
- Open c:\MIMIC\rlstools\
- "Paste" the contents from the clipboard to update the files from the update location  
- Start BP on workspace c:\workspaces\build_artifacts
- Refresh the MIMIC project to pick up the changes
- Commit the changes to SVN
