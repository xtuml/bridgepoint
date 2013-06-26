File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.3 $
Modified:  $Date: 2004/12/28 21:50:17 $

Getting plug-in test-logging running in your Eclipse installation
-----------------------------------------------------------------
- right click on com.mentor.nucleus.bp.test in the package explorer
- select "Export..."
- select "Deployable plug-ins and fragments" for the export destination
- choose "a directory structure" for "Deploy as"
- check "Include source code"
- enter your Eclipse top-level folder as the destination directory
- click "Finish"
- restart Eclipse for the plug-in to get loaded 

Results will be placed in the top-level Eclipse folder in an HTML file 
whose name will be derived from the test package being run.  

End
---

$Log: Test_Readme.txt,v $
Revision 1.3  2004/12/28 21:50:17  campbell
Job: 434
Updating directions for log formatter deployment.

Revision 1.2  2004/12/21 18:03:26  greg
Job: 434
Introduce into HEAD

Revision 1.1.4.5  2004/12/21 16:41:21  jmather
Job: 434
Changed instructions for getting logging working to align with the Eclipse-standard way of deploying plug-ins.

Revision 1.1.4.4  2004/12/21 15:29:16  jmather
Job: 434
Added a note about restarting Eclipse to get JAR modifications to take effect.

Revision 1.1.4.3  2004/12/21 15:28:01  jmather
Job: 434
Added instructions for rebuilding the test.jar file.

Revision 1.1.4.2  2004/12/14 16:37:44  jmather
Job: 434
Added standard header and footer
.

