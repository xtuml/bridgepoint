---

This work is licensed under the Creative Commons CC0 License

---

# Fix CLI Launchers for Mars
### xtUML Project Implementation Note


1. Abstract
-----------
With the recent move to Eclipse Mars, some configuration changes are required.

2. Document References
----------------------
[1] [BridgePoint DEI #8023](https://support.onefact.net/redmine/issues/8023)  

3. Background
-------------
BridgePoint uses "launcher" scripts instead of having the user start eclipse.exe directly. These
scripts exist for both Windows and Linux and for both running the GUI as well as running BridgePoint
functionality from the command line.  The launcher scripts perform some extra checks and configuration of the
runtime environment before eclipse is launched.  

4. Requirements
---------------
4.1  BridgePoint launcher scripts that explicitly reference eclipse plugins shall use the correct version information
for eclipse Mars.  
4.2  Java8 deprecated the MaxPermSize virtual machine argument, the BridgePoint launcher scripts shall not pass that 
parameter any longer.  

5. Work Required
----------------
5.1. Change ```CLI.sh``` and ```CLI.bat``` to use the eclipse launcher plugin ```org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar```     
5.2. Change ```CLI.sh``` and ```CLI.bat``` to not pass ```MaxPermSize``` to the java vm on invocation.   

6. Implementation Comments
--------------------------
None.

7. Unit Test
------------
7.1  Manual test
  * Run ```./CLI.sh Build -help``` and ```CLI.bat Build -help```   
  *  __R__ The help instructions are output   
  
8. Code Changes
---------------
Branch name: 8023_launcher_fixes   
Repository: bridgepoint   

<pre>

src/Installer/CLI.bat
src/Installer/CLI.sh

</pre>

End
---

