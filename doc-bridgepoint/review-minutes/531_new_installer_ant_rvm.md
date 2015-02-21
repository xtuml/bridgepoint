---

This work is licensed under the Creative Commons CC0 License

---

# Create New Installer for BridgePoint Pro
### xtUML Project Review Minutes

Reviewed:  https://github.com/leviathan747/bridgepoint/blob/master/doc-bridgepoint/notes/531_installer/531_installer.ant.md 858f99a   
Present:  Cort, Bob, Levi, Keith

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   BM   general  Update the copyright statement to CC0
_- 02  min   CS   4.1      Remove comma after "Windows,"
_- 03  min   BM   4.1.1    Strike this requirement
_- 04  min   KB   4.2      "droppins" > "dropins"
_- 05  min   BM   4.3      Add 4.3.1, as part of this issue, a list of the work done by the installer scripts shall be recorded
_- 06  min   KB   5.1.1    "executalbe" > "executable"
_- 07  min   CS   5.1.1    We need to consider how to handle cases where the system does not already have Java installed
_- 08  min   LS   5.1.4.1  Note that IzPack supports added customizations via HTML
_- 09  min   BM   5.2      add detail
_- 10  min   KB   7.1      "Bridgpoint" > "BridgePoint"
_- 11  min   BM   5.3      add section about what eclipse itself does
_- 12  min   BM   5.4      investigate linux package managers

installer:
x- 13 min CS/KB   The IzPack in the branding in the installer is acceptable
_- 14 min CS      The target folder should default to use $HOME
_- 15 min CS      The root folder on linux is $HOME/xtuml
_- 16 min CS      The root folder on Windows is c:/xtuml
_- 17 min CS      Show the release notes by "executing" the ReleaseNote.htm file from the installation just done
_- 18 min CS      Want to have a non-interactive command-line mode (phase 2)
_- 19 min KB      In the IzPack installer, the windows post-install script will not run the MSVC redist installer

</pre>
     
No major observations, a re-review is not required.

End
---
