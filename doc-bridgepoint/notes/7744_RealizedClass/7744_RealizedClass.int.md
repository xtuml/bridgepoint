---

This work is licensed under the Creative Commons CC0 License

---

# Unable to relaunch when using inter-project referred EE
### xtUML Project Implementation Note

1. Abstract
-----------
When a project that uses a shared realized external entity by inter-project
is terminated in verifier. Java LinkingError exception is thrown when re-execute 
this model in verifier and invoke the shared external entity.  

2. Document References
----------------------
[1] [BridgePoint DEI #7744] (https://support.onefact.net/redmine/issues/7744)  
[2] [Design Note](https://github.com/nmohamad/bridgepoint/blob/master/doc-bridgepoint/notes/7744_RealizedClass/7744_RealizedClass.dnt.md)  

3. Background
-------------
See [2]

4. Requirements
---------------
See [2]

5. Work Required
----------------
5.1 In BPDebugTarget.terminate() and BPThread.resetClassLoader(), check for the
	Active executing targets, if there is no executing target, clear VM_c.vmclMap
	and BPClassLoader.definitions.
	
5.2 If the terminated project contains a defined realized class, print a 
	warning message if the class definition will not be deleted due to 
	remaining running projects in verifier.

6. Implementation Comments
--------------------------
None.

7. Unit Test
------------
7.1 See [2]
7.2 Verifier Test Suite 1 & 2 shall pass.

8. Code Changes
---------------
Branch name: <7744_realized_relaunch_failure>  bridgepoint repo

<pre>



</pre>

Branch name: <7744_realized_relaunch_failure>  models repo

End
---

