---

This work is licensed under the Creative Commons CC0 License

---

# CLI updates 2
### xtUML Project Implementation Note

1. Abstract
-----------
This work builds on work done as part of [[2.2]](#2.2). Updates were needed to
make the `build_masl` script work with the "Launch" feature. The scripts needed
to be updated for robustness

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9398 Extend CLI to be able to run multiple commands in a single instance of Eclipse](https://support.onefact.net/issues/9398)  
<a id="2.2"></a>2.2 [#9398 implementation note](9398_cli_int.md)  

3. Background
-------------
None

4. Requirements
---------------
4.1 CLI shall be able to import whole projects and build without restarting in
between  
4.2 CLI shall be able to set workspace preferences at launch  

4.3 The CLI port file shall be moved into the workspace (and not in the
installation)  
4.4 The CLI and round trip scripts shall be updated to support more robust
failure on keyboard interrupt  

5. Work Required
----------------

5.1 The `ImportExecutor.importProject` method was updated to set the project
description (in memory version of the `.project` file) after the files are
copied into place. We were not doing this before, and the side effect was that
the project appeared to have no builders, so a subsequent build without a
restart failed. Since we now refresh the project description, we don't have this
problem anymore  

5.2 Minor refactoring of `ImportExecutor` to separate shared functionality into
its own method  

5.3 Added `BPCLIWorkbenchAdvisor.setWorkspacePreferences` method. This method
takes a map of preference keys and values and sets the workspace preferences.
Added `-workspacePreferences` command line option to each of the five
applications. Usage is as follows:
```
CLI.sh <command> -workspacePreferences "<pref1>=<value1>,<pref2>=<value2>"
```

5.4 Moved the `CLI_PORT.txt` file from the install directory to the workspace
and renamed it to be `.cli_info`. It is better suited in the workspace.
Workspaces are 1 to 1 with running Eclipse instances, so this is a natural place
for it to be and it allows multiple command line instances to be running at the
same time on separate workspaces.  

5.5 Change `launch-cli.py` and `CLI.sh` to put the PID of the BridgePoint
process in the `.cli_info` file. Added "abort" command which kills the process
and removes the `.cli_info` file  

5.6 Change `launch-cli.py` to handle keyboard interrupt on initial launch
(before the `.cli_info` file is in place) by killing the BridgePoint process  

5.7 Added "trap" command in the `masl_round_trip` and `regression_test` to call
the CLI abort and exit immediately for keyboard interrupts  

5.8 Added "checkRunning" command used in `masl_round_trip` to check if an
instance is already running on the workspace  

5.9 Updated `build_masl` script to leverage the new Launch feature  

6. Implementation Comments
--------------------------
None

7. Unit Test
------------

7.1 Run the regression test  
7.2 Run `build_masl`  


8. User Documentation
---------------------
Documented in the script usage

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 9398_cli2  

<pre>

 releng/org.xtuml.bp.mctools/pom.xml                                  |  2 ++
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/BPCLIWorkbenchAdvisor.java | 55 ++++++++++++++++++++++++++++++++++++++++++++++++++++++-
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/Build.java                 |  4 ++++
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/Execute.java               |  4 ++++
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/Import.java                |  4 ++++
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/ImportExecutor.java        | 58 ++++++++++++++++++++++++++++++++--------------------------
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/Launch.java                |  4 ++++
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/Merge.java                 |  4 ++++
 8 files changed, 108 insertions(+), 27 deletions(-)

</pre>

Fork/Repository: leviathan747/mc  
Branch: 9398_cli2  

<pre>

 bin/CLI.sh          | 44 ++++++++++++++++++++++++++++++++------------
 bin/build_masl      | 42 +++++++++++++++++++++++++++++-------------
 bin/launch-cli.py   | 47 +++++++++++++++++++++++++++++------------------
 bin/masl_round_trip | 14 ++++++++++++--
 4 files changed, 102 insertions(+), 45 deletions(-)

</pre>

Fork/Repository: leviathan747/models  
Branch: 9398_cli2  

<pre>

 masl/test/regression_test | 9 ++++++++-
 1 file changed, 8 insertions(+), 1 deletion(-)

</pre>

End
---

