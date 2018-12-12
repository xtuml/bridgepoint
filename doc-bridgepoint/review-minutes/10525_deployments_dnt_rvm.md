---

This work is licensed under the Creative Commons CC0 License

---

# Implement deployments
### xtUML Project Review Minutes

Reviewed:  https://github.com/leviathan747/bridgepoint/blob/10525_deployments/doc-bridgepoint/notes/10525_deployments/10525_deployments_dnt.md  2ab63f6   
Present:  Cort, Keith, Levi, Bob   

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   KB   general  What about OAL users?  Shall we just leave the element there? 
  * Do we leave exception on the palette? Yes.  It even has its own tray.
_- 02  min   KB   Documentation   Update Palette and Context Menu customization doc, in the "Pallette and New>" section
_- 03  min   KB   Documentation   This is a metamodel change.  Update the welcome metamodel project
x- 04  min   KB   general   Is deployment properly configured so that it can be hidden with the ini customization?
  * YES
x- 05  min   KB   general   stream and file_io pei sql files updated?
  * YES, I did export/import using single file and also copy/paste
_- 06  min   KB   6.2.1, 6.2.2 : What is the name of the new CME and where does it live?
  * "Import from component..."
  * "Import from file"
  * Don't love the name.  "Import terminators from component"?  Too long?
  * Both should have "..." at end
x- 07  min   KB   6.2.3  "MASLtype is created", imported so it has the same UUID as the one in Globals? Does it matter if the UUID is the same?
  * NO.  Doesn't matter what the UUID is.
_- 08  min   KB   6.5  First sentence needs more explanation.  We already have convert and import.  
_- 09  maj   KB   general   TODOs
x- 10  maj   KB   6.5  I think it is worth asking Tower now if they are OK with abandoning the old  project way right now.  Then we make a clean switch in one swoop.  Maybe they don't care about any of the projects they have created...
  * Decison was made to stick with what we have and not rip anything out now
_- 11  min   KB  Update example GPS Watch (MASL) to use deployments instead of old project way
_- 12  min   BM   6.1   Move "defines" role phrase closer to the line it is associated with
_- 13  min   KB   6.1.2  Missing ending period on last sentence
_- 14  min   BM   6.2.1  Strike the sentences that talk about errors related to invalid port names
_- 15  min   LS   6.2.3  Unbalanced tic types on UI::GoalCriteria
_- 16  min   BM   6.2.4  Add note to the Design Comments that we did the work to automatically add dependencies and this was not part of the original requirements.
_- 17  min   BM   6.5    Link the issue for the rest of the work (or create it if needed)
</pre>
   
Major observations were recorded, a re-review is required.


Notes from running the tool
-------------------------------
* When I went to import from component there is nothing in the list.  I had not turned on IPRs for the project.  
  * I wonder if we should automatically turn on IPRs for the project when the user creates a deployment?  Or at least pop a dialog and ask if IPRs should be turned on when "Import from component" is run.
  * The title of the dialog is "Import from component Selection".  This is odd.
  
* The import from file appears to only support ".int" but the requirements say ".mod" should be supported as well.

* The tool allows me to create multiple deployments in the same package.
  * Should we restrict this?  Should the user even be allowed to create multiple
    
* "Export MASL Domains" is available on a package that has a deployment.  Should this be allowed here?
  * Seem like it should not be allowed to me.
  * What about deployments in the same _project_?
    
* As a usability improvement, I think "Delete stale service(s)" should be available on the context menu of a single stale service.  Or even just make "Delete" available.

End
---
