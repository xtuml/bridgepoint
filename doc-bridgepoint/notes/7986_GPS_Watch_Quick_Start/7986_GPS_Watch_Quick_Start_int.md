---

This work is licensed under the Creative Commons CC0 License

---

# Ready GPS Watch for Quick Start
### xtUML Project Implementation Note

### 1. Abstract
-----------
An implementation of the GPS Watch model that allows for configuration management outside of the xtuml/bridgepoint repository has been implemented.

### 2. Document References
----------------------
[1] [BridgePoint DEI #7986](https://support.onefact.net/redmine/issues/7986)  
[2] [BridgePoint DEI #7927](https://support.onefact.net/redmine/issues/7927)  
[3] [Analysis Note](https://github.com/xtuml/bridgepoint/doc-bridgepoint/notes/7986_GPS_Watch_Quick_Start/7986_GPS_Watch_Quick_Start_ant.md)  
[4] [Design Note](https://github.com/xtuml/bridgepoint/doc-bridgepoint/notes/7986_GPS_Watch_Quick_Start/7986_GPS_Watch_Quick_Start_dnt.md)  

### 3. Background
-------------
[4] describes the design decisions that direct this implementation.

Examination of the existing code showed that it was very much directed at only importing existing projects that were in a directory structure or contained in a single xtuml file, not in an archive file.

### 4. Requirements
---------------
see [3]

### 5. Work Required
----------------
1. Deleted the GPS Watch projects from xtuml/bridgepoint/src/org.xtuml.bp.welcome/models/ and xtuml/models/test/.
2. Added GPS Watch.zip to xtuml/bridgepoint/src/org.xtuml.bp.welcome/models/.
3. Modified xtuml/bridgepoint/src/org.xtuml.bp.welcome/introContent.xml to set SingleFileModel=zip for the GPS Watch model.
4. Updated xtuml/bridgepoint/src/org.xtuml.bp.welcome/src/org/xtuml/bp/welcome/gettingstarted/SampleProjectGettingStartedAction.java method, setupProject, to use the value of SingleFileModel as a string instead of a boolean.
  1. SingleFileModel = "true" path stays the same.
  2. SingleFileModel <> "true" path checks for SingleFileModel <> "false" before calling resolvePath. If not "false", the value of "." + SingleFileModel is appended to modelName, which makes it a fully qualified path to a file instead of a directory.
5. Updated xtuml/bridgepoint/src/org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/ui/ProjectUtilities.java method, setRootFolderOptionsInternal.
  1. Tests the model path for being a directory or file. The result of the decision is used to setup the import wizard for a project or an archive.
  2. Copied the Combo logic from the "Select root directory" case into the "Select archive file" case.
  3. Disable "Select root directory" if a file and enable "Select archive file".
  4. Disable "Select archive file" if a directory and enable "Select root directory".
6. Updated xtuml/bridgepoint/src/org.xtuml.bp.welcome.test/src/org/xtuml/bp/welcome/test/WelcomePageTestGPS.java use of SingleFileModel to reflect source code update.

### 6. Implementation Comments
--------------------------
1. On 5.3, SingleFileModel was originally designed to be only "true" or "false". The value was then used to set a boolean variable. This was changed to use the value as a string, which allows any archive supported by the Eclipse import facility to be specified.

### 7. Unit Test
------------
Existing unit tests are sufficient to test new functionality.

### 8. Code Changes
---------------
Branch name: bridgepoint/7986_GPS_Watch_Quick_Start on fork lwriemen
Branch name: models/7986_GPS_Watch_Quick_Start on fork lwriemen

<pre>

	modified:   bridgepoint/src/org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/ui/ProjectUtilities.java
	modified:   bridgepoint/src/org.xtuml.bp.welcome/introContent.xml
	new file:   bridgepoint/src/org.xtuml.bp.welcome/models/GPS Watch.zip
	deleted:    bridgepoint/src/org.xtuml.bp.welcome/models/GPS Watch
   modified:   bridgepoint/src/org.xtuml.bp.welcome/src/org/xtuml/bp/welcome/gettingstarted/SampleProjectGettingStartedAction.java
   modified:   bridgepoint/src/org.xtuml.bp.welcome.test/src/org/xtuml/bp/welcome/test/WelcomePageTestGPS.java

   deleted:    models/test/GPS Watch

</pre>

End
---
