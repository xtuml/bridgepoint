---

This work is licensed under the Creative Commons CC0 License

---

# Create new installer for BridgePoint Pro
### xtUML Project Review Minutes

Reviewed:  Installer code   
Present:  Bob, Levi, Keith   

General
-----------
<pre>
-- Num Type  Who  Comment
_- 01  min   BM   Move IzPack into xtuml/packaging/build folder
_- 02  min   BM   Get rid of extras/, move data up into the installer/ parent
_- 03  min   BM   Remove staging/ folder
_- 04  min   BM   Change build.sh to pass parameters: 
	- the folder where IzPack lives
	- the staging folder
	- The output folder
_- 05  min   KB   Move installer/windows/install.xml to installer/install_windows.xml
_- 06  min   KB   Move installer/linx/install.xml to installer/install_linux.xml
</pre>

Windows install.xml
-----------
<pre>
-- Num Type  Who  Comment
_- 01  min   BM   We'll need to update the BP version to 4.2.1
_- 02  min   BM   Remove authors
_- 03  min   BM   Paths will have to be updated to account for file moves from the general changes
_- 04  min   BM   In header, remove "sample" text and put the Creative commons notification
_- 05  min   LS   There is additional work here to do what the linux install.xml does to
  handle some functionality that used to be in the post-install.

</pre>


Linux install.xml
-----------
<pre>
-- Num Type  Who  Comment
_- 01  min   BM   We'll need to update the BP version to 4.2.1
_- 02  min   BM   Remove authors
_- 03  min   BM   Paths will have to be updated to account for file moves from the general changes
_- 04  min   BM   In header, remove "sample" text and put the Creative commons notification
</pre>
   
No major observations, a re-review is not required.


End
---
