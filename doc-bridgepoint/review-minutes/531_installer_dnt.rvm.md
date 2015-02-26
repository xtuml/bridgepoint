---

This work is licensed under the Creative Commons CC0 License

---

# Create new installer for BridgePoint Pro
### xtUML Project Review Minutes

Reviewed:  https://github.com/leviathan747/bridgepoint/blob/master/doc-bridgepoint/notes/531_installer/531_installer.dnt.md e67e0c1   
Present:  Bob, Levi, Keith, Cort

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   BM   6.1      Clarify how the differing versions of Windows and Linux eclipse bases are packaged
_- 02  min   BM   6.3, #3  Don't put "etc." in the task list.  Call out all sub-tasks.  Clarify windows vs linux tasks.
_- 03  min   BM   6.3, #2  Call out exactly which files are touched by path replacements on Windows and Linux
_- 04  min   KB   6.3      We do not want to use dos2unix, we have a "tr" command that does the same thing that is safer
_- 05  min   CS   6.3      We will not do in-place editing with sed, tr, etc.  We will redirect to a temp ouput file
  in the command.
_- 06  min   BM   6.4.2    Change the path separator to / for Windows.
_- 07  min   KB   7        Note the status and future plans for the wrapping of the installer JAR into a runnable EXE
_- 08  min   BM   6.4      typo on word "panel"

</pre>
   
No major observations, a re-review is not required.
(or... Major observations were recorded, a re-review is required.)


End
---
